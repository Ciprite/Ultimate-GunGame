package org.ciprite.ugungame.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.ciprite.ugungame.UGunGame;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.game.ArenaState;
import org.ciprite.ugungame.game.scoreboard.ScoreboardStats;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info PlayerDeath
 * @author Ciprite
 */

public class PlayerDeath implements Listener {
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent e) {
		final Player ent = (Player) e.getEntity();
		Player killer = (Player) e.getEntity().getKiller();
		if (ArenaManager.getInstance().getPlayerArena(e.getEntity()) != null) {
			Arena a = ArenaManager.getInstance().getArena(ArenaManager.getInstance().getPlayerArena(e.getEntity()));
	      
			e.getDrops().clear();
			e.setDeathMessage(null);
			e.setDroppedExp(0);
			if (a.getArenaState() != ArenaState.STARTED) {
				return;
			}
			
			a.removeLevel(ent);
			
			// TODO: Death message.
			e.setDeathMessage(UGunGame.getInstance().prefix + "§e" + ent.getName() + " killed by " + killer.getName());
			
			MessageManager.good(killer, "You got §6" + a.getCfg().getInt("arena.money.add") + " §aUGP!");
			MessageManager.bad(ent, "You lost §6" + a.getCfg().getInt("arena.money.remove") + " §cUGP!");
			
			ScoreboardStats.addMoney(a, killer);
			ScoreboardStats.removeMoney(a, ent);
			ScoreboardStats.createScoreboard(killer);
			ScoreboardStats.createScoreboard(ent);
		}
		
		if (killer == null) {
			return;
		}
		
	    if (ArenaManager.getInstance().getPlayerArena(killer) != null) {
	    	Arena a = ArenaManager.getInstance().getArena(ArenaManager.getInstance().getPlayerArena(killer));
	    	if (a.getArenaState() != ArenaState.STARTED) {
	    		return;
	    	}
	    	
			killer.getInventory().clear();
			killer.getInventory().setArmorContents(null);
			a.addLevel(killer);
	    }
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if (ArenaManager.getInstance().getPlayerArena(e.getPlayer()) != null) {
			Arena a = ArenaManager.getInstance().getArena(ArenaManager.getInstance().getPlayerArena(e.getPlayer()));
			if (a.getArenaState() == ArenaState.LOADING) {
				e.setRespawnLocation(a.getSpawn(a.map1, a.map2));
			}
			
			if (a.getArenaState() == ArenaState.STARTED) {
				e.setRespawnLocation(a.getSpawn(a.map1, a.map2));
				e.getPlayer().getInventory().clear();
				e.getPlayer().getInventory().setArmorContents(null);

				a.addWeapon(e.getPlayer(), a.getLevel(e.getPlayer()));
				a.addArmor(e.getPlayer(), a.getLevel(e.getPlayer()));
			}
	      
			if ((a.getArenaState() == ArenaState.COUNTING_DOWN) || (a.getArenaState() == ArenaState.WAITING)) {
				e.setRespawnLocation(a.getLobby());
			}
	    }
	}
	
}
