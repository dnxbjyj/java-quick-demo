# 这是什么？
通过Java命令手工打包一个jar包，同时在代码中依赖第三方jar包。

# demo打包步骤
- 开发代码，项目根目录为：`jar-package-demo`

- 创建`src/main/resources/META-INF/MANIFEST.MF`文件，其内容如下：

``` 
Manifest-Version: 1.0
Class-Path: . ./lib/commons-lang3-3.11.jar
Main-Class: com.demo.portal.Main

```

注：最后需要有一个空白行。

- 在根目录下创建`target`目录。

- 在根目录下执行下面命令进行编译：

``` shell
javac -d target -extdirs lib -encoding utf-8 src/main/java/com/demo/portal/*.java src/main/java/com/demo/utils/*.java
```

- 在根目录下执行下面命令，打包jar包：

```shell
jar cvfm jar-package-demo.jar src/main/resources/META-INF/MANIFEST.MF -C target/ .

```

注：如果想在最终打包成的jar包中包含`lib`目录下的三方件jar包，可以在执行`jar`命令打包前先把`lib`目录复制到`target`目录中：

``` shell
cp -r lib target/
```

- 在根目录下执行生成的jar包：

``` shell
java -jar jar-package-demo.jar 123 456 abc
```

输出：

``` 
123456abc
```

# 遇到的问题记录
## 运行jar包报错
运行命令：`java -jar jar-package-demo.jar 123 456 abc`，报错：`Error: Invalid or corrupt jarfile jar-package-demo.jar`

原因：`MANIFEST.MF`文件中注释独占了一行，不能独占一行。

# 参考资料
- [JAR-使用JAVA命令编译打包一个可执行jar包](https://www.cnblogs.com/forest-xs/p/jar-package-example.html)
- [抛开IDE，了解一下javac如何编译](https://imshuai.com/using-javac)
- [Download Apache Commons Lang](https://commons.apache.org/proper/commons-lang/download_lang.cgi)
- [Apache Commons Lang 3.11 API](https://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html)
