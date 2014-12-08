package com.github.knives.ow2.asm4
/**
 * Copy and paste from the following link
 * 
 * http://www.geekyarticles.com/2011/10/manipulating-java-class-files-with-asm_13.html
 * 
 * Run this class and find HelloWorld.class under /tmp
 * 
 * cd /tmp
 * java HelloWorld.class
 * 
 * Output:
 * Hello World!
 */

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

ClassNode classNode = new ClassNode(Opcodes.ASM4);

//These properties of the classNode must be set
//The generated class will only run on JRE 1.6 or above
classNode.version = Opcodes.V1_6
classNode.access = Opcodes.ACC_PUBLIC
classNode.signature = "LHelloWorld;"
classNode.name = "HelloWorld"
classNode.superName = "java/lang/Object"

//Create a method
MethodNode mainMethod = new MethodNode(Opcodes.ASM4, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V",null, null)

mainMethod.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"))
mainMethod.instructions.add(new LdcInsnNode("Hello World!"))
mainMethod.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"))
mainMethod.instructions.add(new InsnNode(Opcodes.RETURN))

//Add the method to the classNode
classNode.methods.add(mainMethod)

//Write the class
ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES)
classNode.accept(cw)

//Dump the class in a file
File outDir = new File("/tmp/")
DataOutputStream dout = new DataOutputStream(new FileOutputStream(new File(outDir,"HelloWorld.class")))
dout.write(cw.toByteArray())
dout.flush()
dout.close()
