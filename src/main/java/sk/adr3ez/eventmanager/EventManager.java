package sk.adr3ez.eventmanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.eventmanager.commands.event.EventCmdManager;
import sk.adr3ez.eventmanager.files.Config;
import sk.adr3ez.eventmanager.files.Games;
import sk.adr3ez.eventmanager.managers.*;

import java.io.File;
import java.util.Objects;

public final class EventManager extends JavaPlugin {

    public static EventManager plugin;

    public static Config configFile;
    public static Games gamesFile;
    public static GameCreateManager gcm;
    public static GameDeleteManager gdm;
    public static ProtectedBlocks protectedBlocks;
    public static GameStartManager gsm;
    public static FreezeManager fm;
    public static GameManager gm;
    public static CheckpointsManager cpm;
    public static GameRewardsManager grm;

    @Override
    public void onEnable() {
        new File("plugins/EventManager").mkdirs();

        plugin = this;

        configFile = new Config(this);
        gamesFile = new Games(this);

        Objects.requireNonNull(Bukkit.getPluginCommand("event")).setExecutor(new EventCmdManager());

        protectedBlocks = new ProtectedBlocks();
        gcm = new GameCreateManager();
        gdm = new GameDeleteManager();
        gsm = new GameStartManager(this);
        fm = new FreezeManager();
        gm = new GameManager();
        cpm = new CheckpointsManager();
        grm = new GameRewardsManager();
        getServer().getPluginManager().registerEvents(protectedBlocks, this);
        getServer().getPluginManager().registerEvents(gcm, this);
        getServer().getPluginManager().registerEvents(gsm, this);
        getServer().getPluginManager().registerEvents(gm, this);
        getServer().getPluginManager().registerEvents(cpm, this);
    }

    @Override
    public void onDisable() {
    }
}
