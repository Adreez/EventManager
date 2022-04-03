package sk.adr3ez.eventmanager.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import sk.adr3ez.eventmanager.EventManager;

import java.io.File;

public class Config {

    private final EventManager plugin;
    private FileConfiguration customFile;
    private File file;
    String fileName = "config.yml";


    public Config(EventManager plugin) {
        this.plugin = plugin;

        reloadFiles();
    }

    public void reloadFiles() {
        if (file == null)
            file = new File(this.plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            this.plugin.saveResource(fileName, false);
            plugin.getLogger().info("§3File " + fileName + " has not been found. Creating new one.");
        }
        customFile = YamlConfiguration.loadConfiguration(file);

    }

    public FileConfiguration get() {
        if (customFile == null)
            reloadFiles();

        return customFile;
    }

}
