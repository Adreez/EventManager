package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;

public class reload extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the files.";
    }

    @Override
    public String getSyntax() {
        return "/event reload";
    }

    @Override
    public void perform(Player p, String[] args) {
        EventManager.config.reloadFiles();
        EventManager.games.reloadFiles();
        p.sendMessage("Files reloaded!");
    }
}
