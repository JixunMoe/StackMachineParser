const yaml = require('js-yaml')
const fs = require('fs')
const path = require('path')

const javaRoot = path.join(__dirname, '../src/main/java/uk/jixun/project/');

const doc = yaml.safeLoad(fs.readFileSync('./opcodes.yaml', 'utf8'))

const classToLoad = [];

const editWarning = `

// !!!                               !!!
// !!!             STOP              !!!
// !!!                               !!!
//     DO NOT EDIT THIS FILE BY HAND    


// This file was generated using an automated script.
// See 'scripts' directory for more information

`

function b4s(b) {
  return (!!b).toString();
}

// scan can be either string or string[]
function getFirstScan(scan) {
  if (typeof scan === 'string') {
    return scan;
  }

  return scan[0];
}

function writeOpCodeAbstract(opcode) {
  let id = (typeof opcode === 'string') ? opcode : opcode.id;
  let cls = id.toLowerCase().replace(/(^|_)./g, z => z.toUpperCase().slice(-1));

  const absName = `SmOpCode${cls}Abstract`;
  const implName = `SmOpCode${cls}`;
  const absFile = path.join(javaRoot, `./OpCode/OpCodeAbs/${absName}.java`)
  const implFile = path.join(javaRoot, `./OpCode/OpCodeImpl/${implName}.java`)

  // if it contains variation, hash map needs to be imported.
  const importHashMap = !!(opcode.reg || opcode.Varients);

  let code = `package uk.jixun.project.OpCode.OpCodeAbs;

${editWarning}

import uk.jixun.project.OpCode.AbstractBasicOpCode;
import uk.jixun.project.OpCode.SmOpCodeEnum;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.IExecutionContext;

${!importHashMap ? '' : 'import java.util.HashMap;'}

public abstract class ${absName} extends AbstractBasicOpCode {
`

  if (opcode.reg || opcode.Varients) {
    const keyType = opcode.reg ? 'SmRegister' : 'Integer';

    code += `
  private static HashMap<${keyType}, Integer> mapConsume = new HashMap<>();
  private static HashMap<${keyType}, Integer> mapProduce = new HashMap<>();

  static {
    ${!opcode.reg ? "" : Object.keys(opcode.reg).map(reg => `
    mapConsume.put(SmRegister.${reg}, ${opcode.reg[reg].consume});
    mapProduce.put(SmRegister.${reg}, ${opcode.reg[reg].produce});
    `).join('')}

    ${!opcode.Varients ? "" : opcode.Varients.map(variant => `
      mapConsume.put(${variant.id}, ${variant.consume});
      mapProduce.put(${variant.id}, ${variant.produce});
    `).join('')}
  }
`;
  }


code += `
  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.${id};
  }

  @Override
  public boolean isBranch() {
    return ${b4s(opcode.branching)};
  }

  @Override
  public boolean isWriteFlag() {
    return ${b4s(opcode.flag === "write")};
  }

  @Override
  public boolean isReadFlag() {
    return ${b4s(opcode.flag === "read")};
  }
`

  // reg variant
  if (opcode.reg) {
    code += `
  @Override
  public int getProduce() {
    return mapProduce.getOrDefault(getRegisterVariant(), 0);
  }

  @Override
  public int getConsume() {
    return mapConsume.getOrDefault(getRegisterVariant(), 0);
  }

  @Override
  public boolean readRam() {
    ${Object.keys(opcode.reg).map(reg => `
    if (getRegisterVariant() == SmRegister.${reg}) {
      return ${b4s(opcode.reg[reg].ram === 'read')};
    }`).join('\n')}

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public boolean writeRam() {
    ${Object.keys(opcode.reg).map(reg => `
    if (getRegisterVariant() == SmRegister.${reg}) {
      return ${b4s(opcode.reg[reg].ram === 'write')};
    }`).join('\n')}

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public boolean isStaticRamAddress() {
    ${Object.keys(opcode.reg).map(reg => `
    if (getRegisterVariant() == SmRegister.${reg}) {
      return ${b4s(opcode.reg[reg].address === 'op1')};
    }`).join('\n')}

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public int resolveRamAddress(IExecutionContext ctx) throws Exception {
    ${Object.keys(opcode.reg).map(reg => `
    if (getRegisterVariant() == SmRegister.${reg}) {
      ${(opcode.reg[reg].address === 'op1') ?
        'return (int) getInstruction().getOperand(0).getValue();' :
        'throw new RuntimeException("Unknown ram access type");'
      }
    }`).join('\n')}

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public String toAssembly() {
    ${Object.keys(opcode.reg).map(reg => `
    if (getRegisterVariant() == SmRegister.${reg}) {
      return ${JSON.stringify(getFirstScan(opcode.reg[reg].scan))};
    }`).join('\n')}

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public void setVariant(int variant) {
    if (variant != 0) {
      throw new RuntimeException("Variant does not apply for this opcode.");
    }
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    if (${Object.keys(opcode.reg).map(reg => `(regVariant == SmRegister.${reg})`).join(' || ')}) {
      this.regVariant = regVariant;
    } else {
      throw new RuntimeException("Register variant " + regVariant.toString()
        + " is not allowed for opcode ${id}");
    }
  }`
  } else if (opcode.Varients) {
    code += `
  @Override
  public int getProduce() {
    return mapProduce.getOrDefault(getVariant(), 0);
  }

  @Override
  public int getConsume() {
    return mapConsume.getOrDefault(getVariant(), 0);
  }

  @Override
  public boolean readRam() {
    ${opcode.Varients.map(v => `
    if (variant == ${v.id}) {
      return ${v.ram === 'read'};
    }`).join('\n')}

    throw new RuntimeException("Unsupported variant for this opcode.");
  }

  @Override
  public boolean writeRam() {
    ${opcode.Varients.map(v => `
    if (variant == ${v.id}) {
      return ${b4s(v.ram === 'write')};
    }`).join('\n')}

    throw new RuntimeException("Unsupported variant for this opcode.");
  }

  @Override
  public boolean isStaticRamAddress() {
    ${opcode.Varients.map(v => `
    if (variant == ${v.id}) {
      return ${b4s(v.address === 'op1')};
    }`).join('\n')}

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public int resolveRamAddress(IExecutionContext ctx) throws Exception {
    ${opcode.Varients.map(v => `
    if (variant == ${v.id}) {
      ${(v.address === 'op1') ? 'return (int) getInstruction().getOperand(0).getValue();' : '' }
    }`).join('\n')}

    throw new RuntimeException("Unknown ram access type.");
  }

  @Override
  public void setVariant(int variant) {
    if (${opcode.Varients.map(v => `(variant == ${v.id})`).join(' || ')}) {
      this.variant = variant;
    }
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    if (regVariant != SmRegister.NONE) {
      throw new RuntimeException("RegisterVariant does not apply for this opcode.");
    }
  }`
  } else {
    code += `
  @Override
  public int getProduce() {
    return ${opcode.produce};
  }

  @Override
  public int getConsume() {
    return ${opcode.consume};
  }

  @Override
  public boolean readRam() {
    return ${b4s(opcode.ram === 'read')};
  }

  @Override
  public boolean writeRam() {
    return ${b4s(opcode.ram === 'write')};
  }

  @Override
  public boolean isStaticRamAddress() {
    return ${b4s(opcode.address === 'op1')};
  }

  @Override
  public int resolveRamAddress(IExecutionContext ctx) throws Exception {
    return (int) getInstruction().getOperand(0).getValue();
  }
  @Override
  public void setVariant(int variant) {
    if (variant != 0) {
      throw new RuntimeException("Variant does not apply for this opcode.");
    }
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    if (regVariant != SmRegister.NONE) {
      throw new RuntimeException("RegisterVariant does not apply for this opcode.");
    }
  }`
  }

  code += `
}
`

  fs.writeFileSync(absFile, code, 'utf-8')

  if (!fs.existsSync(implFile)) {
    code = `
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.${absName};
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class ${implName} extends ${absName} {
  // TODO: Override any opcode specific methods here.

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    throw new Exception("not implemented");
  }
}
`;
    fs.writeFileSync(implFile, code, 'utf-8')
  }

  classToLoad.push({ id, name: implName });
}


