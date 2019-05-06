编写代码
1 加依赖
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>

    这里，我们为应用引入了 micrometer-registry-prometheus ，事实上，你想对接上文列表中的哪款监控系统，就写啥。例如想对接 Influx ，则需添加依赖 micrometer-registry-influx 。

2 写配置
    server:
      port: 8080
    spring:
      application:
        name: prometheus-test
    management:
      endpoints:
        web:
          exposure:
            include: 'prometheus'
      metrics:
        tags:
          application: ${spring.application.name}
    如配置所示，指定应用名为 prometheus-test ，并将 Actuator 的 /actuator/prometheus 端点暴露出来；management.metrics.tags.application=prometheus-test 作用是为指标设置一个名为application="prometheus-test" 的Tag，Tag是Prometheus提供的一种能力，从而实现更加灵活的筛选。

    测试
        1 启动应用
        2 访问 http://localhost:8080/actuator/prometheus 可获得类似如下的结果：

安装Prometheus
在/root/prometheus目录下创建prometheus.yml文件
    scrape_configs:
    # 任意写，建议英文，不要包含特殊字符
    - job_name: 'spring'
      # 多久采集一次数据
      scrape_interval: 15s
      # 采集时的超时时间
      scrape_timeout: 10s
      # 采集的路径是啥
      metrics_path: '/actuator/prometheus'
      # 采集服务的地址，设置成上面Spring Boot应用所在服务器的具体地址。
      static_configs:
      - targets: ['192.168.237.101:8772']

docker run -d -p 9090:9090 -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
--$(pwd)为/root/prometheus目录

docker run -d -p 3000:3000 grafana/grafana

登录：访问 http://192.168.237.101:3000/login ，初始账号/密码为：admin/admin

dashboards labs
    https://grafana.com/dashboards



SpringCloud使用Prometheus监控(基于Eureka)
修改prometheus.yml文件
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'consul-prometheus'
    scheme: http
    metrics_path: /actuator/prometheus
    consul_sd_configs:
    #consul 地址
    - server: 'localhost:8000'
      scheme: http
      #SPRINGCLOUD-PROMETHEUS-PROVIDER-EUREKA要与eureka中注册的服务名一致，${spring.application.name}
      services: [SPRINGCLOUD-PROMETHEUS-PROVIDER-EUREKA]