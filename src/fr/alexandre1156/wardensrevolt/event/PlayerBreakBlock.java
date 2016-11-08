package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBreakBlock implements Listener{

	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e){
		if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			e.setCancelled(true);
	}
	
}
