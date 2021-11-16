package com.jw;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class PreMainAgent {

//        /**
//         * @param agentParam 启动另外一个应用的vm option参数
//         * @param instrumentation instrumentation agent，能够在agent启动时（Agent_Onload），执行内部的方法，或者在运行时启动agent（Agent_OnAttach），执行内部方法
//         */
//        public static void premain(String agentParam, Instrumentation instrumentation) {
//            System.out.println(agentParam);
//            Class[] allLoadedClasses = instrumentation.getAllLoadedClasses();
//            for (Class allLoadedClass : allLoadedClasses) {
//            System.out.println(allLoadedClass.getName());
//        }
//    }
}
