package fr.alexandre1156.wardensrevolt.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.event.custom.WardenCommandEvent;
import fr.alexandre1156.wardensrevolt.utils.Utils;

public class WardenCommand implements CommandExecutor{
	
	public WardenCommand() {
		WardenRevolt.getInstance().getCommand("wardenrevolt").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			if(label.equalsIgnoreCase("wardenrevolt")){
				Utils.consoleMessage("TEST", ChatColor.RED);
				Bukkit.getPluginManager().callEvent(new WardenCommandEvent((Player) sender, args));
			}
		}
		return false;
	}

}
