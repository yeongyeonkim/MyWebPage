#!/usr/bin/env bash

HOME_PATH=/home/ec2-user/app
JAR_FILE="$HOME_PATH/api.jar"
APP_LOG="$HOME_PATH/application.log"
ERROR_LOG="$HOME_PATH/error.log"

echo "Start api deploy!"
# build 파일 복사
cp $HOME_PATH/build/libs/*.jar $JAR_FILE
# jar 파일 실행
nohup java -jar "$JAR_FILE/api.jar" > $APP_LOG 2> $ERROR_LOG &