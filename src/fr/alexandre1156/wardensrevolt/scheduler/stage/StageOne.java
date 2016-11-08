package fr.alexandre1156.wardensrevolt.scheduler.stage;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;

public class StageOne extends Stage implements Listener{
	
	private boolean isVisible;
	private Random rand;

	public StageOne(Plugin plugin) {
		super(plugin);
	}

	private int count = 0;
	
	public void startStageOne(){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.teleport(Bukkit.getWorld("world").getSpawnLocation());
			p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 0);
		}
		rand = new Random();
		Bukkit.broadcastMessage(Stuff.WARDEN_TAG+" Alors comme ça on pense pouvoir me vaincre ? Et bien, "+ChatColor.YELLOW+""+ChatColor.BOLD+"trouvez moi avant !");
		initWardenPhaseOne();
		stageEngine();
	}
	
	private void stageEngine(){
		BukkitTask id = Bukkit.getScheduler().runTaskTimer(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				wardenPhaseOne.getWorld().spawnParticle(Particle.SMOKE_LARGE, wardenPhaseOne.getLocation(), 10, 1, 1, 5);
				
				for(Player p : Bukkit.getOnlinePlayers()){
					if(wardenPhaseOne.getLocation().distanceSquared(p.getLocation()) <= 5 * 5){
						isVisible = true;
						wardenPhaseOne.setCustomNameVisible(true);
					} else if(isVisible){
						isVisible = false;
						wardenPhaseOne.setCustomNameVisible(false);
					}
				}
			}
			
		}, 0l, 20l);
	}

	@Override
	protected void onPlayerHitWarden() {
		count++;
		switch(count){
		case 1:
			String json = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Comment vous m'avez trouvé ?! Voici votre récompense : \",\"color\":\"none\",\"bold\":false},{\"text\":\"1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie.\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\",\"bold\":false},{\"text\":\"1 mage\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous expulse 5 blocs plus loin sans vous faire de dégâts.\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
			IChatBaseComponent msg = ChatSerializer.a(json);
			PacketPlayOutChat packet = new PacketPlayOutChat(msg);
			for(Player p : Bukkit.getOnlinePlayers())
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			wardenPhaseOne.teleport(new Location(wardenPhaseOne.getWorld(), wardenPhaseOne.getLocation().getX()+10, 
					wardenPhaseOne.getLocation().getY(), wardenPhaseOne.getLocation().getZ()+10));
			int x = rand.nextInt(20);
			int z = rand.nextInt(20);
			Utils.spawnTheBlinding(new Location(wardenPhaseOne.getWorld(), wardenPhaseOne.getLocation().getX()+x, wardenPhaseOne.getLocation().getY(), wardenPhaseOne.getLocation().getZ()+z));
			Utils.spawnTheKiller(new Location(wardenPhaseOne.getWorld(), wardenPhaseOne.getLocation().getX()+x, wardenPhaseOne.getLocation().getY(), wardenPhaseOne.getLocation().getZ()+z));
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
	}
	
	
	
}
