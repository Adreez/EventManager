package sk.adr3ez.eventmanager.managers;

import sk.adr3ez.eventmanager.EventManager;

public class GameDeleteManager {

    public void deleteGame(String gameID) {
        if (EventManager.gcm.gameExists(gameID)) {
            EventManager.games.get().set("Games." + gameID, null);
            EventManager.games.saveConfig();
        }
    }

}
