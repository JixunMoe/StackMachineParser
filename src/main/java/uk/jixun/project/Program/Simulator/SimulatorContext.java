package uk.jixun.project.Program.Simulator;

import java.util.ArrayList;
import java.util.Stack;

public class SimulatorContext implements IExecutionContext {
  private ArrayList<Integer> memory;
  private Stack<Integer> stack;
  private int eip = 0;
  private int cycles = 0;

  public SimulatorContext() {
    memory = new ArrayList<>(256);
  }

  @Override
  public int read(int address) {
    return memory.get(address);
  }

  @Override
  public void write(int address, int value) {
    memory.set(address, value);
  }

  @Override
  public void setStack(int offset, int value) {
    stack.set(stack.size() - 1 - offset, value);
  }

  @Override
  public int getStack(int offset) {
    return stack.get(stack.size() - 1 - offset);
  }

  @Override
  public void push(int value) {
    stack.push(value);
  }

  @Override
  public int pop() {
    return stack.pop();
  }

  @Override
  public int getEip() {
    return eip;
  }

  @Override
  public void setEip(int eip) {
    if (eip >= 0) {
      this.eip = eip;
    }
  }

  @Override
  public void incEip() {
    eip ++;
  }

  @Override
  public int getCycles() {
    return cycles;
  }

  @Override
  public void nextCycle() {
    addCycles(1);
  }

  @Override
  public void addCycles(int cycles) {
    this.cycles += cycles;
  }
}
