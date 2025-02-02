#!/bin/sh
# 로그 파일이 없으면 생성
touch /var/log/test_sys.log
# fluent 유저가 파일을 사용할 수 있도록 변경
chown fluent:fluent /var/log/test_sys.log
chmod 644 /var/log/test_sys.log

touch /var/log/test_user.log
# fluent 유저가 파일을 사용할 수 있도록 변경
chown fluent:fluent /var/log/test_user.log
chmod 644 /var/log/test_user.log
# Fluentd 실행
# exec fluentd -c /fluentd/etc/fluent.conf -p /fluentd/plugins
