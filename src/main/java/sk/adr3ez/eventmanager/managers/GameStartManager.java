package sk.adr3ez.eventmanager.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import sk.adr3ez.eventmanager.EventManager;

import java.util.ArrayList;

public class GameStartManager implements Listener {

    public String activeGame = null;
    public ArrayList<Player> joinedPlayers = new ArrayList<>();

    public void startGame(String gameID) {
        if (EventManager.gcm.gameExists(gameID)) {
            if (activeGame == null) {
                activeGame = gameID;
                if (EventManager.configFile.get().getBoolean("Settings.auto-game-join")) {
                    for (Player ep : Bukkit.getOnlinePlayers()) {
                        addPlayer(ep);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (activeGame != null) {
            if (EventManager.configFile.get().getBoolean("Settings.auto-game-join")) {
                e.getPlayer().sendMessage("Ongoing event... automaticaly teleporting to game!");
                addPlayer(e.getPlayer());
            } else {
                e.getPlayer().sendMessage("There is ongoing event, you can join using §3/event join");
                if (EventManager.configFile.get().getBoolean("Settings.on-join-spawn")) {
                    e.getPlayer().teleport(getSpawnLocation());
                }
            }
        } else {
            if (EventManager.configFile.get().getBoolean("Settings.on-join-spawn")) {
                e.getPlayer().teleport(getSpawnLocation());
            }
        }
    }

    public Location getGameSpawn(String gameID) {
        return new Location(Bukkit.getWorld(EventManager.gamesFile.get().getString("Games." + gameID + ".locations.spawn.world")),
                EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.spawn.x"),
                EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.spawn.y"),
                EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.spawn.z"),
                (float) EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.spawn.yaw"),
                (float) EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.spawn.pitch"));
    }

    public void addPlayer(Player p) {
        if (!joinedPlayers.contains(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
            p.sendTitle("§6Teleporting...", "You've been teleported to game!", 10, 60, 10);
            p.teleport(getGameSpawn(activeGame));
            p.sendMessage("You've been teleported to event!");
            joinedPlayers.add(p);
        } else {
            p.sendMessage("You already joined game!");
        }
    }

    public void removePlayer(Player p) {
        if (joinedPlayers.contains(p)) {
            joinedPlayers.remove(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
            p.sendTitle("§6Teleporting...", "You left the game!", 10, 60, 10);
            p.teleport(getSpawnLocation());
        } else {
            p.sendMessage("You are not in game!");
        }
    }

    int cooldown;
    public void stopActiveGame() {
        if (activeGame != null) {
            cooldown = 0;

            BukkitTask id = Bukkit.getServer().getScheduler().runTaskTimer(EventManager.plugin, () -> {
                if (cooldown > 0) {
                    Bukkit.getServer().broadcastMessage("Events starts in " + cooldown);
                    for (Player p : EventManager.gsm.joinedPlayers) {
                        p.sendTitle("§7Event ends in", String.valueOf(cooldown), 20, 40, 0);
                    }
                    cooldown--;
                }else if (cooldown == 0) {
                    for (Player p : EventManager.gsm.joinedPlayers) {
                        p.sendTitle("§7Event ends", "§6§lNOW", 20, 40, 20);
                    }
                }
            }, 20L, 20L);
            for (Player ep : joinedPlayers) {
                ep.teleport(getSpawnLocation());
            }
            activeGame = null;
            joinedPlayers.clear();
        }
    }

    private Location getSpawnLocation() {
        return new Location(Bukkit.getWorld(EventManager.configFile.get().getString("Locations.spawn.world")),
                EventManager.configFile.get().getDouble("Locations.spawn.x"),
                EventManager.configFile.get().getDouble("Locations.spawn.y"),
                EventManager.configFile.get().getDouble("Locations.spawn.z"),
                (float) EventManager.configFile.get().getDouble("Locations.spawn.yaw"),
                (float) EventManager.configFile.get().getDouble("Locations.spawn.pitch"));
    }

}
