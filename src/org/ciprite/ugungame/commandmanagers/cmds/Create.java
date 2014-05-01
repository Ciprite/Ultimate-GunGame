package org.ciprite.ugungame.commandmanagers.cmds;

import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info Create Command
 * @author Ciprite
 */

public class Create extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.create")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
        }
		
        if (args.length < 1) {
        	MessageManager.bad(p, usage + "create <arena>");
        	return;
        }
        
        if (ArenaManager.getInstance().isExist(args[0])) {
        	MessageManager.bad(p, "The arena §6" + args[0] + " §cdoes already exists!");
        	return;
        }
        
        Arena a = new Arena(args[0]);
        ArenaManager.getInstance().addArena(a);
        MessageManager.good(p, "The arena §6" + a.getName() + " §ahas been created!");
	}

	@Override
	public String name() {
		return "create";
	}

}
