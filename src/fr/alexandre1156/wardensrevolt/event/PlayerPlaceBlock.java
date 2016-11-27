package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerPlaceBlock implements Listener{

	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e){
		if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			e.setCancelled(true);
	}
	
}
