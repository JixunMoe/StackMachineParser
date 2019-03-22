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



public abstract class SmOpCodeInsertByteAbstract extends AbstractBasicOpCode {

  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.INSERT_BYTE;
  }

  @Override
  public boolean isBranch() {
    return false;
  }

  @Override
  public int getProduce() {
    return 0;
  }

  @Override
  public int getConsume() {
    return 0;
  }

  @Override
  public boolean readRam() {
    return false;
  }

  @Override
  public boolean writeRam() {
    return false;
  }

  @Override
  public boolean isStaticRamAddress() {
    return false;
  }

  @Override
  public int accessRamAddress() throws Exception {
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
  }
}
