package org.app;

import lombok.extern.log4j.Log4j2;
import org.app.config.Config;
import org.app.service.WatcherService;
import org.app.util.FileUtil;

import java.io.IOException;

@Log4j2
public class FileWatcherApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        var config = new Config("movement-config.yaml");
        LOGGER.info("File Watcher Started");
        FileUtil.createFolders(config.getMoveDirectory(), config.getFileClassificationNames());

        WatcherService watcherService = new WatcherService(config);
        watcherService.watch(config.getTrackDirectory());
    }
}
