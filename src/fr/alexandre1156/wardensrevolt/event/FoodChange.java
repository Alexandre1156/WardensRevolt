package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodChange implements Listener{

	@EventHandler
	public void onPlayerFoodChange(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}
	
	
}
