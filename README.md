## 介绍

`Web信息收集工具`

## 使用

`使用前请先安装好Java8环境`

1. 创建数据库，导入SQL文件
2. 使用压缩软件打开jar包找到application.yml
3. 配置application.yml

```yml
spring:
  datasource:
    druid:
      url: jdbc:mysql://127.0.0.1:3306/funnytools?useUnicode=true&characterEncoding=utf-8&useSSL=false # 数据库连接地址
      username: root # 数据库用户名
      password: root # 数据库密码
server:
  # Web访问端口
  port: 8080
project:
	# 字典上传后存放的路径
  file-base-path: /root/file/
  
```

4. 将application.yml拖回压缩包进行覆盖
5. 后台运行jar包(源码的话请用maven打成jar包)

```bash
nohup java -jar funnylayer.jar &
```

6. 默认账号和密码 
   * 账号: admin
   * 密码: 123456

7. 进行操作前请先将字典上传