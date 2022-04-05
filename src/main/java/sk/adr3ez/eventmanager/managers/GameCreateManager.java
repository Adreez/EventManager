package sk.adr3ez.eventmanager.managers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sk.adr3ez.eventmanager.EventManager;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCreateManager implements Listener {

    private final ArrayList<Player> playersInSetup = new ArrayList<>();
    private final HashMap<Player, String> gameSetupID = new HashMap<>();
    private final HashMap<Player, Location> gameSetupSpawnLocation = new HashMap<>();

    public void setup(Player p, String gameID) {
        if (!gameExists(gameID)) {
            if (!playersInSetup.contains(p)) {
                p.sendMessage("New game creation setup has been started for " + gameID);
                playersInSetup.add(p);
                gameSetupID.put(p, gameID);

                p.getPlayer().getInventory().clear();

                ItemStack item = new ItemStack(Material.EMERALD, 1);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("§7Set §e§lGame Spawn");
                ArrayList<String> itemLore = new ArrayList<>();
                itemLore.add("");
                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);
                p.getInventory().setItem(0, item);

                ItemStack item2 = new ItemStack(Material.GREEN_WOOL, 1);
                ItemMeta itemMeta2 = item.getItemMeta();
                itemMeta2.setDisplayName("§7Complete §e§lSETUP §7(Right Click with SHIFT)");
                ArrayList<String> itemLore2 = new ArrayList<>();
                itemLore2.add("");
                itemMeta2.setLore(itemLore2);
                item2.setItemMeta(itemMeta2);
                p.getInventory().setItem(8, item2);


            } else {
                p.sendMessage("You are already in event setup editor.");
            }
        } else {
            p.sendMessage("This game already exists!");
        }
    }

    @EventHandler
    private void onItemClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (playersInSetup.contains(p)) {
            if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_AIR) ||
                    e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (p.getPlayer().getInventory().getItemInMainHand().getType() == Material.EMERALD) {
                    gameSetupSpawnLocation.put(e.getPlayer(), e.getPlayer().getLocation());
                    p.sendMessage("Location has been set!");
                } else if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GREEN_WOOL) {
                    if (gameSetupSpawnLocation.containsKey(e.getPlayer())) {
                        createGame(gameSetupID.get(e.getPlayer()), e.getPlayer().getName(), gameSetupSpawnLocation.get(e.getPlayer()));
                        p.sendMessage("§3Game has been created!");
                        p.getInventory().clear();
                        playersInSetup.remove(p);
                    } else {
                        p.sendMessage("You must set spawn location first!");
                    }
                }
            }
        }
    }
    @EventHandler
    private void preventFromDrop(PlayerDropItemEvent e) {
        if (playersInSetup.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void preventFromInvInteraction(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();

            if (playersInSetup.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    public boolean gameExists(String gameID) {
        return EventManager.games.get().get("Games." + gameID) != null;
    }

    public void createGame(String gameID, String creator, Location spawnLoc) {
        EventManager.games.get().set("Games." + gameID + ".info.creator", creator);
        EventManager.games.get().set("Games." + gameID + ".locations.spawn.x", spawnLoc.getBlockX());
        EventManager.games.get().set("Games." + gameID + ".locations.spawn.y", spawnLoc.getBlockY());
        EventManager.games.get().set("Games." + gameID + ".locations.spawn.z", spawnLoc.getBlockZ());
        EventManager.games.get().set("Games." + gameID + ".locations.spawn.pitch", spawnLoc.getPitch());
        EventManager.games.get().set("Games." + gameID + ".locations.spawn.yaw", spawnLoc.getYaw());
        EventManager.games.saveConfig();
    }
}
