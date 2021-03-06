package uk.jixun.project.OpCode.SysCall;

import uk.jixun.project.OpCode.SmRegStatus;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class AllocateSysCall extends AbstractSysCall {
  @Override
  public int getConsume() {
    return 2;
  }

  @Override
  public int getProduce() {
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
  public boolean isBranch() {
    return false;
  }

  @Override
  public boolean isWriteFlag() {
    return false;
  }

  @Override
  public boolean isReadFlag() {
    return false;
  }

  @Override
  public SmRegister getRegisterAccess() {
    return SmRegister.ALLOC_REGISTER;
  }

  @Override
  public SmRegStatus getRegisterStatus() {
    return SmRegStatus.WRITE;
  }

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    int returnAddress = stack.pop();
    ctx.setEip(returnAddress);

    int size = stack.pop();
    int address = ctx.getRegister(SmRegister.ALLOC_REGISTER).getAndAdd(size);
    stack.push(address);
  }

  @Override
  public String toString() {
    return "<SYS:ALLOCATE>";
  }
}
