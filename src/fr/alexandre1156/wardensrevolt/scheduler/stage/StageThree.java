package fr.alexandre1156.wardensrevolt.scheduler.stage;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.entity.EnergySwirl;
import fr.alexandre1156.wardensrevolt.entity.EnergyVortex;
import fr.alexandre1156.wardensrevolt.entity.WardenMobs;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.MathHelper;

public class StageThree extends Stage{
	
	private ArmorStand warden;
	private WitherSkeleton wardenEnt;
	private ItemStack[] wardenEquipement;
	private int wardenCount;
	private ArrayList<WitherSkeleton> wardenDeath;
	private Wither boss;
	private int waves;
	private PotionEffect speedThree;
	private ItemStack ironSword;
	private Random r;
	private int timerWaveThree;
	private boolean finalCinematic;

	public StageThree(Plugin plugin) {
		super(plugin);
		waves = 1;
		speedThree = new PotionEffect(PotionEffectType.SPEED, 9999, 2);
		ironSword = new ItemStack(Material.IRON_SWORD);
		r = new Random();
		timerWaveThree = 120;
		wardenEquipement = new ItemStack[5];
		wardenEquipement[0] = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta meta = (LeatherArmorMeta) wardenEquipement[0].getItemMeta();
		meta.setColor(Color.BLACK);
		wardenEquipement[0].setItemMeta(meta);
		wardenEquipement[1] = new ItemStack(Material.LEATHER_LEGGINGS);
		wardenEquipement[1].setItemMeta(meta);
		wardenEquipement[2] = new ItemStack(Material.LEATHER_BOOTS);
		wardenEquipement[2].setItemMeta(meta);
		wardenEquipement[3] = new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
		wardenEquipement[4] = new ItemStack(Material.DIAMOND_SWORD);
		wardenCount = 4;
		wardenDeath = Lists.newArrayList();
		finalCinematic = true;
	}

