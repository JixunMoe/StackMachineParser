
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeTestNegativeAbstract;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeTestNegative extends SmOpCodeTestNegativeAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int value = stack.pop();
    ctx.setJumpFlag(value < 0);
  }
}
