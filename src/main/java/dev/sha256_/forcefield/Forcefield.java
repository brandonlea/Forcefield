package dev.sha256_.forcefield;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Collection;

public final class Forcefield extends JavaPlugin implements CommandExecutor {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("forcefield").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                player.sendMessage("Please do /forcefield <radius>");
                return false;
            }

            if(args.length == 1) {
                double radius = Integer.parseInt(args[0]);
                Location location = player.getLocation().getBlock().getLocation();
                Collection<Entity> entitiesNearby = location.getWorld().getNearbyEntities(location, radius, radius, radius);


                for(Entity entity : entitiesNearby) {
                    if (entity instanceof Mob) {
                        Vector force = entity.getLocation().toVector().subtract(location.toVector()).normalize().multiply(2);
                        entity.setVelocity(force);
                    }
                }
            }
        }

        return false;
    }
}
