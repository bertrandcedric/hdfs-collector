package com.collector;

import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.hadoop.fs.FsShell;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HdfsWriter {

    private static final Logger logger = LoggerFactory.getLogger(HdfsWriter.class);
    private final String destinationDirectory;
    private final FsShell fsShell;
    private final String backupDirectory;

    public HdfsWriter(String destinationDirectory,
                      String backupDirectory,
                      Configuration configuration) {
        Assert.notNull(destinationDirectory,"Destination directory must not be null.");
        this.destinationDirectory = destinationDirectory;
        Assert.notNull(backupDirectory,"Backup directory must not be null.");
        this.backupDirectory = backupDirectory;
        Assert.notNull(configuration, "Hadoop Configuration must not be null.");
        this.fsShell = new FsShell(configuration);
    }

    public void processMessage(Message<File> message) throws IOException {
        File file = message.getPayload();
        logger.info("Copy file to HDFS : {}", file);
        String dst = destinationDirectory + File.separator + file.getName();
        fsShell.copyFromLocal(file.getAbsolutePath(), dst);
        Files.createDirectories(Paths.get(backupDirectory));
        if (fsShell.test(dst)) Files.move(Paths.get(file.getAbsolutePath()), Paths.get(backupDirectory + File.separator + file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }
}
