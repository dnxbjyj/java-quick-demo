# 这是什么
Maven配置和Maven项目的开发简单demo。

介绍IDEA的安装配置、Maven的安装配置、Maven工程的结构、通过pom引用三方件、打包Maven工程。

# 开工
## IDEA的安装配置
见参考链接：[社区版官方下载链接](https://www.jetbrains.com/idea/download/#section=windows)，绿色软件，直接解压安装。

## Maven的安装配置
见参考链接`Maven`章节，绿色软件，直接解压安装，然后配置环境变量。

在安装好Maven之后，需要创建`~/.m2\settings.xml`文件，并在其中改两处：
- `localRepository`：改为本地的一个目录，作为本地仓库，用于存放Maven下载的jar包。
- `mirrors`：在其中添加一个`mirror`标签，内容为阿里云的镜像：

```xml
<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>
</mirror>
```

配置完Maven之后，需要在IDEA中修改Maven的配置文件路径和本地仓库路径。

## Maven工程的结构

```
─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─demo
│  │  └─resources
│  └─test
│      └─java
│          └─com
│              └─demo
└─target
    ├─classes
    │  └─com
    │      └─demo
    ├─generated-sources
    │  └─annotations
    ├─generated-test-sources
    │  └─test-annotations
    └─test-classes
        └─com
            └─demo
```

各个目录的说明：
- `src`：源码目录，存放所有的Java源代码、单元测试源代码、资源文件等。
  - `src/main/`：Java源代码、资源文件目录。
    - `src/main/java`：Java源代码目录。
    - `src/main/resources`：资源文件目录。
  - `src/test/`：单元测试源代码、资源文件目录。
    - `src/test/java/`：单元测试源代码目录。
- `target`：Java源代码、单元测试源代码编译生成的字节码文件目录。

## 通过pom引用三方件
如果想要引用某三方件，可以先去[Maven中心仓](https://mvnrepository.com/)搜索三方件maven坐标，然后添加到工程的`pom.xml`文件的`dependency`标签中。

比如本demo中通过pom引入了junit三方件。

# 参考链接
## IDEA
- [社区版官方下载链接](https://www.jetbrains.com/idea/download/#section=windows)

## Maven
- [Installing Apache Maven](https://maven.apache.org/install.html)
- [详解Maven settings.xml配置(指定本地仓库、阿里云镜像设置)](https://www.jb51.net/article/152958.htm)
- [Maven中心仓](https://mvnrepository.com/)

