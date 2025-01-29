FROM fluent/fluentd:v1.18-1

USER root
ENV FLUENTD_DISABLE_BUNDLER_INJECTION 1

# Elasticsearch 7.x에 맞는 클라이언트 버전(7.17.10) 설치
RUN gem install elasticsearch -v 7.17.10 && \
    gem install fluent-plugin-elasticsearch --no-document
    
USER fluent

CMD ["fluentd", "-c", "/fluentd/etc/fluent.conf", "-p", "/fluentd/plugins"]
