package com.collector;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.fs.FsShell;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/hadoop-context.xml"})
public class CollectorTests {

    private static final Logger logger = LoggerFactory.getLogger(CollectorTests.class);

    @Autowired
    private Configuration configuration;

    private HdfsWriter handler;
    private String outputDirectory = "/tmp/hadoop/test/output/";
    private String backupDirectory = "/tmp/hadoop/test/backup/";
    private FsShell fsShell;
    private File sourceFile;

    @Rule
    public TemporaryFolder temp = new TemporaryFolder() {
        public void create() throws IOException {
            super.create();
            sourceFile = temp.newFile("sourceFile.txt");
            FileCopyUtils.copy("HelloWorld".getBytes("UTF-8"),
                    new FileOutputStream(sourceFile, false));
        }
    };

    @Before
    public void initAndClearHdfsFolder() {
        handler = new HdfsWriter(outputDirectory,backupDirectory, configuration);
        fsShell = new FsShell(configuration);
        if (fsShell.test(outputDirectory)) fsShell.rmr(outputDirectory);
    }

    @Test
    public void listFile() throws Exception {
        Message<File> message = MessageBuilder.withPayload(sourceFile).build();
        handler.processMessage(message);

        Collection<FileStatus> status = fsShell.ls(outputDirectory).stream().filter(fileStatus -> fileStatus.isDirectory()).collect(Collectors.toList());
        logger.debug("{}", status);
        Assert.assertEquals(1, status.size());
    }
}
