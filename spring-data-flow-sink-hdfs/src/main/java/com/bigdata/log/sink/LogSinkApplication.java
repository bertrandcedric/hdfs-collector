package com.bigdata.log.sink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(LogSinkConfiguration.class)
public class LogSinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogSinkApplication.class, args);
	}
}
