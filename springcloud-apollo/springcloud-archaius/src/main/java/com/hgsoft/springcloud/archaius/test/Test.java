package com.hgsoft.springcloud.archaius.test;

import com.netflix.config.*;
import com.netflix.config.sources.URLConfigurationSource;
import com.sun.deploy.ref.Helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws IOException {
//        DynamicStringProperty stringProperty = DynamicPropertyFactory.getInstance().getStringProperty( "springcloud.archaius.properties.one", "" );
//        System.out.println( stringProperty.get() );

//        ConfigurationManager.getConfigInstance().setProperty( "name", "Jay" );
//
//        ConfigurationManager.getConfigInstance().setProperty( "name", "Join" );
//
//        System.out.println( ConfigurationManager.getConfigInstance().getString( "name" ) );

//      加载application.properties文件
//        ConfigurationManager.loadCascadedPropertiesFromResources( "application" );
//        System.out.println( ConfigurationManager.getConfigInstance().getString( "server.port" ) );

        PolledConfigurationSource source = new DynamicConfigurationSource();
//        PolledConfigurationSource source = new URLConfigurationSource("classpath:secondConfig.properties");
//        archaius.fixedDelayPollingScheduler.initialDelayMills	延迟加载，默认30秒	30000
//        archaius.fixedDelayPollingScheduler.delayMills	两次属性读取时间间隔，默认1分钟	60000
        AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(2000,2000,true);
        DynamicConfiguration configuration = new DynamicConfiguration(source,scheduler);
        ConfigurationManager.install(configuration);
        final DynamicStringProperty stringProperty = DynamicPropertyFactory.getInstance().getStringProperty("springcloud.archaius.properties.one","defaultValue");

        while(true){
            System.out.println("property: "+stringProperty.get());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class DynamicConfigurationSource implements PolledConfigurationSource {
        @Override
        public PollResult poll(boolean initial,Object checkPoint) throws Exception {
//            Map<String,Object> map = new HashMap<>();
//            map.put("test", UUID.randomUUID().toString());
//            return PollResult.createFull(map);
            System.out.println("polling.....");
            Map<String, Object> map = new HashMap<>();
            FileInputStream in = null;
            try {
                in = new FileInputStream("E:\\workspaces\\intellij\\2018\\springcloud\\springcloud-apollo\\springcloud-archaius\\src\\main\\resources\\config.properties");
                Properties properties = new Properties();
                properties.load(in);
                Set<Object> keys = properties.keySet();

                for (Object k : keys) {
                    map.put((String) k, properties.get(k));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null)
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            return PollResult.createFull(map);

        }
    }
}
