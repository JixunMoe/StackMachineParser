package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePopFrameAddrAbstract;
import uk.jixun.project.OpCode.SmRegStatus;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePopFrameAddr extends SmOpCodePopFrameAddrAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx)
    throws OutOfRangeOperand {

    // pop [fp + n], val
    int fp = ctx.getRegister(SmRegister.FP).get();
    int offset = getInstruction().getOperand(0).resolve(ctx);
    int address = fp + offset;

    // pop data from stack to operand 1
    int value = stack.pop();
    ctx.write(address, value);
  }

  @Override
  public String toAssembly() {
    return "STORE  FP+";
  }

  @Override
  public boolean writeRam() {
    return true;
  }

  @Override
  public boolean isStaticRamAddress() {
    return false;
  }

  @Override
  public int resolveRamAddress(IExecutionContext ctx) throws Exception {
    return ctx.getRegister(SmRegister.FP).get()
      + getInstruction().getOperand(0).resolve(ctx);
  }

  @Override
  public SmRegister getRegisterAccess() {
    return SmRegister.FP;
  }

  @Override
  public SmRegStatus getRegisterStatus() {
    return SmRegStatus.READ;
  }
}
