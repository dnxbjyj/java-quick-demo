# 这是什么
Java诊断工具Arthas极简demo。

# 安装
## 快速安装：使用arthas-boot
https://arthas.aliyun.com/doc/install-detail.html#arthas-boot

## 全量安装
https://arthas.aliyun.com/doc/install-detail.html#id2

# 使用
## 启动待调试demo进程
使用全量安装方式安装，在全量安装包中有一个名为`arthas-demo.jar`的jar包，这是一个简单的程序，每隔一秒生成一个随机数，再执行质因数分解，并打印出分解结果。

把`arthas-demo.jar`运行起来：`java -jar arthas-demo.jar`，这样就启动了一个java进程，下面准备用arthas对这个进程进行调试。

## 启动arthas-boot.jar
安装好之后，另起一个命令行窗口，然后执行：`java -jar arthas-boot.jar`，就把arthas启动起来了。然后会把当前所有的java进程列出来，输入要调试的进程前面的序号（如`1`），即可进入arthas进程调试命令行，然后就可以使用arthas提供的各种命令对进程进行调试了。

查看帮助信息：`java -jar arthas-boot.jar -h`

如果连接不上目标进程，可以查看该目录下的日志进行定位：`~/logs/arthas/`

## 调试java进程
以下命令和操作都是在arthas进程调试命令行中执行的。

### 查看dashboard
命令：`dashboard`，查看dashboard，按`C-c`可以退出查看。

# 总结

# 参考资料
- [Arthas 用户文档](https://arthas.aliyun.com/doc/)
- [jvm调优神器——arthas](https://www.cnblogs.com/spareyaya/p/13177513.html)
- [jvm调优的几种场景](https://www.cnblogs.com/spareyaya/p/13174003.html)
