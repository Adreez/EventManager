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
        Bukkit.getServer().getConsoleSender().sendMessage(EventManager.games.get().getString("Messages.test"));
        if (!EventManager.games.get().getValues(false).isEmpty()) {
            gamesList.addAll(EventManager.games.get().getConfigurationSection("Games.").getKeys(false));
        }
        loadBlocks();
    }

    public void loadBlocks() {
        protectedBlocks.clear();
        if (gamesList != null) {
            for (String s : gamesList) {
                protectedBlocks.add(new Location(Bukkit.getWorld(EventManager.games.get().getString("Games." + s + ".locations.end.world")),
                        EventManager.games.get().getDouble("Games." + s + ".locations.end.x"),
                        EventManager.games.get().getDouble("Games." + s + ".locations.end.y"),
                        EventManager.games.get().getDouble("Games." + s + ".locations.end.z")));
            }
        }
    }

    public void addBlock(Location loc) {
        protectedBlocks.add(loc.getBlock().getLocation());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
            if (protectedBlocks.contains(e.getBlock().getLocation())) {
                e.getPlayer().sendMessage("This block is protected!");
                e.setCancelled(true);
            }
        }
    }
}
