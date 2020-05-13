#!/bin/bash

PROJECT_REPO=https://github.com/epochwz/seckill.git # 项目仓库
PROJECT_PATH=$HOME/projects/seckill                 # 项目路径
JARFILE_PATH=$PROJECT_PATH/target/seckill.jar       # 执行文件
RUNNING_PATH=$PROJECT_PATH/running && mkdir -p $RUNNING_PATH # 运行路径
LOGFILE_PATH=$RUNNING_PATH/seckill.log              # 日志文件
CONTEXT_FILE=$RUNNING_PATH/application.properties   # 配置文件
OPTIONS="-Xms512m -Xmx1024m -XX:NewSize=512m -XX:MaxNewSize=1024m -Dspring.config.additional-location=$CONTEXT_FILE"

# 克隆源代码
function clone() {
    [ ! -d "$PROJECT_PATH" ] && git clone $PROJECT_REPO $PROJECT_PATH || echo "directory $PROJECT_PATH was exist" || true
}

# 更新源代码
function pull() {
    cd $PROJECT_PATH && git pull
}

# 打包
function package() {
    cd $PROJECT_PATH && mvn clean package -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true
}

# 部署
function deploy() {
    clone && pull && package
}

# 获取PID
function pid(){
    ps -ef | grep $JARFILE_PATH | grep -v grep | awk '{print $2}'
}

# 查看日志
function log() {
    tail -f $LOGFILE_PATH
}

# 停止
function stop() {
    [ -n "$(pid)" ] && kill -9 "$(pid)" || true
}

# 启动
function start() {
    (java $OPTIONS -jar $JARFILE_PATH 1>$LOGFILE_PATH 2>&1 &) && sleep 1 && echo "$(pid) was started"
}

# 重启
function restart() {
    stop && sleep 1 && start
}

# 功能清单
function menu() {
    echo "Usage: $(basename $BASH_SOURCE) <deploy>"
    echo "Usage: $(basename $BASH_SOURCE) <clone|pull|package>"
    echo "Usage: $(basename $BASH_SOURCE) <start|stop|restart>"
}

# 主函数
function main() {
    case $1 in
        # 部署相关
        clone)      clone   ;;
        pull)       pull    ;;
        package)    package ;;
        deploy)     deploy  ;;
        # 调试相关
        pid)        pid     ;;
        log)        log     ;;
        # 启动相关
        start)      start   ;;
        stop)       stop    ;;
        restart)    restart ;;
        # 功能清单
        *)          menu    ;;
    esac
}

main $1