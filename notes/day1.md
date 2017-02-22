# day1

准备工作：

- 为了方便后续开发，需要对hosts文件进行调整，以减少配置文件在云端、本地之间的差距，因此将需要的服务注册到本地hosts中，具体怎么修改请参考链接
[win10下的hosts修改](http://jingyan.baidu.com/article/624e7459b194f134e8ba5a8e.html)，*nix下的修改直接sudo或者：wq!即可
    - 当在命令行中ping以下任何一个host能够被解析成127.0.0.1就代表配置成功
```config

127.0.0.1 discovery config-server gateway movie user feign ribbon

```

- 在开始编写内容之前，需要准备一个父项目的pom文件，以便在整个过程中对各个包进行管理，详情参见[父文件夹下code/pom.xml](../code/pom.xml)


# Eureka

1. 是啥：
    - Eureka本身是一个基于REST结构的负载均衡器和链接件服务器，详情参考[官方文档](https://github.com/Netflix/eureka/wiki)
2. 可选替代品：
    - 在不考虑兼容性情况下，我们可以使用:Zookeeper、Consul等其他服务发现组件
3. 结论：
    - Eureka其实就是自动化的服务连接服务中间件，可以自动配置各个服务，达到集群控制的功能

# Eureka项目的创建与初始化

1. 创建过程请参考spring-boot的[官方引导](http://spring.io/guides/gs/actuator-service/)
    - 需要创建src/main/java 和 src/main/resources

2. 当访问discovery:port能够见到Eureka的画面时，代表操作成功

3. 关于基础的配置文件应该怎么写，可以参考[本次案例](../code/microservice-discovery-eureka/src/main/resources/application.yml)

4. 几个比较重要的注解和功能
    - 1
    - 2
    - 3

5. 第一天不考虑高可用问题，因此不在此处赘述，但大致可以解释为，在配置文件中制定接收参数，在启动时以目标参数启动即可，关于如何实现可以参考：
    - spring-boot文档中的[配置部分](http://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/reference/htmlsingle/)
    - [本文的参考资料](http://git.oschina.net/itmuch/spring-cloud-book/blob/master/2%20Spring%20Cloud/2.1.2%20Eureka%E7%9A%84%E9%AB%98%E5%8F%AF%E7%94%A8.md?dir=0&filepath=2+Spring+Cloud%2F2.1.2+Eureka%E7%9A%84%E9%AB%98%E5%8F%AF%E7%94%A8.md&oid=a0de7baa7526802ce4d701952ef9ceba41ca4b55&sha=77018435fececa0db89d4c0f4ea91553b8b99ab0)
    - 以及最靠谱的版本：[spring cloud 文档](http://cloud.spring.io/spring-cloud-static/Brixton.SR5/#spring-cloud-eureka-server)


# Service Provider

具体到能够提供服务的实体，根据现在通常意义上讲的微服务架构，就是一些能够提供REST服务的包，这些包往往会继承自web-starter中，向外提供服务的同时，需要向 Eureka 服务器注册为客户端，由 Eureka 集中管理

## 需要的操作

在这里不赘述一个Spring boot 的web应用应该如何使用，详情参见Spring MVC等文档

- 首先需要在该项目目录下配置目标 Eureka 服务器，该配置可以由启动参数指定，但是由于云端环境特殊，所以可以尝试现在YAML文件中加入描述内容

spring.application决定的是当前工程的属性，application.name则代表本工程在spring cloud环境下，可以被索引的名字

eureka下的内容则是配置作为服务提供者在服务器下的属性信息，比如这里的最简化的设置，作为client的时候，应该在哪个默认的 eureka 服务器上注册自己，在 eureka 服务器中，是否优先使用IP地址来查找实例，而非hostname。

```YAML

spring:
  application:
    name: microservice-provider-user    # 项目名称尽量用小写

eureka:
  client:
    serviceUrl:
      defaultZone: http://discovery:9000/eureka/    # 指定注册中心的地址
  instance:
    preferIpAddress: true                           

```

如果想体验集群形式的service-provider的话，可以在启动时修改server下的各个配置，如`server.port=8001`等方式，运行多个实例，只要将服务注册在同一个 Eureka 服务群（不仅仅指服务器）中，就可以获得多个实例


# 服务消费者

服务消费者本身也是REST服务的提供者，只是需要通过REST方式去依赖其他service-provider。

一个服务消费者的基本调用方法在于如下代码：

首先，在项目的启动类中，定义RESTtemplate类的获取方式，并利用@LoadBalance注解加强，使其在解析url时，能够根据spring cloud根据服务id解析到不同的实例上，从而具有负载平衡的功能

```java
@Bean
@LoadBalanced
public RestTemplate restTemplate(){
    return new RestTemplate();
}
```

不用担心@Bean注解是否会导致spring内部的错误，别忘了，每一个生产者、消费者，本质上都是单独启动运行的实例，这个方法和注解只会在Spring加载时有效

```java
@Autowired
RestTemplate restTemplate;

public User searchUser(String id){
    return restTemplate.getForObject("http://microservice-provider-user/"+id, User.class);
}

```

```YAML
server:
  port: 8010
spring:
  application:
    name: microservice-consumer-movie-ribbon
eureka:
  client:
    serviceUrl:
      defaultZone: http://discovery:9000/eureka/
  instance:
    preferIpAddress: true

```

当然，在配置文件中，千万不要忘了补充具有生产者的Eureka服务发现器，其余的部分完全可以当做一个普通的spring MVC框架下的webapp编写


# 第一天结束

那么到现在，一个完整的，具有初步微服务雏形的项目就完成了。具体花费了大概两到三个小时的时间进行编写和学习，如果对Spring Boot框架比较熟悉，这个时间还能更短

Feign方式同样可以实现消费者的调用，但是比起Ribbon，理解起来和代码风格都更为玄幻（= =只能这么描述了），不在项目中演示

