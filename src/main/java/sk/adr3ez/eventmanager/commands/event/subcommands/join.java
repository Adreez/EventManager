package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;

public class join extends SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join game.";
    }

    @Override
    public String getSyntax() {
        return "/event join";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 1) {
            if (EventManager.gsm.activeGame != null) {
                EventManager.gsm.addPlayer(p);

            } else {
                p.sendMessage("There is not active game!");
            }
        } else {
            p.sendMessage("Usage: §3" + getSyntax());
        }
    }
}
