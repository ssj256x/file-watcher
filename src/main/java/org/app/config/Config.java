package org.app.config;

import org.app.FileWatcherApplication;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Config {

    private final Map<String, Set<String>> configMap;

    public Config(String filename) {
        Yaml yaml = new Yaml();
        this.configMap = yaml.load(FileWatcherApplication.class.getClassLoader().getResourceAsStream(filename));
    }

    public String getTrackDirectory() {
        Map<String, Object> map = (Map<String, Object>) configMap.get("directory");
        return ((List<String>) map.get("track")).get(0);
    }

    public String getMoveDirectory() {
        Map<String, Object> map = (Map<String, Object>) configMap.get("directory");
        return (String) map.get("move");
    }

    public List<String> getFileClassificationNames() {
        Map<String, List<String>> map = getFileClassificationsMap();
        return new ArrayList<>(map.keySet());
    }

    public Map<String, List<String>> getFileClassificationsMap() {
        return (Map<String, List<String>>) configMap.get("fileClassifications");
    }
}
