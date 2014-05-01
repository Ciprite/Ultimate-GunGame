package org.ciprite.ugungame.commandmanagers.cmds;

import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.game.ArenaState;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info Leave Command
 * @author Ciprite
 */

public class Leave extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.leave")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
        }
		
        if (args.length >= 0) {
        	if (ArenaManager.getInstance().getPlayerArena(p) != null) {
        		Arena a = ArenaManager.getInstance().getArena(ArenaManager.getInstance().getPlayerArena(p));
        		MessageManager.good(p, "You have left the game!");
            	p.getInventory().clear();
            	p.getInventory().setArmorContents(null);
            	p.setLevel(0);
            	a.removePlayer(p);
            	a.sendMessage("§e" + p.getName() + " §7has left the game! (" + a.getPlayers().size() + "/" + a.getCfg().getInt("arena.players.max") + ")");
            	if (a.getPlayers().size() < 1) {
            		if (a.getArenaState() != ArenaState.STARTED) {
            			return;
            		}
            		
            		a.sendMessage("§cThe game has been cancelled!");
            		a.stop();
            	}
            	return;
        	}
        	
        	MessageManager.bad(p, "You're not playing right now!");
        }
	}

	@Override
	public String name() {
		return "leave";
	}

}
