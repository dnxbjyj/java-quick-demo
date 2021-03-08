# java-security-manager-demo
Java安全管理器demo，通过这个demo可以了解Java安全管理器的基本用法。

# 测试步骤
## 编译、打包itfc.jar
基于itfc根目录。

- 编译：

``` shell
mkdir target
javac -d target -encoding utf-8 src/main/java/com/itfc/*.java
```

- 打包：

``` shell
jar cvf itfc.jar -C target/ .
```

## 编译、打包third.jar
基于third根目录。

- 准备itfc.jar包：

``` shell
mkdir lib
cp ../itfc/itfc.jar ./lib
```

- 编译：

``` shell
mkdir target
javac -d target -encoding utf-8 -extdirs lib src/main/java/com/third/*.java
```

- 打包：

``` shell
jar cvfm third.jar src/main/resources/META-INF/MANIFEST.MF -C target/ .
```


## 编译、运行main
基于main根目录。

- 准备itfc.jar包：

``` shell
mkdir lib
cp ../itfc/itfc.jar ./lib
```

- 编译：

``` shell
mkdir target
javac -d target -encoding utf-8 -extdirs lib -Xlint:unchecked src/main/java/com/main/*.java
```

- 运行：

``` shell
cp -r lib target/
java -cp target;target/lib/itfc.jar com.main.Main
```

运行结果报错：

``` 
java.security.AccessControlException: access denied ("java.io.FilePermission" "d:\github\java-quick-demo\java-security-manager-demo\third\third.jar" "read")
        at java.security.AccessControlContext.checkPermission(Unknown Source)
        at java.security.AccessController.checkPermission(Unknown Source)
        at java.lang.SecurityManager.checkPermission(Unknown Source)
        at java.lang.SecurityManager.checkRead(Unknown Source)
        at java.io.File.isDirectory(Unknown Source)
        at java.io.File.toURI(Unknown Source)
        at com.main.Main.getClassInstance(Main.java:52)
        at com.main.Main.testExecuteDangerCmd(Main.java:25)
        at com.main.Main.main(Main.java:14)
get executor from third.jar is null!
```

这是因为开启了安全管理器，但是没有通过policy文件授予主线程代码任何权限（包括类加载权限）。

- 指定policy文件使用安全管理器运行：

``` shell
java -cp target;target/lib/itfc.jar -Djava.security.policy=="src/resources/policy/mypolicy.policy" com.main.Main
```

此时能正常运行，其中`mypolicy.policy`文件的内容为：

``` 
grant {
    permission java.security.AllPermission;
};
```

授予了所有代码所有权限。

- 下面仅对主线程的代码授予所有权限，修改`mypolicy.policy`文件的内容为：

``` 
grant codeBase "file:d:/github/java-quick-demo/java-security-manager-demo/main/target/-" {
    permission java.security.AllPermission;
};
```

运行：

``` shell
java -cp target;target/lib/itfc.jar -Djava.security.policy=="src/resources/policy/mypolicy.policy" com.main.Main
```

还是能正常运行。

- 此处认真思考了一下，`third.jar`虽然没有被授予任何权限，但是其代码却可以正常执行，这是比较诡异的。思考了半天，才意识到是起了两个线程在跑，预期第二个线程跑之前是把安全管理器关掉了，而线程的执行顺序又是不定的，正好每次都是跑`third.jar`的代码前，安全管理器都被关掉了。

- 下面在Main类中仅起一个线程跑`third.jar`中的代码，并且不关闭安全管理器，`mypolicy.policy`文件的内容为：

```
grant codeBase "file:d:/github/java-quick-demo/java-security-manager-demo/main/target/-" {
    permission java.security.AllPermission;
};
```

重新编译：

``` shell
javac -d target -encoding utf-8 -extdirs lib -Xlint:unchecked src/main/java/com/main/*.java
```

运行：

``` shell
java -cp target;target/lib/itfc.jar -Djava.security.policy=="src/resources/policy/mypolicy.policy" com.main.Main
```

运行结果报错：

