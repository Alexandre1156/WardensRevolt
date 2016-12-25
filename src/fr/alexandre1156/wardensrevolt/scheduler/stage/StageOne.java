package fr.alexandre1156.wardensrevolt.scheduler.stage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.entity.WardenMobs;
import fr.alexandre1156.wardensrevolt.entity.WardenWitch;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import net.minecraft.server.v1_11_R1.EnumParticle;

public class StageOne extends Stage{
	
	private Location spawnLoc;
	private static ArmorStand wardenPhaseOne;
	private boolean isNameVisible;
	private boolean isKilled;
	private ItemStack sword;
	private ItemStack axe;
	private int count = 0;

	public StageOne(Plugin plugin) {
		super(plugin);
		sword = new ItemStack(Material.GOLD_SWORD);
		axe = new ItemStack(Material.GOLD_AXE);
	}
	
	public void start(){
		if(spawnLoc == null)
			spawnLoc = new Location(ConfigList.world, ConfigList.spawn[0], ConfigList.spawn[1], ConfigList.spawn[2]);
		for(Player p : Bukkit.getOnlinePlayers())
			p.teleport(spawnLoc);
		Utils.playSoundToAllPlayer(Sound.ENTITY_WITHER_SPAWN, 1, 0);
		
		for(Player p : Bukkit.getOnlinePlayers())
			p.sendMessage(Stuff.WARDEN_STAGEONE_MESSAGE_START);
		initWardenPhaseOne();
		this.updateMobsCountScoreboard();
	}
	
