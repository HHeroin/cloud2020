### payment8001

##### 建表语句

```sql
CREATE TABLE `payment` ( 
`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT 'ID', 
`serial` VARCHAR ( 200 ) DEFAULT '', PRIMARY KEY ( `id` ) 
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8

windows上连接mysql5.5项目启动运行报错serverTimezone则在数据库执行 set global time_zone = '+8:00'; 
```


```xml
当xml文件与Mapper接口文件在同一目录可不配置mybatis.mapper-locations
配置多个mapper位置
ybatis:
  mapper-locations: classpath*:mapper/*.xml,classpath*:com/urthink/upfs/**/*Mapper.xml
```

```java
Payment对象可通过url后的参数获得 或者 通过form表单获得值
@PostMapping("/create")
public CommonResult<Integer> create(Payment payment)

如果通过请求体提交数据则使用@RequestBoay接收对象（前端json请求）
@PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody Payment payment)
```

mybatis
```xml
useGeneratedKeys : 获取数据库自动产生的主键 keyColumn主键对应的数据库列可不指定 keyProperty将主键值映射到入参对象中的属性
接口返回的是受影响的行数（插入成功行数）

int create(Payment payment);
<insert id="create" parameterType="com.guowii.payment.entity.Payment" useGeneratedKeys="true" keyColumn="id" keyProperty="id"  >
    insert into payment (serial) values (#{serial})
</insert>
```



### Order80

1. 参照payment8001工程，在order微服务中使用restTemplate调用payment服务

2. 配置类创建restTemplate

   ```java
   @Configuration
   public class ApplicationContextConfig {
   
       @Bean
       public RestTemplate restTemplate() {
           return new RestTemplate();
       }
   }
   ```

3. OrderController
   使用restTemplate的getForObject、PostForObject进行get/post请求payment接口

   ```java
   @RestController
   @RequestMapping("/consumer")
   public class OrderController {
   
       @Resource
       private RestTemplate restTemplate;
   
       private static final String PAYMENT_URL = "http://localhost:8001/payment";
   
       @GetMapping("/payment/get/{id}")
       public CommonResult<Payment> getPayment(@PathVariable("id") String id) {
           return restTemplate.getForObject(PAYMENT_URL + "/get/" + id, CommonResult.class);
       }
   
       @PostMapping("/payment/create")
       public CommonResult<Integer> create(Payment payment) {
           return restTemplate.postForObject(PAYMENT_URL + "/create",payment,CommonResult.class);
       }
   }
   ```



### Eureka7001注册中心单机版

[Eureka wiki]: https://github.com/Netflix/eureka/wiki

![Eureka High level Architecture](./images/eureka_architecture.png)

1. pom依赖

   ```xml
   <!--eureka server-->
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   </dependency>
   ```

2. yml配置

   ```yml
   server:
     port: 7001
   
   spring:
     application:
       name: cloud-eureka-server
   
   eureka:
     instance:
       hostname: localhost
       prefer-ip-address: true
     client:
       register-with-eureka: false
       fetch-registry: false
       service-url:
         defaultZone: http://${spring.application.name}:${server.port}/eureka/
   ```

   

3. 主启动类添加`@EnableEurekaServer`

   ```java
   @SpringBootApplication
   @EnableEurekaServer
   public class Eureka7001 {
       public static void main(String[] args) {
           SpringApplication.run(Eureka7001.class,args);
       }
   }
   ```


### payment8001 order80进驻Eureka

1. 启动类增加`@EnableEurekaClient`注解

   ```java
   @SpringBootApplication
   @EnableEurekaClient
   public class Payment8001 {
       public static void main(String[] args) {
           SpringApplication.run(Payment8001.class,args);
       }
   }
   ```

   

2. 修改配置文件application.yml

   ```yml
   eureka:
     client:
       fetch-registry: true
       register-with-eureka: true
       service-url:
         defaultZone: http://localhost:7001/eureka
   ```

3. 一次启动Eureka7001及payment8001 order80微服务
   ![localhoost:7001](E:\java2020\cloud2020\images\eureka 微服务进驻.png)

### Eureka集群环境搭建

原理：相互注册、互相守望

1. 修改host文件

   ```txt
   127.0.0.1	eureka7001.com
   127.0.0.1	eureka7002.com
   ```

   

2. 修改配置文件

   ```yml
   eureka:
     instance:
       hostname: eureka7001.com
     client:
       register-with-eureka: false
       fetch-registry: true
       service-url:
         defaultZone: http://eureka7002.com:7002/eureka/
   ```

3. payment及order微服务修改注册中心地址

   ```yml
   defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
   ```

### order80通过restTemplate通过微服务名调用payment

1. 配置类中restTemplate添加负载均衡注解@LoadBalanced

   ```java
   @Bean
   @LoadBalanced
   public RestTemplate restTemplate() {
       return new RestTemplate();
   }
   ```

   

2. 将payment服务url替换为微服务名

   ```java
   @RestController
   @RequestMapping("/consumer")
   public class OrderController {
   
       @Resource
       private RestTemplate restTemplate;
   
       //private static final String PAYMENT_URL = "http://localhost:8001";
       private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
   
   
       @GetMapping("/payment/get/{id}")
       public CommonResult<Payment> getPayment(@PathVariable("id") String id) {
           return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
       }
   
       @PostMapping("/payment/create")
       public CommonResult<Integer> create(Payment payment) {
           return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
       }
   }
   ```

### DiscoveryClient服务发现

获取注册中心有哪些微服务及微服务实例的具体信息（uri port ）

1. 注解开启服务发现

   ```
   @SpringBootApplication
   @EnableEurekaClient
   @EnableDiscoveryClient
   public class Payment8001 {
       public static void main(String[] args) {
           SpringApplication.run(Payment8001.class,args);
       }
   }
   
   ```

   

2. 代码中直接注入DiscoveryClient对象

   `discoveryClient.getServices() `获取所有服务ID列表

   `discoveryClient.getInstances(String serviceId)`  获取某一serviceId的实例信息

   ```java
   @Autowired
   private DiscoveryClient discoveryClient;
   
   @GetMapping("/discovery")
       public Object getDiscoveryClient() {
           List<String> services = discoveryClient.getServices();
           for (String service : services) {
               List<ServiceInstance> instances = discoveryClient.getInstances(service);
               log.info("instances--{} :{},url",service,instances);
               for (ServiceInstance instance : instances) {
                   log.info("uri:{}",instance.getUri());
               }
           }
           return services;
       }
   ```

   

### Eureka自我保护机制

当一个微服务不可用时，Eureka不会立刻清理，依旧会对该微服务的信息进行保存

关闭自我保护机制

1. 修改Eureka Server

   ```
   eureka:
     server:
       # 关闭自我保护机制
       enable-self-preservation: false
       # 剔除检测时间
       eviction-interval-timer-in-ms: 2000
   ```

   

2. 修改Eureka Client

   ```
   eureka:
     instance:
       # 服务过期时间 （告诉注册中心超过这个时间没有收到心跳则剔除：默认90s）
       lease-expiration-duration-in-seconds: 1
       # 发送心跳时间 （告诉注册中心我还存活:默认30s）
       lease-renewal-interval-in-seconds: 1
   ```


### 使用Zookeeper替代Eureka注册中心

1. zookeeper快速安装

   ```cmd
   docker run --name zk -d -p 2181:2181 --privileged zookeeper:3.6.0
   
   docker exec -it zk /bin/bash
   cd bin
   ./zkCli.sh
   ```

2. payment8003进驻 zookeeper

   - pom.xml

     ```xml
     <!--zookeeper-->
             <dependency>
                 <groupId>org.springframework.cloud</groupId>
                 <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
                 <exclusions>
                     <!--排除自带版本-->
                     <exclusion>
                         <groupId>org.apache.zookeeper</groupId>
                         <artifactId>zookeeper</artifactId>
                     </exclusion>
                 </exclusions>
             </dependency>
     
             <!--使用与zookeeper服务器版本一致客户端-->
             <dependency>
                 <groupId>org.apache.zookeeper</groupId>
                 <artifactId>zookeeper</artifactId>
                 <version>3.4.14</version>
             </dependency>
     
             <!--web-->
             <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-starter-web</artifactId>
             </dependency>
     
     
             <!--actuator-->
             <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-starter-actuator</artifactId>
             </dependency>
     ```

   - application.yml

     ```
     server:
       port: 8003
     
     spring:
       application:
         name: cloud-payment-service
       cloud:
         zookeeper:
           connect-string: server.guowii.com:2181
     ```

     

   - 启动类添加`EnableDiscoveryClient`注解

   - 启动微服务查看zookeeper目录节点

     ![](E:\java2020\cloud2020\images\payment-zookeeper.png)

3. order80调用payment8003