# chengshi
橙市一个专注优质橙类产品的网站
# 小型电商后台管理系统
## 项目使用技术
1. springBoot + mybatis 框架
2. freemarker 页面渲染模板
3. 开源的前端html5跨屏框架zui，官网地址http://zui.sexy
4. 数据库mysql，shiro安全登录框架，redis缓存session等信息，便于服务器集群，图片数据使用阿里提供的oss服务
5. maven相关依赖
`
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
        </dependency>`
