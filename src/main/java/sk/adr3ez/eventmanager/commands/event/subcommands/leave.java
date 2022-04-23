package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;

public class leave extends SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave joined game";
    }

    @Override
    public String getSyntax() {
        return "/event leave";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 1) {
            if (EventManager.gsm.activeGame != null) {
                EventManager.gsm.removePlayer(p);
            } else {
                p.sendMessage("There is not active game!");
            }
        } else {
            p.sendMessage("Usage: §3" + getSyntax());
        }
    }
}
