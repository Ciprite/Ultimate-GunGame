package org.ciprite.ugungame.listener.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ciprite.ugungame.UGunGame;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.game.ArenaState;
import org.ciprite.ugungame.util.MessageManager;

public class PlayerListener implements Listener {

	private Inventory inv = null;
	
	public PlayerListener() {
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), UGunGame.getInstance());
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
	    if ((ArenaManager.getInstance().getPlayerArena(e.getPlayer()) != null) && (!e.getPlayer().isOp())) {
	    	if ((!e.getMessage().startsWith("/ugg")) || (!e.getMessage().startsWith("/ugungame"))) {
	    		e.setCancelled(true);
	    		MessageManager.bad(e.getPlayer(), "I'm sorry but you are not allowed to use commands while you are ingame! Use /ugg leave to leave the arena!");
	    	}
	    }
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		Player p = (Player) e.getEntity();
		if (ArenaManager.getInstance().getPlayerArena(p) != null) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (ArenaManager.getInstance().getPlayerArena(p) != null) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (ArenaManager.getInstance().getPlayerArena(e.getPlayer()) != null) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equalsIgnoreCase("§2Map Voting")) {
			e.setCancelled(true);
			
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == null) {
				e.getView().close();
				return;
			}
			
			if (e.getCurrentItem().getType() == Material.DIAMOND) {
				p.performCommand("ugg vote 1");
			} else if (e.getCurrentItem().getType() == Material.EMERALD) {
				p.performCommand("ugg vote 2");
			}
			
			e.getView().close();
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (ArenaManager.getInstance().getPlayerArena(e.getPlayer()) != null) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getPlayer().getItemInHand().getType() == Material.PAPER) {
					inv = e.getPlayer().getServer().createInventory(null, 9, "§2Map Voting");
					
					Arena a = ArenaManager.getInstance().getArena(ArenaManager.getInstance().getPlayerArena(e.getPlayer()));
					
					ItemStack imap1 = new ItemStack(Material.DIAMOND);
					ItemMeta imap1Meta = imap1.getItemMeta();
					imap1Meta.setDisplayName("§6§lMap: §3" + a.getCfg().getString("arena.maps.map1"));
					imap1.setItemMeta(imap1Meta);

					ItemStack imap2 = new ItemStack(Material.EMERALD);
					ItemMeta imap2Meta = imap2.getItemMeta();
					imap2Meta.setDisplayName("§6§lMap: §3" + a.getCfg().getString("arena.maps.map2"));
					imap2.setItemMeta(imap2Meta);
					
					inv.setItem(2, imap1);
					inv.setItem(6, imap2);
					
					e.getPlayer().openInventory(inv);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (ArenaManager.getInstance().getPlayerArena(e.getPlayer()) != null) {
			Arena a = ArenaManager.getInstance().getArena(ArenaManager.getInstance().getPlayerArena(e.getPlayer()));
			e.setQuitMessage(null);
			a.removePlayer(e.getPlayer());
			a.sendMessage("§e" + e.getPlayer().getName() + " §7has left the game! (" + a.getPlayers().size() + "/" + a.getCfg().getInt("arena.players.max") + ")");
			e.getPlayer().getInventory().clear();
			if (a.getPlayers().size() < 2) {
				if (a.getArenaState() != ArenaState.STARTED) {
					return;
				}
				
				a.sendMessage("§cThe game has been cancelled!");
				a.stop();
			}
		}
	}
	
}
