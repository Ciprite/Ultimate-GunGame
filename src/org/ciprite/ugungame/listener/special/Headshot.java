package org.ciprite.ugungame.listener.special;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.ciprite.ugungame.UGunGame;
import org.ciprite.ugungame.game.ArenaManager;

/**
 * @info Headshot
 * @author Ciprite
 */

public class Headshot implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getCause() != DamageCause.PROJECTILE) return;
		Projectile proj = (Projectile) e.getDamager();
		if (!(proj.getShooter() instanceof Player)) return;
		Entity shot = e.getEntity();
		if (ArenaManager.getInstance().getPlayerArena((Player) shot) != null) {
			double y = proj.getLocation().getY();
			double shotY = shot.getLocation().getY();
			boolean headshot = y - shotY > 1.35D;
			if (headshot) {
				e.setDamage(e.getDamage() * 2);
				StringBuilder message = new StringBuilder(headshot ? UGunGame.getInstance().prefix + "§cHeadshot:" : "§cKein Headshot");
				if (shot instanceof Player) {
					message.append(" " + ((Player) shot).getDisplayName());
				} else {
					message.append(" " + shot.getType().getName());
				}
				
				((Player) proj.getShooter()).sendMessage(message.toString());
			}
		}
	}
	
}
