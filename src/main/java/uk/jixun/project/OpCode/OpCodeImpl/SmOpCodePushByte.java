package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushByteAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushByte extends SmOpCodePushByteAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    stack.push(getInstruction().getOperand(0).resolve(ctx) & 0xFF);
  }

  @Override
  public String toAssembly() {
    return "PUSH";
  }
}
