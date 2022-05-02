package sk.adr3ez.eventmanager.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import sk.adr3ez.eventmanager.EventManager;

import java.util.HashMap;

public class GameManager implements Listener {

    public HashMap<Player, Integer> finishedPlayers = new HashMap<>();

    public int playerOrder = 1;

    public void addFinishedPlayer(Player p) {
        finishedPlayers.put(p, playerOrder);
        playerOrder++;
    }

    @EventHandler
    public void onInteractWithPressurePlate(PlayerInteractEvent e) {

        if (e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
            if (EventManager.gsm.activeGame != null) {
                if (GameStartManager.joinedPlayers.contains(e.getPlayer()) && !finishedPlayers.containsKey(e.getPlayer())) {
                    if (e.getClickedBlock().getLocation().getBlockX() == EventManager.gsm.getEndingLocation(EventManager.gsm.activeGame).getBlockX()) {
                        if (e.getClickedBlock().getLocation().getBlockY() == EventManager.gsm.getEndingLocation(EventManager.gsm.activeGame).getBlockY()) {
                            if (e.getClickedBlock().getLocation().getBlockZ() == EventManager.gsm.getEndingLocation(EventManager.gsm.activeGame).getBlockZ()) {
                                addFinishedPlayer(e.getPlayer());
                                e.getPlayer().sendMessage("You finished the event as " + finishedPlayers.get(e.getPlayer()));
                            }
                        }
                    }
                }
            }
        }
    }

}
