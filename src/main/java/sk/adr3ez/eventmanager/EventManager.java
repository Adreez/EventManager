package sk.adr3ez.eventmanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.eventmanager.commands.event.EventCmdManager;
import sk.adr3ez.eventmanager.files.Config;
import sk.adr3ez.eventmanager.files.Games;
import sk.adr3ez.eventmanager.managers.GameCreateManager;
import sk.adr3ez.eventmanager.managers.GameDeleteManager;
import sk.adr3ez.eventmanager.managers.ProtectedBlocks;

import java.io.File;
import java.util.Objects;

public final class EventManager extends JavaPlugin {

    public static Config config;
    public static Games games;
    public static GameCreateManager gcm;
    public static GameDeleteManager gdm;
    public static ProtectedBlocks protectedBlocks;

    @Override
    public void onEnable() {
        new File("plugins/EventManager").mkdirs();
        // Plugin startup logic

        config = new Config(this);
        games = new Games(this);


        Objects.requireNonNull(Bukkit.getPluginCommand("event")).setExecutor(new EventCmdManager());

        protectedBlocks = new ProtectedBlocks();
        gcm = new GameCreateManager();
        gdm = new GameDeleteManager();
        getServer().getPluginManager().registerEvents(protectedBlocks, this);
        getServer().getPluginManager().registerEvents(gcm, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
