package org.ciprite.ugungame;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.ciprite.ugungame.commandmanagers.CommandManager;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.listener.AllListener;

/**
 * @info Main
 * @author Ciprite
 */

public class UGunGame extends JavaPlugin {

	public static Economy econ = null;
	
	public String prefix = "§8[§3UGG§8] ";
	
	private static UGunGame instance;
	
	@Override
	public void onEnable() {
		instance = this;
	
		if (!setupEconomy()) {
			System.out.println("[UGG] ERROR: Vault not found!");
			return;
		}
		
		for (String s : ArenaManager.getInstance().getCfg().getStringList("arenas")) {
			Arena a = new Arena(s);
			ArenaManager.getInstance().addArena(a);
			a.updateSign();
	    }
		
		CommandManager cm = new CommandManager();
		cm.setup();
		this.getCommand("ugungame").setExecutor(cm);
		
		Bukkit.getServer().getPluginManager().registerEvents(new AllListener(), this);
	}
	
	@Override
	public void onDisable() {
		for (String s : ArenaManager.getInstance().getCfg().getStringList("arenas")) {
			Arena a = ArenaManager.getInstance().getArena(s);
			a.stop();
		}
	}
	
	public static UGunGame getInstance() {
		return instance;
	}
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		
		econ = rsp.getProvider();
		return econ != null;
	}
	
}
