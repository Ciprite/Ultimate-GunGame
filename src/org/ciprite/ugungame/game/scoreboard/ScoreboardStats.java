package org.ciprite.ugungame.game.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.ciprite.ugungame.UGunGame;
import org.ciprite.ugungame.game.Arena;

/**
 * @info ScoreboardStats
 * @author Ciprite
 */

public class ScoreboardStats {

	public static void createScoreboard(Player p) {
		ScoreboardManager sm = p.getServer().getScoreboardManager();
		Scoreboard sb = sm.getNewScoreboard();
		
		Objective score = sb.registerNewObjective("stats", "dummystats");
		score.setDisplayName("§eUGunGame Stats");
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Score ugp = score.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "UGP:"));
		Score lvl = score.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Level:"));
	
		ugp.setScore((int) getMoney(p));
		lvl.setScore(p.getLevel());
		
		p.setScoreboard(sb);
	}
	
	public static void removeScoreboard(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		p.setScoreboard(sb);
	}
	
	public static void addMoney(Arena a, Player p) {
		UGunGame.econ.depositPlayer(p.getName(), a.getCfg().getInt("arena.money.add"));
	}
	
	public static void removeMoney(Arena a, Player p) {
		UGunGame.econ.withdrawPlayer(p.getName(), a.getCfg().getInt("arena.money.remove"));
	}
	
	public static double getMoney(Player p) {
		return UGunGame.econ.getBalance(p.getName());
	}
	
}
