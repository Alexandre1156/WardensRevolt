package fr.alexandre1156.wardensrevolt.scheduler.stage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import net.minecraft.server.v1_11_R1.EnumParticle;

public class StageOne extends Stage implements Listener{
	
	private Location spawnLoc;
	private static ArmorStand wardenPhaseOne;
	private boolean isNameVisible;
	private boolean isKilled;
	private BukkitTask task;
	//private boolean isActive;

	public StageOne(Plugin plugin) {
		super(plugin);
	}

	private int count = 0;
	
	public void start(){
		if(spawnLoc == null)
			spawnLoc = new Location(ConfigList.world, ConfigList.spawn[0], ConfigList.spawn[1], ConfigList.spawn[2]);
		for(Player p : Bukkit.getOnlinePlayers()){
			p.teleport(spawnLoc);
			p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 0);
		}
		Bukkit.broadcastMessage(Stuff.WARDEN_TAG+" Alors comme ça on pense pouvoir me vaincre ? Et bien, "+ChatColor.YELLOW+""+ChatColor.BOLD+"trouvez moi avant !");
		initWardenPhaseOne();
		stageEngine();
	}
	
	private void stageEngine(){
		task = Bukkit.getScheduler().runTaskTimer(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					if(!isKilled){
						//wardenPhaseOne.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, wardenPhaseOne.getLocation(), 1000, 10d, 0.1d, 10d);
						Utils.spawnParticle(EnumParticle.EXPLOSION_NORMAL, true, wardenPhaseOne.getLocation(), 1f, 1.5f, 1f, 0.0001f, 100, null);
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
			}
			
		}, 0l, 10l);
	}

	@Override
	protected void onPlayerHitWarden() {
		count++;
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
			
	//		if(ConfigList.mobSpawnPosRand){
	//			for(int i = 0; i < 40; i++)
	//				Utils.spawnZombie(Utils.getMobRandomLocation());
	//		} else {
	//			for(int i = 0; i < 40; i++)
	//				Utils.spawnZombie(Utils.getMobExistingLocation());
	//		}
			Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					updateMobsCountScoreboard();
					wardenPhaseOne.remove();
					for(Entity ent : ConfigList.world.getEntities()){
						Utils.consoleMessage(ent.getName());
						if(ent.getCustomName() != null){
							String customName = ent.getCustomName();
							if(customName.equals(Stuff.ZOMBIE_TAG) || customName.equals(Stuff.BLINDING_TAG) || customName.equals(Stuff.KILLER_TAG) || customName.equals(Stuff.WIZARD_TAG))
								ent.setInvulnerable(false);
							Utils.consoleMessage(customName+" "+ent.isInvulnerable());
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
				WardenRevolt.getInstance().getScoreboard().updateValue(3, ChatColor.BLACK+"Aveuglants : "+Utils.getBlindingsNumber());
				WardenRevolt.getInstance().getScoreboard().updateValue(4, ChatColor.DARK_GREEN+"Mages : "+Utils.getWizardsNumber());
				WardenRevolt.getInstance().getScoreboard().updateValue(5, ChatColor.RED+"Tueurs : "+Utils.getKillersNumber());
				WardenRevolt.getInstance().getScoreboard().updateValue(6, ChatColor.GREEN+"Zombies : "+Utils.getZombiesNumber());
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
				wardenPhaseOne.setInvulnerable(true);
				wardenPhaseOne.setGravity(false);
				wardenPhaseOne.setCustomName(ChatColor.RED+"Warden");
				wardenPhaseOne.setCustomNameVisible(false);
				//wardenPhaseOne.setVisible(false);
			}
		});
	}

	@Override
	protected void onAllMonstersDie() {
		task.cancel();
		Utils.sendJsonMessageToAllPlayers(Stuff.WARDEN_STAGEONE_MESSAGE_FINAL);
		WardenRevolt.getInstance().nextStage(WardenStage.STAGE_TWO, this);
	}
	
	private void teleportWarden(){
		if(ConfigList.wardenHideRand)
			wardenPhaseOne.teleport(Utils.getWardenRandomLocation());
		else
			wardenPhaseOne.teleport(Utils.getWardenExistingLocation());
	}
	
	private void spawnKiller(){
		if(ConfigList.mobSpawnPosRand)
			Utils.spawnKiller(Utils.getMobRandomLocation());
		else 
			Utils.spawnKiller(Utils.getMobExistingLocation());
	}
	
	private void spawnBlinding(){
		if(ConfigList.mobSpawnPosRand)
			Utils.spawnBlinding(Utils.getMobRandomLocation());
		else 
			Utils.spawnBlinding(Utils.getMobExistingLocation());
	}
	
	private void spawnWizard(){
		if(ConfigList.mobSpawnPosRand)
			Utils.spawnWizard(Utils.getMobRandomLocation());
		 else 
			Utils.spawnWizard(Utils.getMobExistingLocation());
	}

	@Override
	protected void onAnEntityDie(Entity entityDeath) {
		//Nothing to do
	}

	
	
	
	
}
