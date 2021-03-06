# Prerequis

```
/opt/zookeeper-3.4.9/bin/zkServer.sh start
/opt/kafka_2.11-0.10.1.0/bin/kafka-server-start.sh /opt/kafka_2.11-0.10.1.0/config/server.properties
sudo service redis_6379 start
```

# Admin spring boot

```
java -jar admin/target/admin-1.0.0-SNAPSHOT.jar

http://0.0.0.0:8080/
```

# Spring data-flow server + shell (il faut cloner le projet github : https://github.com/spring-cloud/spring-cloud-dataflow.git)

## démarrage :
```
java -jar ~/workspace/spring-cloud-dataflow/spring-cloud-dataflow-server-local/target/spring-cloud-dataflow-server-local-1.1.0.BUILD-SNAPSHOT.jar

http://0.0.0.0:9393/dashboard

java -jar ~/workspace/spring-cloud-dataflow/spring-cloud-dataflow-shell/target/spring-cloud-dataflow-shell-1.1.0.BUILD-SNAPSHOT.jar
```

## Init stream

```
app import --uri http://bit.ly/stream-applications-kafka-maven
app unregister --name custom_hdfs --type sink
app register --name custom_hdfs --type sink --uri file:///home/vinte/workspace/bigdata/spring-data-flow-sink-hdfs/target/spring-data-flow-sink-hdfs-1.0.0-SNAPSHOT.jar

stream destroy --name counter
stream destroy --name log
stream destroy --name hdfs

stream create --definition "in-file: file --directory=/tmp/hadoop/input --fixed-delay=5 --mode=ref --filename-pattern=*.txt | custom_hdfs --destinationDirectory=/tmp/hadoop/output --backupDirectory=/tmp/hadoop/backup --fs-uri=hdfs://0.0.0.0:32770" --name hdfs
stream create --definition ":hdfs.in-file > counter" --name counter
stream create --definition ":hdfs.in-file > log" --name log

stream deploy --name hdfs
stream deploy --name counter
stream deploy --name log
```

## Test copie fichier

mettre des fichiers dans /tmp/hadoop/input
ils doivent se retrouver dans /tmp/hadoop/output sur hadoop

```
touch input/{{a..z},{A..Z},{0..99}}.txt
```

# Configuration qui a été ajouté dans le code de spring-cloud-dataflow/spring-cloud-dataflow-server-local

## modification pom.xml :
```
<dependency>
   <groupId>de.codecentric</groupId>
   <artifactId>spring-boot-admin-starter-client</artifactId>
   <version>1.4.3</version>
</dependency>
<dependency>
   <groupId>org.jolokia</groupId>
   <artifactId>jolokia-core</artifactId>
   <version>1.3.5</version>
</dependency>

<include>bootstrap.yml</include>
<include>logback-spring.xml</include>
```

## modification application.yml :
```
spring:
  boot:
    admin:
      url: http://localhost:8080
```   

## ajout bootstrap.yml : 
```
spring:
  cloud:
    config:
      enabled: false
```

## ajout logback-spring.xml :
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml"/>
    <jmxConfigurator/>
</configuration>
```
