# 这是什么？
SPI设计思想的一个简单demo。

# SPI三种角色
- 标准制定方：制定标准服务接口，该接口可由服务实现方实现，由服务调用方调用。
- 服务实现方：提供标准服务接口的具体实现。
- 服务调用方：调用标准服务接口定义的服务功能。

# 代码结构
- `module/standard`：标准制定方代码目录，定义标准服务接口。
- `module/provider`：服务实现方代码目录，提供标准服务接口的具体实现代码。
- `module/caller`：服务调用方代码目录，调用标准服务接口定义的服务功能。

# 验证流程
## 编译、打包`standard.jar`

```
javac -d target -encoding utf-8 src/main/java/com/standard/*.java
jar cvfm standard.jar src/main/resources/META-INF/MANIFEST.MF -C target/ .
```

## 编译、打包`provider.jar`

```
javac -d target -extdirs lib -encoding utf-8 src/main/java/com/provider/impl/*.java src/main/java/com/provider/*.java

java -cp . com.provider.SearchService

jar cvfm provider.jar src/main/resources/META-INF/MANIFEST.MF -C target/ .

java -jar provider.jar
```

# 思维导图总结
xx

# 问题总结
## `Could not find or load main class`错误
编译然后运行单个Java类`SearchService`时，报错：

``` 
Error: Could not find or load main class SearchService
```

原因：Java命令运行的时候没有指定classpath，需要指定，像这样：

``` 
java -cp ../../ com.provider.SearchService
```

注：这里的`../../`表示以上上级父路径为包根路径。

## `java.lang.ClassNotFoundException: com.standard.Searcher`报错
在`targer`目录使用下面命令运行代码时候：

```
java -cp . com.provider.SearchService
```

报错：

```
java.lang.ClassNotFoundException: com.standard.Searcher
```

注：其中`com.standard.Searcher`为jar包中的一个类。

原因：java命令运行时没有指定jar包的classpath，解决办法：

## 扫描不到在`META-INF/services/com.standard.Searcher`中配置的实现类
使用`java -jar provider.jar`命令执行provider jar包的主类`SearchService`时，代码中并扫描不到在`META-INF/services/com.standard.Searcher`中配置的实现类。

原因：未知

# 参考资料
- [Java SPI思想梳理](https://zhuanlan.zhihu.com/p/28909673)
