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
  public int getProduce() {
    return mapProduce.getOrDefault(getVariant(), 0);
  }

  @Override
  public int getConsume() {
    return mapConsume.getOrDefault(getVariant(), 0);
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
