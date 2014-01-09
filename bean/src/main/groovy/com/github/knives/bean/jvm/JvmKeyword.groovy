package com.github.knives.bean.jvm;

import org.objectweb.asm.Opcodes


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
	
	private static List<JvmKeyword> translateAccessToModifiers(int access, Closure filter, Closure tweak) {
		final def keywords = [] as List<JvmKeyword>
		
		JvmKeyword.values().grep(filter).each { JvmKeyword keyword ->
			if (keyword.getOpcode() != null) {
				if ((access & keyword.getOpcode()) != 0) {
					keywords << keyword
				}
			}
		}
			
		
		return tweak.call(keywords)
	}	
	
	private static List<JvmKeyword> translateAccessToModifiers(int access, Closure filter) {
		translateAccessToModifiers(access, filter) { }
	}
	
	public static List<JvmKeyword> translateAccessToTypeKeywords(int access) {
		final def filter = { JvmKeyword keyword -> keyword.isTypeKeyword() }
		
		return translateAccessToModifiers(access, filter) { List<JvmKeyword> keywords ->
			
			// tweak, interface is actually an abstract class
			if (JvmKeyword.INTERFACE in keywords) {
				keywords.remove(JvmKeyword.ABSTRACT)
			}
			
			def defaultType = [JvmKeyword.INTERFACE, JvmKeyword.ENUM, JvmKeyword.ANNOTATION]
			
			// tweak, there is no class in Opcodes, because it is by default
			// everything is a class
			if (keywords.intersect(defaultType).isEmpty()) {
				keywords << JvmKeyword.CLASS
			}
			
			// tweak, both annotation and interface show up, so we have to remove
			// interface when have annotation
			if (JvmKeyword.ANNOTATION in keywords) {
				keywords.remove(JvmKeyword.INTERFACE)
			}
			
			return keywords
		}
	}
	
	public static List<JvmKeyword> translateAccessToMethodKeywords(int access) {
		final def filter = { JvmKeyword keyword -> keyword.isMethodKeyword() }
		
		return translateAccessToModifiers(access, filter)
	}
	
	public static List<JvmKeyword> translateAccessToFieldKeywords(int access) {
		final def filter = { JvmKeyword keyword -> keyword.isFieldKeyword() }
		
		return translateAccessToModifiers(access, filter)
	}	
}
