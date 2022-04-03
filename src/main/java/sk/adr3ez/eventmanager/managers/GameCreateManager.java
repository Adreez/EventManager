package sk.adr3ez.eventmanager.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sk.adr3ez.eventmanager.EventManager;

import java.util.ArrayList;

public class GameCreateManager implements Listener {

    private final ArrayList<Player> playersInSetup = new ArrayList<>();

    public void setup(Player p, String gameID) {

        if (!gameExists(gameID)) {
            if (!playersInSetup.contains(p)) {
                p.sendMessage("New game creation setup has been started for " + gameID);
                playersInSetup.add(p);

                p.getPlayer().getInventory().clear();

                ItemStack item = new ItemStack(Material.EMERALD, 1);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("§7Set §e§lGame Spawn");
                ArrayList<String> itemLore = new ArrayList<>();
                itemLore.add("");
                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);
                p.getInventory().setItem(0, item);
            }
        } else {
            p.sendMessage("This game already exists!");
        }
    }

    @EventHandler
    private void onItemClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        //if (playersInSetup.contains(p)) {
            /*ItemStack item = new ItemStack(Material.EMERALD, 1);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§7Set &e&lGame Spawn");
            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add("");
            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);*/

            if (p.getPlayer().getInventory().getItemInMainHand().getType() == Material.EMERALD) {
                p.sendMessage("Yea");
            } else {
                p.sendMessage("Nope.");
            }
        //}
    }
    @EventHandler
    private void preventFromDrop(PlayerDropItemEvent e) {
        if (playersInSetup.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    public boolean gameExists(String gameID) {
        return EventManager.games.get().get("Games." + gameID) != null;
    }
}
