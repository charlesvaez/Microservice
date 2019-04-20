Jasypt-spring-boot-starter的使用方法
我们引入项目的dependency：
pom.xml
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>

首先，我们需要定义一个标记，来告诉工具哪些属性是需要解密的。比方说，我们在加密的属性前增加{cipher}字样：
application.properties
spring.datasource.username=rmproject
spring.datasource.password={cipher}MTJiN3JtcHJvamVjdA==

为了支持自定义的加密属性前缀，需要提供自己实现的EncryptablePropertyDetector类
public class MyEncryptablePropertyDetector implements EncryptablePropertyDetector {

    public static final String ENCODED_PASSWORD_HINT = "{cipher}";

    // 如果属性的字符开头为"{cipher}"，返回true，表明该属性是加密过的
    @Override
    public boolean isEncrypted(String s) {
        if (null != s) {
            return s.startsWith(ENCODED_PASSWORD_HINT);
        }
        return false;
    }
    // 该方法告诉工具，如何将自定义前缀去除
    @Override
    public String unwrapEncryptedValue(String s) {
        return s.substring(ENCODED_PASSWORD_HINT.length());
    }
}

有了自定义加密属性的检测方法，我们还需要告诉工具如何进行解密操作：
public class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {
    //自定义解密方法
    @Override
    public String resolvePropertyValue(String s) {
        if (null != s && s.startsWith(MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT)) {
            return PasswordUtil.decode(s.substring(MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT.length()));
        }
        return s;
    }
}

最后，我们需要将他们注册为bean
// 入口类添加该注解，开启属性自动解密功能
@EnableEncryptableProperties
public class DemoSpringBootApplication {
    //  注册这两个bean
    @Bean(name = "encryptablePropertyDetector")
    public EncryptablePropertyDetector encryptablePropertyDetector() {
        return new MyEncryptablePropertyDetector();
    }

    @Bean(name = "encryptablePropertyResolver")
    public EncryptablePropertyResolver encryptablePropertyResolver() {
        return new MyEncryptablePropertyResolver();
    }
}