function matchScan(scan) {
  if (typeof scan === 'string') {
    scan = [scan];
  }

  return scan.map(s => `"${s.toUpperCase()}".equals(opcode)`).join(" || ");
}


let code = `package uk.jixun.project.OpCode;

${editWarning}

import uk.jixun.project.Exceptions.UnknownOpCodeException;
import uk.jixun.project.Register.SmRegister;

public class SmOpcodeParser {
  public static ISmOpCode parse(String opcode) throws UnknownOpCodeException {
    opcode = opcode.trim().toUpperCase();
    if (opcode.length() == 0) {
      return new SmNoOpCode();
    }
    char lastChar = opcode.charAt(opcode.length() - 1);
`

doc.opcodes.forEach(opcode => {
  writeOpCodeAbstract(opcode);

  if (typeof opcode === 'string') {
    // OPCODE id and scan is same.
    code += `
    if (${matchScan(opcode)}) {
      return OpCodeFactory.create(SmOpCodeEnum.${opcode});
    }`
  } else if (opcode.scan) {
    code += `
    if (${matchScan(opcode.scan)}) {
      return OpCodeFactory.create(SmOpCodeEnum.${opcode.id});
    }`
  } else if (opcode.Varients) {
    code += `
    if (
      "${opcode.id.toUpperCase()}".equals(opcode.substring(0, opcode.length() - 1))
      && (${opcode.Varients.map(v => `lastChar == '${v.id}'`).join(' || ')})
    ) {
      return OpCodeFactory.create(SmOpCodeEnum.${opcode.id},
        Character.getNumericValue(lastChar));
    }`
  } else if (opcode.reg) {
    Object.keys(opcode.reg).forEach(reg => {
      const scan = opcode.reg[reg].scan
      code += `
    if (${matchScan(scan)}) {
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

code = `package uk.jixun.project.OpCode;

${editWarning}

import uk.jixun.project.OpCode.OpCodeImpl.*;

class OpCodeRegistry {
  static void registerAll() {
    ${classToLoad.map(({ id, name }) => `
    OpCodeFactory.register(SmOpCodeEnum.${id}, ${name}.class);`).join("")}
  }
}
`

fs.writeFileSync(path.join(javaRoot, './OpCode/OpCodeRegistry.java'), code, 'utf-8')
