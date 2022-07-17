##### 목표: github actions 생성하여 push할 때 마다 CI로 빌드 & 테스트 후 S3에 적재하고 Code Deploy (ing)

- main 브랜치에 push 하면 자동으로 EC2 까지 배포되는 Workflow
  1. Github Actions 코드 빌드 및 테스트 (CI)
  2. AWS 인증
  3. jar 파일 S3 업로드
  4. Code Deploy를 통한 EC2 배포

---

##### 1. Github Actions 자동 빌드 및 테스트

* Workflow 생성

![1](img/Github Actions/1.png)

```yaml
name: Java CI with Gradle

# main 브랜치로 push 할 때 trigger 하기 위함
on:
  push:
    branches: [ "main" ]
#  pull_request:
#    branches: [ "main" ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    # 1) workflow 실행 전 기본적으로 checkout이 필요
    - uses: actions/checkout@v2
    
    # 2) JDK 버전 설치, 1.8을 사용하므로 설정
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
    # 3) Gradle wrapper 파일 실행 권한 부여
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    # 4) Gradle 사용
    - name: Build with Gradle
      run: ./gradlew build
```

![2](img/Github Actions/2.png)

---

##### 2. JUnit Test 결과 확인 및 Report

* 빌드 테스트 및 Report를 하는 step을 각각 생성

```yaml
    - name: Build and Test
      run: ./gradlew --info test
#      with:
#         aws-access-key-id: ${{secrets.AWS_ACCESS_KEY}}
#         aws-secret-access-key: ${{secrets.AWS_SECRET_KEY}}
#         aws-region: ap-northeast-2

     # Test Result
    - name: Report Unit Test Result
      uses: dorny/test-reporter@v1
      if: always()  # success() || failure()
      with:
       name: Test Result
       path: build/test-results/**/*.xml
       reporter: java-junit
       fail-on-error: true
```

* Report 확인

![3](img/Github Actions/3.png)

* log 파일로 다운 받아서 결과를 확인 또한 가능

![4](img/Github Actions/4.png)
