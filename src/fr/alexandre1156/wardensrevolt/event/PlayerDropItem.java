package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e){
		if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			e.setCancelled(true);
	}
	
}

