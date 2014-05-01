package org.ciprite.ugungame.listener.special;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.ciprite.ugungame.UGunGame;

public class FlameThrower implements Runnable {

	private Item fireball;
	private int taskId;

	public void flameListener(Item fireball) {
		this.taskId = UGunGame.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(UGunGame.getInstance(), this, 1L, 1L);
		this.fireball = fireball;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		List<Entity> entities = fireball.getNearbyEntities(1D, 1D, 1D);
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof LivingEntity) {
				Location loc1 = fireball.getLocation();
				Location loc2 = ((LivingEntity)entities.get(i)).getEyeLocation();
				loc2.setY(loc2.getY() - ((LivingEntity)entities.get(i)).getEyeHeight());
				Double radius = Math.pow(1D, 2D); 
				if (loc1.distanceSquared(loc2) <= radius) {
					LivingEntity entity = (LivingEntity) entities.get(i);
					entity.setFireTicks(120);
					entity.damage(2);
					fireball.remove();
					UGunGame.getInstance().getServer().getScheduler().cancelTask(this.taskId);
				}
			}
		}
		
		if (fireball.getTicksLived() > 5) {
			fireball.remove();
			UGunGame.getInstance().getServer().getScheduler().cancelTask(this.taskId);
		}

		if (fireball.getLocation().getBlock().getType() == Material.AIR) {
			fireball.getLocation().getBlock().setType(Material.FIRE);
		}

	}

}
