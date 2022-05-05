package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;

public class checkpoint extends SubCommand {
    @Override
    public String getName() {
        return "checkpoint";
    }

    @Override
    public String getDescription() {
        return "Checkpoints";
    }

    @Override
    public String getSyntax() {
        return "/event checkpoint set/remove <gameID> <checkpointID>";
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 4) {
            if (args[1].equalsIgnoreCase("set")) {

                if (EventManager.gcm.gameExists(args[2])) {

                    if (isInt(args[3])) {
                        EventManager.cpm.setCheckpoint(args[2], Integer.parseInt(args[3]), p.getLocation());
                        p.sendMessage("Checkpoint " + args[3] + " has been set for game " + args[2]);
                    } else {
                        p.sendMessage("CheckpointID must be integer!");
                    }

                } else {
                    p.sendMessage("This game doesn't exist!");
                }

            } else if (args[1].equalsIgnoreCase("remove")) {

                if (EventManager.gcm.gameExists(args[2])) {

                    if (isInt(args[3])) {
                        EventManager.cpm.deleteCheckpoint(args[2], Integer.parseInt(args[3]));
                        p.sendMessage("Checkpoint " + args[3] + " has been deleted for game " + args[2]);
                    } else {
                        p.sendMessage("CheckpointID must be integer!");
                    }

                } else {
                    p.sendMessage("This game doesn't exist!");
                }

            }
        } else {
            p.sendMessage(getSyntax());
        }
    }
}
