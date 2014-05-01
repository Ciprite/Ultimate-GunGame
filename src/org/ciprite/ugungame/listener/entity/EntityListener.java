package org.ciprite.ugungame.listener.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.game.ArenaState;

public class EntityListener implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();
			if (ArenaManager.getInstance().getPlayerArena(damager) != null) {
				Arena a = ArenaManager.getInstance().getArena(ArenaManager.getInstance().getPlayerArena(damager));
			    if (a.getArenaState() != ArenaState.STARTED) {
			    	e.setCancelled(true);
			    }
			}
		}
	}
	
}
