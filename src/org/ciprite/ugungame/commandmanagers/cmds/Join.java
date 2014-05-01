package org.ciprite.ugungame.commandmanagers.cmds;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.game.ArenaState;
import org.ciprite.ugungame.game.scoreboard.ScoreboardStats;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info Join Command
 * @author Ciprite
 */

public class Join extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.join")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
		}
        
		if (args.length < 1) {
			MessageManager.bad(p, usage + "join <arena>");
			return;
        }
        
		if (ArenaManager.getInstance().getPlayerArena(p) != null) {
			MessageManager.bad(p, "You're already playing!");
			return;
        }
        
		if (!ArenaManager.getInstance().isExist(args[0])) {
			MessageManager.bad(p, "The arena §6" + args[0] + " §cdoesn't exists!");
			return;
        }
        
		Arena a = ArenaManager.getInstance().getArena(args[0]);
        if (a.getArenaState() == ArenaState.STARTED) {
        	MessageManager.bad(p, "The arena §6" + args[0] + " §care already started!");
        	return;
        }

        if (a.getArenaState() == ArenaState.DISABLED) {
        	MessageManager.bad(p, "The arena §6" + args[0] + " §cis disabled!");
        	return;
        }
        
        if (a.getPlayers().size() >= a.getCfg().getInt("arena.players.max")) {
        	MessageManager.bad(p, "The arena §6" + args[0] + " §cis full!");
        	return;
        }
        
        if (ArenaManager.getInstance().getBankCfg().get("accounts." + p.getName()) != null) {
        	ScoreboardStats.addMoney(a, p);
        	ArenaManager.getInstance().getBankCfg().set("accounts." + p.getName(), ScoreboardStats.getMoney(p));
    		try {
				ArenaManager.getInstance().getBankCfg().save(ArenaManager.getInstance().getBank());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        a.addPlayer(p);
        a.sendMessage("§e" + p.getName() + " §7has joined the game! (" + a.getPlayers().size() + "/" + a.getCfg().getInt("arena.players.max") + ")");
        if (a.getPlayers().size() == a.getCfg().getInt("arena.players.start")) {
        	a.start();
        }
	}

	@Override
	public String name() {
		return "join";
	}

}
