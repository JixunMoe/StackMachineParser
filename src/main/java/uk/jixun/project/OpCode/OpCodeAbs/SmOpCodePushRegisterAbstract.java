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

public abstract class SmOpCodePushRegisterAbstract extends AbstractBasicOpCode {

  private static HashMap<SmRegister, Integer> mapConsume = new HashMap<>();
  private static HashMap<SmRegister, Integer> mapProduce = new HashMap<>();

  static {
    
    mapConsume.put(SmRegister.TOS, 1);
    mapProduce.put(SmRegister.TOS, 1);
    
    mapConsume.put(SmRegister.XP, 0);
    mapProduce.put(SmRegister.XP, 1);
    
    mapConsume.put(SmRegister.YP, 0);
    mapProduce.put(SmRegister.YP, 1);
    

    
  }

  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.PUSH_REGISTER;
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
    return mapProduce.getOrDefault(getRegisterVariant(), 0);
  }

  @Override
  public int getConsume() {
    return mapConsume.getOrDefault(getRegisterVariant(), 0);
  }

  @Override
  public boolean readRam() {
    
    if (getRegisterVariant() == SmRegister.TOS) {
      return true;
    }

    if (getRegisterVariant() == SmRegister.XP) {
      return true;
    }

    if (getRegisterVariant() == SmRegister.YP) {
      return true;
    }

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public boolean writeRam() {
    
    if (getRegisterVariant() == SmRegister.TOS) {
      return false;
    }

    if (getRegisterVariant() == SmRegister.XP) {
      return false;
    }

    if (getRegisterVariant() == SmRegister.YP) {
      return false;
    }

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public boolean isStaticRamAddress() {
    
    if (getRegisterVariant() == SmRegister.TOS) {
      return false;
    }

    if (getRegisterVariant() == SmRegister.XP) {
      return false;
    }

    if (getRegisterVariant() == SmRegister.YP) {
      return false;
    }

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public int resolveRamAddress(IExecutionContext ctx) throws Exception {
    
    if (getRegisterVariant() == SmRegister.TOS) {
      throw new RuntimeException("Unknown ram access type");
    }

    if (getRegisterVariant() == SmRegister.XP) {
      throw new RuntimeException("Unknown ram access type");
    }

    if (getRegisterVariant() == SmRegister.YP) {
      throw new RuntimeException("Unknown ram access type");
    }

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public String toAssembly() {
    
    if (getRegisterVariant() == SmRegister.TOS) {
      return "@";
    }

    if (getRegisterVariant() == SmRegister.XP) {
      return "@[XP]";
    }

    if (getRegisterVariant() == SmRegister.YP) {
      return "@[YP]";
    }

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public void setVariant(int variant) {
    if (variant != 0) {
      throw new RuntimeException("Variant does not apply for this opcode.");
    }
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    if ((regVariant == SmRegister.TOS) || (regVariant == SmRegister.XP) || (regVariant == SmRegister.YP)) {
      this.regVariant = regVariant;
    } else {
      throw new RuntimeException("Register variant " + regVariant.toString()
        + " is not allowed for opcode PUSH_REGISTER");
    }
  }
}
