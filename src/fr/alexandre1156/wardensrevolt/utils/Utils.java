package fr.alexandre1156.wardensrevolt.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Witch;
import org.bukkit.scheduler.BukkitTask;

import fr.alexandre1156.wardensrevolt.WardenRevolt;

public class Utils {

	private static Random r = new Random();
	
	public static String randomString(int lenght){
		String alphabet = "abcdefghijklmnopkrstuvwxyz";
		char[] text = new char[lenght];
		for(int i = 0; i < lenght; i++)
			text[i] = alphabet.charAt(r.nextInt(alphabet.length()));
		return new String(text);
	}
	
	public static void consoleMessage(String theMessage){
		Bukkit.getServer().getConsoleSender().sendMessage(theMessage);
	}
	
	public static void consoleMessageWithColor(String theMessage, ChatColor color){
		Bukkit.getServer().getConsoleSender().sendMessage(color+theMessage);
	}
	
	public static void playSoundToAllPlayer(Sound soundToPlay, float volume, float pitch){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.playSound(p.getLocation(), soundToPlay, volume, pitch);
		}
	}
	
	public static void spawnTheBlinding(Location loc){
		BukkitTask task = Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Skeleton ent = loc.getWorld().spawn(loc, Skeleton.class);
				ent.setSkeletonType(SkeletonType.WITHER);
				ent.setInvulnerable(true);
				ent.setCustomName(ChatColor.BLACK+"L'aveuglant");
			}
		});
	}
	
	public static void spawnTheWizard(Location loc){
		BukkitTask task = Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Witch ent = loc.getWorld().spawn(loc, Witch.class);
				ent.setInvulnerable(true);
				ent.setCustomName(ChatColor.DARK_GREEN+"Mage");
			}
		});
	}
	
	public static void spawnTheKiller(Location loc){
		BukkitTask task = Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Utils.consoleMessage(String.valueOf(EntityType.fromName("husk"))); //null
//				Zombie ent = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.fromName("husk"));
//				ent.setInvulnerable(true);
//				ent.setCustomName(ChatColor.RED+"Tueur");
			}
		});
	}
	
}
