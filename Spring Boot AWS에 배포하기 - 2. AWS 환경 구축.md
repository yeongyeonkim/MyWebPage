### AWS 환경 구축

---

##### EC2 생성

* OS 이미지 - Amazon Linux or Ubuntu
* 키 페어 생성
* 탄력적 IP 생성 후 연결


![1](img/AWS/1.png)

* SSH 클라이언트 서버 접속

  ![2](img/AWS/2.png)

---

##### Host로 등록하여 접속 설정

1. 키 페어 파일 복사 

   * `cp api.pem ~/.ssh/`

2. 파일 권한 변경

   * `chmod 600 ~/.ssh/api.pem`

3. config 파일 생성 및 내용 입력

   * `vi ~/.ssh/config`

     ![3](img/AWS/3.png)

4. Hostname으로 접속

   ![4](img/AWS/4.png)

---

##### 태그 추가

![5](img/AWS/5.png)

---

##### 인스턴스에 IAM 역할 설정 (S3 파일에 접근하기 위함)

![6](img/AWS/6.png)

![7](img/AWS/7.png)

![8](img/AWS/8.png)

---

##### S3

* 버킷 생성 (퍼블릭 액세스를 비롯한 모든 설정 default)

  ![9](img/AWS/9.png)

---

##### CodeDeploy Agent

* 설치 ([공식 문서](https://docs.aws.amazon.com/ko_kr/codedeploy/latest/userguide/codedeploy-agent-operations-install-linux.html))

```bash
sudo yum update -y
sudo yum install ruby -y
sudo yum install wget -y

#!/bin/bash
CODEDEPLOY_BIN="/opt/codedeploy-agent/bin/codedeploy-agent"
$CODEDEPLOY_BIN stop
yum erase codedeploy-agent -y

cd /home/ec2-user
wget https://aws-codedeploy-ap-northeast-2.s3.ap-northeast-2.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
sudo service codedeploy-agent status
```

* CodeDeploy 역할 생성

  ![10](img/AWS/10.png)

* CodeDeploy 애플리케이션 생성

  ![11](img/AWS/11.png)

  ![12](img/AWS/12.png)

* CodeDeploy 배포 그룹 생성

  ![13](img/AWS/13.png)

  * 생성한 iam role 적용

  ![14](img/AWS/14.png)

  * 대상이 될 EC2 인스턴스의 Tag 명을 기재

  ![15](img/AWS/15.png)

  ![16](img/AWS/16.png)

---

