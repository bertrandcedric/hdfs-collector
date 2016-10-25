package com.collector;

import com.collector.launcher.CollectorLauncher;
import org.apache.hadoop.conf.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Scanner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/collector-context.xml"})
public class CollectorIntegrationTests {

    private static final Logger logger = LoggerFactory.getLogger(CollectorIntegrationTests.class);

    @Autowired
    private Configuration configuration;

    @Test
    public void collect() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                "/META-INF/spring/collector-context.xml", CollectorLauncher.class);
        logger.debug("File Polling Application Running");
        context.registerShutdownHook();
        Scanner scanIn = new Scanner(System.in);
        scanIn.nextLine();
    }

}
