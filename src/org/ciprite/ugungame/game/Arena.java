package org.ciprite.ugungame.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ciprite.ugungame.UGunGame;
import org.ciprite.ugungame.game.scoreboard.ScoreboardStats;
import org.ciprite.ugungame.game.scoreboard.ScoreboardVote;
import org.ciprite.ugungame.util.Countdown;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info Arena
 * @author Ciprite
 */

public class Arena {
	
	public boolean map1, map2;
	
	private ArenaState state;
	private String name;
	private File file, signFile, levelFile;
	private FileConfiguration cfg, signCfg, levelCfg;
	private ArrayList<String> players;
	private HashMap<String, Integer> level;
	private int taskid;
	private Integer[] countdown, armor;
	private String[] items;
  
	public Arena(String name) {
		this.name = name;
		this.state = ArenaState.WAITING;
    
		this.file = new File("plugins/UGunGame/Arenas/" + this.name, this.name + ".yml");
		this.signFile = new File("plugins/UGunGame/Arenas/" + this.name, "Signs.yml");
		this.levelFile = new File("plugins/UGunGame/Arenas/" + this.name, "Level.yml");
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
		this.signCfg = YamlConfiguration.loadConfiguration(this.signFile);
		this.levelCfg = YamlConfiguration.loadConfiguration(this.levelFile);
		
		this.countdown = new Integer[] { 30, 20, 10, 5, 4, 3, 2, 1 };
		this.items = new String[] { "289 1" };
		this.armor = new Integer[] { 298, 299, 300, 301 };
    
		this.cfg.addDefault("arena.players.max", Integer.valueOf(12));
		this.cfg.addDefault("arena.players.start", Integer.valueOf(6));
		this.cfg.addDefault("arena.countdown", Arrays.asList(countdown));
		this.cfg.addDefault("arena.money.add", Integer.valueOf(2));
		this.cfg.addDefault("arena.money.remove", Integer.valueOf(1));
		this.cfg.addDefault("arena.maps.map1", "Map1");
		this.cfg.addDefault("arena.maps.map2", "Map2");
		this.cfg.options().copyDefaults(true);

		if (!levelFile.exists()) {
			this.levelCfg.addDefault("levels.level.0.items", Arrays.asList(items));
			this.levelCfg.addDefault("levels.level.0.armor", Arrays.asList(armor));
			this.levelCfg.options().copyDefaults(true);
		}
		
		this.players = new ArrayList<String>();
		this.level = new HashMap<String, Integer>();
		
		try {
			this.cfg.save(this.file);
			this.levelCfg.save(this.levelFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addLevel(Player p) {
		if (this.level.containsKey(p.getName())) {
			this.level.put(p.getName(), Integer.valueOf(((Integer)this.level.get(p.getName())).intValue() + 1));
			if (((Integer)this.level.get(p.getName())).intValue() < 6) {
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
	        	addWeapon(p, ((Integer) this.level.get(p.getName())).intValue());
				addArmor(p, ((Integer) this.level.get(p.getName())).intValue());
				p.playSound(p.getLocation(), Sound.NOTE_PIANO, 100.0F, 100.0F);
				p.setLevel(p.getLevel() + 1);
				ScoreboardStats.createScoreboard(p);
			}
	    }
	}
	  
	public void removeLevel(Player p) {
		if (this.level.containsKey(p.getName())) {
			if (((Integer)this.level.get(p.getName())).intValue() <= 0) {
				return;
			}
			
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 100.0F, 100.0F);
			p.setLevel(p.getLevel() - 1);
			this.level.put(p.getName(), Integer.valueOf(((Integer)this.level.get(p.getName())).intValue() - 1));
			ScoreboardStats.createScoreboard(p);
		}
	}
	
	public int getLevel(Player p) {
		if (this.level.containsKey(p.getName())) {
			return ((Integer)this.level.get(p.getName())).intValue();
	    }
	    
		return 0;
	}
	
	@SuppressWarnings("deprecation")
	public void addWeapon(Player p, int level) {
		if (!this.levelCfg.contains("levels.level." + level + ".items")) {
			win(p);
			return;
	    }

		p.getInventory().clear();
		
		for (String list : this.levelCfg.getStringList("levels.level." + level + ".items")) {
			String[] split0 = list.split(" ");
			String amount = split0[1];
			String id = split0[0];
			int damage = 0;
			if (split0[0].contains(":")) {
				String[] split1 = split0[0].split(":");
				id = split1[0];
				damage = Integer.parseInt(split1[1]);
			}
			
			try {
				int itemId = Integer.parseInt(id);
				int itemAmount = Integer.parseInt(amount);
				p.getInventory().addItem(new ItemStack[] { new ItemStack(itemId, itemAmount, (short)damage) });
			}
			catch (NumberFormatException e) {
				System.out.println("An error ocurred by paresing the item id/item amount to an integer");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addArmor(Player p, int level) {
		p.getInventory().setArmorContents(null);
		
		for (int i = 0; i < this.levelCfg.getStringList("levels.level." + level + ".armor").size(); i++) {
			try {
				int itemId = Integer.parseInt(this.levelCfg.getStringList("levels.level." + level + ".armor").get(i));
				if (i == 0) p.getInventory().setHelmet(new ItemStack(itemId, 1, (short) 0));
				if (i == 1) p.getInventory().setChestplate(new ItemStack(itemId, 1, (short) 0));
				if (i == 2) p.getInventory().setLeggings(new ItemStack(itemId, 1, (short) 0));
				if (i == 3) p.getInventory().setBoots(new ItemStack(itemId, 1, (short) 0));
			}
			catch (NumberFormatException e) {
				System.out.println("An error ocurred by paresing the item id/item amount to an integer");
			}
		}
	}
  
	public void updateSign() {
		try {
			BlockState bs = new Location(Bukkit.getWorld(getSignCfg().getString("locations.join-sign.world")), getSignCfg().getDouble("locations.join-sign.x"), getSignCfg().getDouble("locations.join-sign.y"), getSignCfg().getDouble("locations.join-sign.z")).getBlock().getState();
			if ((bs instanceof Sign)) {
				Sign s = (Sign)bs;
				s.setLine(2, "§8" + this.players.size() + "/" + this.cfg.getInt("arena.players.max"));
				s.update();
			}
		} catch (Exception e) {
			System.out.println("No sign found for arena!");
		}
	}
  
	public void addPlayer(Player p) {
		this.players.add(p.getName());
		this.level.put(p.getName(), Integer.valueOf(0));
    
		ScoreboardVote.createScoreboard(this, p);
		
		p.teleport(getLobby());
		
		p.getInventory().setItem(0, new ItemStack(Material.PAPER));
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(20D);
		p.setFoodLevel(20);
		
		ArenaManager.getInstance().addPlayer(p, this);
		updateSign();
	}
  
	public void removePlayer(Player p) {
		this.players.remove(p.getName());
		this.level.remove(p.getName());
    
		ScoreboardStats.removeScoreboard(p);
		
		p.teleport(getHub());
    
		ArenaManager.getInstance().removePlayer(p);
		updateSign();
	}
  
	public void sendMessage(String msg) {
		for (String s : this.players) {
			MessageManager.info(Bukkit.getPlayer(s), msg);
		}
	}
	
	public void forceStart() {
		if (this.state == ArenaState.COUNTING_DOWN) {
			return;
		}
		
		this.state = ArenaState.COUNTING_DOWN;
    
		final Countdown c = new Countdown(this.getCfg().getIntegerList("arena.countdown").get(0), "§bThe game starts in §e%time §bseconds!", this, this.getCfg().getIntegerList("arena.countdown"));
		this.taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(UGunGame.getInstance(), new Runnable() {
			public void run() {
				if (!c.isDone()) {
					c.run();
				}
				else {
					Bukkit.getScheduler().cancelTask(taskid);
					
					Arena.this.state = ArenaState.STARTED;
					for (String s : players) { 
						ScoreboardStats.removeScoreboard(Bukkit.getPlayer(s));
					}

					if (ScoreboardVote.map1Vote > ScoreboardVote.map2Vote) {
						map1 = true;
					} else if (ScoreboardVote.map2Vote > ScoreboardVote.map1Vote) {
						map2 = true;
					} else {
						Random rand = new Random();
						int r = rand.nextInt(2);
						if (r == 0) {
							map1 = true;
						} else if (r == 1) {
							map2 = true;
						}
					}
					
					for (int i = 0; i < players.size(); i++) {
						for (String s : players) {
							Bukkit.getPlayer(s).teleport(getSpawn(map1, map2));
						}
					}
					
					for (String s : players) {
						Player p = Bukkit.getPlayer(s);
						
						MessageManager.info(p, "You're now level §e0§7! Kill other players to level up!");
						ScoreboardStats.createScoreboard(p);

						addWeapon(p, 0);
						addArmor(p, 0);
					}
				}
			}
		}, 0L, 20L);
	}
	
	public void start() {
		if (this.state == ArenaState.COUNTING_DOWN) {
			return;
		}
		
		this.state = ArenaState.COUNTING_DOWN;
    
		final Countdown c = new Countdown(this.getCfg().getIntegerList("arena.countdown").get(0), "§bThe game starts in §e%time §bseconds!", this, this.getCfg().getIntegerList("arena.countdown"));
		this.taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(UGunGame.getInstance(), new Runnable() {
			public void run() {
				if (!c.isDone()) {
					c.run();
				}
				else {
					Bukkit.getScheduler().cancelTask(taskid);
					if (players.size() < cfg.getInt("arena.players.start")) {
						sendMessage("§cNot enough players to start the game!");
						stop();
						return;
					}
					
					Arena.this.state = ArenaState.STARTED;
					for (String s : players) { 
						ScoreboardStats.removeScoreboard(Bukkit.getPlayer(s));
					}

					if (ScoreboardVote.map1Vote > ScoreboardVote.map2Vote) {
						map1 = true;
					} else if (ScoreboardVote.map2Vote > ScoreboardVote.map1Vote) {
						map2 = true;
					} else {
						Random rand = new Random();
						int r = rand.nextInt(2);
						if (r == 0) {
							map1 = true;
						} else if (r == 1) {
							map2 = true;
						}
					}
					
					for (int i = 0; i < players.size(); i++) {
						for (String s : players) {
							Bukkit.getPlayer(s).teleport(getSpawn(map1, map2));
						}
					}
					
					for (String s : players) {
						Player p = Bukkit.getPlayer(s);
						
						MessageManager.info(p, "You're now level §e0§7! Kill other players to level up!");
						ScoreboardStats.createScoreboard(p);

						addWeapon(p, 0);
						addArmor(p, 0);
					}
				}
			}
		}, 0L, 20L);
	}
  
	public void stop() {
		for (String s : players) {
			Player p = Bukkit.getPlayer(s);
      
			ScoreboardStats.removeScoreboard(p);
			
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.teleport(getHub());
			p.setLevel(0);
			
			ArenaManager.getInstance().removePlayer(p);
		}
		
		state = ArenaState.WAITING;
    
		ScoreboardVote.vote.clear();
		ScoreboardVote.map1Vote = 0;
		ScoreboardVote.map2Vote = 0;
		map1 = false;
		map2 = false;
		
		players.clear();
		updateSign();
	}
  
	public void win(Player p) {
		state = ArenaState.LOADING;
    	sendMessage("");
    	sendMessage("§c§lGAME OVER! §bThe winner is " + p.getName() + "!");
    	sendMessage("§bYou will be teleported until 5 seconds!");
    	sendMessage("");
    	for (String s : players) {
    		Bukkit.getPlayer(s).getInventory().clear();
    		
    		ArenaManager.getInstance().getBankCfg().set("accounts." + Bukkit.getPlayer(s).getName(), ScoreboardStats.getMoney(Bukkit.getPlayer(s)));
    		try {
				ArenaManager.getInstance().getBankCfg().save(ArenaManager.getInstance().getBank());
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	Bukkit.getScheduler().scheduleSyncDelayedTask(UGunGame.getInstance(), new Runnable() {
    		public void run() {
    			stop();
    		}
    	}, 100L);
	}
  
	public Location getSpawn(boolean map1, boolean map2) {
		if (map1) {
			World w = Bukkit.getWorld(this.cfg.getString("locations.spawns.spawn.map1.world"));
			double x = this.cfg.getDouble("locations.spawns.spawn.map1.x");
			double y = this.cfg.getDouble("locations.spawns.spawn.map1.y");
			double z = this.cfg.getDouble("locations.spawns.spawn.map1.z");
			double yaw = this.cfg.getDouble("locations.spawns.spawn.map1.yaw");
			double pitch = this.cfg.getDouble("locations.spawns.spawn.map1.pitch");
			return new Location(w, x, y, z, (float)yaw, (float)pitch);
		} else if (map2) {
			World w = Bukkit.getWorld(this.cfg.getString("locations.spawns.spawn.map2.world"));
			double x = this.cfg.getDouble("locations.spawns.spawn.map2.x");
			double y = this.cfg.getDouble("locations.spawns.spawn.map2.y");
			double z = this.cfg.getDouble("locations.spawns.spawn.map2.z");
			double yaw = this.cfg.getDouble("locations.spawns.spawn.map2.yaw");
			double pitch = this.cfg.getDouble("locations.spawns.spawn.map2.pitch");
			return new Location(w, x, y, z, (float)yaw, (float)pitch);
		} else {
			return null;
		}
	}
	
	public Location getHub() {
		return new Location(Bukkit.getWorld(this.cfg.getString("locations.spawns.hub.world")), this.cfg.getDouble("locations.spawns.hub.x"), this.cfg.getDouble("locations.spawns.hub.y"), this.cfg.getDouble("locations.spawns.hub.z"), (float)this.cfg.getDouble("locations.spawns.hub.yaw"), (float)this.cfg.getDouble("locations.spawns.hub.pitch"));
	}
	
	public Location getLobby() {
		return new Location(Bukkit.getWorld(this.cfg.getString("locations.spawns.lobby.world")), this.cfg.getDouble("locations.spawns.lobby.x"), this.cfg.getDouble("locations.spawns.lobby.y"), this.cfg.getDouble("locations.spawns.lobby.z"), (float)this.cfg.getDouble("locations.spawns.lobby.yaw"), (float)this.cfg.getDouble("locations.spawns.lobby.pitch"));
	}
	
	public ArenaState getArenaState() {
		return this.state;
	}
  
	public String getName() {
		return this.name;
	}
  
	public File getFile() {
		return this.file;
	}
	
	public File getSignFile() {
		return this.signFile;
	}

	public File getLevelFile() {
		return this.levelFile;
	}

	public FileConfiguration getCfg() {
		return this.cfg;
  	}
	
	public FileConfiguration getSignCfg() {
		return this.signCfg;
	}
	
	public FileConfiguration getLevelCfg() {
		return this.levelCfg;
	}
  
	public ArrayList<String> getPlayers() {
		return this.players;
  	}
  
	public void setArenaState(ArenaState state) {
		this.state = state;
	}
	  
}

