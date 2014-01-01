package com.github.knives.bean.jvm;

import org.objectweb.asm.Opcodes;


public enum JvmKeyword {
	PUBLIC("public", Opcodes.ACC_PUBLIC, true, true, true),
	PRIVATE("private", Opcodes.ACC_PRIVATE, true, true, true),
	PROTECTED("protected", Opcodes.ACC_PROTECTED, true, true, true),
	STATIC("static", Opcodes.ACC_STATIC, false, true, true),
	FINAL("final", Opcodes.ACC_FINAL, true, true, true),
	SUPER("super", true, false, false),
	SYNCHRONIZED("synchronized", Opcodes.ACC_SYNCHRONIZED, false, true, false),
	VOLATILE("volatile", Opcodes.ACC_VOLATILE, false, false, true),
	BRIDGE(Opcodes.ACC_BRIDGE, false, true, false),
	VARARGS("...", Opcodes.ACC_VARARGS, false, true, false),
	TRANSIENT("transient", Opcodes.ACC_TRANSIENT, false, false, true),
	NATIVE("native", Opcodes.ACC_NATIVE, false, true, false),
	INTERFACE("interface", Opcodes.ACC_INTERFACE, true, false, false),
	ABSTRACT("abstract", Opcodes.ACC_ABSTRACT, true, true, false),
	SYNTHETIC("synthetic", Opcodes.ACC_SYNTHETIC, false, false, true),
	STRICT("strict", false, false, false),
	ANNOTATION("@interface", Opcodes.ACC_ANNOTATION, true, false, false),
	ENUM("enum", Opcodes.ACC_ENUM, true, false, false),
	DEPRECATED("@Deprecated", Opcodes.ACC_DEPRECATED, true, false, false),
	CLASS("class", true, false, false);
	
	final private String repr;
	final private Integer opcode;
	final private boolean typeKeyword;
	final private boolean methodKeyword;
	final private boolean fieldKeyword;
	
	private JvmKeyword(Integer opcode, boolean typeKeyword, boolean methodKeyword, boolean fieldKeyword) {
		this(null, opcode, typeKeyword, methodKeyword, fieldKeyword);
	}
	
	private JvmKeyword(String repr, boolean typeKeyword, boolean methodKeyword, boolean fieldKeyword) {
		this(repr, null, typeKeyword, methodKeyword, fieldKeyword);
	}
	
	private JvmKeyword(String repr, Integer opcode, boolean typeKeyword, boolean methodKeyword, boolean fieldKeyword) {
		this.repr = repr;
		this.opcode = opcode;
		this.typeKeyword = typeKeyword;
		this.methodKeyword = methodKeyword;
		this.fieldKeyword = fieldKeyword;
	}
	
	public String getRepr() { 
		return repr; 
	}
	
	public Integer getOpcode() {
		return opcode;
	}
	
	public boolean isTypeKeyword() {
		return typeKeyword;
	}

	public boolean isMethodKeyword() {
		return methodKeyword;
	}

	public boolean isFieldKeyword() {
		return fieldKeyword;
	}	
}
