package cn.nuaa.compiler.lexer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TypeTable {
	private static Map<String,Integer> Type;
	private static Map<Integer,String> IType;
	enum TYPE{
		K_int, K_short, K_long, K_float, K_double, K_char, K_signed, K_unsigned, K_struct, K_union, K_enum, K_void,
		K_for, K_do, K_while, K_break, K_continue, K_if, K_else, K_goto, K_switch, K_case, K_default, K_return,
		K_auto, K_extern,  K_static, K_register,
		K_const, K_sizeof, K_typedef, K_volatile,
		
		O_add, O_sub,O_mul, O_div, O_remainder, O_inc, O_dec,
		O_less, O_greater, O_unequal, O_equal, O_notless, O_notgreater,
		O_and, O_or, O_not, 
		O_shr, O_shl, O_band, O_bor, O_xor, O_bnot,
		O_assignment, O_addeq, O_subeq, O_muleq, O_diveq, O_remaindereq, O_shreq, O_shleq,
		
		B_brace_l, B_brace_r, B_paren_l, B_paren_r, B_braket_l, B_braket_r, B_quota_d, B_quota_s, B_pound, B_comma, B_semi, B_colon
	}
	public TypeTable() {
		Type = new HashMap<String, Integer>();
		IType = new HashMap<Integer, String>();
		//数据类型
		Type.put("int",(Integer)TYPE.K_int.ordinal());
		Type.put("short",(Integer)TYPE.K_short.ordinal());
		Type.put("long",(Integer)TYPE.K_long.ordinal());
		Type.put("float",(Integer)TYPE.K_float.ordinal());
		Type.put("double",(Integer)TYPE.K_double.ordinal());
		Type.put("char",(Integer)TYPE.K_char.ordinal());
		Type.put("signed",(Integer)TYPE.K_signed.ordinal());
		Type.put("unsigned",(Integer)TYPE.K_unsigned.ordinal());
		Type.put("struct",(Integer)TYPE.K_struct.ordinal());
		Type.put("union",(Integer)TYPE.K_union.ordinal());
		Type.put("enum",(Integer)TYPE.K_enum.ordinal());
		Type.put("void",(Integer)TYPE.K_void.ordinal());
		//控制语句
		Type.put("for",(Integer)TYPE.K_for.ordinal());
		Type.put("do",(Integer)TYPE.K_do.ordinal());
		Type.put("while",(Integer)TYPE.K_while.ordinal());
		Type.put("break",(Integer)TYPE.K_break.ordinal());
		Type.put("continue",(Integer)TYPE.K_continue.ordinal());
		Type.put("if",(Integer)TYPE.K_if.ordinal());
		Type.put("else",(Integer)TYPE.K_else.ordinal());
		Type.put("goto",(Integer)TYPE.K_goto.ordinal());
		Type.put("switch",(Integer)TYPE.K_switch.ordinal());
		Type.put("case",(Integer)TYPE.K_case.ordinal());
		Type.put("default",(Integer)TYPE.K_default.ordinal());
		Type.put("return",(Integer)TYPE.K_return.ordinal());
		//存储类型
		Type.put("auto",(Integer)TYPE.K_auto.ordinal());
		Type.put("extern",(Integer)TYPE.K_extern.ordinal());
		Type.put("static",(Integer)TYPE.K_static.ordinal());
		Type.put("register",(Integer)TYPE.K_register.ordinal());
		//其他关键字
		Type.put("const",(Integer)TYPE.K_const.ordinal());
		Type.put("sizeof",(Integer)TYPE.K_sizeof.ordinal());
		Type.put("typedef",(Integer)TYPE.K_typedef.ordinal());
		Type.put("volatile",(Integer)TYPE.K_volatile.ordinal());
		//算数运算符
		Type.put("+",(Integer)TYPE.O_add.ordinal());
		Type.put("-",(Integer)TYPE.O_sub.ordinal());
		Type.put("*",(Integer)TYPE.O_mul.ordinal());
		Type.put("/",(Integer)TYPE.O_div.ordinal());
		Type.put("%",(Integer)TYPE.O_remainder.ordinal());
		Type.put("++",(Integer)TYPE.O_inc.ordinal());
		Type.put("--",(Integer)TYPE.O_dec.ordinal());
		//关系运算符
		Type.put("<",(Integer)TYPE.O_less.ordinal());
		Type.put(">",(Integer)TYPE.O_greater.ordinal());
		Type.put("!=",(Integer)TYPE.O_unequal.ordinal());
		Type.put("==",(Integer)TYPE.O_equal.ordinal());
		Type.put(">=",(Integer)TYPE.O_notless.ordinal());
		Type.put("<=",(Integer)TYPE.O_notgreater.ordinal());
		//逻辑运算符
		Type.put("&&",(Integer)TYPE.O_and.ordinal());
		Type.put("||",(Integer)TYPE.O_or.ordinal());
		Type.put("!",(Integer)TYPE.O_not.ordinal());
		//位运算符
		Type.put("<<",(Integer)TYPE.O_shl.ordinal());
		Type.put(">>",(Integer)TYPE.O_shr.ordinal());
		Type.put("&",(Integer)TYPE.O_band.ordinal());
		Type.put("|",(Integer)TYPE.O_bor.ordinal());
		Type.put("~",(Integer)TYPE.O_bnot.ordinal());
		Type.put("^",(Integer)TYPE.O_xor.ordinal());
		//赋值运算符
		Type.put("=",(Integer)TYPE.O_assignment.ordinal());
		Type.put("+=",(Integer)TYPE.O_addeq.ordinal());
		Type.put("-=",(Integer)TYPE.O_subeq.ordinal());
		Type.put("*=",(Integer)TYPE.O_muleq.ordinal());
		Type.put("/=",(Integer)TYPE.O_diveq.ordinal());
		Type.put("%=",(Integer)TYPE.O_remaindereq.ordinal());
		Type.put("<<=",(Integer)TYPE.O_shleq.ordinal());
		Type.put(">>=",(Integer)TYPE.O_shreq.ordinal());
		//界符
		Type.put("{",(Integer)TYPE.B_brace_l.ordinal());
		Type.put("}",(Integer)TYPE.B_brace_r.ordinal());
		Type.put("(",(Integer)TYPE.B_paren_l.ordinal());
		Type.put(")",(Integer)TYPE.B_paren_r.ordinal());
		Type.put("[",(Integer)TYPE.B_braket_l.ordinal());
		Type.put("]",(Integer)TYPE.B_braket_r.ordinal());
		Type.put("\"",(Integer)TYPE.B_quota_d.ordinal());
		Type.put("'",(Integer)TYPE.B_quota_s.ordinal());
		Type.put("#",(Integer)TYPE.B_pound.ordinal());
		Type.put(",",(Integer)TYPE.B_comma.ordinal());
		Type.put(";",(Integer)TYPE.B_semi.ordinal());
		Type.put(":",(Integer)TYPE.B_colon.ordinal());
		Iterator iter = Type.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			String key =(String) entry.getKey();
			Integer value = (Integer)entry.getValue();
			IType.put(value, key);
		}
	}
	public Map<String,Integer> getType() {
		return Type;
	}
	public Map<Integer,String> getIType() {
		return IType;
	}
}
