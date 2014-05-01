package org.ciprite.ugungame.commandmanagers;

import org.bukkit.entity.Player;

/**
 * @info SubCommand
 * @author Ciprite
 */

public abstract class SubCommand {

	public String usage = "Invalid usage: §6/ugungame ";
	
	public abstract void onCommand(Player p, String[] args);
	
	public abstract String name();
	
}
