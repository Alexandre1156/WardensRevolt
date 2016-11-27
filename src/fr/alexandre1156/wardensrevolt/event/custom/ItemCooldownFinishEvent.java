package fr.alexandre1156.wardensrevolt.event.custom;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ItemCooldownFinishEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	private UUID uuid;
	private ItemStack item;
	
	public ItemCooldownFinishEvent(UUID uuid, ItemStack item) {
		this.uuid = uuid;
		this.item = item;
	}
	
	/**
	 * 
	 * @return L'item dont le cooldown vient de se finir
	 */
	public ItemStack getItem() {
		return item;
	}
	
	/**
	 * 
	 * @return L'UUID du sorcier a qui appartient l'item
	 */
	public UUID getUuid() {
		return uuid;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
