package sk.adr3ez.eventmanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.eventmanager.commands.event.EventCmdManager;
import sk.adr3ez.eventmanager.files.Config;
import sk.adr3ez.eventmanager.files.Games;
import sk.adr3ez.eventmanager.managers.GameCreateManager;

import java.io.File;
import java.util.Objects;

public final class EventManager extends JavaPlugin {

    public static Config config;
    public static Games games;
    public static GameCreateManager gcm;

    @Override
    public void onEnable() {
        new File("plugins/EventManager").mkdirs();
        // Plugin startup logic

        config = new Config(this);
        games = new Games(this);


        Objects.requireNonNull(Bukkit.getPluginCommand("event")).setExecutor(new EventCmdManager());

        getServer().getPluginManager().registerEvents(new GameCreateManager(), this);
        gcm = new GameCreateManager();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
