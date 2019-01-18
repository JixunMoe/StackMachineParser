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

public abstract class SmOpCodeCopyAbstract extends AbstractBasicOpCode {
  @Override
  public SmOpCodeEnum getOpCode() {
    return SmOpCodeEnum.COPY;
  }

  @Override
  public void setVariant(int variant) {
    if ((variant == 1) || (variant == 2) || (variant == 3) || (variant == 4)) {
      this.variant = variant;
    }
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    if (regVariant != SmRegister.NONE) {
      throw new RuntimeException("RegisterVariant does not apply for this opcode.");
    }
  }
}
