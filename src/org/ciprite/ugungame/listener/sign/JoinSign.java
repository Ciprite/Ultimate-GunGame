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
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info JoinSign
 * @author Ciprite
 */

public class JoinSign implements Listener {

	@EventHandler
	public void onSign(SignChangeEvent e) {
		if ((e.getLine(0).equalsIgnoreCase("ugg")) && (e.getLine(1) != null)) {
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

				e.setLine(0, "§8[§6UGunGame§8]");
				e.setLine(1, a.getName());
				e.setLine(2, "§8" + a.getPlayers().size() + "/" + a.getCfg().getInt("max-players"));

				a.getSignCfg().set("locations.join-sign.world", e.getBlock().getLocation().getWorld().getName());
				a.getSignCfg().set("locations.join-sign.x", Integer.valueOf(e.getBlock().getX()));
				a.getSignCfg().set("locations.join-sign.y", Integer.valueOf(e.getBlock().getY()));
				a.getSignCfg().set("locations.join-sign.z", Integer.valueOf(e.getBlock().getZ()));
				try {
					a.getSignCfg().save(a.getSignFile());
					MessageManager.good(e.getPlayer(), "The sign for arena §7" + a.getName() + " §ahas been set!");
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
				if ((s.getLine(0).equalsIgnoreCase("§8[§6UGunGame§8]")) && (s.getLine(1) != null)) {
					p.performCommand("ugungame join " + s.getLine(1));
				}
			}
		}
	}

}
