package org.ciprite.ugungame.commandmanagers;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.cmds.Create;
import org.ciprite.ugungame.commandmanagers.cmds.Join;
import org.ciprite.ugungame.commandmanagers.cmds.Leave;
import org.ciprite.ugungame.commandmanagers.cmds.List;
import org.ciprite.ugungame.commandmanagers.cmds.Remove;
import org.ciprite.ugungame.commandmanagers.cmds.SetEndSpawn;
import org.ciprite.ugungame.commandmanagers.cmds.SetLobby;
import org.ciprite.ugungame.commandmanagers.cmds.SetSpawn;
import org.ciprite.ugungame.commandmanagers.cmds.Start;
import org.ciprite.ugungame.commandmanagers.cmds.Stop;
import org.ciprite.ugungame.commandmanagers.cmds.Vote;
import org.ciprite.ugungame.util.MessageManager;

/**
 * @info CommandManager
 * @author Ciprite
 */

public class CommandManager implements CommandExecutor {

	private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
	
	public void setup() {
		commands.add(new SetSpawn());
		commands.add(new Create());
		commands.add(new Join());
		commands.add(new Leave());
		commands.add(new Remove());
		commands.add(new SetLobby());
		commands.add(new SetEndSpawn());
		commands.add(new Start());
		commands.add(new Stop());
		commands.add(new Vote());
		commands.add(new List());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			MessageManager.bad(sender, "You must be a player to perform this command.");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("ugungame") || label.equalsIgnoreCase("ugg")) {
			if (args.length == 0) {
				p.sendMessage("§8[][][]§6UGunGame - §aby §3Ciprite§8[][][]");
				p.sendMessage("§6/ugungame create <arenaname> §8- Create a new arena");
				p.sendMessage("§6/ugungame remove <arenaname> §8- Remove a arena");
				p.sendMessage("§6/ugungame list §8- Show all arenas");
				p.sendMessage("§6/ugungame setendspawn <arenaname> §8- Set the end spawn");
				p.sendMessage("§6/ugungame setlobby <arenaname> §8- Set the lobby for an arena");
				p.sendMessage("§6/ugungame setspawn <arenaname> <mapid> §8- Set the spawn for the map");
				p.sendMessage("§6/ugungame join <arenaname> §8- Join a arena");
				p.sendMessage("§6/ugungame leave §8- Leave a arena");
				p.sendMessage("§6/ugungame start <arenaname> §8- Force start a arena");
				p.sendMessage("§6/ugungame stop <arenaname> §8- Force stop a arena");
				p.sendMessage("§6/ugungame vote <mapid> §8- Vote for a map");
				return true;
			}
			
			SubCommand target = get(args[0]);
			
			if (target == null) {
				MessageManager.bad(p, "/ugungame " + args[0] + " is not a valid subcommand!");
				return true;
			}
			
			ArrayList<String> a = new ArrayList<String>();
			a.addAll(Arrays.asList(args));
			a.remove(0);
			args = a.toArray(new String[a.size()]);
			
			try {
				target.onCommand(p, args);
			} 
			catch (Exception e) {
				MessageManager.bad(p, "ERROR: " + e.getCause());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	private SubCommand get(String name) {
		for (SubCommand cmd : commands) {
			if (cmd.name().equalsIgnoreCase(name)) return cmd;
		}
		return null;
	}
	
}
