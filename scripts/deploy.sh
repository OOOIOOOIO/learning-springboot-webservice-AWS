#!/bin/bash

REPOSITORY=~/app/step2
PROJECT_NAME=learning-springboot-webservice-AWS

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/$PROJECT_NAME.jar

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -l learning-springboot-webservice-AWS | grep jar | awk '{print $1}')

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"

# [ -z ] 는 문자열의 길이가 0이면 참
if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

# ls: $REPOSITORY 디렉토리 내용 출력, -tr 옵션은 newest first sorting 결과를 reverse 따라서 최신파일이 나중에 출력
# tail: grep 결과를 받아서 마지막 행부터 1번째 줄 출력
#JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1) # 경로까지 다 나옴
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

# nohup은 터미널이 끊겨도 실행한 프로세스는 계속 동작하게 함. 실행 중 생기는 메시지를 nohup.out 파일을 만들어 이 파일에 출력
# 2>&1는 표준오류(stderr)를 표준출력(stdout)이 전달되는 곳으로 보냄. 따라서 nohup.out에 표준오류도 같>이 씀
# 마지막 &는 명령을 백그라운드 작업으로 실행함

# -jar 하기 전에 폴더 이동
cd $REPOSITORY

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
        -Dspring.config.location=classpath:/application.yml,/home/ubuntu/app/application-db.yml,/home/ubuntu/app/application-oauth.yml \
        $REPOSITORY/$JAR_NAME 2>&1 &
