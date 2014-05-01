package org.ciprite.ugungame.util;

import org.bukkit.command.CommandSender;
import org.ciprite.ugungame.UGunGame;

/**
 * @info MessageManager
 * @author Ciprite
 */

public class MessageManager {

	public static void good(CommandSender sender, String msg) {
		sender.sendMessage(UGunGame.getInstance().prefix + "§a" + msg);
	}

	public static void bad(CommandSender sender, String msg) {
		sender.sendMessage(UGunGame.getInstance().prefix + "§c" + msg);
	}
	
	public static void info(CommandSender sender, String msg) {
		sender.sendMessage(UGunGame.getInstance().prefix + "§7" + msg);
	}
	
}
