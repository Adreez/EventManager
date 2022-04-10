package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;

public class delete extends SubCommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Delete created game.";
    }

    @Override
    public String getSyntax() {
        return "/event delete <gameID>";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 1) {
            p.sendMessage("usage");
        }
        else if (args.length >= 2) {
            if (EventManager.gcm.gameExists(args[1])) {
                p.sendMessage("Game deleted!");
                EventManager.gdm.deleteGame(args[1]);
            } else {
                p.sendMessage("Game doesnt exist!");
            }
        }
    }
}
