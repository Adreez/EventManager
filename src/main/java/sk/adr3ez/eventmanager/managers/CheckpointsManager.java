package sk.adr3ez.eventmanager.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import sk.adr3ez.eventmanager.EventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;

public class CheckpointsManager implements Listener {

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int getMinValue(int[] array) {
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    public HashMap<Player, Integer> playerCheckpoint = new HashMap<>();

    public HashMap<Location, Integer> activeCheckpoints = new HashMap<>();

    private int firstCheckpoint = 1;

    /*
    *  Load checkpoints when you're starting a game.
     */
    public void loadCheckpoints(String gameID) {
        if (EventManager.gamesFile.get().getConfigurationSection("Games." + gameID + ".checkpoints").getValues(false) != null) {
            for (String c : EventManager.gamesFile.get().getConfigurationSection("Games." + gameID + ".checkpoints").getKeys(false)) {
                if (isInt(c)) {
                    activeCheckpoints.put(new Location(
                            Bukkit.getWorld(EventManager.gamesFile.get().getString("Games." + gameID + ".checkpoints." + c + ".world")),
                            EventManager.gamesFile.get().getDouble("Games." + gameID + ".checkpoints." + c + ".x"),
                            EventManager.gamesFile.get().getDouble("Games." + gameID + ".checkpoints." + c + ".y"),
                            EventManager.gamesFile.get().getDouble("Games." + gameID + ".checkpoints." + c + ".z")), Integer.valueOf(c));
                } else {
                    EventManager.plugin.getLogger().log(Level.WARNING, "Failed to load checkpoint " + c + " for game " + gameID + " because checkpoint name can only be number! This checkpoint will be skipped :)");
                }
            }
        }
        ArrayList<Integer> listOfNumbers = new ArrayList<>(activeCheckpoints.values());
        Collections.sort(listOfNumbers);
        firstCheckpoint = listOfNumbers.get(0);
    }

    /*
    * Unload active checkpoints when game is stopped.
     */
    public void unloadGameCP() {
        activeCheckpoints.clear();
        playerCheckpoint.clear();
    }

    public void setCheckpoint(String gameID, int checkpoint, Location loc) {
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".world", loc.getWorld().getName());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".x", loc.getBlockX());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".y", loc.getBlockY());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".z", loc.getBlockZ());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".pitch", loc.getPitch());
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint + ".yaw", loc.getYaw());
        EventManager.gamesFile.saveConfig();

        EventManager.protectedBlocks.addBlock(loc.getBlock().getLocation());
        loc.getBlock().setType(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
    }

    public void deleteCheckpoint(String gameID, int checkpoint) {
        EventManager.gamesFile.get().set("Games." + gameID + ".checkpoints." + checkpoint, null);
        EventManager.gamesFile.saveConfig();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
            if (EventManager.gsm.activeGame != null) {
                if (!EventManager.gm.finishedPlayers.containsKey(e.getPlayer()) && GameStartManager.getJoinedPlayers().contains(e.getPlayer())) {
                    if (activeCheckpoints.containsKey(e.getClickedBlock().getLocation())) {

                        if (playerCheckpoint.get(e.getPlayer()) == null) {
                            if (activeCheckpoints.get(e.getClickedBlock().getLocation()) <= firstCheckpoint) {
                                playerCheckpoint.put(e.getPlayer(), activeCheckpoints.get(e.getClickedBlock().getLocation()));
                                e.getPlayer().sendTitle("§6§6Checkpoint reached", String.valueOf(activeCheckpoints.get(e.getClickedBlock().getLocation())),
                                        10, 20, 10);
                            } else {
                                e.getPlayer().sendTitle("§cGo to first checkpoint!", "", 5,10,5);
                            }
                        } else {
                            if (activeCheckpoints.get(e.getClickedBlock().getLocation()) > playerCheckpoint.get(e.getPlayer())) {
                                if (!playerCheckpoint.get(e.getPlayer()).equals(activeCheckpoints.get(e.getClickedBlock().getLocation()))) {
                                    playerCheckpoint.put(e.getPlayer(), activeCheckpoints.get(e.getClickedBlock().getLocation()));
                                    e.getPlayer().sendTitle("§6§6Checkpoint reached", String.valueOf(activeCheckpoints.get(e.getClickedBlock().getLocation())),
                                            10, 20, 10);
                                }
                            } else {
                                e.getPlayer().sendTitle("§cYou already been there.", "", 5,10,5);
                            }
                        }
                    }
                }
            }
        }
    }

}
