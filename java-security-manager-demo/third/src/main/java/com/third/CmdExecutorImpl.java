package com.third;

import com.itfc.CmdExecutorItfc;

/**
 * 命令执行器实现类
 */
public class CmdExecutorImpl implements CmdExecutorItfc {
    @Override
    public void execute(String cmd) throws Exception{
        System.out.println("start CmdExecutorImpl.execute, cmd = " + cmd);
        Runtime.getRuntime().exec(cmd);
        System.out.println("end CmdExecutorImpl.execute");
    }
}
