package fr.alexandre1156.wardensrevolt.scheduler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Maps;

import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.event.custom.ItemCooldownFinishEvent;

public class ItemCooldown {
	
	private HashMap<UUID, HashMap<ItemStack, Integer>> cooldowns;
	
	public ItemCooldown() {
		cooldowns = Maps.newHashMap();
		this.itemCooldown();
	}

	private void itemCooldown(){
		Bukkit.getScheduler().runTaskTimer(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				if(!cooldowns.isEmpty()){
					Iterator<Entry<UUID, HashMap<ItemStack, Integer>>> iter = cooldowns.entrySet().iterator();
					while(iter.hasNext()){
						Entry<UUID, HashMap<ItemStack, Integer>> entries = iter.next();
						if(entries.getValue() != null){
							Iterator<Entry<ItemStack, Integer>> iter2 = entries.getValue().entrySet().iterator();
							while(iter2.hasNext()){
								Entry<ItemStack, Integer> entries2 = iter2.next();
								entries2.setValue(entries2.getValue().intValue()-1);
								if(entries2.getValue() == 0){
									iter2.remove();
									//entries.getValue().remove(entries2.getKey());
									Bukkit.getPluginManager().callEvent(new ItemCooldownFinishEvent(entries.getKey(), entries2.getKey()));
								}
							}
						}
					}
				}
			}
			
		}, 0L, 20L);
	}
	
	/**
	 * Rajoute un cooldown à l'item
	 * @param uuid L'uuid du sorcier
	 * @param item L'item dont il faut rajouter un cooldown
	 * @param cooldown Cooldown (en seconde)
	 */
	public void addItemCooldown(UUID uuid, ItemStack item, int cooldown){
		if(cooldowns.get(uuid) != null)
			this.cooldowns.get(uuid).put(item, cooldown);
	}
	
	/**
	 * Si l'item est en cooldown
	 * @param uuid L'uuid du sorcier
	 * @param item L'item à checker
	 * @return true si l'item a un cooldown
	 */
	public boolean isItemHasCooldown(UUID uuid, ItemStack item){
		if(cooldowns.get(uuid) != null && cooldowns.get(uuid).containsKey(item)) return true;
		return false;
	}
	
	public void addWizard(UUID uuid){
		this.cooldowns.put(uuid, new HashMap<ItemStack, Integer>());
	}

	/**
	 * Permet de connaitre le temps restant du cooldown
	 * @param uuid L'uuid du sorcier
	 * @param item L'item dont on veut savoir le temps du cooldown restant
	 * @return Le temps restant, ou 0 s'il n'as pas de cooldown
	 */
	public int getCooldownLeft(UUID uuid, ItemStack item) {
		if(this.isItemHasCooldown(uuid, item)){
			return cooldowns.get(uuid).get(item);
		}
		return 0;
		
	}
	
}
