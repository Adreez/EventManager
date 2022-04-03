package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;
import sk.adr3ez.eventmanager.managers.GameCreateManager;

public class create extends SubCommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create new game.";
    }

    @Override
    public String getSyntax() {
        return "/event create <gameID>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if (args.length == 1) {
            p.sendMessage("usage");
        }
        else if (args.length >= 2) {
            EventManager.gcm.setup(p, args[1]);
        }
    }
}
