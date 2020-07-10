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


