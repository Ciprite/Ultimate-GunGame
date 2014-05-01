package org.ciprite.ugungame.listener.special;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ciprite.ugungame.game.ArenaManager;

public class Minigun implements Listener {

	@EventHandler
	public void miniGun(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (ArenaManager.getInstance().getPlayerArena(p) != null) {
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (p.getInventory().getItemInHand().getType() == Material.STICK) {
					if (p.getInventory().contains(Material.ARROW)) {
						p.launchProjectile(Arrow.class);
						p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.ARROW, 1) });
					}
				}
			}
		}
	}
	
}
