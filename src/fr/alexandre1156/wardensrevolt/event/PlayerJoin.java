package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;

public class PlayerJoin implements Listener{

	private WardenRevolt instance;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		if(instance == null)
			instance = WardenRevolt.getInstance();
		if(!instance.getGameTimer().isAlive())
			instance.getGameTimer().start();
		
		Player p = e.getPlayer();
		p.teleport(p.getWorld().getSpawnLocation());
		p.getInventory().clear();
		p.getInventory().addItem(Stuff.CHANGE_KIT);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance().getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.openInventory(Stuff.KITS_INVENTORY);
			}
			
		}, 10L);
		instance.getScoreboard().displayScoreboard();
	}
	
}	
