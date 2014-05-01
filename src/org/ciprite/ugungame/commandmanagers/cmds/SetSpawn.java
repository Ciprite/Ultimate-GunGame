package org.ciprite.ugungame.commandmanagers.cmds;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info AddSpawn Command
 * @author Ciprite
 */

public class SetSpawn extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.setspawn")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
		}
        
		if (args.length < 2) {
			MessageManager.bad(p, usage + "setspawn <arena> <mapid(1|2)>");
			return;
        }
        
		if (!ArenaManager.getInstance().isExist(args[0])) {
			MessageManager.bad(p, "§cThe arena §6" + args[0] + " §cdoes not exist!");
			return;
        }
		
		Arena a = ArenaManager.getInstance().getArena(args[0]);

		if (args[1].equalsIgnoreCase("1")) {
	        a.getCfg().set("locations.spawns.spawn.map1.world", p.getLocation().getWorld().getName());
	        a.getCfg().set("locations.spawns.spawn.map1.x", Double.valueOf(p.getLocation().getX()));
	        a.getCfg().set("locations.spawns.spawn.map1.y", Double.valueOf(p.getLocation().getY()));
	        a.getCfg().set("locations.spawns.spawn.map1.z", Double.valueOf(p.getLocation().getZ()));
	        a.getCfg().set("locations.spawns.spawn.map1.yaw", Float.valueOf(p.getLocation().getYaw()));
	        a.getCfg().set("locations.spawns.spawn.map1.pitch", Float.valueOf(p.getLocation().getPitch()));
	        try {
	        	a.getCfg().save(a.getFile());
	        	MessageManager.good(p, "The spawn for arena §6" + a.getName() + " §aand for the map §61 §ahas been set!");
	        }
	        catch (IOException e) {
	        	e.printStackTrace();
	        }
		} else if (args[1].equalsIgnoreCase("2")) {
	        a.getCfg().set("locations.spawns.spawn.map2.world", p.getLocation().getWorld().getName());
	        a.getCfg().set("locations.spawns.spawn.map2.x", Double.valueOf(p.getLocation().getX()));
	        a.getCfg().set("locations.spawns.spawn.map2.y", Double.valueOf(p.getLocation().getY()));
	        a.getCfg().set("locations.spawns.spawn.map2.z", Double.valueOf(p.getLocation().getZ()));
	        a.getCfg().set("locations.spawns.spawn.map2.yaw", Float.valueOf(p.getLocation().getYaw()));
	        a.getCfg().set("locations.spawns.spawn.map2.pitch", Float.valueOf(p.getLocation().getPitch()));
	        try {
	        	a.getCfg().save(a.getFile());
	        	MessageManager.good(p, "The spawn for arena §6" + a.getName() + " §aand for the map §62 §ahas been set!");
	        }
	        catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
	}

	@Override
	public String name() {
		return "setspawn";
	}

}
