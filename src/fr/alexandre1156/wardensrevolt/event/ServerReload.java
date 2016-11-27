package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class ServerReload implements Listener{

	@EventHandler
	public void onServerReloadByPlayer(PlayerCommandPreprocessEvent e){
		if(e.getMessage().equals("/reload")){
			for(Player p : Bukkit.getOnlinePlayers())
				p.kickPlayer("Obligatoire pour le bon fonctionnement du plugin !");
		}
	}
	
	@EventHandler
	public void onServerReloadByConsole(ServerCommandEvent e){
		if(e.getCommand().equals("reload")){
			for(Player p : Bukkit.getOnlinePlayers())
				p.kickPlayer("Obligatoire pour le bon fonctionnement du plugin !");
		}
	}
	
}
