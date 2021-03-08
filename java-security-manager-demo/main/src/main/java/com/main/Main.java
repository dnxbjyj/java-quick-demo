package com.main;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.itfc.CmdExecutorItfc;

/**
 * main测试
 */
public class Main {
    public static void main(String[] args) throws Exception {
        testExecuteDangerCmd();
    }

    private static void testExecuteDangerCmd() throws Exception {
        System.out.println("start testExecuteDangerCmd");
        // - 开启安全管理器，使用JVM参数中指定的policy文件
        System.setSecurityManager(new SecurityManager());

        // - 通过自定义类加载器加载third.jar中的执行命令实现类
        String thirdJarPath = "d:/github/java-quick-demo/java-security-manager-demo/third/third.jar";
        String implClassName = "com.third.CmdExecutorImpl";
        CmdExecutorItfc executor = getClassInstance(thirdJarPath, implClassName);
        if (executor == null) {
            System.out.println("get executor from third.jar is null!");
            return;
        }

        final String cmd = "D:/program/Git/usr/bin/touch.exe d:/tmp/test.txt";     
        // - 在线程中执行危险命令
        executeCmdInThread(executor, cmd, true);

        // - 关闭安全管理器，然后再在线程中执行危险命令，注意：因为多线程执行顺序的不确定性，此处关闭安全管理器有可能对前面的线程产生影响，从而程序运行结果与预期不一致，所以先注释掉下面几行
        // System.setSecurityManager(null);
        // executeCmdInThread(executor, cmd, false);
        
        // System.out.println("end testExecuteDangerCmd");        
    }

    /**
     * 加载类的实例对象
     *
     * @param jarPath   jar包文件路径
     * @param className 类全限定名
     * @param clazz     类的class
     * @return 类的实例对象
     */
    private static <T> T getClassInstance(String jarPath, String className) {
        try {
            URLClassLoader loader = new URLClassLoader(new URL[]{new File(jarPath).toURI().toURL()});
            T instance = (T) Class.forName(className, true, loader).newInstance();
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用执行器执行名
     *
     * @param executor 执行器
     * @param cmd 要执行的命令
     * @param isWithSecurityManager 当前是否开启安全管理器
     */
    private static void executeCmdInThread(CmdExecutorItfc executor, String cmd, boolean isWithSecurityManager) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(">>> isWithSecurityManager: " + isWithSecurityManager);
                    executor.execute(cmd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
}
