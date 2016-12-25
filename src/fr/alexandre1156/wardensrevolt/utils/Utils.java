package fr.alexandre1156.wardensrevolt.utils;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class Utils {

	private static Random r = new Random();
	private static int prevPosWarden = -1;
	private static int prevPosMob = -1;
	public static PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 9999, 0);
	
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
	
	public static <T extends net.minecraft.server.v1_11_R1.EntityLiving> int getCustomEntityCountByClass(Class<T> clazz, String customName){
		int i = 0;
		for(Object entity : ((CraftWorld) ConfigList.world).getHandle().entityList) {
			if(entity instanceof EntityLiving){
				if(entity.getClass().isAssignableFrom(clazz)){
					i++;
				}
			}
        }
		return i;
	}
	
	@SuppressWarnings("rawtypes")
	public static <T extends Entity> int getEntityCountByClass(Class<T> clazz, String customName){
		int i = 0;
        for(Object entity : ((CraftWorld) ConfigList.world).getHandle().entityList) {
            Class bukkitClass;
            CraftEntity bukkitEntity;
            if (!(entity instanceof net.minecraft.server.v1_11_R1.Entity) || (bukkitEntity = ((net.minecraft.server.v1_11_R1.Entity)entity).getBukkitEntity()) == null || !clazz.isAssignableFrom(bukkitClass = bukkitEntity.getClass())) continue;
            if(bukkitEntity.getCustomName() != null && !bukkitEntity.getCustomName().equals(customName)) continue;
            i++;
        }
		return i;
	}
	
