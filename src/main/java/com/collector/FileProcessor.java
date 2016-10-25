package com.collector;

import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.hadoop.fs.FsShell;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

import java.io.File;
import java.nio.file.Files;

public class FileProcessor {

    private static final Logger logger = LoggerFactory.getLogger(FileProcessor.class);

    private final String destinationDirectory;
    private final String errorDirectory;
    private final FsShell fsShell;

    public FileProcessor(String destinationDirectory,
                         String errorDirectory,
                         Configuration configuration) {
        Assert.notNull(configuration, "Hadoop Configuration must not be null.");
        this.fsShell = new FsShell(configuration);
        Assert.notNull(destinationDirectory, "Destination directory must not be null.");
        this.destinationDirectory = destinationDirectory;
        Assert.notNull(errorDirectory, "Error directory must not be null.");
        this.errorDirectory = errorDirectory;
    }

    public File handle(Message<File> message) {
        File file = message.getPayload();

        String dst = destinationDirectory + File.separator + file.getName();
        if (fsShell.test(dst)) {
            logger.info("File already exists in HDFS: {}", dst);
            new File(errorDirectory).mkdirs();
            file.renameTo(new File(errorDirectory + File.separator + file.getName()));
            return null;
        } else {
            logger.info("Processing file: {}", file.getAbsolutePath());
            return file;
        }
    }
}
