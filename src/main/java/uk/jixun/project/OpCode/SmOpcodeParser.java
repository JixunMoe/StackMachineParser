package uk.jixun.project.OpCode;



// !!!                               !!!
// !!!             STOP              !!!
// !!!                               !!!
//     DO NOT EDIT THIS FILE BY HAND    


// This file was generated using an automated script.
// See 'scripts' directory for more information



import uk.jixun.project.Exceptions.UnknownOpCodeException;
import uk.jixun.project.Register.SmRegister;

public class SmOpcodeParser {
  public static ISmOpCode parse(String opcode) throws UnknownOpCodeException {
    opcode = opcode.trim().toUpperCase();
    if (opcode.length() == 0) {
      return new SmNoOpCode();
    }
    char lastChar = opcode.charAt(opcode.length() - 1);

    if ("ADD".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.ADD);
    }
    if ("SUB".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.SUB);
    }
    if ("HMUL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.HMUL);
    }
    if ("HDIV".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.HDIV);
    }
    if ("MUL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.MUL);
    }
    if ("DIV".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.DIV);
    }
    if ("AND".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.AND);
    }
    if ("OR".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.OR);
    }
    if ("NOT".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.NOT);
    }
    if ("XOR".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.XOR);
    }
    if ("LSL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.LSL);
    }
    if ("LSR".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.LSR);
    }
    if ("ROL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.ROL);
    }
    if ("ROR".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.ROR);
    }
    if ("RBYTE".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.ROTATE_RIGHT_BYTE);
    }
    if ("RHWD".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.ROTATE_RIGHT_WORD);
    }
    if ("XBYTE_N".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.EXTRACT_RIGHT_BYTE);
    }
    if ("XWRD_N".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.EXTRACT_RIGHT_WORD);
    }
    if ("IBYTE_N".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.INSERT_BYTE);
    }
    if ("IWRD_N".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.INSERT_WORD);
    }
    if (
      "COPY".equals(opcode.substring(0, opcode.length() - 1))
      && (lastChar == '1' || lastChar == '2' || lastChar == '3' || lastChar == '4')
    ) {
      return OpCodeFactory.create(SmOpCodeEnum.COPY,
        Character.getNumericValue(lastChar));
    }
    if (
      "DROP".equals(opcode.substring(0, opcode.length() - 1))
      && (lastChar == '1' || lastChar == '2' || lastChar == '3' || lastChar == '4')
    ) {
      return OpCodeFactory.create(SmOpCodeEnum.DROP,
        Character.getNumericValue(lastChar));
    }
    if (
      "RSU".equals(opcode.substring(0, opcode.length() - 1))
      && (lastChar == '2' || lastChar == '3' || lastChar == '4')
    ) {
      return OpCodeFactory.create(SmOpCodeEnum.RSU,
        Character.getNumericValue(lastChar));
    }
    if (
      "RSD".equals(opcode.substring(0, opcode.length() - 1))
      && (lastChar == '3' || lastChar == '4')
    ) {
      return OpCodeFactory.create(SmOpCodeEnum.RSD,
        Character.getNumericValue(lastChar));
    }
    if (">R".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_TO_RS);
    }
    if ("<R".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_FROM_RS);
    }
    if ("TEQ".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_EQ);
    }
    if ("TNE".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_NEQ);
    }
    if ("TGT".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_GT);
    }
    if ("TLT".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_LT);
    }
    if ("TNV".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_NEGATIVE);
    }
    if ("TPV".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_POSITIVE);
    }
    if ("TNZ".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_NOT_ZERO);
    }
    if ("TXPZ".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_XP_ZERO);
    }
    if ("TYPZ".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.TEST_YP_ZERO);
    }
    if ("BP".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.RELATIVE_JUMP);
    }
    if ("BL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.ABSOLUTE_JUMP);
    }
    if ("CP".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.RELATIVE_CALL);
    }
    if ("CL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.ABSOLUTE_CALL);
    }
    if ("EXIT".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.RETURN);
    }
    if ("BCR".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.COND_RELATIVE_JUMP);
    }
    if ("BCP".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.COND_PAGE_JUMP);
    }
    if ("BCL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.COND_ABSOLUTE_JUMP);
    }
    if ("CCP".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.COND_RELATIVE_CALL);
    }
    if ("CCL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.COND_ABSOLUTE_CALL);
    }
    if ("?EXIT".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.COND_RETURN);
    }
    if ("LIT".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_BYTE);
    }
    if ("LSI".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_SHORT);
    }
    if ("LEI".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_EXTEND);
    }
    if ("@LOC".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_FRAME_ADDR);
    }
    if ("!LOC".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_FRAME_ADDR);
    }
    if ("LDP".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_INDIRECT_RELATIVE);
    }
    if ("STP".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_INDIRECT_RELATIVE);
    }
    if ("LDL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_INDIRECT_ABSOLUTE);
    }
    if ("STL".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_INDIRECT_ABSOLUTE);
    }
    if ("!".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_REGISTER, SmRegister.NOS);
    }
    if ("![TOS]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_REGISTER, SmRegister.TOS);
    }
    if ("![XP]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_REGISTER, SmRegister.XP);
    }
    if ("![YP]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_REGISTER, SmRegister.YP);
    }
    if ("@".equals(opcode) || "@[TOS]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_REGISTER, SmRegister.TOS);
    }
    if ("@[XP]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_REGISTER, SmRegister.XP);
    }
    if ("@[YP]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_REGISTER, SmRegister.YP);
    }
    if ("![XP++]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_REGISTER_INC, SmRegister.XP);
    }
    if ("![YP++]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_REGISTER_INC, SmRegister.YP);
    }
    if ("![XP--]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_REGISTER_DEC, SmRegister.XP);
    }
    if ("![YP--]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.POP_REGISTER_DEC, SmRegister.YP);
    }
    if ("@[XP++]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_REGISTER_INC, SmRegister.XP);
    }
    if ("@[YP++]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_REGISTER_INC, SmRegister.YP);
    }
    if ("@[XP--]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_REGISTER_DEC, SmRegister.XP);
    }
    if ("@[YP--]".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.PUSH_REGISTER_DEC, SmRegister.YP);
    }
    if ("SP+".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_ADD, SmRegister.SP);
    }
    if ("RP+".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_ADD, SmRegister.RP);
    }
    if ("YP+".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_ADD, SmRegister.YP);
    }
    if ("XP+".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_ADD, SmRegister.XP);
    }
    if ("FP+".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_ADD, SmRegister.FP);
    }
    if ("SP-".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_SUB, SmRegister.SP);
    }
    if ("RP-".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_SUB, SmRegister.RP);
    }
    if ("YP-".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_SUB, SmRegister.YP);
    }
    if ("XP-".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_SUB, SmRegister.XP);
    }
    if ("FP-".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_SUB, SmRegister.FP);
    }
    if ("SP++".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_INC, SmRegister.SP);
    }
    if ("RP++".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_INC, SmRegister.RP);
    }
    if ("YP++".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_INC, SmRegister.YP);
    }
    if ("XP++".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_INC, SmRegister.XP);
    }
    if ("FP++".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_INC, SmRegister.FP);
    }
    if ("TOS++".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_INC, SmRegister.TOS);
    }
    if ("SP--".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_DEC, SmRegister.SP);
    }
    if ("RP--".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_DEC, SmRegister.RP);
    }
    if ("YP--".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_DEC, SmRegister.YP);
    }
    if ("XP--".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_DEC, SmRegister.XP);
    }
    if ("FP--".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_DEC, SmRegister.FP);
    }
    if ("TOS--".equals(opcode)) {
      return OpCodeFactory.create(SmOpCodeEnum.REG_DEC, SmRegister.TOS);
    }
    throw new UnknownOpCodeException(opcode);
  }
}
