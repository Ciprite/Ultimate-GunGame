package org.ciprite.ugungame.listener.special;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.util.MessageManager;

public class FlameThrowerListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent evt){
		if (evt.getAction() == Action.LEFT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_AIR) {
			Player player = evt.getPlayer();
			ItemStack iih = player.getItemInHand();
			if (ArenaManager.getInstance().getPlayerArena(player) != null) {
				if (iih.getType() == Material.BLAZE_ROD) {
					if (player.getInventory().contains(Material.FIREBALL)) {
						player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1, 1);
						Vector vel = player.getEyeLocation().getDirection().multiply(2.5F);
						Item fireball = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.FIRE, 16));
						fireball.setVelocity(vel);
						fireball.setPickupDelay(6000);
						player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.FIREBALL, 1) });
						FlameThrower flame = new FlameThrower();
						flame.flameListener(fireball);
						evt.setCancelled(true);
					} else {
						MessageManager.bad(player, "You dont have any fireballs in your inventory!");
					}
				}
			}
		}
	}
	
}
