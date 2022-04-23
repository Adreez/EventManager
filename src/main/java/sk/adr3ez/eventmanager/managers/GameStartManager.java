package sk.adr3ez.eventmanager.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import sk.adr3ez.eventmanager.EventManager;

import java.util.ArrayList;

public class GameStartManager implements Listener {

    public String activeGame = null;
    public ArrayList<Player> joinedPlayers = new ArrayList<>();

    public void startGame(String gameID) {
        if (EventManager.gcm.gameExists(gameID)) {
            if (activeGame == null) {
                activeGame = gameID;
                if (EventManager.configFile.get().getBoolean("Settings.auto-player-teleport")) {
                    joinedPlayers.addAll(Bukkit.getServer().getOnlinePlayers());
                    for (Player ep : joinedPlayers) {
                        ep.teleport(getGameSpawn(activeGame));
                        ep.sendMessage("You've been teleported to event!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (activeGame != null) {
            if (EventManager.configFile.get().getBoolean("Settings.auto-player-teleport")) {
                e.getPlayer().sendMessage("Ongoing event... automaticaly teleporting to game!");
                addPlayer(e.getPlayer());
                e.getPlayer().teleport(getGameSpawn(activeGame));
            } else {
                e.getPlayer().sendMessage("There is ongoing event, you can join using §3/event join");
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
            p.teleport(getGameSpawn(activeGame));
            p.sendMessage("You joined game!");
            joinedPlayers.add(p);
        } else {
            p.sendMessage("You already joined game!");
        }
    }

    public void removePlayer(Player p) {
        joinedPlayers.remove(p);
        p.sendMessage("You left the game!");
    }

    public void stopActiveGame() {
        activeGame = null;
        joinedPlayers.clear();
    }

    public String getGameType(@NotNull String gameID) {
        if (EventManager.gcm.gameExists(gameID)) {
            return EventManager.gamesFile.get().getString("Games." + gameID + ".gameType");
        } else {
            return null;
        }
    }

}
