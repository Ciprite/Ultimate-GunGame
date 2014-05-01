package org.ciprite.ugungame.commandmanagers.cmds;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info SetHub Command
 * @author Ciprite
 */

public class SetEndSpawn extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.setendspawn")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
        }
        
		if (args.length < 1) {
			MessageManager.bad(p, usage + "sethub <arena>");
			return;
        }
        
		if (!ArenaManager.getInstance().isExist(args[0])) {
			MessageManager.bad(p, "The arena §6" + args[0] + " §cdoesn't exists");
			return;
        }
        
		Arena a = ArenaManager.getInstance().getArena(args[0]);
        a.getCfg().set("locations.spawns.hub.world", p.getLocation().getWorld().getName());
        a.getCfg().set("locations.spawns.hub.x", Double.valueOf(p.getLocation().getX()));
        a.getCfg().set("locations.spawns.hub.y", Double.valueOf(p.getLocation().getY()));
        a.getCfg().set("locations.spawns.hub.z", Double.valueOf(p.getLocation().getZ()));
        a.getCfg().set("locations.spawns.hub.yaw", Float.valueOf(p.getLocation().getYaw()));
        a.getCfg().set("locations.spawns.hub.pitch", Float.valueOf(p.getLocation().getPitch()));
        try {
        	a.getCfg().save(a.getFile());
        	MessageManager.good(p, "The hub for arena §6" + a.getName() + " §ahas been set!");
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
	}

	@Override
	public String name() {
		return "setendspawn";
	}

}
