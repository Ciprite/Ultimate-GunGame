package org.ciprite.ugungame.commandmanagers.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.game.ArenaState;
import org.ciprite.ugungame.game.scoreboard.ScoreboardVote;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info Vote Command
 * @author Ciprite
 */

public class Vote extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.vote")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
		}
		
		if (args.length < 1) {
			MessageManager.bad(p, usage + "vote <mapid>");
			return;
		}
		
		if (ArenaManager.getInstance().getPlayerArena(p) == null) {
			MessageManager.bad(p, "You are not in a game!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(ArenaManager.getInstance().getPlayerArena(p));
		
		if (a.getArenaState() == ArenaState.DISABLED || a.getArenaState() == ArenaState.STARTED || a.getArenaState() == ArenaState.LOADING) {
			MessageManager.bad(p, "You cant vote now!");
			return;
		}
		
		int vote = Integer.parseInt(args[0]);
		if (vote == 1) {
			ScoreboardVote.addVoteMap1(a, p);
		} else if (vote == 2) {
			ScoreboardVote.addVoteMap2(a, p);
		} else {
			MessageManager.bad(p, "This MapId does not exists!");
		}
		
		for (String s : a.getPlayers()) {
			ScoreboardVote.createScoreboard(a, Bukkit.getPlayer(s));
		}
	}

	@Override
	public String name() {
		return "vote";
	}

}
