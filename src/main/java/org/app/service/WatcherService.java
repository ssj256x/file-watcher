package org.app.service;

import lombok.extern.log4j.Log4j2;
import org.app.config.Config;
import org.app.util.FileUtil;

import java.io.IOException;
import java.nio.file.*;

@Log4j2
public class WatcherService {

    private final Config config;

    public WatcherService(Config config) {
        this.config = config;
    }

    public void watch(String trackDirectoryPath) throws IOException, InterruptedException {

        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(trackDirectoryPath);
        path.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE);

        WatchKey watchKey;
        String destPath = config.getMoveDirectory();

        while((watchKey = watchService.take()) != null) {
            for(WatchEvent<?> event : watchKey.pollEvents()) {
                if(event.kind().toString().equals(StandardWatchEventKinds.ENTRY_CREATE.name())) {
                    var filePath = destPath +
                            "/" +
                            FileUtil.determineCategory(event.context().toString(), config.getFileClassificationsMap());

                    FileUtil.moveFile(event.context().toString(), trackDirectoryPath, filePath);
                }
            }
            watchKey.reset();
        }
    }
}
