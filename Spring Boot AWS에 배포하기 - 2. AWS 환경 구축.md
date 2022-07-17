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

