package fr.alexandre1156.wardensrevolt.scheduler.stage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import fr.alexandre1156.wardensrevolt.WardenRevolt;

public abstract class Stage implements Listener{
	
	private static StageListener listener;
	protected static ArmorStand wardenPhaseOne;
	protected static World world;
	
	public Stage(Plugin plugin){
		listener = new StageListener(this);
		world = Bukkit.getWorld("world");
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
	}
	
	protected static void initWardenPhaseOne(){
		Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				wardenPhaseOne = (ArmorStand) world.spawn(new Location(world, 
						world.getSpawnLocation().getX()+20.5, world.getSpawnLocation().getY(), world.getSpawnLocation().getZ()+20.5), 
						ArmorStand.class);
				wardenPhaseOne.setInvulnerable(true);
				wardenPhaseOne.setGravity(false);
				wardenPhaseOne.setCustomName(ChatColor.RED+"Warden");
				wardenPhaseOne.setCustomNameVisible(false);
				//wardenPhaseOne.setVisible(false);
			}
			
		});
	}
	
	public void onEntityHitEntity(EntityDamageByEntityEvent e){
		Entity damager = e.getDamager();
		Entity damaged = e.getEntity();
		if(damaged instanceof ArmorStand && damager instanceof Player){
			onPlayerHitWarden();
		}
	}
	
	protected abstract void onPlayerHitWarden();
	
	public StageListener getStageListener(){
		return listener;
	}
	
	public class StageListener implements Listener {
		 private Stage stage;
		 
		 public StageListener(Stage stage) {
			 this.stage = stage;
		}
		 
		 @EventHandler
		 public void onEntityHitEntity(EntityDamageByEntityEvent e){
			 stage.onEntityHitEntity(e);
		 }
	}
}
