package org.ciprite.ugungame.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.ciprite.ugungame.game.ArenaManager;

public class BlockListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (ArenaManager.getInstance().getPlayerArena(e.getPlayer()) != null) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (ArenaManager.getInstance().getPlayerArena(e.getPlayer()) != null) {
			e.setCancelled(true);
		}
	}
	
}
