package com.bigdata.hdfs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;

@EnableBinding(Sink.class)
@EnableConfigurationProperties(HdfsSinkProperties.class)
public class HdfsSinkConfiguration {

	@Autowired
	private HdfsSinkProperties properties;

	@Bean
	@ServiceActivator(inputChannel = Sink.INPUT)
	public HdfsHandler hdfsSinkHandler() {
        HdfsHandler hdfsHandler = new HdfsHandler(
        		this.properties.getDestinationDirectory(),
				this.properties.getBackupDirectory(),
				this.properties.getFsUri());
		return hdfsHandler;
	}

}