```
java.security.AccessControlException: access denied ("java.io.FilePermission" "D:/program/Git/usr/bin/touch.exe" "execute")
        at java.security.AccessControlContext.checkPermission(Unknown Source)
        at java.security.AccessController.checkPermission(Unknown Source)
        at java.lang.SecurityManager.checkPermission(Unknown Source)
        at java.lang.SecurityManager.checkExec(Unknown Source)
        at java.lang.ProcessBuilder.start(Unknown Source)
        at java.lang.Runtime.exec(Unknown Source)
        at java.lang.Runtime.exec(Unknown Source)
        at java.lang.Runtime.exec(Unknown Source)
        at com.third.CmdExecutorImpl.execute(CmdExecutorImpl.java:12)
        at com.main.Main$1.run(Main.java:74)
        at java.lang.Thread.run(Unknown Source)java.security.AccessControlException: access denied ("java.io.FilePermission" "D:/program/Git/usr/bin/touch.exe" "execute")
        at java.security.AccessControlContext.checkPermission(Unknown Source)
        at java.security.AccessController.checkPermission(Unknown Source)
        at java.lang.SecurityManager.checkPermission(Unknown Source)
        at java.lang.SecurityManager.checkExec(Unknown Source)
        at java.lang.ProcessBuilder.start(Unknown Source)
        at java.lang.Runtime.exec(Unknown Source)
        at java.lang.Runtime.exec(Unknown Source)
        at java.lang.Runtime.exec(Unknown Source)
        at com.third.CmdExecutorImpl.execute(CmdExecutorImpl.java:12)
        at com.main.Main$1.run(Main.java:74)
        at java.lang.Thread.run(Unknown Source)
```

说明`third.jar`中的代码没有权限执行`touch.exe`文件，满足预期！

- 修改`mypolicy.policy`文件的内容为：

```
grant codeBase "jar:file:/d:/github/java-quick-demo/java-security-manager-demo/third/third.jar!/" {
    permission java.io.FilePermission "D:/program/Git/usr/bin/touch.exe", "execute";
};

grant codeBase "file:d:/github/java-quick-demo/java-security-manager-demo/main/target/-" {
    permission java.security.AllPermission;
};
```

运行：

``` shell
java -cp target;target/lib/itfc.jar -Djava.security.policy=="src/resources/policy/mypolicy.policy" com.main.Main
```

能够正常运行！输出结果：

```
start testExecuteDangerCmd
>>> isWithSecurityManager: true
start CmdExecutorImpl.execute, cmd = D:/program/Git/usr/bin/touch.exe d:/tmp/test.txt
end CmdExecutorImpl.execute
```

- 尝试配置codeBase为相对路径！修改`mypolicy.policy`文件的内容为：

```
grant codeBase "jar:file:../third/third.jar!/" {
    permission java.io.FilePermission "D:/program/Git/usr/bin/touch.exe", "execute";
};

grant codeBase "file:target/-" {
    permission java.security.AllPermission;
};
```

运行：

``` shell
java -cp target;target/lib/itfc.jar -Djava.security.policy=="src/resources/policy/mypolicy.policy" com.main.Main
```

能够正常运行！输出结果：

```
start testExecuteDangerCmd
>>> isWithSecurityManager: true
start CmdExecutorImpl.execute, cmd = D:/program/Git/usr/bin/touch.exe d:/tmp/test.txt
end CmdExecutorImpl.execute
```

- 尝试修改jar包的codeBase相对路径为通配符！修改`mypolicy.policy`文件的内容为：

```
grant codeBase "jar:file:../third/-!/" {
    permission java.io.FilePermission "D:/program/Git/usr/bin/touch.exe", "execute";
};

grant codeBase "file:target/-" {
    permission java.security.AllPermission;
};
```

运行：

``` shell
java -cp target;target/lib/itfc.jar -Djava.security.policy=="src/resources/policy/mypolicy.policy" com.main.Main
```

能够正常运行！输出结果：

```
start testExecuteDangerCmd
>>> isWithSecurityManager: true
start CmdExecutorImpl.execute, cmd = D:/program/Git/usr/bin/touch.exe d:/tmp/test.txt
end CmdExecutorImpl.execute
```


# 参考
- [java中的安全模型(沙箱机制)](https://www.cnblogs.com/MyStringIsNotNull/p/8268351.html)
- [Default Policy Implementation and Policy File Syntax](https://docs.oracle.com/javase/8/docs/technotes/guides/security/PolicyFiles.html)
- [java安全沙箱（一）之ClassLoader双亲委派机制](https://www.cnblogs.com/duanxz/p/6108343.html)
- [Java沙箱逃逸走过的二十个春秋（一）](https://xz.aliyun.com/t/2840)
- [(翻译)Java类型混淆，沙箱逃逸](https://bbs.pediy.com/thread-227189.htm)
- [Flink Security](https://flink.apache.org/security.html#during-a-security-analysis-of-flink-i-noticed-that-flink-allows-for-remote-code-execution-is-this-an-issue)
