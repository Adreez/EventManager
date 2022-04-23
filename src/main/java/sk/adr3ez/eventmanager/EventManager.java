package sk.adr3ez.eventmanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.eventmanager.commands.event.EventCmdManager;
import sk.adr3ez.eventmanager.files.Config;
import sk.adr3ez.eventmanager.files.Games;
import sk.adr3ez.eventmanager.managers.GameStartManager;
import sk.adr3ez.eventmanager.managers.GameCreateManager;
import sk.adr3ez.eventmanager.managers.GameDeleteManager;
import sk.adr3ez.eventmanager.managers.ProtectedBlocks;

import java.io.File;
import java.util.Objects;

public final class EventManager extends JavaPlugin {

    public static Config configFile;
    public static Games gamesFile;
    public static GameCreateManager gcm;
    public static GameDeleteManager gdm;
    public static ProtectedBlocks protectedBlocks;
    public static GameStartManager gsm;

    @Override
    public void onEnable() {
        new File("plugins/EventManager").mkdirs();
        // Plugin startup logic

        configFile = new Config(this);
        gamesFile = new Games(this);

        Objects.requireNonNull(Bukkit.getPluginCommand("event")).setExecutor(new EventCmdManager());

        protectedBlocks = new ProtectedBlocks();
        gcm = new GameCreateManager();
        gdm = new GameDeleteManager();
        gsm = new GameStartManager();
        getServer().getPluginManager().registerEvents(protectedBlocks, this);
        getServer().getPluginManager().registerEvents(gcm, this);
        getServer().getPluginManager().registerEvents(gsm, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
