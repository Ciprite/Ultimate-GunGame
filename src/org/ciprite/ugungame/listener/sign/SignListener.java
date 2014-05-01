package org.ciprite.ugungame.listener.sign;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.ciprite.ugungame.UGunGame;

public class SignListener implements Listener {

	public SignListener() {
		Bukkit.getServer().getPluginManager().registerEvents(new JoinSign(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new VoteSign1(), UGunGame.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new VoteSign2(), UGunGame.getInstance());
	}
	
}
