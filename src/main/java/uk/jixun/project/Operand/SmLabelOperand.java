package uk.jixun.project.Operand;

import uk.jixun.project.Exceptions.LabelNotFoundException;
import uk.jixun.project.Simulator.Context.IExecutionContext;

public class SmLabelOperand extends SmBasicOperandAbstract {
  private String label = "";

  @Override
  public SmOperandType getOperandType() {
    return SmOperandType.LABEL;
  }

  @Override
  public Object getValue() {
    throw new RuntimeException("Do not use setValue/getValue on SmLabelOperand.");
  }

  @Override
  public void setValue(Object val) {
    throw new RuntimeException("Do not use setValue/getValue on SmLabelOperand.");
  }

  @Override
  public int resolve(IExecutionContext ctx) {
    // Nothing to resolve.
    try {
      return (int) resolveAddress();
    } catch (LabelNotFoundException e) {
      return 0;
    }
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public long resolveAddress() throws LabelNotFoundException {
    return this.getInstruction().getProgram().resolveLabel(label);
  }

  @Override
  public String toAssembly() {
    String resolvedAddress;
    try {
      resolvedAddress = String.valueOf(resolveAddress());
    } catch (LabelNotFoundException ex) {
      resolvedAddress = "<UNKNOWN>";
    }

    return "<label(" + label + ": " + resolvedAddress + ")>";
  }
}
