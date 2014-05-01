package org.ciprite.ugungame.commandmanagers.cmds;

import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info Remove Command
 * @author Ciprite
 */

public class Remove extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.remove")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
        }
		
        if (args.length < 1) {
        	MessageManager.bad(p, usage + "remove <arena>");
        	return;
        }
        
        if (!ArenaManager.getInstance().isExist(args[0])) {
        	MessageManager.bad(p, "The arena §6" + args[0] + " §cdoes not exists!");
        	return;
        }
        
        Arena a = new Arena(args[0]);
        ArenaManager.getInstance().removeArena(a);
        MessageManager.good(p, "The arena §6" + a.getName() + " §ahas been removed");
	}

	@Override
	public String name() {
		return "remove";
	}

}
