package org.ciprite.ugungame.listener.special;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ciprite.ugungame.game.ArenaManager;

public class BlendGrenade implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		Entity ent = e.getEntity();
		if (ArenaManager.getInstance().getPlayerArena((Player) e.getEntity().getShooter()) != null) {
			if (ent instanceof Snowball) {
				for (Entity s : ent.getNearbyEntities(5D, 5D, 5D)) {
					Player p = (Player) s;
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 10));
				}
			}
			
			if (ent instanceof Arrow) {
				ent.remove();
			}
		}
	}
	
}
