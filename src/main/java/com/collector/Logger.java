package com.collector;

import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    public void processMessage(Message<File> message) throws IOException {
        File file = message.getPayload();
        long size = Files.size(Paths.get(file.getAbsolutePath()));
        logger.info("Storing for history : {} - size : {} bytes", file, size);
    }
}
