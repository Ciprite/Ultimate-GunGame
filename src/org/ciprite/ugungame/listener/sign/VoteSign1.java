package org.ciprite.ugungame.listener.sign;

import java.io.IOException;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.game.scoreboard.ScoreboardVote;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info VoteSign1
 * @author Ciprite
 */

public class VoteSign1 implements Listener {

	@EventHandler
	public void onSign(SignChangeEvent e) {
		if ((e.getLine(0).equalsIgnoreCase("uggv")) && (e.getLine(1) != null) && (e.getLine(2).equalsIgnoreCase("map1"))) {
			if (!e.getPlayer().hasPermission("ugungame.create")) {
				e.getBlock().breakNaturally();
				MessageManager.bad(e.getPlayer(), "You dont have the permission to do this!");
				return;
			}
			
			if (!ArenaManager.getInstance().isExist(e.getLine(1))) {
				e.getBlock().breakNaturally();
				MessageManager.bad(e.getPlayer(), "The arena " + e.getLine(1) + " does not exist!");
			} 
			else {
				Arena a = ArenaManager.getInstance().getArena(e.getLine(1));
	
				e.setLine(0, "§8[§3UGunGame§8]");
				e.setLine(1, a.getName());
				e.setLine(2, "§cVote " + ScoreboardVote.worldmap1);
	
				a.getSignCfg().set("locations.vote-sign1.world", e.getBlock().getLocation().getWorld().getName());
				a.getSignCfg().set("locations.vote-sign1.x", Integer.valueOf(e.getBlock().getX()));
				a.getSignCfg().set("locations.vote-sign1.y", Integer.valueOf(e.getBlock().getY()));
				a.getSignCfg().set("locations.vote-sign1.z", Integer.valueOf(e.getBlock().getZ()));
				try {
					a.getSignCfg().save(a.getSignFile());
					MessageManager.good(e.getPlayer(), "The sign to vote the map §7" + ScoreboardVote.worldmap1 + " §ahas been set!");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			BlockState bs = e.getClickedBlock().getState();
			if ((bs instanceof Sign)) {
				Sign s = (Sign) bs;
				if ((s.getLine(0).equalsIgnoreCase("§8[§3UGunGame§8]")) && (s.getLine(1) != null) && (s.getLine(2).equalsIgnoreCase("§cVote " + ScoreboardVote.worldmap1))) {
					p.performCommand("ugungame vote 1");
				}
			}
		}
	}
	
}
