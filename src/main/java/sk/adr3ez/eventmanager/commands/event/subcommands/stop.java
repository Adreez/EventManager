package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;

public class stop extends SubCommand {

    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
        return "/event stop <cooldown>";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 1 || args.length > 2) {
            p.sendMessage("Usage");
        }
        else if (args.length == 2) {
            if (isInt(args[1])) {
                if (EventManager.gsm.activeGame != null) {
                    EventManager.gsm.stopActiveGame(Integer.parseInt(args[1]));
                    p.sendMessage("Stop game request has been sent!");
                } else {
                    p.sendMessage("There isn't any active game!");
                }
            } else {
                p.sendMessage("Coundown value must be number!");
            }
        }
    }
}
