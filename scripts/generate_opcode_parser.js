const yaml = require('js-yaml')
const fs = require('fs')
const path = require('path')

const javaRoot = path.join(__dirname, '../src/main/java/uk/jixun/project/');

const doc = yaml.safeLoad(fs.readFileSync('./opcodes.yaml', 'utf8'))

const editWarning = `

// !!!                               !!!
// !!!             STOP              !!!
// !!!                               !!!
//     DO NOT EDIT THIS FILE BY HAND    


// This file was generated using an automated script.
// See 'scripts' directory for more information

`

function writeOpCodeAbstract(opcode) {
  let id = (typeof opcode === 'string') ? opcode : opcode.id;
  let cls = id.toLowerCase().replace(/(^|_)./g, z => z.toUpperCase().slice(-1));

  const absName = `SmOpCode${cls}Abstract`;
  const implName = `SmOpCode${cls}`;
  const absFile = path.join(javaRoot, `./OpCode/OpCodeAbs/${absName}.java`)
  const implFile = path.join(javaRoot, `./OpCode/OpCodeImpl/${implName}.java`)

  let code = `package uk.jixun.project.OpCode.OpCodeAbs;

${editWarning}

import uk.jixun.project.OpCode.AbstractBasicOpCode;
import uk.jixun.project.OpCode.SmOpCodeEnum;
import uk.jixun.project.Register.SmRegister;

public abstract class ${absName} extends AbstractBasicOpCode {
  @Override
  public SmOpCodeEnum getOpCode() {
    return SmOpCodeEnum.${id};
  }
`

  // reg variant
  if (opcode.reg) {
    code += `
  @Override
  public String toAssembly() {
    ${Object.keys(opcode.reg).map(reg => `
    if (getRegisterVariant() == SmRegister.${reg}) {
      return ${JSON.stringify(opcode.reg[reg])};
    }`).join('\n')}

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public void setVariant(int variant) {
    throw new RuntimeException("Variant does not apply for this opcode.");
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    if (${Object.keys(opcode.reg).map(reg => `regVariant != SmRegister.${reg}`).join(' && ')}) {
      this.regVariant = regVariant;
    }

    throw new RuntimeException("Register variant " + regVariant.toString()
      + " is not allowed for opcode ${id}");
  }`
  } else if (opcode.Varients) {
    code += `
  @Override
  public void setVariant(int variant) {
    if (${opcode.Varients.map(v => `variant != ${v}`).join(' && ')}) {
      this.variant = variant;
    }
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    throw new RuntimeException("RegisterVariant does not apply for this opcode.");
  }`
  } else {
    code += `
  @Override
  public void setVariant(int variant) {
    throw new RuntimeException("Variant does not apply for this opcode.");
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    throw new RuntimeException("RegisterVariant does not apply for this opcode.");
  }`
  }

  code += `
}`

  fs.writeFileSync(absFile, code, 'utf-8')

  if (!fs.existsSync(implFile)) {
    code = `
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.${absName};

public class ${implName} extends ${absName} {
  // TODO: Override any opcode specific methods here.
}`;
    fs.writeFileSync(implFile, code, 'utf-8')
  }
}




let code = `package uk.jixun.project.OpCode;

${editWarning}

import uk.jixun.project.Exceptions.UnknownOpCodeException;
import uk.jixun.project.Register.SmRegister;

public class SmOpcodeParser {
  public static ISmOpCode Parse(String opcode) throws UnknownOpCodeException {
    opcode = opcode.trim().toUpperCase();
    char lastChar = opcode.charAt(opcode.length() - 1);
`

doc.opcodes.forEach(opcode => {
  writeOpCodeAbstract(opcode);

  if (typeof opcode === 'string') {
    // OPCODE id and scan is same.
    code += `
    if ("${opcode.toUpperCase()}".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.${opcode});
    }`
  } else if (opcode.scan) {
    code += `
    if ("${opcode.scan.toUpperCase()}".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.${opcode.id});
    }`
  } else if (opcode.Varients) {
    code += `
    if (
      "${opcode.id.toUpperCase()}".equals(opcode.substring(0, opcode.length() - 1))
      && (${opcode.Varients.map(v => `lastChar == '${v}'`).join(' || ')})
    ) {
      return OpCodeFactory.create(SmOpCodeEnum.${opcode.id},
        Character.getNumericValue(lastChar));
    }`
  } else if (opcode.reg) {
    Object.keys(opcode.reg).forEach(reg => {
      const scan = opcode.reg[reg]
      code += `
    if ("${scan}".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.${opcode.id}, SmRegister.${reg});
    }`
    })
  }
})

code += `
    throw new UnknownOpCodeException(opcode);
  }
}
`

const target = path.join(javaRoot, './OpCode/SmOpcodeParser.java')
fs.writeFileSync(target, code, 'utf-8')
