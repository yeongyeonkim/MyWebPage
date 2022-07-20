#!/usr/bin/env bash

HOME_PATH=/home/ec2-user/app
JAR_FILE="$HOME_PATH/api.jar"

echo "Kill java process"
`ps -ef | grep "api.jar" | grep -v grep | awk '{print $2}' | xargs kill -9`