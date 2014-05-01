package org.ciprite.ugungame.commandmanagers.cmds;

import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.game.ArenaState;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info Start Command
 * @author Ciprite
 */

public class Start extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.start")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
        }
        
		if (args.length < 1) {
			MessageManager.bad(p, usage + "start <arena>");
        	return;
        }
       
        Arena a = ArenaManager.getInstance().getArena(args[0]);
        if (a.getArenaState() == ArenaState.STARTED) {
        	MessageManager.bad(p, "The arena §6" + args[0] + " §calready started!");
        	return;
        }
        
        a.forceStart();
        MessageManager.good(p, "The arena §6" + a.getName() + " §ahas been started!");
	}

	@Override
	public String name() {
		return "start";
	}

}