	@Override
	protected void onAnEntityDie(Entity entityDeath) {
		if(entityDeath instanceof WitherSkeleton && entityDeath.getCustomName() != null && finalCinematic){
			if(entityDeath.getCustomName().equals(Stuff.WARDEN_TAG+" 1") ||
				entityDeath.getCustomName().equals(Stuff.WARDEN_TAG+" 2") ||
				entityDeath.getCustomName().equals(Stuff.WARDEN_TAG+" 3") ||
				entityDeath.getCustomName().equals(Stuff.WARDEN_TAG+" 4")){
					wardenCount--;
					WardenRevolt.getInstance().getScoreboard().updateValue(7, Stuff.WARDEN_TAG+" : "+wardenCount);
					wardenDeath.add((WitherSkeleton) entityDeath);
					this.createDeadWarden(entityDeath.getLocation(), entityDeath.getCustomName(), entityDeath.getLocation().getYaw());
				if(wardenCount == 0){
					finalCinematic = false;
					ArrayList<WitherSkeleton> reviveWiSk = Lists.newArrayList();
					Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){
		
						@Override
						public void run() {
							for(WitherSkeleton ws : wardenDeath){
								reviveWiSk.add(createWitherSqueletonWarden(ws.getLocation(), ws.getCustomName(), true, false, 20d));
								for(ArmorStand ent : ConfigList.world.getEntitiesByClass(ArmorStand.class)){
									if(ent.getCustomName() != null && ent.getCustomName().equals(ws.getCustomName()))
										ent.remove();
								}
							}
						}
						
					}, 20);
					Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){
		
						@Override
						public void run() {
							ArrayList<Location> teleportLoc = Lists.newArrayList();
							teleportLoc.add(new Location(ConfigList.world, ConfigList.wardenSpawnStageThree[0], ConfigList.wardenSpawnStageThree[1], ConfigList.wardenSpawnStageThree[2]));
							teleportLoc.add(new Location(ConfigList.world, ConfigList.wardenSpawnStageThree[0], ConfigList.wardenSpawnStageThree[1]+2, ConfigList.wardenSpawnStageThree[2]));
							teleportLoc.add(new Location(ConfigList.world, ConfigList.wardenSpawnStageThree[0]+1, ConfigList.wardenSpawnStageThree[1]+2, ConfigList.wardenSpawnStageThree[2]));
							teleportLoc.add(new Location(ConfigList.world, ConfigList.wardenSpawnStageThree[0]-1, ConfigList.wardenSpawnStageThree[1]+2, ConfigList.wardenSpawnStageThree[2]));
							for(int i = 0; i < reviveWiSk.size(); i++){
								reviveWiSk.get(i).teleport(teleportLoc.get(i));
								((CraftEntity) reviveWiSk.get(i)).getHandle().yaw = 0;
								((CraftEntity) reviveWiSk.get(i)).getHandle().pitch = 0;
							}
						}
						
					}, 60);
					Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){

						@Override
						public void run() {
							for(int i = 0; i < reviveWiSk.size(); i++)
								reviveWiSk.get(i).setInvulnerable(false);
							Location loc = new Location(ConfigList.world, ConfigList.wardenSpawnStageThree[0], ConfigList.wardenSpawnStageThree[1]+1, ConfigList.wardenSpawnStageThree[2]);
							ConfigList.world.createExplosion(loc, 7f);
							boss = ConfigList.world.spawn(loc, Wither.class);
							boss.setRemoveWhenFarAway(false);
							boss.setInvulnerable(true);
							boss.setAI(false);
							boss.setCustomName(Stuff.CHEAT_WARDEN_TAG);
							Utils.sendJsonMessageToAllPlayers(Stuff.CHEATWARDEN_STAGETHREE_MESSAGE_FINAL);
							WardenRevolt.getInstance().nextStage(WardenStage.STAGE_FOUR.getID(), this);
						}
						
					}, 100);
				}
			}
		}
	}

	@Override
	protected void onPlayerHitWarden(Player p) {
		if(waves < 5){
			p.setVelocity(p.getLocation().getDirection().multiply(-2));
		}
	}
	
	@Override
	protected void onEntityHitWarden(Entity damager) {
		if(damager instanceof Arrow){
			Arrow arrow = (Arrow) damager;
			if(arrow.getShooter() instanceof Player){
				Player p = (Player) arrow.getShooter();
				EntityPlayer craftP = ((CraftPlayer) p).getHandle();
				double d0 = p.getLocation().getX() - arrow.getLocation().getX();
		        double d1 = craftP.getBoundingBox().b + (double)(craftP.length / 3.0f) - arrow.getLocation().getY();
		        double d2 = craftP.locZ - arrow.getLocation().getZ();
		        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		        arrow.setVelocity(new Vector(d0, d1 + d3 * 0.20000000298023224, d2));
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onAllMonstersDie() {
		waves++;
		switch(waves){
		case 2:
			Location wardenLoc = warden.getLocation();
			int airBlock = -1;
			while(ConfigList.world.getBlockAt(wardenLoc.getBlockX(), wardenLoc.getBlockY()+airBlock, wardenLoc.getBlockZ()).isEmpty()){
				airBlock--;
			}
			Location spawnLoc = new Location(ConfigList.world, wardenLoc.getBlockX(), wardenLoc.getY()+airBlock+1, wardenLoc.getBlockZ());
			for(int i = 0; i < 50; i++)
				this.spawnZombie(spawnLoc);
			for(int i = 0; i < 10; i++)
				this.spawnRapid(spawnLoc);
			break;
		case 3:
			Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					ConfigList.world.strikeLightningEffect(warden.getLocation());
					wardenEnt = createWitherSqueletonWarden(warden.getLocation(), Stuff.WARDEN_TAG, true, true, 20d);
					warden.remove();
				}
				
			}, 60);
			//Waves 4
			Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					waves++;
					Location loc = null;
					for(int i = 1; i <= 40; i++){
						if(ConfigList.mobSpawnPosRand)
							loc = Utils.getMobRandomLocation();
						else
							loc = Utils.getMobExistingLocation();
						spawnZombie(loc);
					}
					for(int i = 1; i <= 2; i++){
						if(ConfigList.mobSpawnPosRand)
							loc = Utils.getMobRandomLocation();
						else
							loc = Utils.getMobExistingLocation();
						spawnEnergyVortex(loc);
					}
					WardenRevolt.getInstance().getScoreboard().updateValue(1, ChatColor.YELLOW+"→Tuez tous les mobs !");
				}
				
			}, 100); //1200
			WardenRevolt.getInstance().getScoreboard().updateValue(1, "→ Survivez pendant 1 minute !");
			break;
		case 5:
			wardenEnt.setAI(false);
			wardenEnt.setInvulnerable(true);
			wardenEnt.setMaxHealth(100d);
			wardenEnt.setHealth(100d);
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGETHREE_MESSAGE_FOUR);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					Location loc = new Location(ConfigList.world, wardenEnt.getLocation().getX()+1, wardenEnt.getLocation().getY(), wardenEnt.getLocation().getZ());
					createWitherSqueletonWarden(loc, Stuff.WARDEN_TAG+" 2", true, false, 100d);
					ConfigList.world.strikeLightningEffect(loc);
					WardenRevolt.getInstance().getScoreboard().updateValue(7, Stuff.WARDEN_TAG+" : 2");
				}
				
			}, 60);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					Location loc = new Location(ConfigList.world, wardenEnt.getLocation().getX()-1, wardenEnt.getLocation().getY(), wardenEnt.getLocation().getZ());
					createWitherSqueletonWarden(loc, Stuff.WARDEN_TAG+" 3", true, false, 100d);
					ConfigList.world.strikeLightningEffect(loc);
					WardenRevolt.getInstance().getScoreboard().updateValue(7, Stuff.WARDEN_TAG+" : 3");
				}
				
			}, 70);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					Location loc = new Location(ConfigList.world, wardenEnt.getLocation().getX(), wardenEnt.getLocation().getY(), wardenEnt.getLocation().getZ()+1);
					createWitherSqueletonWarden(loc, Stuff.WARDEN_TAG+" 4", true, false, 100d);
					ConfigList.world.strikeLightningEffect(loc);
					WardenRevolt.getInstance().getScoreboard().updateValue(7, Stuff.WARDEN_TAG+" : 4");
				}
				
			}, 80);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					wardenEnt.setCustomName(Stuff.WARDEN_TAG+" 1");
					ConfigList.world.strikeLightningEffect(wardenEnt.getLocation());
					for(WitherSkeleton witherSke : ConfigList.world.getEntitiesByClass(WitherSkeleton.class)){
						if(witherSke.getCustomName() != null && (witherSke.getCustomName().equals(Stuff.WARDEN_TAG+" 1") || 
								witherSke.getCustomName().equals(Stuff.WARDEN_TAG+" 2") || 
								witherSke.getCustomName().equals(Stuff.WARDEN_TAG+" 3") || 
								witherSke.getCustomName().equals(Stuff.WARDEN_TAG+" 4"))){
							witherSke.setAI(true);
							witherSke.setInvulnerable(false);
						}
					}
					WardenRevolt.getInstance().getScoreboard().updateValue(1, ChatColor.YELLOW+"→Tuez tous les Warden !");
				}
				
			}, 90);
			break;
		}
	}

	@Override
	public void start() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				warden = ConfigList.world.spawn(new Location(ConfigList.world, ConfigList.wardenSpawnStageThree[0], ConfigList.wardenSpawnStageThree[1], ConfigList.wardenSpawnStageThree[2]), ArmorStand.class);
				warden.setCustomName(Stuff.WARDEN_TAG);
				warden.setCustomNameVisible(true);
				warden.setInvulnerable(true);
				warden.setGravity(false);
				warden.setHeadPose(new EulerAngle(0.5, 0.0, 0.0));
				warden.setLeftLegPose(new EulerAngle(0.5, 0.0, 0.0));
				warden.setRightLegPose(new EulerAngle(0.5, 0.0, 0.0));
				warden.setArms(true);
				warden.setBasePlate(false);
				ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
				LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
				meta.setColor(Color.BLACK);
				chestplate.setItemMeta(meta);
				ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
				leggings.setItemMeta(meta);
				ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
				boots.setItemMeta(meta);
				warden.setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (byte) 1));
				warden.setChestplate(chestplate);
				warden.setBoots(boots);
				warden.setLeggings(leggings);
				warden.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
				warden.setRemoveWhenFarAway(false);
				warden.addPotionEffect(Utils.speed);
			}
			
		});
		Utils.playSoundToAllPlayer(Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
		WardenRevolt.getInstance().getScoreboard().updateValue(1, ChatColor.YELLOW+"→ Tuez tous les mobs !");
		WardenRevolt.getInstance().getScoreboard().updateValue(3, Stuff.ZOMBIE_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(4, Stuff.ENERGY_SWIRL_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(5, Stuff.RAPID_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(6, Stuff.ENERGY_VORTEX_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(7, Stuff.WARDEN_TAG+" : 1");
		WardenRevolt.getInstance().getScoreboard().addScore(8, ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString());
		Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				for(int i = 0; i < 4; i++){
					Location loc = new Location(ConfigList.world, ConfigList.mobSpawnPhaseThreeFourPillar.get(i)[0], ConfigList.mobSpawnPhaseThreeFourPillar.get(i)[1], ConfigList.mobSpawnPhaseThreeFourPillar.get(i)[2]);
					spawnEnergySwirl(loc);
					for(int j = 1; j <= 35; j++){
						spawnZombie(loc);
					}
				}
			}
		});
	}

	@Override
	protected void stageEngine() {
		if(!warden.isDead())
			Utils.spawnParticle(EnumParticle.SPELL_WITCH, true, warden.getLocation(), 0.1f, 0.5f, 0.1f, 3, 50, null, null);
		if(wardenEnt != null && !wardenEnt.isDead() && (waves == 3 || waves == 4))
			Utils.spawnParticle(EnumParticle.SPELL_WITCH, true, wardenEnt.getLocation(), 0.1f, 0.5f, 0.1f, 3, 50, null, null);
		if(waves == 1 || waves == 2 || waves >= 6){
			double lastDistance = 21;
			Player player = null;
			for(Player p : ConfigList.world.getPlayers()){
				double distance = Math.sqrt(warden.getLocation().distanceSquared(p.getLocation()));
				if(distance < lastDistance){
					lastDistance = distance;
					player = p;
				}
			}
			if(lastDistance < 21 && player != null){
				if(!warden.isDead())
				//https://www.spigotmc.org/threads/make-entity-face-the-player.184853/#post-1944129 -> Merci FisheyLP !
				((CraftArmorStand) warden).getHandle().yaw = (float) Math.toDegrees(Math.atan2(
					player.getLocation().getZ()-warden.getLocation().getZ(), player.getLocation().getX()-warden.getLocation().getX())) - 90;
				else if(boss != null && !boss.isDead()){
					float yaw = (float) Math.toDegrees(Math.atan2(
							player.getLocation().getZ()-boss.getLocation().getZ(), player.getLocation().getX()-boss.getLocation().getX())) - 90;
					//https://www.spigotmc.org/threads/make-entity-face-the-player.184853/ -> Merci NickAC !
					double xDiff = player.getLocation().getX() - boss.getLocation().getX();
			        double yDiff = player.getLocation().getY() - boss.getLocation().getY();
			        double zDiff = player.getLocation().getZ() - boss.getLocation().getZ();

			        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
			        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);

					float pitch = (float) (Math.toDegrees(Math.acos(yDiff / distanceY)) - 90.0D);
					boss.teleport(new Location(boss.getWorld(), boss.getLocation().getX(), boss.getLocation().getY(), boss.getLocation().getZ(), yaw, pitch));
					//((CraftWither) boss).getHandle().yaw = (float) Math.toDegrees(Math.atan2(
						//	player.getLocation().getZ()-boss.getLocation().getZ(), player.getLocation().getX()-boss.getLocation().getX())) - 90;
				}
			}
		}
		if(this.mobsSpawn.contains(WardenMobs.WARDEN.getId()))
			this.mobsSpawn.remove(WardenMobs.WARDEN.getId());
		if(waves == 3 || waves == 4){
			if(r.nextInt(6) == 5){
				Location strikeLoc = ConfigList.world.getPlayers().get(r.nextInt(ConfigList.world.getPlayers().size())).getLocation();
				Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){

					@Override
					public void run() {
						ConfigList.world.strikeLightning(strikeLoc);
					}
					
				}, 20l);
			}
		}
		this.updateScoreboard();
	}

	private void spawnEnergySwirl(Location loc){
		EnergySwirl ent = new EnergySwirl(((CraftWorld) ConfigList.world).getHandle());
		ent.setCustomName(Stuff.ENERGY_SWIRL_TAG);
		ent.setHealth(60f);
		Utils.spawnEntity(ent, loc);
	}
	
	private void spawnZombie(Location loc){
		Zombie ent = ConfigList.world.spawn(loc, Zombie.class);
		ent.setBaby(false);
		ent.getEquipment().clear();
		ent.setCustomName(Stuff.ZOMBIE_TAG);
		ent.setRemoveWhenFarAway(false);
	}
	
	private void spawnRapid(Location loc){
		Zombie zombie = ConfigList.world.spawn(loc, Zombie.class);
		zombie.addPotionEffect(speedThree);
		zombie.setBaby(true);
		zombie.getEquipment().clear();
		zombie.getEquipment().setItemInMainHand(ironSword);
		zombie.setCustomName(Stuff.RAPID_TAG);
		zombie.setRemoveWhenFarAway(false);
	}
	
	private void spawnEnergyVortex(Location loc){
		EnergyVortex ent = new EnergyVortex(((CraftWorld) ConfigList.world).getHandle());
		ent.setCustomName(Stuff.ENERGY_VORTEX_TAG);
		Utils.spawnEntity(ent, loc);
	}
	
	private void updateScoreboard(){
		WardenRevolt.getInstance().getScoreboard().updateValue(3, Stuff.ZOMBIE_TAG+" : "+this.getMobCountByID(WardenMobs.ZOMBIE.getId()));
		WardenRevolt.getInstance().getScoreboard().updateValue(4, Stuff.ENERGY_SWIRL_TAG+" : "+this.getMobCountByID(WardenMobs.ENERGY_SWIRL.getId()));
		WardenRevolt.getInstance().getScoreboard().updateValue(5, Stuff.RAPID_TAG+" : "+this.getMobCountByID(WardenMobs.RAPID.getId()));
		WardenRevolt.getInstance().getScoreboard().updateValue(6, Stuff.ENERGY_VORTEX_TAG+" : "+this.getMobCountByID(WardenMobs.ENERGY_VORTEX.getId()));
		if(waves == 3){
			if(timerWaveThree % 2 == 0)
				WardenRevolt.getInstance().getScoreboard().updateValue(1, ChatColor.YELLOW+"→ Survivez pendant "+timerWaveThree/2+" secondes !");
			timerWaveThree--;
		}
	}
	
	private void createDeadWarden(Location location, String customName, float yaw){
		Location loc = new Location(ConfigList.world, location.getX(), location.getY()-0.6, location.getZ());
		loc.setYaw(yaw);
		ArmorStand as = ConfigList.world.spawn(loc, ArmorStand.class);
		as.setArms(true);
		as.setBasePlate(false);
		as.setInvulnerable(true);
		as.setGravity(false);
		as.setCustomName(customName);
		as.setBoots(wardenEquipement[2]);
		as.setLeggings(wardenEquipement[1]);
		as.setChestplate(wardenEquipement[0]);
		as.setHelmet(wardenEquipement[3]);
		as.setItemInHand(wardenEquipement[4]);
		as.setHeadPose(new EulerAngle(0.70, 0, 0));
		EulerAngle legsAngle = new EulerAngle(Math.PI/2, 0, 0);
		as.setLeftLegPose(legsAngle);
		as.setRightLegPose(legsAngle);
		//FIXME wither pas suivre regard joueur 
	}
	
	@SuppressWarnings("deprecation")
	private WitherSkeleton createWitherSqueletonWarden(Location loc, String customName, boolean isInvulnerable, boolean hasAI, double health){
		WitherSkeleton wardenEnt = ConfigList.world.spawn(loc, WitherSkeleton.class);
		wardenEnt.setCanPickupItems(false);
		wardenEnt.setCustomName(customName);
		wardenEnt.setCustomNameVisible(true);
		wardenEnt.setInvulnerable(isInvulnerable);
		wardenEnt.setAI(hasAI);
		wardenEnt.setMaxHealth(health);
		wardenEnt.setHealth(health);
		wardenEnt.getEquipment().setHelmet(wardenEquipement[3]);
		wardenEnt.getEquipment().setBoots(wardenEquipement[2]);
		wardenEnt.getEquipment().setChestplate(wardenEquipement[0]);
		wardenEnt.getEquipment().setLeggings(wardenEquipement[1]);
		wardenEnt.getEquipment().setItemInMainHand(wardenEquipement[4]);
		return wardenEnt;
	}
	
	@Override
	public int getStageID() {
		return WardenStage.STAGE_THREE.getID();
	}

}
