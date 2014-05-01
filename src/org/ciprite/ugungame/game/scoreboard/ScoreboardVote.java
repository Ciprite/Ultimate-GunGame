package org.ciprite.ugungame.game.scoreboard;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info ScoreboardVote
 * @author Ciprite
 */

public class ScoreboardVote {

	public static String worldmap1;
	public static String worldmap2;
	
	public static int map1Vote = 0;
	public static int map2Vote = 0;
	
	public static ArrayList<String> vote = new ArrayList<String>();
	
	public static void createScoreboard(Arena a, Player p) {
		worldmap1 = a.getCfg().getString("arena.maps.map1");
		worldmap2 = a.getCfg().getString("arena.maps.map2");
		
		ScoreboardManager sm = p.getServer().getScoreboardManager();
		Scoreboard sb = sm.getNewScoreboard();
		
		Objective score = sb.registerNewObjective("vote", "votedummy");
		score.setDisplayName("§eMap Voting");
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Score map1 = score.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "1: " + worldmap1));
		Score map2 = score.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "2: " + worldmap2));
	
		map1.setScore(map1Vote);
		map2.setScore(map2Vote);
		
		p.setScoreboard(sb);
	}
	
	public static void addVoteMap1(Arena a, Player p) {
		if (!vote.contains(p.getName())) {
			map1Vote++;
			vote.add(p.getName());
			MessageManager.good(p, "You vote for the map §6" + a.getCfg().getString("arena.maps.map1"));
		} else {
			MessageManager.bad(p, "You have already voted!");
		}
	}

	public static void addVoteMap2(Arena a, Player p) {
		if (!vote.contains(p.getName())) {
			map2Vote++;
			vote.add(p.getName());
			MessageManager.good(p, "You vote for the map §6" + a.getCfg().getString("arena.maps.map2"));
		} else {
			MessageManager.bad(p, "You have already voted!");
		}
	}
	
}
