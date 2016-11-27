package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDie implements Listener{

	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e){
		e.setKeepInventory(true);
	}
	
}
