# Getting Started

## 项目部署

1. 确保服务器能够访问 `githubusercontent.com`

   如果服务器能够正常访问则可以忽略本步骤，否则请尝试执行以下命令进行修复

   ```bash
   echo "199.232.68.133 raw.githubusercontent.com" >> /etc/hosts
   echo "199.232.68.133 pkg.githubusercontent.com" >> /etc/hosts
   ```

2. 预先安装好运行环境 `Git / JDK / Maven`

   如果已经安装了上述软件则可以忽略本步骤，否则可以自行安装或者通过执行以下命令进行快速安装

   ```bash
   # 安装 Git
   sudo apt-get -y update && sudo apt-get -y install git
   # 安装 常用开发软件管理命令
   wget https://raw.githubusercontent.com/epochwz/shell/master/src/init.sh && . init.sh && rm init.sh
   # 安装 Maven
   Maven install 3.6.3
   # 安装 JDK
   JDK  install 8
   ```

3. 配置 Maven 认证

   ```bash
   <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
      <server>
        <id>epochwz</id>
        <username>epochwz</username>
        <password>38512843ff86463e004d25f3df640125d73705b4</password>
      </server>
    </servers>
   </settings>
   ```

4. 初始化项目运维脚本

   ```bash
   cd /usr/local/bin && wget https://raw.githubusercontent.com/epochwz/seckill/master/bin/deploy.sh -O seckill && chmod +x seckill
   ```

5. 克隆项目并打包 `seckill deploy`
6. 自定义项目配置 (可选)

   直接创建自定义配置文件 `$HOME/projects/seckill/application.properties` 来覆盖默认配置即可 (未自定义的参数仍然会使用默认配置)

   ```bash
   # DataSource
   spring.datasource.url=jdbc:mysql://127.0.0.1:3306/seckill?useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
   spring.datasource.username=root
   spring.datasource.password=root
   # Redis
   spring.redis.host=127.0.0.1
   spring.redis.port=6379
   spring.redis.database=0
   spring.redis.jedis.pool.max-active=50
   spring.redis.jedis.pool.min-idle=50
   ```

7. 直接启动本项目 `seckill start` 并通过 <http://localhost:8080/static/index.html> 进行访问

   其他常用的命令

   ```bash
   # 部署相关
   seckill deploy  # 部署
   seckill clone   # 克隆
   seckill pull    # 更新
   seckill package # 打包
   # 启动相关
   seckill restart # 重启
   seckill start   # 启动
   seckill stop    # 停止
   # 调试相关
   seckill log     # 查看程序的日志
   seckill pid     # 查看程序进程号 
   ```

   项目常用的文件

   ```bash
   $HOME/projects/seckill                       # 项目路径
   $HOME/projects/seckill/seckill.log           # 日志文件
   $HOME/projects/seckill/target/seckill.jar    # 执行文件
   ```
