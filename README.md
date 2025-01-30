#  Search Stack
### 이 프로젝트는 데이터 수집, 처리, 시각화를 통해 로그 데이터를 효과적으로 활용하는 방법을 배우는 데 중점을 둡니다.

## 목차
1. [Team](#1-team)
2. [Project intro & subject](#2-project-intro--subject)
3. [Stack and Tools](#3-stack-and-tools)


## 1. Team

👥 팀명 : 설날에 집에 있는 사람들

|<img src="https://avatars.githubusercontent.com/u/74342019?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/71498489?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/55776421?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/82265395?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/107902336?v=4" width="150" height="150"/>|
|:-:|:-:|:-:|:-:|:-:|
|나원호<br/>[@CooolRyan](https://github.com/CooolRyan)|한정현<br/>[@letsgojh0810](https://github.com/letsgojh0810)|이슬기<br/>[@seulg2027](https://github.com/seulg2027)|구민지<br/>[@minjee83](https://github.com/minjee83)|김대연<br/>[@dyoun12](https://github.com/dyoun12)|



## 2. Project intro & subject



## 3. Stack and Tools

| 기술           | 설명                         |
|----------------|------------------------------|
| <img src="https://img.shields.io/badge/fluentd-005571?style=for-the-badge&logo=fluentd&logoColor=white">   | 데이터 수집 및 처리 도구 |
| <img src="https://img.shields.io/badge/logstash-005571?style=for-the-badge&logo=logstash&logoColor=white">      | 데이터 수집 및 처리 도구      |
| <img src="https://img.shields.io/badge/elasticsearch-005571?style=for-the-badge&logo=elasticsearch&logoColor=white">    | 데이터 검색 및 분석 엔진      |
| <img src="https://img.shields.io/badge/kibana-005571?style=for-the-badge&logo=kibana&logoColor=white">        | 데이터 시각화 및 대시보드 도구 |


## 4. How to run


## 5. Troubleshooting

### Fluent에서 elasticsearch output plugin을 찾을 수 없는 오류
docker-compose 실행 중에 fluentd 컨테이너에서 다음과 같은 에러가 발생

```
2025-01-28 16:42:06 2025-01-28 07:42:06 +0000 [error]: config error file="/fluentd/etc/fluent.conf" error_class=Fluent::NotFoundPluginError error="Unknown output plugin 'elasticsearch'. Run 'gem search -rd fluent-plugin' to find plugins"
```

fluentd 컨테이너에 마운트된 fluent.conf 파일 내용

``` yml
<match **>

@type elasticsearch

host elasticsearch

port 9200

index_name fluentd-log

logstash_format true

</match>
```

@type elasticsearch으로 선언했으나 플러그인을 설치되지 않아 오류가 발생한 것으로 보인다.


[How to installation fluent-plugin-slasticsearch](https://github.com/uken/fluent-plugin-elasticsearch?tab=readme-ov-file#installation)


문제 해결을 위해 fluentd 이미지는 dockerfile을 이용해 해당 플러그인을 설치한 뒤 빌드하도록 설정한다.

```dockerfile
FROM fluent/fluentd:v1.18-1

USER root

ENV FLUENTD_DISABLE_BUNDLER_INJECTION 1

RUN gem install fluent-plugin-elasticsearch
  
USER fluent

CMD ["fluentd", "-c", "/fluentd/etc/fluent.conf", "-p", "/fluentd/plugins"]
```

--- 

### elasticsearch 7.11.1 과 elasticsearch client 8.17.1의 버전 차이 문제

elasticsearch 플러그인을 인식하지만 fluentd의 elasticsearch client의 버전 8.17.1과 elasticsearch image 버전 7.11.1 버전이 맞지 않아 fluentd 컨테이너가 종료되는 문제가 발생

```
2025-01-29 13:47:18 2025-01-29 04:47:18 +0000 [error]: #0 config error file="/fluentd/etc/fluent.conf" error_class=Fluent::ConfigError error="Using Elasticsearch client 8.17.1 is not compatible for your Elasticsearch server. Please check your using elasticsearch gem version and Elasticsearch server."

2025-01-29 13:47:18 2025-01-29 04:47:18 +0000 [error]: Worker 0 exited unexpectedly with status 2
```

dockerfile 빌드 시 Elasticsearch 7.11.1 버전에 맞는 elasticsearch gem 설치

```dockerfile
FROM fluent/fluentd:v1.18-1

USER root

ENV FLUENTD_DISABLE_BUNDLER_INJECTION 1

# Elasticsearch 7.x에 맞는 클라이언트 버전(7.17.10) 설치
RUN gem install elasticsearch -v 7.17.10 && \
gem install fluent-plugin-elasticsearch --no-document

USER fluent

CMD ["fluentd", "-c", "/fluentd/etc/fluent.conf", "-p", "/fluentd/plugins"]
```

### fluentd test.log 파일을 읽지 못하는 문제
컨테이너 내부 쉘 접속 후 파일 정보 확인

``` bash
/var/log $ ls -al 

total 8 
drwxr-xr-x 4 root root 128 Jan 29 05:25 . 
drwxr-xr-x 12 root root 4096 Sep 6 11:35 .. 
drwxr-xr-x 3 fluent fluent 96 Jan 29 05:01 fluentd 
-rw-r--r-- 1 1000 1000 308 Jan 29 05:25 test.log
```

test.log 파일의 사용자가 1000 1000 이를 fluent 유저로 변경하기 위해 dockerfile에서 entrypoint 지정

```bash
# entry.sh

# 로그 파일이 없으면 생성
touch /var/log/test.log

# fluent 유저가 파일을 사용할 수 있도록 변경
chown fluent:fluent /var/log/test.log
chmod 644 /var/log/test.log

# Fluentd 실행
exec fluentd -c /fluentd/etc/fluent.conf -p /fluentd/plugins
```

이미지 빌드 후 확인

```bash
/var/log $ ls -al 

total 8 
drwxr-xr-x 4 root root 128 Jan 29 05:25 . 
drwxr-xr-x 12 root root 4096 Sep 6 11:35 .. 
drwxr-xr-x 3 fluent fluent 96 Jan 29 05:01 fluentd 
-rw-r--r-- 1 fluent fluent 308 Jan 29 05:25 test.log
```
