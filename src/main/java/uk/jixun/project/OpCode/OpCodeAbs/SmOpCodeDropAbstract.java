package uk.jixun.project.OpCode.OpCodeAbs;



// !!!                               !!!
// !!!             STOP              !!!
// !!!                               !!!
//     DO NOT EDIT THIS FILE BY HAND    


// This file was generated using an automated script.
// See 'scripts' directory for more information



import uk.jixun.project.OpCode.AbstractBasicOpCode;
import uk.jixun.project.OpCode.SmOpCodeEnum;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.IExecutionContext;

import java.util.HashMap;

public abstract class SmOpCodeDropAbstract extends AbstractBasicOpCode {

  private static HashMap<Integer, Integer> mapConsume = new HashMap<>();
  private static HashMap<Integer, Integer> mapProduce = new HashMap<>();

  static {
    

    
      mapConsume.put(1, 1);
      mapProduce.put(1, 0);
    
      mapConsume.put(2, 2);
      mapProduce.put(2, 0);
    
      mapConsume.put(3, 3);
      mapProduce.put(3, 0);
    
      mapConsume.put(4, 4);
      mapProduce.put(4, 0);
    
  }

  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.DROP;
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
  public int getProduce() {
    return mapProduce.getOrDefault(getVariant(), 0);
  }

  @Override
  public int getConsume() {
    return mapConsume.getOrDefault(getVariant(), 0);
  }

  @Override
  public boolean readRam() {
    
    if (variant == 1) {
      return false;
    }

    if (variant == 2) {
      return false;
    }

    if (variant == 3) {
      return false;
    }

    if (variant == 4) {
      return false;
    }

    throw new RuntimeException("Unsupported variant for this opcode.");
  }

  @Override
  public boolean writeRam() {
    
    if (variant == 1) {
      return false;
    }

    if (variant == 2) {
      return false;
    }

    if (variant == 3) {
      return false;
    }

    if (variant == 4) {
      return false;
    }

    throw new RuntimeException("Unsupported variant for this opcode.");
  }

  @Override
  public boolean isStaticRamAddress() {
    
    if (variant == 1) {
      return false;
    }

    if (variant == 2) {
      return false;
    }

    if (variant == 3) {
      return false;
    }

    if (variant == 4) {
      return false;
    }

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public int resolveRamAddress(IExecutionContext ctx) throws Exception {
    
    if (variant == 1) {
      
    }

    if (variant == 2) {
      
    }

    if (variant == 3) {
      
    }

    if (variant == 4) {
      
    }

    throw new RuntimeException("Unknown ram access type.");
  }

  @Override
  public void setVariant(int variant) {
    if ((variant == 1) || (variant == 2) || (variant == 3) || (variant == 4)) {
      this.variant = variant;
    }
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    if (regVariant != SmRegister.NONE) {
      throw new RuntimeException("RegisterVariant does not apply for this opcode.");
    }
  }
}
