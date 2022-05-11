package sk.adr3ez.eventmanager.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class GameRewardsManager {

    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private final ArrayList<Integer> rewardsInt = new ArrayList<>();

    public void sendRewards(String gameID) {
        //If method should use global-rewards
        if (EventManager.gamesFile.get().getBoolean("Games." + gameID + ".settings.use-global-reward")) {
            // Use global rewards
            for (String s : EventManager.configFile.get().getConfigurationSection("Global-rewards").getKeys(false)) {
                if (isInt(s)) {
                    rewardsInt.add(Integer.valueOf(s));
                } else {
                    EventManager.plugin.getLogger().log(Level.WARNING, "Reward " + s + " is incorrect! For reward ID you can only use numbers!");
                }
            }
            for (Player p : GameStartManager.getJoinedPlayers()) {
                if (EventManager.gm.finishedPlayers.containsKey(p)) {

                    if (rewardsInt.contains(EventManager.gm.finishedPlayers.get(p))) {
                        //Reward for this position has been set
                        p.sendMessage("You finished as: " + EventManager.gm.finishedPlayers.get(p) + " \nsending rewards :D");

                        List<String> listOfCmds = EventManager.configFile.get().getStringList("Global-rewards." + EventManager.gm.finishedPlayers.get(p));
                        if (!listOfCmds.isEmpty()) {
                            for (String line : listOfCmds) {
                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), line.replaceAll("%player%", p.getName()));
                            }
                        } else {
                            p.sendMessage("There is problem to load reward for position " + EventManager.gm.finishedPlayers.get(p) + " in game " + gameID);
                        }
                    } else {
                        //Reward for this position not set - one day maybe rewards for finishing :D
                        p.sendMessage("You finished game as: " + EventManager.gm.finishedPlayers.get(p) + "\ni hope you will have better luck next time :D");
                    }

                } else {
                    //One day maybe rewards for playing :D
                    p.sendMessage("You didn't finished current game. I hope you will be better in another one :3");
                }
            }
            //Normal rewards
        } else {
            if (EventManager.gamesFile.get().getConfigurationSection("Games." + gameID + ".rewards").getKeys(false) != null) {
                for (String s : EventManager.gamesFile.get().getConfigurationSection("Games." + gameID + ".rewards").getKeys(false)) {
                    if (isInt(s)) {
                        rewardsInt.add(Integer.valueOf(s));
                    } else {
                        EventManager.plugin.getLogger().log(Level.WARNING, "Reward " + s + " is incorrect! For reward ID you can only use numbers!");
                    }
                }
                for (Player p : GameStartManager.getJoinedPlayers()) {
                    if (EventManager.gm.finishedPlayers.containsKey(p)) {

                        if (rewardsInt.contains(EventManager.gm.finishedPlayers.get(p))) {
                            //Reward for this position has been set
                            p.sendMessage("You finished as: " + EventManager.gm.finishedPlayers.get(p) + " §5sending rewards :D");

                            List<String> listOfCmds = EventManager.gamesFile.get().getStringList("Games." + gameID + ".rewards." + EventManager.gm.finishedPlayers.get(p));
                            if (!listOfCmds.isEmpty()) {
                                for (String line : listOfCmds) {
                                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), line.replaceAll("%player%", p.getName()));
                                }
                            } else {
                                p.sendMessage("There is problem to load reward for position " + EventManager.gm.finishedPlayers.get(p) + " in game " + gameID);
                            }
                        } else {
                            //Reward for this position not set - one day maybe rewards for finishing :D
                            p.sendMessage("You finished game as: " + EventManager.gm.finishedPlayers.get(p) + "\ni hope you will have better luck next time :D");
                        }
                    } else {
                        //One day maybe rewards for playing :D
                        p.sendMessage("You didn't finished current game. I hope you will be better in another one :3");
                    }
                }
            }
        }
        rewardsInt.clear();
    }
}
