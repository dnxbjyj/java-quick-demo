# 这是什么？
通过Java命令手工打包一个jar包，同时在代码中依赖第三方jar包。

# demo打包步骤
- 在根目录`jar-package-demo`下创建`target`目录。

- 在根目录`jar-package-demo`下执行：

``` shell
javac -d target -extdirs lib -encoding utf-8 src/com/demo/portal/*.java src/com/demo/utils/*.java
```

- 进入`target`目录，创建`META-INF/MANIFEST.MF`文件，文件内容为：

``` 
Manifest-Version: 1.0
Class-Path: . ./lib/commons-lang3-3.11.jar
Main-Class: com.demo.portal.Main

```

- 在`target`目录下执行：`jar -cfM jar-package-demo.jar *`，打包jar包。

- 在`target`目录下执行：`java -jar jar-package-demo.jar 123 456 abc`，输出：

``` 
123456abc
```

# 遇到的问题记录
## 运行jar包报错
运行命令：`java -jar jar-package-demo.jar Main abc 123`，报错：`Error: Invalid or corrupt jarfile jar-package-demo.jar`

原因：`MANIFEST.MF`文件中注释独占了一行，不能独占一行。

# 思维导图总结

# 参考资料
- [JAR-使用JAVA命令编译打包一个可执行jar包](https://www.cnblogs.com/forest-xs/p/jar-package-example.html)
- [Download Apache Commons Lang](https://commons.apache.org/proper/commons-lang/download_lang.cgi)
- [Apache Commons Lang 3.11 API](https://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html)
