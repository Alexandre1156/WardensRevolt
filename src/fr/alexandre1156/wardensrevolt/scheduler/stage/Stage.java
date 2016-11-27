package fr.alexandre1156.wardensrevolt.scheduler.stage;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.Utils;

public abstract class Stage {
	
	private static StageListener listener;
	protected static World world;
	public boolean isActive;
	
	public Stage(Plugin plugin){
		listener = new StageListener(this);
		world = ConfigList.world;
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
	}
	
	public void onEntityHitEntity(EntityDamageByEntityEvent e){
		if(this.isActive){
			Entity damager = e.getDamager();
			Entity damaged = e.getEntity();
			if(damaged instanceof ArmorStand && damager instanceof Player){
				if(!((Player) damager).getGameMode().equals(GameMode.CREATIVE))
					onPlayerHitWarden();
				else
					e.setCancelled(true);
			}
		}
	}
	
	public void onEntityDie(EntityDeathEvent e){
		if(this.isActive){
			Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					int blindNum = Utils.getBlindingsNumber();
					int killerNum = Utils.getKillersNumber();
					int zombieNum = Utils.getZombiesNumber();
					int wizNum = Utils.getWizardsNumber();
					if(blindNum == 0 && killerNum == 0 && wizNum == 0 && zombieNum == 0 && !(e.getEntity() instanceof Player))
						onAllMonstersDie();
					Utils.consoleMessage(blindNum+" "+killerNum+" "+zombieNum+" "+wizNum+" "+e.getEntity());
					onAnEntityDie(e.getEntity());
				}
			});
		}
	}
	
	protected abstract void onAnEntityDie(Entity entityDeath);
	
	protected abstract void onPlayerHitWarden();
	
	protected abstract void onAllMonstersDie();
	
	public abstract void start();
	
	public StageListener getStageListener(){
		return listener;
	}
	
	public enum WardenStage {
		STAGE_ONE, STAGE_TWO, STAGE_THREE, STAGE_FOUR, STAGE_FIVE
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
		 
		 @EventHandler
		 public void onEntityDie(EntityDeathEvent e){
			 stage.onEntityDie(e);
		 }
	}
	
}
