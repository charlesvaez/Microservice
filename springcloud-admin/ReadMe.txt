Spring Boot Admin是一个开源社区项目，用于管理和监控SpringBoot应用程序。 应用程序作为Spring Boot Admin Client向为Spring Boot Admin Server注册（通过HTTP）或使用SpringCloud注册中心（例如Eureka，Consul）发现。
UI是的AngularJs应用程序，展示Spring Boot Admin Client的Actuator端点上的一些监控。

常见的功能或者监控如下：
    显示健康状况
    显示详细信息，例如
    JVM和内存指标
    micrometer.io指标
    数据源指标
    缓存指标
    显示构建信息编号
    关注并下载日志文件
    查看jvm系统和环境属性
    查看Spring Boot配置属性
    支持Spring Cloud的postable / env-和/ refresh-endpoint
    轻松的日志级管理
    与JMX-beans交互
    查看线程转储
    查看http跟踪
    查看auditevents
    查看http-endpoints
    查看计划任务
    查看和删除活动会话（使用spring-session）
    查看Flyway / Liquibase数据库迁移
    下载heapdump
    状态变更通知（通过电子邮件，Slack，Hipchat，…）
    状态更改的事件日志（非持久性）
