package com.bigdata.hdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(HdfsSinkConfiguration.class)
public class HdfsSinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HdfsSinkApplication.class, args);
    }
}
