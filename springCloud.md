### payment8001

##### 建表语句

```sql
CREATE TABLE `payment` ( 
`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT 'ID', 
`serial` VARCHAR ( 200 ) DEFAULT '', PRIMARY KEY ( `id` ) 
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8
```

```xml
当xml文件与Mapper接口文件在同一目录可不配置mybatis.mapper-locations
配置多个mapper位置
ybatis:
  mapper-locations: classpath*:mapper/*.xml,classpath*:com/urthink/upfs/**/*Mapper.xml
```


