package org.ciprite.ugungame.listener.special;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.ciprite.ugungame.UGunGame;

public class AllSpecialListener implements Listener {

	public AllSpecialListener() {
		Bukkit.getServer().getPluginManager().registerEvents(new BlendGrenade(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new FlameThrowerListener(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new GasGrenade(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new Headshot(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new Minigun(), UGunGame.getInstance());
	}
	
}
