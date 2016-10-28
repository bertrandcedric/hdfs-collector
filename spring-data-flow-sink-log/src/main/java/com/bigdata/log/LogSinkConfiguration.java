package com.bigdata.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;

@EnableBinding(Sink.class)
@EnableConfigurationProperties(LogSinkProperties.class)
public class LogSinkConfiguration {

	@Autowired
	private LogSinkProperties properties;

	@Bean
	@ServiceActivator(inputChannel = Sink.INPUT)
	public CustomLoggingHandler logSinkHandler() {
        CustomLoggingHandler loggingHandler = new CustomLoggingHandler(this.properties.getLevel().name());
		loggingHandler.setExpression(this.properties.getExpression());
		loggingHandler.setLoggerName(this.properties.getName());
		return loggingHandler;
	}

}
