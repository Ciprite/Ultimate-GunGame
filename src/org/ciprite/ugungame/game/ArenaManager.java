package org.ciprite.ugungame.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * @info ArenaManager
 * @author Ciprite
 */

public class ArenaManager {
	
	private static ArenaManager instance;
	
	private HashMap<String, Arena> arenas = new HashMap<String, Arena>();
	private HashMap<String, String> players = new HashMap<String, String>();
	private File file = new File("plugins/UGunGame", "arenas.yml");
	private File bank = new File("plugins/UGunGame", "accounts.yml");
	private FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.file);
	private FileConfiguration bankCfg = YamlConfiguration.loadConfiguration(this.bank);
  
	public static ArenaManager getInstance() {
		if (instance == null) {
			instance = new ArenaManager();
		}
		return instance;
	}
  
	public Arena getArena(String name) {
		if (this.arenas.containsKey(name)) {
			return (Arena)this.arenas.get(name);
		}
		return null;
	}	
  
	public void addArena(Arena a) {
		this.arenas.put(a.getName(), a);
		if (!this.cfg.getStringList("arenas").contains(a.getName())) {
			List<String> list = this.cfg.getStringList("arenas");
			if (list == null) {
				list = new ArrayList<String>();
			}
			
			list.add(a.getName());
			this.cfg.set("arenas", list);
			try {
				this.cfg.save(this.file);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
  
	public void removeArena(Arena a) {
		this.arenas.remove(a);
	    if (this.cfg.getStringList("arenas").contains(a.getName())) {
	    	List<String> list = this.cfg.getStringList("arenas");
	    	if (list == null) {
	   			list = new ArrayList<String>();
	    	}
	    	
	   		list.remove(a.getName());
	    	this.cfg.set("arenas", list);
	   		try {
	   			this.cfg.save(this.file);
	    	}
	    	catch (IOException e) {
	   			e.printStackTrace();
	   		}
	   	}
	}
  
	public boolean isExist(String name) {
		if (this.arenas.containsKey(name)) {
			return true;
		}
		return false;
	}
  
	public String getPlayerArena(Player p) {
		if (this.players.containsKey(p.getName())) {
			return (String)this.players.get(p.getName());
		}
		return null;
	}
  
	public void addPlayer(Player p, Arena a) {
		this.players.put(p.getName(), a.getName());
	}
  
	public void removePlayer(Player p) {
		this.players.remove(p.getName());
	}
  
	public File getFile() {
		return this.file;
	}
  
	public FileConfiguration getCfg() {
		return this.cfg;
  	}
	
	public File getBank() {
		return this.bank;
	}
	
	public FileConfiguration getBankCfg() {
		  return this.bankCfg;
	}
	
}
