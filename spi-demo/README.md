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
以下操作都是基于provider根路径。

- 编译源代码，把编译结果class放到target目录，并指定外部依赖库路径lib：

``` shell
javac -d target -extdirs lib -encoding utf-8 src/main/java/com/provider/impl/*.java src/main/java/com/provider/*.java
```

- 把lib目录和META-INF目录复制到target目录，以在运行时能找到

``` shell
cp -r lib target/
cp -r src/main/resources/META-INF target/
```

- 运行主类SearchService：

``` shell
java -cp target;target/lib/standard.jar com.provider.SearchService
```

输出结果：
```
start SearchService.search...
search keyword: "my keyword" by FileSearcher...
search keyword: "my keyword" by DatabaseSearcher...
end SearchService.search
```

- 用jar打包，通过指定MANIFEST.MF文件（文件中指定了主类和依赖包）：

``` shell
jar cvfm provider.jar target/META-INF/MANIFEST.MF -C target/ .
```

- 运行jar包，输出结果和运行主类SearchService的结果一致

``` shell
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
