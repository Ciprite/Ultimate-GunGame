package org.ciprite.ugungame.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.ciprite.ugungame.UGunGame;
import org.ciprite.ugungame.listener.block.BlockListener;
import org.ciprite.ugungame.listener.entity.EntityListener;
import org.ciprite.ugungame.listener.player.PlayerListener;
import org.ciprite.ugungame.listener.sign.SignListener;
import org.ciprite.ugungame.listener.special.AllSpecialListener;

/**
 * @info AllListener
 * @author Ciprite
 */

public class AllListener implements Listener {

	public AllListener() {
		Bukkit.getServer().getPluginManager().registerEvents(new BlockListener(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new SignListener(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new EntityListener(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new AllSpecialListener(), UGunGame.getInstance());
	}
	
}