//	public static int getEntityCountByClass(Class<? extends net.minecraft.server.v1_11_R1.Entity> clazz, String customName) {
//		
//		return 0;
//	}
	
	public static void sendJsonMessageToAllPlayers(String json){
		IChatBaseComponent msg = ChatSerializer.a(json);
		PacketPlayOutChat packet = new PacketPlayOutChat(msg);
		for(Player p : Bukkit.getOnlinePlayers())
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static ItemStack addUnbreakableTag(ItemStack item){
		net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		tag.setBoolean("Unbreakable", true);
		nmsItem.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	/**
	 * Ne marche pas
	 */
	@Deprecated
	public static ItemStack changeAttackSpeedValue(ItemStack item, float value){
		net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbt = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		nbt.setFloat("generic.attackSpeed", value);
//		tag.set("AttributeName", new NBTTagString("generic.attackSpeed"));
//		tag.set("Name", new NBTTagString("generic.attackSpeed"));
//		tag.set("Amount", new NBTTagDouble(value));
//		tag.set("Operation", new NBTTagInt(0));
//		tag.set("UUIDLeast", new NBTTagInt(894654));
//		tag.set("UUIDMost", new NBTTagInt(2872));
//		tag.set("Slot", new NBTTagString("mainhand"));
//		tag.set("Slot", new NBTTagString("offhand"));
		nmsItem.setTag(nbt);
        return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	public static void spawnParticle(EnumParticle particleID, boolean longDistance, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int count, Player particularPlayer, int... params){
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particleID,
				longDistance, 
				(float) loc.getX(),(float) loc.getY(),(float) loc.getZ(), 
				offsetX, offsetY, offsetZ, speed, count, params);
		if(particularPlayer != null)
			((CraftPlayer) particularPlayer).getHandle().playerConnection.sendPacket(packet);
		else {
			for(Player p : Bukkit.getOnlinePlayers())
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public static void drawCircleParticle(int radius, Location center, EnumParticle particle){
		int x = 0, y = radius, d = y - 1; 
		while(y >= x){
			Utils.spawnParticle(particle, true, new Location(ConfigList.world, x+center.getX(), center.getY(), y+center.getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
			Utils.spawnParticle(particle, true, new Location(ConfigList.world, y+center.getX(), center.getY(), x+center.getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
			Utils.spawnParticle(particle, true, new Location(ConfigList.world, -x+center.getX(), center.getY(), y+center.getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
			Utils.spawnParticle(particle, true, new Location(ConfigList.world, -y+center.getX(), center.getY(), x+center.getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
			Utils.spawnParticle(particle, true, new Location(ConfigList.world, x+center.getX(), center.getY(), -y+center.getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
			Utils.spawnParticle(particle, true, new Location(ConfigList.world, y+center.getX(), center.getY(), -x+center.getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
			Utils.spawnParticle(particle, true, new Location(ConfigList.world, -x+center.getX(), center.getY(), -y+center.getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
			Utils.spawnParticle(particle, true, new Location(ConfigList.world, -y+center.getX(), center.getY(), -x+center.getZ()), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
			if(d >= 2*x){
				d -= 2*x+1;
				x++;
			} else if(d < 2 * (radius - y)) {
				d += 2*y-1;
				y--;
			} else {
				d += 2*(y-x-1);
				y--;
				x++;
			}
		}
	}
	
	public static void drawFilledCircleParticle(int radius, Location center, EnumParticle particle, int count){
		int x = radius;
	    int z = 0;
	    int xChange = 1 - (radius << 1);
	    int zChange = 0;
	    int radiusError = 0;

	    while (x >= z) {
	        for (int i = center.getBlockX() - x; i <= center.getBlockX() + x; i++) {
	            Utils.spawnParticle(particle, true, new Location(ConfigList.world, i, center.getBlockY(), center.getBlockZ() + z), 0.1f, 0.1f, 0.1f, 0.1f, count, null, null);
	            Utils.spawnParticle(particle, true, new Location(ConfigList.world, i, center.getBlockY(), center.getBlockZ() - z), 0.1f, 0.1f, 0.1f, 0.1f, count, null, null);
	        }
	        for (int i = center.getBlockX() - z; i <= center.getBlockX() + z; i++) {
	        	Utils.spawnParticle(particle, true, new Location(ConfigList.world, i, center.getBlockY(), center.getBlockZ() + x), 0.1f, 0.1f, 0.1f, 0.1f, count, null, null);
	        	Utils.spawnParticle(particle, true, new Location(ConfigList.world, i, center.getBlockY(), center.getBlockZ() - x), 0.1f, 0.1f, 0.1f, 0.1f, count, null, null);
	        }

	        z++;
	        radiusError += zChange;
	        zChange += 2;
	        if (((radiusError << 1) + xChange) > 0) {
	            x--;
	            radiusError += xChange;
	            xChange += 2;
	        }
	    }
	}
	
	@Deprecated
	public static void drawLine(int x, int y, int z, int w, EnumParticle particle){
		for(int i = 0; i < w; i++)
			spawnParticle(particle, true, new Location(ConfigList.world, x, y, z), 0.1f, 0.1f, 0.1f, 0.1f, 20, null, null);
	}
	
	public static boolean isMobTagExist(String name){
		if(name.equals(Stuff.BLINDING_TAG) || name.equals(Stuff.JUDOKAS_TAG) || name.equals(Stuff.KILLER_TAG) || name.equals(Stuff.RAPID_TAG) 
				|| name.equals(Stuff.WARDEN_TAG) || name.equals(Stuff.ZOMBIE_BAND_TAG) || name.equals(Stuff.WIZARD_TAG) || name.equals(Stuff.ZOMBIE_TAG) 
				|| name.equals(Stuff.CANARDIER_TAG) || name.equals(Stuff.ENERGY_SWIRL_TAG) || name.equals(Stuff.REGENERATOR_TAG)
				|| name.equals(Stuff.ENERGY_VORTEX_TAG) || name.equals(Stuff.CHEAT_WARDEN_TAG))
			return true;
		return false;
	}
	
	public static void spawnEntity(net.minecraft.server.v1_11_R1.Entity ent, Location loc){
		ent.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
		((CraftWorld) loc.getWorld()).getHandle().addEntity(ent);
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getPrivateField(String fieldName, Class clazz, Object object) {
		Field field;
		Object o = null;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			o = field.get(object);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public static int getAvailableStageID(){
		return StageRegistry.getAllStages().size();
	}
	
	
	
}
