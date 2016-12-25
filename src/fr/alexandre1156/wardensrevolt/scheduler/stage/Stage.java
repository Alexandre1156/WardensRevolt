package fr.alexandre1156.wardensrevolt.scheduler.stage;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vindicator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.entity.WardenMobs;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import net.minecraft.server.v1_11_R1.EnumParticle;

public abstract class Stage {
	
	private static StageListener listener;
	protected static World world;
	public boolean isActive;
	protected BukkitTask task;
	protected ArrayList<Integer> mobsSpawn;
	
	public Stage(Plugin plugin){
		listener = new StageListener(this);
		world = ConfigList.world;
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		mobsSpawn = new ArrayList<Integer>();
		this.executeEngine();
	}
	
	public void onEntityHitEntity(EntityDamageByEntityEvent e){
		if(this.isActive){
			Entity damager = e.getDamager();
			Entity damaged = e.getEntity();
			if(damaged instanceof ArmorStand && damaged.getCustomName() != null && damaged.getCustomName().equals(Stuff.WARDEN_TAG)){
				if(damager instanceof Player && !((Player) damager).getGameMode().equals(GameMode.CREATIVE))
					onPlayerHitWarden((Player) damager);
				else {
					e.setCancelled(true);
					this.onEntityHitWarden(damager);
				}
			}
		}
	}
	
	protected void onEntityHitWarden(Entity damager){}
	
	public void onEntityDie(EntityDeathEvent e){
		if(this.isActive){
			if(e.getEntity().getCustomName() != null){
				if(Utils.isMobTagExist(e.getEntity().getCustomName())){
					WardenMobs mob = WardenMobs.getMobByTag(e.getEntity().getCustomName());
					if(mob != null && mobsSpawn.contains(mob.getId()))
						mobsSpawn.remove(mobsSpawn.indexOf(mob.getId()));
				}
			}
			if(mobsSpawn.isEmpty())
				onAllMonstersDie();
			onAnEntityDie(e.getEntity());
		}
	}
	
	public void executeEngine(){
		task = Bukkit.getScheduler().runTaskTimer(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				if(isActive){
					stageEngine();
					for(LivingEntity ent : ConfigList.world.getLivingEntities()){
						if(ent instanceof Evoker && ent.getCustomName() != null && ent.getCustomName().equals(Stuff.ENERGY_SWIRL_TAG)){
							int x = 0, y = 4, d = y - 1, r = 4; 
							while(y >= x){
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, x+ent.getLocation().getX(), ent.getLocation().getY(), y+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, y+ent.getLocation().getX(), ent.getLocation().getY(), x+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, -x+ent.getLocation().getX(), ent.getLocation().getY(), y+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, -y+ent.getLocation().getX(), ent.getLocation().getY(), x+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, x+ent.getLocation().getX(), ent.getLocation().getY(), -y+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, y+ent.getLocation().getX(), ent.getLocation().getY(), -x+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, -x+ent.getLocation().getX(), ent.getLocation().getY(), -y+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, -y+ent.getLocation().getX(), ent.getLocation().getY(), -x+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								if(d >= 2*x){
									d -= 2*x+1;
									x++;
								} else if(d < 2 * (r - y)) {
									d += 2*y-1;
									y--;
								} else {
									d += 2*(y-x-1);
									y--;
									x++;
								}
							}
						} else if(ent instanceof Vindicator && ent.getCustomName() != null && ent.getCustomName().equals(Stuff.ENERGY_VORTEX_TAG)){
							int x = 0, y = 6, d = y - 1, r = 6; 
							while(y >= x){
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, x+ent.getLocation().getX(), ent.getLocation().getY(), y+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, y+ent.getLocation().getX(), ent.getLocation().getY(), x+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, -x+ent.getLocation().getX(), ent.getLocation().getY(), y+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, -y+ent.getLocation().getX(), ent.getLocation().getY(), x+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, x+ent.getLocation().getX(), ent.getLocation().getY(), -y+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, y+ent.getLocation().getX(), ent.getLocation().getY(), -x+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, -x+ent.getLocation().getX(), ent.getLocation().getY(), -y+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								Utils.spawnParticle(EnumParticle.CRIT_MAGIC, true, new Location(ConfigList.world, -y+ent.getLocation().getX(), ent.getLocation().getY(), -x+ent.getLocation().getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
								if(d >= 2*x){
									d -= 2*x+1;
									x++;
								} else if(d < 2 * (r - y)) {
									d += 2*y-1;
									y--;
								} else {
									d += 2*(y-x-1);
									y--;
									x++;
								}
							}
						}
					}
				}
			}
			
		}, 0l, 10l);

	}
	
	private void onCreatureSpawn(CreatureSpawnEvent e){
		if(isActive){
			Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					if(e.getSpawnReason().equals(SpawnReason.CUSTOM) || e.getSpawnReason().equals(SpawnReason.DEFAULT)){
						if(e.getEntity().getCustomName() != null){
							String customName = e.getEntity().getCustomName();
							if(Utils.isMobTagExist(customName)){
								WardenMobs mob = WardenMobs.getMobByTag(customName);
								if(mob != null){
									mobsSpawn.add(mob.getId());
									e.getEntity().setRemoveWhenFarAway(false);
								}
							}
						}
					}
				}
				
			});
		}
	}
	
	protected int getMobCountByID(int id){
		int i = 0;
		for(int entID : this.mobsSpawn){
			if(entID == id)
				i++;
		}
		return i;
	}
	
	protected abstract void onAnEntityDie(Entity entityDeath);
	
	protected abstract void onPlayerHitWarden(Player p);
	
	protected abstract void onAllMonstersDie();
	
	public abstract void start();
	
	protected abstract void stageEngine();
	
	public abstract int getStageID();
	
	public StageListener getStageListener(){
		return listener;
	}
	
	public enum WardenStage {
		STAGE_ONE(0), STAGE_TWO(1), STAGE_THREE(2), STAGE_FOUR(3), STAGE_FIVE(4);
		
		private int id;
		
		private WardenStage(int ID) {
			this.id = ID;
		}
		
		public int getID(){
			return id;
		}
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
		 
		 @EventHandler
		 public void onEntitySpawn(CreatureSpawnEvent e){
			 stage.onCreatureSpawn(e);
		 }
	}
	
}
