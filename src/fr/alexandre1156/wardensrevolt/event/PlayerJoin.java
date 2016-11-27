package fr.alexandre1156.wardensrevolt.event;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigError;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.Utils;

public class PlayerJoin implements Listener{
	
	private static ArrayList<ArrayList<String>> configError = new ArrayList<>();

	private WardenRevolt instance;
	private Location hub;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		if(instance == null)
			instance = WardenRevolt.getInstance();
		if(!instance.getGameTimer().isAlive())
			instance.getGameTimer().start();
		if(hub == null)
			hub = new Location(ConfigList.world, ConfigList.hub[0], ConfigList.hub[1], ConfigList.hub[2]);
		
		Player p = e.getPlayer();
		p.teleport(hub);
		p.getInventory().clear();
		p.getInventory().addItem(Stuff.changeKit);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance().getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.openInventory(Stuff.inventoryKits);
			}
			
		}, 10L);
		instance.getScoreboard().displayScoreboard();
		if(!configError.isEmpty()){
			p.sendMessage(Stuff.PLUGIN_TAG+ChatColor.RED+"-----------Config Error-----------");
			for(ArrayList<String> arr : configError){
				for(String error : arr){
					p.sendMessage(error);
					Utils.consoleMessage(error, ChatColor.YELLOW);
				}
			}
			p.sendMessage(ChatColor.YELLOW+"-Tous les erreurs peuvent êtres trouvés dans les logs du serveur !");
			p.sendMessage(Stuff.PLUGIN_TAG+ChatColor.RED+"-----------Config Error-----------");
		}
	}
	
	public static void addError(ConfigError errors){
		configError.add(errors.getLogs());
	}
	
}	
