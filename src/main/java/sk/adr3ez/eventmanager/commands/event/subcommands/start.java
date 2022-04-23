package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;
import sk.adr3ez.eventmanager.managers.GameStartManager;

public class start extends SubCommand {
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start created game.";
    }

    @Override
    public String getSyntax() {
        return "/event start <game>";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 1) {
            p.sendMessage("usage");
        }
        else if (args.length >= 2) {
            if (EventManager.gcm.gameExists(args[1])) {
                if (EventManager.gsm.activeGame == null) {
                    //Start
                    EventManager.gsm.startGame(args[1]);
                    p.sendMessage("§2Starting game...");
                } else {
                    p.sendMessage("There is already 1 game started! Use /event stop to stop current game.");
                }
            } else {
                p.sendMessage("This game doesn't exist!");
            }
        }
    }
}
