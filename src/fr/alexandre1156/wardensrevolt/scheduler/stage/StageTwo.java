package fr.alexandre1156.wardensrevolt.scheduler.stage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.entity.Canardier;
import fr.alexandre1156.wardensrevolt.entity.EnergySwirl;
import fr.alexandre1156.wardensrevolt.entity.Judokas;
import fr.alexandre1156.wardensrevolt.entity.WardenMobs;
import fr.alexandre1156.wardensrevolt.utils.Utils;

public class StageTwo extends Stage {
	
	private PotionEffect resistance;
	private PotionEffect speedThree;
	private PotionEffect regeneration;
	private ItemStack stoneSword;
	private ItemStack ironSword;
	private int waves;
	
	public StageTwo(Plugin plugin) {
		super(plugin);
		resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999, 0);
		stoneSword = new ItemStack(Material.STONE_SWORD);
		ironSword = new ItemStack(Material.IRON_SWORD);
		speedThree = new PotionEffect(PotionEffectType.SPEED, 9999, 2);
		regeneration = new PotionEffect(PotionEffectType.REGENERATION, 9999, 3);
		waves = 1;
	}
	
	public void start(){
		Utils.playSoundToAllPlayer(Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
		WardenRevolt.getInstance().getScoreboard().updateValue(3, Stuff.CANARDIER_TAG+" : 0");
		//WardenRevolt.getInstance().getScoreboard().updateValue(4, Stuff.WARDEN_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(4, Stuff.JUDOKAS_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(5, Stuff.RAPID_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(6, Stuff.ZOMBIE_BAND_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(7, Stuff.ENERGY_SWIRL_TAG+" : 0");
		//WardenRevolt.getInstance().getScoreboard().addScore(9, Stuff.ENERGY_VORTEX_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().addScore(8, Stuff.REGENERATOR_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().addScore(9, ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString());
		Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				for(int i = 0; i < Stuff.STAGETWO_WAVEONE_SPAWN_MESSAGE.length; i++)
					Utils.sendJsonMessageToAllPlayers(Stuff.STAGETWO_WAVEONE_SPAWN_MESSAGE[i]);
				for(int i = 1; i <= 2; i++)
					spawnTenZombies();
				for(int i = 3; i <= 3; i++)
					spawnRapid();
				spawnJudokas();
				spawnRegenerator();
			}
			
		}, 60);
	}
	
	@Override
	protected void stageEngine() {
		this.updateScoreboard();
	}

	@Override
	protected void onPlayerHitWarden(Player p) {
		
	}

	@Override
	protected void onAllMonstersDie() {
		waves++;
		Utils.consoleMessage(waves+" ");
		switch(waves){
		case 2:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGETWO_MESSAGE_ONE);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					for(int i = 0; i < Stuff.STAGETWO_WAVETWO_SPAWN_MESSAGE.length; i++)
						Utils.sendJsonMessageToAllPlayers(Stuff.STAGETWO_WAVETWO_SPAWN_MESSAGE[i]);
					for(int i = 1; i <= 3; i++)
						spawnTenZombies();
					for(int i = 1; i <= 4; i++)
						spawnRapid();
					for(int i = 1; i <= 2; i++)
						spawnJudokas();
					for(int i = 1; i <= 2; i++)
						spawnRegenerator();
					spawnCanardier();
				}
					
			}, 60);
			
			break;
		case 3:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGETWO_MESSAGE_TWO);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					for(int i = 0; i < Stuff.STAGETWO_WAVETHREE_SPAWN_MESSAGE.length; i++)
						Utils.sendJsonMessageToAllPlayers(Stuff.STAGETWO_WAVETHREE_SPAWN_MESSAGE[i]);
					for(int i = 1; i <= 4; i++)
						spawnTenZombies();
					for(int i = 1; i <= 6; i++)
						spawnRapid();
					for(int i = 1; i <= 3; i++)
						spawnJudokas();
					for(int i = 1; i <= 3; i++)
						spawnRegenerator();
					for(int i = 1; i <= 2; i++)
						spawnCanardier();
					spawnEnergySwirl();
				}
				
			}, 60);
			
			break;
		case 4:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGETWO_MESSAGE_THREE);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){
			
				@Override
				public void run() {
					for(int i = 0; i < Stuff.STAGETWO_WAVEFOUR_SPAWN_MESSAGE.length; i++)
						Utils.sendJsonMessageToAllPlayers(Stuff.STAGETWO_WAVEFOUR_SPAWN_MESSAGE[i]);
					for(int i = 1; i <= 4; i++)
						spawnTenZombies();
					for(int i = 1; i <= 8; i++)
						spawnRapid();
					for(int i = 1; i <= 3; i++)
						spawnJudokas();
					for(int i = 1; i <= 5; i++)
						spawnRegenerator();
					for(int i = 1; i <= 4; i++)
						spawnCanardier();
					for(int i = 1; i <= 2; i++)
						spawnEnergySwirl();
					
				}
				
			}, 60);
			
			break;
		case 5:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGETWO_MESSAGE_FOUR);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					for(int i = 0; i < Stuff.STAGETWO_WAVEFIVE_SPAWN_MESSAGE.length; i++)
						Utils.sendJsonMessageToAllPlayers(Stuff.STAGETWO_WAVEFIVE_SPAWN_MESSAGE[i]);
					for(int i = 1; i <= 5; i++)
						spawnTenZombies();
					for(int i = 1; i <= 10; i++)
						spawnRapid();
					for(int i = 1; i <= 5; i++)
						spawnJudokas();
					for(int i = 1; i <= 5; i++)
						spawnRegenerator();
					for(int i = 1; i <= 5; i++)
						spawnCanardier();
					for(int i = 1; i <= 4; i++)
						spawnEnergySwirl();
				}

			}, 60);
			
			break;
		case 6:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGETWO_MESSAGE_FINAL);
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){

				@Override
				public void run() {
					WardenRevolt.getInstance().nextStage(WardenStage.STAGE_THREE.getID(), this);
				}
				
			}, 60);
		}
	}

	@Override
	protected void onAnEntityDie(Entity entityDeath) {
		
	}
	
	private void spawnTenZombies(){
		Location loc;
		if(ConfigList.mobSpawnPosRand)
			loc = Utils.getMobRandomLocation();
		else
			loc = Utils.getMobExistingLocation();
		for(int i = 0; i < 10; i++){
			Zombie zombie = ConfigList.world.spawn(loc, Zombie.class);
			zombie.addPotionEffect(resistance);
			zombie.setBaby(false);
			zombie.getEquipment().clear();
			zombie.getEquipment().setItemInMainHand(stoneSword);
			zombie.setCustomName(Stuff.ZOMBIE_BAND_TAG);
		}
	}
	
	private void spawnRapid(){
		Location loc;
		if(ConfigList.mobSpawnPosRand)
			loc = Utils.getMobRandomLocation();
		else
			loc = Utils.getMobExistingLocation();
		Zombie zombie = ConfigList.world.spawn(loc, Zombie.class);
		zombie.addPotionEffect(speedThree);
		zombie.setBaby(true);
		zombie.getEquipment().clear();
		zombie.getEquipment().setItemInMainHand(ironSword);
		zombie.setCustomName(Stuff.RAPID_TAG);
	}
	
	private void spawnJudokas(){
		Judokas judo = new Judokas(((CraftWorld) ConfigList.world).getHandle());
		judo.setCustomName(Stuff.JUDOKAS_TAG);
		Location loc;
		if(ConfigList.mobSpawnPosRand)
			loc = Utils.getMobRandomLocation();
		else
			loc = Utils.getMobExistingLocation();
		Utils.spawnEntity(judo, loc);
	}
	
	private void spawnRegenerator(){
		Location loc;
		if(ConfigList.mobSpawnPosRand)
			loc = Utils.getMobRandomLocation();
		else
			loc = Utils.getMobExistingLocation();
		WitherSkeleton ent = ConfigList.world.spawn(loc, WitherSkeleton.class);
		ent.setCustomName(Stuff.REGENERATOR_TAG);
		ent.addPotionEffect(regeneration);
	}
	
	private void spawnCanardier(){
		Location loc;
		if(ConfigList.mobSpawnPosRand)
			loc = Utils.getMobRandomLocation();
		else
			loc = Utils.getMobExistingLocation();
		Canardier bat = new Canardier(((CraftWorld) ConfigList.world).getHandle());
		bat.setCustomName(Stuff.CANARDIER_TAG);
		bat.setHealth(60f);
		bat.setSilent(true);
		Utils.spawnEntity(bat, loc);
	}
	
	private void spawnEnergySwirl(){
		Location loc;
		if(ConfigList.mobSpawnPosRand)
			loc = Utils.getMobRandomLocation();
		else
			loc = Utils.getMobExistingLocation();
		EnergySwirl ent = new EnergySwirl(((CraftWorld) ConfigList.world).getHandle());
		ent.setCustomName(Stuff.ENERGY_SWIRL_TAG);
		ent.setHealth(60f);
		Utils.spawnEntity(ent, loc);
	}
	
	private void updateScoreboard(){
		WardenRevolt.getInstance().getScoreboard().updateValue(3, Stuff.CANARDIER_TAG+" : "+this.getMobCountByID(WardenMobs.CANARDIER.getId()));
		//WardenRevolt.getInstance().getScoreboard().updateValue(4, Stuff.WARDEN_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(4, Stuff.JUDOKAS_TAG+" : "+this.getMobCountByID(WardenMobs.JUDOKAS.getId()));
		WardenRevolt.getInstance().getScoreboard().updateValue(5, Stuff.RAPID_TAG+" : "+this.getMobCountByID(WardenMobs.RAPID.getId()));
		WardenRevolt.getInstance().getScoreboard().updateValue(6, Stuff.ZOMBIE_BAND_TAG+" : "+this.getMobCountByID(WardenMobs.ZOMBIE_BAND.getId()));
		WardenRevolt.getInstance().getScoreboard().updateValue(7, Stuff.ENERGY_SWIRL_TAG+" : "+this.getMobCountByID(WardenMobs.ENERGY_SWIRL.getId()));
		//WardenRevolt.getInstance().getScoreboard().updateValue(9, Stuff.ENERGY_VORTEX_TAG+" : 0");
		WardenRevolt.getInstance().getScoreboard().updateValue(8, Stuff.REGENERATOR_TAG+" : "+this.getMobCountByID(WardenMobs.REGENERATOR.getId()));
	}

	@Override
	public int getStageID() {
		return WardenStage.STAGE_TWO.getID();
	}
	
	

}
