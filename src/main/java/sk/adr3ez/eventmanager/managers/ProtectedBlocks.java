package sk.adr3ez.eventmanager.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import sk.adr3ez.eventmanager.EventManager;

import java.util.ArrayList;

public class ProtectedBlocks implements Listener {
    ArrayList<String> gamesList = new ArrayList<>();
    ArrayList<Location> protectedBlocks = new ArrayList<>();

    public ProtectedBlocks() {
        Bukkit.getServer().getConsoleSender().sendMessage(EventManager.gamesFile.get().getString("Messages.test"));
        if (!EventManager.gamesFile.get().getValues(false).isEmpty()) {
            gamesList.addAll(EventManager.gamesFile.get().getConfigurationSection("Games.").getKeys(false));
        }
        loadBlocks();
    }

    public void loadBlocks() {
        protectedBlocks.clear();
        if (gamesList != null) {
            for (String s : gamesList) {
                protectedBlocks.add(new Location(Bukkit.getWorld(EventManager.gamesFile.get().getString("Games." + s + ".locations.end.world")),
                        EventManager.gamesFile.get().getDouble("Games." + s + ".locations.end.x"),
                        EventManager.gamesFile.get().getDouble("Games." + s + ".locations.end.y"),
                        EventManager.gamesFile.get().getDouble("Games." + s + ".locations.end.z")));
                if (EventManager.gamesFile.get().getConfigurationSection("Games." + s + ".checkpoints").getKeys(false) != null) {
                    for (String c : EventManager.gamesFile.get().getConfigurationSection("Games." + s + ".checkpoints").getKeys(false)) {

                        protectedBlocks.add(new Location(Bukkit.getWorld(EventManager.gamesFile.get().getString("Games." + s + ".checkpoints." + c + ".world")),
                                EventManager.gamesFile.get().getDouble("Games." + s + ".checkpoints." + c + ".x"),
                                EventManager.gamesFile.get().getDouble("Games." + s + ".checkpoints." + c + ".y"),
                                EventManager.gamesFile.get().getDouble("Games." + s + ".checkpoints." + c + ".z")));
                    }
                }
            }
        }
    }

    public void addBlock(Location loc) {
        protectedBlocks.add(loc);
    }

    public void deleteBlock(Location loc) {
        protectedBlocks.remove(loc);
        loc.getBlock().setType(Material.AIR);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE || e.getBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
            if (protectedBlocks.contains(e.getBlock().getLocation())) {
                e.getPlayer().sendMessage("This block is protected!");
                e.setCancelled(true);
            }
        }
    }
}
