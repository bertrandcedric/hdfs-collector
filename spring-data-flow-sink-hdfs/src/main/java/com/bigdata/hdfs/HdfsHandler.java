package com.bigdata.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.springframework.data.hadoop.fs.FsShell;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HdfsHandler extends AbstractMessageHandler {

    private volatile String destinationDirectory;

    private volatile String backupDirectory;

    private volatile String fsUri;

    private volatile FsShell fsShell;

    public HdfsHandler(String destinationDirectory, String backupDirectory, String fsUri) {
        this.destinationDirectory = destinationDirectory;
        this.backupDirectory = backupDirectory;
        this.fsUri = fsUri;
    }

    @Override
    protected void onInit() throws Exception {
        super.onInit();

        Configuration cf = new Configuration();
        cf.set(CommonConfigurationKeys.FS_DEFAULT_NAME_KEY, fsUri);
        fsShell = new FsShell(cf);
    }

    @Override
    protected void handleMessageInternal(Message<?> message) throws Exception {
        File file = (File) message.getPayload();
        String dstFile = destinationDirectory + File.separator + file.getName();
        String backupFile = backupDirectory + File.separator + file.getName();

        logger.info("Copy file to HDFS : " + file.getAbsolutePath() + " to " + dstFile + " and backup to " + backupFile);

        fsShell.copyFromLocal(file.getAbsolutePath(), dstFile);

        if (fsShell.test(dstFile))
            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(backupFile), StandardCopyOption.REPLACE_EXISTING);
    }

    public String getBackupDirectory() {
        return backupDirectory;
    }

    public void setBackupDirectory(String backupDirectory) {
        this.backupDirectory = backupDirectory;
    }

    public String getFsUri() {
        return fsUri;
    }

    public void setFsUri(String fsUri) {
        this.fsUri = fsUri;
    }

    public String getDestinationDirectory() {

        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }
}
