package fr.alexandre1156.wardensrevolt.command;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.event.custom.WardenCommandEvent;

public class ReloadCommand implements Listener{

	@EventHandler
	public void onWardenCommand(WardenCommandEvent e){
		Player p = e.getPlayer();
		String[] args = e.getArgs();
		if(args.length == 0)
			p.sendMessage("Reload le fichier de configuration");
		else if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
			WardenRevolt.getInstance().getWardenConfig().reloadConfig(p);
		}
	}


	

	

	
	
}
