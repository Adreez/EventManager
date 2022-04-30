package sk.adr3ez.eventmanager.commands.event.subcommands;

import org.bukkit.entity.Player;
import sk.adr3ez.eventmanager.EventManager;
import sk.adr3ez.eventmanager.commands.event.SubCommand;

public class setspawn extends SubCommand {
    @Override
    public String getName() {
        return "setspawn";
    }

    @Override
    public String getDescription() {
        return "Sets spawn of your server.";
    }

    @Override
    public String getSyntax() {
        return "/event setspawn";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 1) {
            EventManager.configFile.get().set("Locations.spawn.world", p.getWorld());
            EventManager.configFile.get().set("Locations.spawn.x", p.getLocation().getBlockX());
            EventManager.configFile.get().set("Locations.spawn.y", p.getLocation().getBlockY());
            EventManager.configFile.get().set("Locations.spawn.z", p.getLocation().getBlockZ());
            EventManager.configFile.get().set("Locations.spawn.yaw", p.getLocation().getYaw());
            EventManager.configFile.get().set("Locations.spawn.pitch", p.getLocation().getPitch());
            EventManager.configFile.saveConfig();
            p.sendTitle("§6Spawn location has been set!", "§8(§eX: §f%x% §eY: §f%y% §eZ: §f%z%§8)".replaceAll("%x%", String.valueOf(p.getLocation().getBlockX()))
                    .replaceAll("%y%", String.valueOf(p.getLocation().getBlockY()))
                    .replaceAll("%z%", String.valueOf(p.getLocation().getBlockZ())), 10, 100, 10);
        } else {
            p.sendMessage("Usage.");
        }

    }
}
