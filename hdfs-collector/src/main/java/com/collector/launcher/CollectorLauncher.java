package com.collector.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.Scanner;

@SpringBootApplication
@ImportResource("classpath:/META-INF/spring/collector-context.xml")
public class CollectorLauncher {

	public static void main(String[] args) throws Exception {
        SpringApplication.run(CollectorLauncher.class, args);
		Scanner scanIn = new Scanner(System.in);
	    scanIn.nextLine();
	}
}
