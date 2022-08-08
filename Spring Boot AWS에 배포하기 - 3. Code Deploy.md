##### AppSpec 파일 작성

* 프로젝트의 특정 파일들을 EC2의 어느 경로에 복사할 지 설정할 수 있다.
* 배포 프로세스 이후 수행할 스크립트를 지정하여 서버를 자동으로 기동할 수 있다.

```yaml
version: 0.0
os: linux

files:
  - source: /api.jar
    destination: /home/ec2-user/app

permissions:
  - object: /home/ec2-user/app
    pattern: "**"
    owner: ec2-user
    mode: 644
    type:
      - file

hooks:
  BeforeInstall:
    - location: /stop_api.sh
      timeout: 60
      runas: ec2-user
  ApplicationStart:
    - location: /start_api.sh
      timeout: 60
      runas: ec2-user
```

1. files 섹션 ([공식 문서](https://docs.aws.amazon.com/ko_kr/codedeploy/latest/userguide/reference-appspec-file-structure-files.html)) - 애플리케이션 수정 버전의 파일에 대한 정보
   * source: 인스턴스에 복사할 수정된 파일 또는 디렉토리 경로 (/ 하나인 경우, 수정 버전의 모든 파일이 인스턴스에 복사 된다.)
   * destination: 인스턴스에서 파일이 복사되어야 하는 경로
2. permissions 섹션 ([공식 문서](https://docs.aws.amazon.com/ko_kr/codedeploy/latest/userguide/reference-appspec-file-structure-permissions.html)) - 복사한 파일에 대한 권한 설정
   * object: 인스턴스에 복사된, 권한이 지정될 파일 또는 디렉터리
   * pattern: 권한을 적용할 패턴을 지정. `"**"` 모든 파일 및 디렉터리 적용
   * owner: `object` 소유자의 이름
3. hooks 섹션 ([공식 문서](https://docs.aws.amazon.com/ko_kr/codedeploy/latest/userguide/reference-appspec-file-structure-hooks.html#appspec-hooks-server)) - 배포 수명 주기 이벤트 후크를 하나 이상의 스크립트에 연결
   * BeforeInstall: 현재 버전의 백업 만들기와 같은 사전 설치 작업에 사용
   * ApplicationStart: 중지된 서비스를 다시 시작
     * location: 실행할 스크립트 경로
     * timeout: 스크립트 실행에 허용되는 시간
     * runas: 스크립트 실행 사용자

---

##### 배포 스크립트 작성

* start_api.sh

```shell
#!/usr/bin/env bash

HOME_PATH=/home/ec2-user/app
JAR_FILE="$HOME_PATH/api.jar"
APP_LOG="$HOME_PATH/application.log"
ERROR_LOG="$HOME_PATH/error.log"

echo "Start api deploy!"
# build 파일 복사
cp $HOME_PATH/build/libs/*.jar $JAR_FILE
# jar 파일 실행
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

```

* stop_api.sh

```shell

```

---

##### build.gradle 파일 수정

* Spring Boot 2.5 버전부터 빌드 시 `-plain.jar` 파일이 추가로 생성되는데, 생성되지 않도록 한다.

```groovy
# build.gradle
jar {
	enabled = false
}
```

---

##### Github Actions Workflow 작성

* deploy.yml

```
name: 2. 배포

on:
  pull_request:
    branches:
      - main

env:
  AWS_REGION: ap-northeast-2
  S3_BUCKET_NAME: yeongyeon-api-s3-bucket
  CODE_DEPLOY_APPLICATION_NAME: api-deploy
  CODE_DEPLOY_DEPLOYMENT_GROUP_NAME: api-deploy-group

permissions:
  contents: read

jobs:
  deploy:
#     if: github.event.pull_request.merged == true # 2022-08-08
    name: deploy
    runs-on: ubuntu-latest
    #    environment: production

    steps:
      - name: Checkout
        uses: actions/checkout@v2

      - name: Configure-aws-credentials
        uses: aws-actions/configure-aws-credentials@v1
        with:
          aws-access-key-id: ${{secrets.AWS_ACCESS_KEY}}
          aws-secret-access-key: ${{secrets.AWS_SECRET_KEY}}
          aws-region: ap-northeast-2

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
        
      - name: Build with Gradle
        run: ./gradlew build

      - name: Upload to S3
        run: |
          aws deploy push \
            --application-name ${{env.CODE_DEPLOY_APPLICATION_NAME}} \
            --ignore-hidden-files \
            --s3-location s3://$S3_BUCKET_NAME/$GITHUB_SHA.zip \
            --source codedeploy/

      - name: Deploy to EC2
        run: |
          aws deploy create-deployment \
            --application-name ${{env.CODE_DEPLOY_APPLICATION_NAME}} \
            --deployment-config-name CodeDeployDefault.OneAtATime \
            --deployment-group-name ${{env.CODE_DEPLOY_DEPLOYMENT_GROUP_NAME}} \
            --s3-location bucket=$S3_BUCKET_NAME,key=$GITHUB_SHA.zip,bundleType=zip
      
```





