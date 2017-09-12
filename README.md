# chengshi
## 小型电商后台管理系统
### 项目介绍
1. springBoot + mybatis 框架
2. freemarker 页面渲染模板
3. 开源的前端html5跨屏框架zui，官网地址http://zui.sexy
4. 数据库mysql，shiro安全登录框架，redis缓存session等信息，便于服务器集群，图片数据使用阿里提供的oss服务
5. maven相关依赖
``` xml
        <!--druid-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.2</version>
        </dependency>
        <!--pagehelper-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.1.3</version>
        </dependency>
        <!-- MySQL 连接驱动依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.42</version>
        </dependency>
        <!--shiro-->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>1.4.0</version>
        </dependency>
        <dependency>
            <groupId>org.crazycake</groupId>
            <artifactId>shiro-redis</artifactId>
            <version>2.4.2.1-RELEASE</version>
        </dependency>
        <!--Apache Commons包含了很多开源的工具，用于解决平时编程经常会遇到的问题，减少重复劳动。 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.5</version>
        </dependency>
        <!-- fastjson阿里巴巴jSON处理器 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.13</version>
        </dependency>
        <!-- redis数据库 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!-- session存放redis-->
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
        </dependency>
        <!-- cache缓存 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <!-- 阿里云oss -->
        <dependency>
            <groupId>com.aliyun.oss</groupId>
            <artifactId>aliyun-sdk-oss</artifactId>
            <version>2.8.1</version>
        </dependency>
```
 6.项目截图
 ![截图1](/intro/screenshot1.png)
 ![截图2](/intro/screenshot2.png)
 ![截图3](/intro/screenshot3.png)
 ![截图4](/intro/screenshot4.png)
 
### 项目声明
1. 本人是java后端，兴趣使然想找一个组件式开源前端框架，zui是基于Bootstrap深度开发的，由于本人之前项目使用过Bootstrap，对于一个纯后台人员来说这简直有点复杂，哈哈，zui简化了很多并且封装了很多常用的组件，基本上够正常开发使用，不用再去辛辛苦苦寻觅了，还要担心各种兼容问题，简直就是后端人员的福音
2. 于是作者在工作闲余之际断断续续写了点，可能会更新的慢点，但是会一直完善下去。
3. 本人是2年java菜鸟，肯定有代码结构上的不完善和bug，大家一起交流学习
