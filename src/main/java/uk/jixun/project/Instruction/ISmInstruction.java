package uk.jixun.project.Instruction;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Program.ISmProgram;

import java.util.List;

public interface ISmInstruction {
  ISmOpCode getOpCode();

  /**
   * Get operand in this instruction at given index.
   *
   * @param index Index of instruction; start from 0.
   * @return Requested Operand
   * @throws OutOfRangeOperand When the given index is invalid, this exception will be thrown.
   */
  ISmOperand getOperand(int index) throws OutOfRangeOperand;

  long getLine();

  void setLine(long lineNumber);

  int getVirtualAddress();

  void setVirtualAddress(int address);

  /**
   * Set an operand; use {@link #setOperands(List)} if you are trying to assign many operands.
   *
   * @param index   Index to set
   * @param operand Operand to be set.
   * @throws OutOfRangeOperand When the given index is greater than existing operand size, this error will be thrown.
   */
  void setOperand(int index, ISmOperand operand) throws OutOfRangeOperand;

  /**
   * Sets all operands
   *
   * @param operands operands to be set as whole
   */
  void setOperands(List<ISmOperand> operands);

  /**
   * Get the number of operands in this instruction.
   *
   * @return Number of operands.
   */
  int getOperandCount();

  /**
   * Get the number of cycles required to complete this instruction.
   *
   * @return Cycles to complete this instruction.
   */
  int getCycleTime();

  /**
   * Convert instruction to assembly code representation (not byte code)
   *
   * @return Compiled assembly code.
   */
  String toAssembly();

  /**
   * Convert instruction to assembly code representation (not byte code)
   * @param prefix Size of the address in front of the code.
   * @return Compiled assembly code.
   */
  String toAssemblyWithAddress(int prefix);

  ISmProgram getProgram();

  void setProgram(ISmProgram program);

  boolean isBranch();

  boolean isMetaInst();

  boolean usesAlu();

  boolean readRam();

  boolean writeRam();

  boolean readOrWriteRam();

  int getEip();

  void setEip(int eip);

  /**
   * Check dependency for a given instruction.
   *
   * @param inst Instruction to check
   * @return check if instruction is depends on the given instruction.
   * @apiNote DO NOT USE! The result is not correct.
   */
  @Deprecated
  boolean depends(ISmInstruction inst);

  /**
   * Get instruction in original text format.
   * @return original stack machine assembly format.
   */
  String getStackAssembly();
}
