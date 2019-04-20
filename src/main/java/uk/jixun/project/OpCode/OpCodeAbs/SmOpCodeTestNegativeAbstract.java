package uk.jixun.project.OpCode.OpCodeAbs;



// !!!                               !!!
// !!!             STOP              !!!
// !!!                               !!!
//     DO NOT EDIT THIS FILE BY HAND    


// This file was generated using an automated script.
// See 'scripts' directory for more information



import uk.jixun.project.OpCode.*;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;



public abstract class SmOpCodeTestNegativeAbstract extends AbstractBasicOpCode {

  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.TEST_NEGATIVE;
  }

  @Override
  public boolean isBranch() {
    return false;
  }

  @Override
  public boolean isWriteFlag() {
    return true;
  }

  @Override
  public boolean isReadFlag() {
    return false;
  }

  @Override
  public int getProduce() {
    return 0;
  }

  @Override
  public int getConsume() {
    return 1;
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
  }
}
