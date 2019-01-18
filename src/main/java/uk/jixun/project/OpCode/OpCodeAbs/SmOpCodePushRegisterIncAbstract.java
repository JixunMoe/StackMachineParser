package uk.jixun.project.OpCode.OpCodeAbs;



// !!!                               !!!
// !!!             STOP              !!!
// !!!                               !!!
//     DO NOT EDIT THIS FILE BY HAND    


// This file was generated using an automated script.
// See 'scripts' directory for more information



import uk.jixun.project.OpCode.AbstractBasicOpCode;
import uk.jixun.project.OpCode.SmOpCodeEnum;
import uk.jixun.project.Register.SmRegister;

public abstract class SmOpCodePushRegisterIncAbstract extends AbstractBasicOpCode {
  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.PUSH_REGISTER_INC;
  }

  @Override
  public String toAssembly() {
    
    if (getRegisterVariant() == SmRegister.XP) {
      return "![XP++]";
    }

    if (getRegisterVariant() == SmRegister.YP) {
      return "![YP++]";
    }

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
    if ((regVariant == SmRegister.XP) || (regVariant == SmRegister.YP)) {
      this.regVariant = regVariant;
    } else {
      throw new RuntimeException("Register variant " + regVariant.toString()
        + " is not allowed for opcode PUSH_REGISTER_INC");
    }
  }
}
