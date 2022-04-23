package sk.adr3ez.eventmanager.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import sk.adr3ez.eventmanager.EventManager;

public class GameDeleteManager {

    public void deleteGame(String gameID) {
        if (EventManager.gcm.gameExists(gameID)) {
            EventManager.protectedBlocks.deleteBlock(new Location(Bukkit.getWorld(EventManager.gamesFile.get().getString("Games." + gameID + ".locations.end.world")),
                    EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.end.x"),
                    EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.end.y"),
                    EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.end.z")));
            EventManager.protectedBlocks.deleteBlock(new Location(Bukkit.getWorld(EventManager.gamesFile.get().getString("Games." + gameID + ".locations.end.world")),
                    EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.end.x"),
                    EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.end.y")-1,
                    EventManager.gamesFile.get().getDouble("Games." + gameID + ".locations.end.z")));
            EventManager.gamesFile.get().set("Games." + gameID, null);
            EventManager.gamesFile.saveConfig();
        }
    }

}
