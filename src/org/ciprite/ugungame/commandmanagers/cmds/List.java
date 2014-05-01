package org.ciprite.ugungame.commandmanagers.cmds;

import org.bukkit.entity.Player;
import org.ciprite.ugungame.commandmanagers.SubCommand;
import org.ciprite.ugungame.game.Arena;
import org.ciprite.ugungame.game.ArenaManager;
import org.ciprite.ugungame.util.MessageManager;

public class List extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {
		if (!p.hasPermission("ugungame.list")) {
			MessageManager.bad(p, "You dont have the permission to do this!");
			return;
        }
        
		for (String s : ArenaManager.getInstance().getCfg().getStringList("arenas")) {
			Arena a = ArenaManager.getInstance().getArena(s);
			MessageManager.info(p, "- " + a.getName());
		}
	}

	@Override
	public String name() {
		return "list";
	}

}
