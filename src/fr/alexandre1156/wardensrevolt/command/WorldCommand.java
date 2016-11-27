package fr.alexandre1156.wardensrevolt.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.event.custom.WardenCommandEvent;

public class WorldCommand implements Listener{

	@EventHandler
	public void onWardenCommand(WardenCommandEvent e){
		Player p = e.getPlayer();
		String[] args = e.getArgs();
		if(args.length == 0)
			p.sendMessage("Permet de changer le monde dans le fichier de configuration, par celui-ci");
		else if(args.length == 1 && args[0].equalsIgnoreCase("world")){
			WardenRevolt.getInstance().getWardenConfig().setValue("World", p.getLocation().getWorld().toString());
			p.sendMessage(Stuff.PLUGIN_TAG+ChatColor.GREEN+" Le monde dans le fichier de config a bien était changé par lui !");
		}
	}


}
