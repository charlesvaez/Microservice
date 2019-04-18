1. SpringCloud2.0以后,没有/refresh手动调用的刷新配置地址
    在原来,只要加入依赖:
    <dependency>
       <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
     </dependency>

    并且在类上,变量上打上@RefreshScope的注解,在启动的时候,都会看到
    RequestMappingHandlerMapping : Mapped "{/refresh,methods=[post]}"

    也就是SpringCloud暴露了一个接口 /refresh 来给我们去刷新配置,但是SpringCloud 2.0.0以后,有了改变.

    我们需要在bootstrap.yml里面加上需要暴露出来的地址
    management:
      endpoints:
        web:
          exposure:
            include: refresh,health
    现在的地址也不是/refresh了,而是/actuator/refresh