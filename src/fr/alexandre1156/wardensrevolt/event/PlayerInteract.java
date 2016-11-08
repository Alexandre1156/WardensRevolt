package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.alexandre1156.wardensrevolt.Stuff;

public class PlayerInteract implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		boolean alreadyClicked = false;
		Action action = e.getAction();
		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)){
			if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInOffHand() != null){
				switch(p.getInventory().getItemInMainHand().getType()){
				case NAME_TAG:
					p.openInventory(Stuff.KITS_INVENTORY);
					alreadyClicked = true;
					break;
				default:
					alreadyClicked = false;
					break;
				}
				if(!alreadyClicked){
					switch(p.getInventory().getItemInMainHand().getType()){
					case NAME_TAG:
						p.openInventory(Stuff.KITS_INVENTORY);
					default: 
						break;
					}
				}
			}
		}
	}
	
}
