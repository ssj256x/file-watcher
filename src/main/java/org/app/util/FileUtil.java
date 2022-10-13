package org.app.util;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
public class FileUtil {

    private FileUtil() {
    }

    public static void moveFile(String fileName, String src, String dest) {
        Path s = Paths.get(src + "/" + fileName);
        Path d = Paths.get(dest + "/" + fileName);

        try {
            if (Files.exists(d)) {
                File renameFile = s.toFile();
                String rf = src + "/" + String.format("[%s] %s", getTS(), fileName);

                if (renameFile.renameTo(new File(rf)))
                    LOGGER.info("Renamed file to : {}", rf);
                s = Paths.get(rf);
            }
            Files.move(s, d);
            LOGGER.info("Moved file [{}] from {} --> {}", fileName, src, dest);
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace(), e);
        }
    }

    public static void createFolders(String path, List<String> folders) {
        try {
            for (String folder : folders) {
                var filePath = Paths.get(path + "/" + folder);
                if (!Files.exists(filePath)) {
                    Files.createDirectory(filePath);
                    LOGGER.info("Created Folder : {}", folder);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace(), e);
        }
    }

    public static String determineCategory(String fileName, Map<String, List<String>> fileClassifications) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return fileClassifications.entrySet()
                .stream()
                .filter(e -> e.getValue().contains(extension))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("random");
    }

    public static String getTS() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
        return sdf.format(new Date());
    }
}
