package fr.alexandre1156.wardensrevolt.event.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WardenCommandEvent extends Event{

	private Player p;
	private String[] args;
	private static final HandlerList handlers = new HandlerList();
	
	public WardenCommandEvent(Player player, String[] args) {
		this.p = player;
		this.args = args;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public String[] getArgs(){
		return args;
	}

}
