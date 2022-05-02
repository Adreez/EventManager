package sk.adr3ez.eventmanager.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import sk.adr3ez.eventmanager.EventManager;

import java.util.HashMap;

public class CheckpointsManager implements Listener {

    public HashMap<Player, Integer> playerCheckpoint = new HashMap<>();

    public HashMap<Integer, Location> activeCheckpoints = new HashMap<>();

    public void loadCheckpoints(String gameID) {

    }

    public void setPlayerCheckpoint(Player p, int checkpoint) {

    }

    public void setCheckpoint(String gameID, int checkpoint, Location loc) {
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".world", loc.getWorld().getName());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".x", loc.getBlockX());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".y", loc.getBlockY());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".z", loc.getBlockZ());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".pitch", loc.getPitch());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".yaw", loc.getYaw());
        EventManager.gamesFile.saveConfig();
    }

    public void deleteCheckpoint(String gameID, int checkpoint) {
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint, null);
        EventManager.gamesFile.saveConfig();
    }

}