	@Override
	protected void stageEngine(){
		//task = Bukkit.getScheduler().runTaskTimer(WardenRevolt.getInstance(), new Runnable(){

			//@Override
			//public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					if(!isKilled){
						//wardenPhaseOne.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, wardenPhaseOne.getLocation(), 1000, 10d, 0.1d, 10d);
						Utils.spawnParticle(EnumParticle.SMOKE_LARGE, true, wardenPhaseOne.getLocation(), 1f, 1.5f, 1f, 0.0001f, 100, null);
						if(wardenPhaseOne.getLocation().distanceSquared(p.getLocation()) <= 5 * 5){
							isNameVisible = true;
							wardenPhaseOne.setCustomNameVisible(true);
						} else if(isNameVisible){
							isNameVisible = false;
							wardenPhaseOne.setCustomNameVisible(false);
						}
					} else
						updateMobsCountScoreboard();
				}
			//}
			
		//}, 0l, 10l);
	}

	@Override
	protected void onPlayerHitWarden(Player p) {
		count++;
		Utils.playSoundToAllPlayer(Sound.ENTITY_WITHER_HURT, 1f, 0f);
		switch(count){
		case 1:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGEONE_MESSAGE_ONE);
			this.teleportWarden();
				
			this.spawnBlinding();
			this.spawnWizard();
			
			this.updateMobsCountScoreboard();
			break;
		case 2:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGEONE_MESSAGE_TWO);
			this.teleportWarden();
	
			this.spawnBlinding();
			this.spawnWizard();
				
			this.updateMobsCountScoreboard();
			break;
		case 3:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGEONE_MESSAGE_THREE);
			this.teleportWarden();
	
			this.spawnKiller();
			this.spawnWizard();
				
			this.updateMobsCountScoreboard();
			break;
		case 4:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGEONE_MESSAGE_FOUR);
			this.teleportWarden();
			
			this.spawnBlinding();
			this.spawnKiller();
			 
			this.updateMobsCountScoreboard();
			break;
		case 5:
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGEONE_MESSAGE_FIVE);
			
			this.spawnBlinding();
			this.spawnKiller();
			
			if(ConfigList.mobSpawnPosRand){
				for(int i = 0; i < 40; i++)
					Utils.spawnZombie(Utils.getMobRandomLocation());
			} else {
				for(int i = 0; i < 40; i++)
					Utils.spawnZombie(Utils.getMobExistingLocation());
			}
			Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					updateMobsCountScoreboard();
					wardenPhaseOne.setHealth(0.0D);
					for(Entity ent : ConfigList.world.getEntities()){
						if(ent.getCustomName() != null){
							String customName = ent.getCustomName();
							if(Utils.isMobTagExist(customName))
								ent.setInvulnerable(false);
						}
					}
				}
			});
			Utils.sendJsonMessageToAllPlayers(Stuff.TIP_ONE);
			isKilled = true;
			WardenRevolt.getInstance().getScoreboard().updateValue(1, ChatColor.YELLOW+"→Tuez tous les monstres !");
			break;
		}
	}
	
	private void updateMobsCountScoreboard(){
		Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				WardenRevolt.getInstance().getScoreboard().updateValue(3, ChatColor.BLACK+"Aveuglants : "+getMobCountByID(WardenMobs.BLINDING.getId()));
				WardenRevolt.getInstance().getScoreboard().updateValue(4, ChatColor.DARK_GREEN+"Mages : "+getMobCountByID(WardenMobs.WIZARD.getId()));
				WardenRevolt.getInstance().getScoreboard().updateValue(5, ChatColor.RED+"Tueurs : "+getMobCountByID(WardenMobs.KILLER.getId()));
				WardenRevolt.getInstance().getScoreboard().updateValue(6, ChatColor.GREEN+"Zombies : "+getMobCountByID(WardenMobs.ZOMBIE.getId()));
			}
		}, 20L);
	}
	
	private static void initWardenPhaseOne(){
		Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				if(ConfigList.wardenHideRand)
					wardenPhaseOne = (ArmorStand) world.spawn(Utils.getWardenRandomLocation(), ArmorStand.class);
				else
					wardenPhaseOne = (ArmorStand) world.spawn(
							Utils.getWardenExistingLocation(), 
							ArmorStand.class);
				
				ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
				LeatherArmorMeta lam = (LeatherArmorMeta) boots.getItemMeta();
				lam.setColor(Color.BLACK);
				boots.setItemMeta(lam);
				ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
				chestPlate.setItemMeta(lam);
				ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
				leggings.setItemMeta(lam);
				ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
				helmet.setItemMeta(lam);
				ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
				sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
				
				wardenPhaseOne.setInvulnerable(true);
				wardenPhaseOne.setGravity(false);
				wardenPhaseOne.setCustomName(Stuff.WARDEN_TAG);
				wardenPhaseOne.setCustomNameVisible(false);
				wardenPhaseOne.setBasePlate(false);
				wardenPhaseOne.setArms(true);
				wardenPhaseOne.setBoots(boots);
				wardenPhaseOne.setChestplate(chestPlate);
				wardenPhaseOne.setLeggings(leggings);
				wardenPhaseOne.setHelmet(helmet);
				wardenPhaseOne.setItemInHand(sword);
				wardenPhaseOne.setHeadPose(new EulerAngle(32f, 0f, 0f));
			}
		});
	}

	@Override
	protected void onAllMonstersDie() {
		if(count == 5){
			Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGEONE_MESSAGE_FINAL);
			this.updateMobsCountScoreboard();
			//this.isActive = false;
			Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable(){
	
				@Override
				public void run() {
					WardenRevolt.getInstance().nextStage(WardenStage.STAGE_TWO.getID(), this);
				}
				
			}, 100);
		}
	}
	
	private void teleportWarden(){
		if(ConfigList.wardenHideRand)
			wardenPhaseOne.teleport(Utils.getWardenRandomLocation());
		else
			wardenPhaseOne.teleport(Utils.getWardenExistingLocation());
	}
	
	private void spawnKiller(){
		if(ConfigList.mobSpawnPosRand)
			this.spawnKiller(Utils.getMobRandomLocation());
		else 
			this.spawnKiller(Utils.getMobExistingLocation());
	}
	
	private void spawnBlinding(){
		if(ConfigList.mobSpawnPosRand)
			this.spawnBlinding(Utils.getMobRandomLocation());
		else 
			this.spawnBlinding(Utils.getMobExistingLocation());
	}
	
	private void spawnWizard(){
		if(ConfigList.mobSpawnPosRand)
			this.spawnWizard(Utils.getMobRandomLocation());
		 else 
			 this.spawnWizard(Utils.getMobExistingLocation());
	}

	@Override
	protected void onAnEntityDie(Entity entityDeath) {
		//Nothing to do
	}
	
	/**
	 * Fait spawn un Aveuglant.
	 */
	public void spawnBlinding(Location loc){
		Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				WitherSkeleton ent = loc.getWorld().spawn(loc, WitherSkeleton.class);
				ent.setInvulnerable(true);
				ent.setCustomName(Stuff.BLINDING_TAG);
				ent.getEquipment().setItemInMainHand(sword);
				ent.addPotionEffect(Utils.speed);
			}
		});
	}
	
	/**
	 * Fait spawn un Mage.
	 */
	public void spawnWizard(Location loc){
		Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				WardenWitch witch = new WardenWitch(((CraftWorld) loc.getWorld()).getHandle());
				witch.setCustomName(Stuff.WIZARD_TAG);
				witch.setInvulnerable(true);
				//((LivingEntity) witch).addPotionEffect(speed); --> Ce n'est pas une LivingEntity !
				Utils.spawnEntity(witch, loc);
			}
		});
	}
	
	/**
	 * Fait spawn un Tueur.
	 */
	public void spawnKiller(Location loc){
		Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				ZombieVillager ent = loc.getWorld().spawn(loc, ZombieVillager.class);
				ent.setInvulnerable(true);
				ent.setCustomName(Stuff.KILLER_TAG);
				ent.getEquipment().clear();
				ent.getEquipment().setItemInMainHand(axe);
				ent.setBaby(false);
				ent.setVillagerProfession(Profession.BLACKSMITH);
				ent.addPotionEffect(Utils.speed);
			}
		});
	}

	@Override
	public int getStageID() {
		return WardenStage.STAGE_ONE.getID();
	}
	
//	private int getKillersNumber(){
//		int i = 0;
//		for(ZombieVillager ent : Bukkit.getWorlds().get(0).getEntitiesByClass(ZombieVillager.class)){
//			if(ent.getCustomName() != null && ent.getCustomName().equals(Stuff.KILLER_TAG))
//				i++;
//		}
//		return i;
//	}
//	
//	private int getBlindingsNumber(){
//		int i = 0;
//		for(WitherSkeleton ent : Bukkit.getWorlds().get(0).getEntitiesByClass(WitherSkeleton.class)){
//			if(ent.getCustomName() != null && ent.getCustomName().equals(Stuff.BLINDING_TAG))
//				i++;
//		}
//		return i;
//	}
//	
//	private int getWizardsNumber(){
//		int i = 0;
//		for(Entity ent : Bukkit.getWorlds().get(0).getEntities()){
//			if(ent.getCustomName() != null && ent.getCustomName().equals(Stuff.WIZARD_TAG))
//				i++;
//		}
//		return i;
//	}
//	
//	private int getZombiesNumber() {
//		int i = 0;
//		for(Zombie ent : Bukkit.getWorlds().get(0).getEntitiesByClass(Zombie.class)){
//			if(ent.getCustomName() != null && ent.getCustomName().equals(Stuff.ZOMBIE_TAG))
//				i++;
//		}
//		return i;
//	}

	
	
	
	
}
