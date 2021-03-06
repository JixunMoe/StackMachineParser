package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeTestNeqAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeTestNeq extends SmOpCodeTestNeqAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int param2 = stack.pop();
    int param1 = stack.pop();
    ctx.setJumpFlag(param1 != param2);
  }

  @Override
  public String toAssembly() {
    return "TEST: IF NOT EQUAL";
  }
}
