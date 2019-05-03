Archaius是Apache公共配置库的扩展， 它允许您从多个动态源中检索属性，并且它解决了前面提到的所有问题（异构的属性源，运行时更改等）。

archaius是读取类路径中某处的“config.properties”文件,如果不想或不能将属性文件命名为“config.property”，该怎么办？在这种情况下，您需要告诉Archaius在哪里查找此文件。
您可以轻松地更改系统属性'archaius.configurationSource.defaultFileName'，在启动应用程序时将其作为参数传递给vm：java ... -Darchaius.configurationSource.defaultFileName=customName.properties

如果想读几个属性文件怎么办？可以从首先加载的默认文件开始，轻松定义属性文件链及其加载顺序。从那里，您可以使用键“@ next = nextFile.properties”指定一个特殊属性来告诉Archaius哪个是
应该加载的下一个文件。
    在我们的示例中，我们可以在“customConfig.properties”文件中添加以下行：
    @next=secondConfig.properties

    并将相应的“secondConfig.properties”添加到我们的resources文件夹中，其中包含以下内容：
    cascade.property=cascade value



可以通过
-Darchaius.fixedDelayPollingScheduler.initialDelayMills 设置初始化延时，
-Darchaius.fixedDelayPollingScheduler.delayMills设置多久重新加载一次
 ConcurrentCompositeConfiguration 也可以禁止加载系统配置和环境配置
-Darchaius.dynamicProperty.disableSystemConfig 不加载系统配置
-Darchaius.dynamicProperty.disableEnvironmentConfig 不加载环境配置
-Darchaius.dynamicPropertyFactory.registerConfigWithJMX 设置启用JMX

archaius.configurationSource.defaultFileName	指定Archaius默认加载的配置源属性文件名，默认：classpath:config.properties
archaius.fixedDelayPollingScheduler.initialDelayMills	延迟加载，默认30秒	30000
archaius.fixedDelayPollingScheduler.delayMills	两次属性读取时间间隔，默认1分钟	60000




