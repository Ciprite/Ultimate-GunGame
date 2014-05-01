package org.ciprite.ugungame.listener.special;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ciprite.ugungame.game.ArenaManager;

public class GasGrenade implements Listener {

	@EventHandler
	public void onEgg(PlayerEggThrowEvent e) {
		if (ArenaManager.getInstance().getPlayerArena(e.getPlayer()) != null) {
			for (Entity s : e.getEgg().getNearbyEntities(5D, 5D, 5D)) {
				Player p = (Player) s;
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20, 10));
				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10 * 20, 10));
			}
		}
	}
	
}
