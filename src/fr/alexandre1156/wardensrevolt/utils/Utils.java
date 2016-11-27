package fr.alexandre1156.wardensrevolt.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.entity.WardenWitch;
import fr.alexandre1156.wardensrevolt.entity.WardenWitchType;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagString;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class Utils {

	private static Random r = new Random();
	private static ItemStack sword = new ItemStack(Material.GOLD_SWORD);
	private static ItemStack axe = new ItemStack(Material.GOLD_AXE);
	private static int prevPosWarden = -1;
	private static int prevPosMob = -1;
	private static PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 9999, 0);
	
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
	
	public static void consoleMessage(String theMessage, ChatColor color){
		Bukkit.getServer().getConsoleSender().sendMessage(color+theMessage);
	}
	
	public static void playSoundToAllPlayer(Sound soundToPlay, float volume, float pitch){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.playSound(p.getLocation(), soundToPlay, volume, pitch);
		}
	}
	
	/**
	 * Fait spawn un Aveuglant.
	 */
	public static void spawnBlinding(Location loc){
		BukkitTask task = Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				WitherSkeleton ent = loc.getWorld().spawn(loc, WitherSkeleton.class);
				ent.setInvulnerable(true);
				ent.setCustomName(Stuff.BLINDING_TAG);
				ent.getEquipment().setItemInMainHand(sword);
				ent.addPotionEffect(speed);
			}
		});
	}
	
	/**
	 * Fait spawn un Mage.
	 */
	public static void spawnWizard(Location loc){
		BukkitTask task = Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				WardenWitch witch = new WardenWitch(((CraftWorld) loc.getWorld()).getHandle());
				witch.setCustomName(Stuff.WIZARD_TAG);
				witch.setInvulnerable(true);
				((LivingEntity) witch).addPotionEffect(speed);
				WardenWitchType.spawnEntity(witch, loc);
			}
		});
	}
	
	/**
	 * Fait spawn un Tueur.
	 */
	public static void spawnKiller(Location loc){
		BukkitTask task = Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				ZombieVillager ent = loc.getWorld().spawn(loc, ZombieVillager.class);
				ent.setInvulnerable(true);
				ent.setCustomName(Stuff.KILLER_TAG);
				ent.getEquipment().clear();
				ent.getEquipment().setItemInMainHand(axe);
				ent.setBaby(false);
				ent.setVillagerProfession(Profession.BLACKSMITH);
				ent.addPotionEffect(speed);
			}
		});
	}
	
	/*
	 * Fait spawn un Zombie.
	 */
	public static void spawnZombie(Location loc){
		BukkitTask task = Bukkit.getScheduler().runTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Zombie ent = loc.getWorld().spawn(loc, Zombie.class);
				ent.setCustomName(Stuff.ZOMBIE_TAG);
				ent.getEquipment().clear();
				ent.setBaby(false);
				ent.addPotionEffect(speed);
			}
		});
	}
	
	public static int getKillersNumber(){
		int i = 0;
		for(ZombieVillager ent : Bukkit.getWorlds().get(0).getEntitiesByClass(ZombieVillager.class)){
			if(ent.getCustomName() != null && ent.getCustomName().equals(Stuff.KILLER_TAG))
				i++;
		}
		return i;
	}
	
	public static int getBlindingsNumber(){
		int i = 0;
		for(WitherSkeleton ent : Bukkit.getWorlds().get(0).getEntitiesByClass(WitherSkeleton.class)){
			if(ent.getCustomName() != null && ent.getCustomName().equals(Stuff.BLINDING_TAG))
				i++;
		}
		return i;
	}
	
	public static int getWizardsNumber(){
		int i = 0;
		for(Entity ent : Bukkit.getWorlds().get(0).getEntities()){
			if(ent.getCustomName() != null && ent.getCustomName().equals(Stuff.WIZARD_TAG))
				i++;
		}
		return i;
	}
	
	public static int getZombiesNumber() {
		int i = 0;
		for(ZombieVillager ent : Bukkit.getWorlds().get(0).getEntitiesByClass(ZombieVillager.class)){
			if(ent.getCustomName() != null && ent.getCustomName().equals(Stuff.ZOMBIE_TAG))
				i++;
		}
		return i;
	}
	
	public static Location getWardenExistingLocation(){
		if(!ConfigList.wardenHideRand){
			int pos = r.nextInt(ConfigList.wardenHidePosition.size());
			while(prevPosWarden == pos){
				pos = r.nextInt(ConfigList.wardenHidePosition.size());
			}
			prevPosWarden = pos;
			return new Location(ConfigList.world, ConfigList.wardenHidePosition.get(pos)[0], ConfigList.wardenHidePosition.get(pos)[1], ConfigList.wardenHidePosition.get(pos)[2]);
		}
		Utils.consoleMessage("La section WardenHideRandom est sur false, il faut utiliser getRandomLocation !", ChatColor.YELLOW);
		return new Location(ConfigList.world, 0, 0, 0);
	}
	
	public static Location getWardenRandomLocation(){
		if(ConfigList.wardenHideRand){
			int x = ConfigList.wardenHideRandBorder[0] + r.nextInt(ConfigList.wardenHideRandBorder[3]);
			int y = ConfigList.wardenHideRandBorder[1];
			int z = ConfigList.wardenHideRandBorder[2] + r.nextInt(ConfigList.wardenHideRandBorder[3]);
			while(ConfigList.world.getBlockAt(x, y, z) != null && ConfigList.world.getBlockAt(x, y, z).getType() != Material.AIR && ConfigList.world.getBlockAt(x, y+1, z) != null && ConfigList.world.getBlockAt(x, y+1, z).getType() != Material.AIR){
				y++;
			}
			while(ConfigList.world.getBlockAt(x, y-1, z) != null && ConfigList.world.getBlockAt(x, y-1, z).getType() == Material.AIR){
				y--;
			}
			return new Location(ConfigList.world, x, y, z);
		}
		Utils.consoleMessage("La section WardenHideRandom est sur true, il faut utiliser getExistingLocation !", ChatColor.YELLOW);
		return new Location(ConfigList.world, 0, 0, 0);
	}
	
	public static Location getMobExistingLocation(){
		if(!ConfigList.mobSpawnPosRand){
			int pos = r.nextInt(ConfigList.mobSpawnPos.size());
			while(prevPosMob == pos){
				pos = r.nextInt(ConfigList.mobSpawnPos.size());
			}
			prevPosMob = pos;
			return new Location(ConfigList.world, ConfigList.mobSpawnPos.get(pos)[0], ConfigList.mobSpawnPos.get(pos)[1], ConfigList.mobSpawnPos.get(pos)[2]);
		}
		Utils.consoleMessage("La section WardenHideRandom est sur false, il faut utiliser getRandomLocation !", ChatColor.YELLOW);
		return new Location(ConfigList.world, 0, 0, 0);
	}
	
	public static Location getMobRandomLocation(){
		if(ConfigList.mobSpawnPosRand){
			int x = ConfigList.mobHideRandBorder[0] + r.nextInt(ConfigList.mobHideRandBorder[3]);
			int y = ConfigList.mobHideRandBorder[1];
			int z = ConfigList.mobHideRandBorder[2] + r.nextInt(ConfigList.mobHideRandBorder[3]);
			while(ConfigList.world.getBlockAt(x, y, z) != null && ConfigList.world.getBlockAt(x, y, z).getType() != Material.AIR && ConfigList.world.getBlockAt(x, y+1, z) != null && ConfigList.world.getBlockAt(x, y+1, z).getType() != Material.AIR){
				y++;
			}
			while(ConfigList.world.getBlockAt(x, y-1, z) != null && ConfigList.world.getBlockAt(x, y-1, z).getType() == Material.AIR){
				y--;
			}
			return new Location(ConfigList.world, x, y, z);
		}
		Utils.consoleMessage("La section WardenHideRandom est sur true, il faut utiliser getExistingLocation !", ChatColor.YELLOW);
		return new Location(ConfigList.world, 0, 0, 0);
	}
	
	public static void sendJsonMessageToAllPlayers(String json){
		IChatBaseComponent msg = ChatSerializer.a(json);
		PacketPlayOutChat packet = new PacketPlayOutChat(msg);
		for(Player p : Bukkit.getOnlinePlayers())
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static ItemStack addUnbrakeableTag(ItemStack item){
		net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		tag.setBoolean("Unbreakable", true);
		nmsItem.setTag(tag);
		return (ItemStack) CraftItemStack.asCraftMirror(nmsItem);
	}
	
	public static ItemStack changeAttackSpeedValue(ItemStack item, double value){
		net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		tag.set("AttributeName", new NBTTagString("generic.attackSpeed"));
		tag.set("Name", new NBTTagString("generic.attackSpeed"));
		tag.set("Amount", new NBTTagDouble(value));
		tag.set("Operation", new NBTTagInt(0));
		tag.set("UUIDLeast", new NBTTagInt(894654));
		tag.set("UUIDMost", new NBTTagInt(2872));
		tag.set("Slot", new NBTTagString("mainhand"));
		tag.set("Slot", new NBTTagString("offhand"));
		nmsItem.setTag(tag);
        return (ItemStack) CraftItemStack.asCraftMirror(nmsItem);
	}
	
	public static void spawnParticle(EnumParticle particleID, boolean longDistance, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int count, Player particularPlayer){
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particleID,
				longDistance, 
				(float) loc.getX(),(float) loc.getY(),(float) loc.getZ(), 
				offsetX, offsetY, offsetZ, speed, count, null);
		if(particularPlayer != null)
			((CraftPlayer) particularPlayer).getHandle().playerConnection.sendPacket(packet);
		else {
			for(Player p : Bukkit.getOnlinePlayers())
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	
	
}
