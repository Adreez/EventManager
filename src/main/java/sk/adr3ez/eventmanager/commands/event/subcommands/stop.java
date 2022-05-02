package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;

public class stop extends SubCommand {
    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stop active game";
    }

    @Override
    public String getSyntax() {
        return "/event stop";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 1) {
            if (EventManager.gsm.activeGame != null) {
                EventManager.gsm.stopActiveGame();
                p.sendMessage("Stop game request has been sent!");
            } else {
                p.sendMessage("There isn't any active game!");
            }
        } else {
            p.sendMessage("Usage");
        }
    }
}
