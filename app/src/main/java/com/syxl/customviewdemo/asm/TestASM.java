package com.syxl.customviewdemo.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


public class TestASM {

//    public static void main(String[] args) throws Exception {
//        Class clazz = User.class;
//        String clazzFilePath = getClassFilePath(clazz);
//        ClassReader classReader = new ClassReader(new FileInputStream(clazzFilePath));
//        ClassNode classNode = new ClassNode(Opcodes.ASM5);
//        classReader.accept(classNode, 0);
//
//        List<MethodNode> methods = classNode.methods;
//        List<FieldNode> fields = classNode.fields;
//
//        System.out.println("methods:");
//        for (MethodNode methodNode : methods) {
//            System.out.println(methodNode.name + ", " + methodNode.desc);
//        }
//
//        System.out.println("fields:");
//        for (FieldNode fieldNode : fields) {
//            System.out.println(fieldNode.name + ", " + fieldNode.desc);
//        }
//
//    }
//
//    public static String getClassFilePath(Class clazz) {
//        // file:/Users/zhy/hongyang/repo/BlogDemo/app/build/intermediates/javac/debug/classes/
//        String buildDir = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
//        String fileName = clazz.getSimpleName() + ".class";
//        File file = new File(buildDir + clazz.getPackage().getName().replaceAll("[.]", "/") + "/", fileName);
//        return file.getAbsolutePath();
//    }

}
