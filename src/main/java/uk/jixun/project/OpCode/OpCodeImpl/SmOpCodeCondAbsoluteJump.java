package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeCondAbsoluteJumpAbstract;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeCondAbsoluteJump extends SmOpCodeCondAbsoluteJumpAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    if (!ctx.getJumpFlag()) {
      // Do nothing when jump flag is not set.
      return;
    }

    ISmOperand operand = null;
    try {
      operand = getInstruction().getOperand(0);
    } catch (OutOfRangeOperand outOfRangeOperand) {
      outOfRangeOperand.printStackTrace();
    }
    assert operand != null;
    int functionAddress = operand.resolve(ctx);
    logger.finest("Branch taken: " + functionAddress);
    ctx.setEip(functionAddress);
  }

  @Override
  public String toAssembly() {
    return "JMP?";
  }
}
