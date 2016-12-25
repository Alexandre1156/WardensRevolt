package fr.alexandre1156.wardensrevolt.wizard;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class WizardGelato extends Wizard{
	
	private ArrayList<net.minecraft.server.v1_11_R1.Entity> freezedMobs;
	private ArrayList<BlockDataPos> iglooStructure;
	private PotionEffect speed;
	private PotionEffect regen;

	public WizardGelato(CraftPlayer cp) {
		super(cp);
		freezedMobs = Lists.newArrayList();
		iglooStructure = Lists.newArrayList();
		speed = new PotionEffect(PotionEffectType.SPEED, 100, 0);
		regen = new PotionEffect(PotionEffectType.REGENERATION, 100, 2);
		inventoryItem = ItemCreator.createItem(Material.ICE)
				.setCustomName(ChatColor.BLUE+"Gelato")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage de la glace")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Freeze "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tornade gelée "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 15 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Igloo "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 25 secs de recharge")
				.build();
		inventoryItemPosition = 14;
	}

	@Override
	public void giveKitItems() {
		spellItemOne = SpellItemCreator.createItem(Material.ICE)
				.setCustomName(ChatColor.BLUE+"Freeze")
				.addDescription("Gèle tous les monstres dans un rayon de 10x10")
				.addDescription(ChatColor.ITALIC+"Vous pouvez les taper, et les canardiers tombent au sol")
				.addDuration(5)
				.setCooldown(20)
				.build();
		
		spellItemTwo = SpellItemCreator.createItem(Material.PACKED_ICE)
				.setCustomName(ChatColor.DARK_BLUE+"Tornade gelée")
				.addDescription("Vous donne du speed 1")
				.addDuration(5)
				.setCooldown(15)
				.build();
		
		spellItemThree = SpellItemCreator.createItem(Material.SNOW_BLOCK)
				.setCustomName(ChatColor.WHITE+"Igloo")
				.addDescription("Vous entoure d'un igloo et donne Regeneration III")
				.addDuration(5)
				.setCooldown(2) //25
				.build();
		super.giveKitItems();
	}

	@Override
	protected void useFirstSpell() {
		for(Entity ent : this.getNearbyEntities(10d, 10d, 10d)){
			net.minecraft.server.v1_11_R1.Entity nmsEnt = ((CraftEntity) ent).getHandle();
			NBTTagCompound nbt = new NBTTagCompound();
			nmsEnt.c(nbt);
			nbt.setByte("NoAI", (byte) 1);
			nmsEnt.f(nbt);
			freezedMobs.add(nmsEnt);
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(net.minecraft.server.v1_11_R1.Entity nmsEnt : freezedMobs)
					Utils.spawnParticle(EnumParticle.BLOCK_CRACK, true, new Location(ConfigList.world, nmsEnt.locX, nmsEnt.locY+2, nmsEnt.locZ),  
							0.1f, 0.7f, 0.1f, 0.1f, 20, null, 79);
			}
		}, 0, 5);
		Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Iterator<net.minecraft.server.v1_11_R1.Entity> iter = freezedMobs.iterator();
				while(iter.hasNext()){
					net.minecraft.server.v1_11_R1.Entity nmsEnt = iter.next();
					NBTTagCompound nbt = new NBTTagCompound();
					nmsEnt.c(nbt);
					nbt.setByte("NoAI", (byte) 0);
					nmsEnt.f(nbt);
					iter.remove();
				}
			}
		}, 100);
	}

	@Override
	protected void useSecondSpell() {
		this.addPotionEffect(speed);
		BukkitTask task = Bukkit.getScheduler().runTaskTimer(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Utils.spawnParticle(EnumParticle.SNOW_SHOVEL, true, new Location(ConfigList.world, getLocation().getX(), getLocation().getY()+2, getLocation().getZ()), 0.1f, 0.5f, 0.1f, 0.1f, 30, null);
			}
		}, 0, 10);
		Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(task.getTaskId());
			}
		}, 100);
	}

	@Override
	protected void useThirdSpell() {
		this.addPotionEffect(regen);
		Location loc = new Location(ConfigList.world, this.getLocation().getX(), this.getLocation().getY()-1, this.getLocation().getZ());
		for(Entity ent : this.getNearbyEntities(5d, 5d, 5d)){
			double vecPEX = ent.getLocation().getX()-this.getLocation().getX(); //4 | max : 5.5
			double vecPEZ = ent.getLocation().getZ()-this.getLocation().getZ();
			double vecMulti = 0d;
			if(vecPEX >=0 && vecPEX < 1)
				vecMulti = 1.25d;
			else if(vecPEX >=1 && vecPEX < 2)
				vecMulti = 1d;
			else if(vecPEX >=2 && vecPEX < 3)
				vecMulti = 0.75d;
			else if(vecPEX >=3 && vecPEX < 4)
				vecMulti = 0.50d;
			else if(vecPEX >=4 && vecPEX <= 5)
				vecMulti = 0.25d;
			else if(vecPEX > 5)
				vecMulti = 0d;
			else if(vecPEX < 0 && vecPEX > -1)
				vecMulti = 1.25d;
			else if(vecPEX <= -1 && vecPEX > -2)
				vecMulti = 1d;
			else if(vecPEX <= -2 && vecPEX > -3)
				vecMulti = 0.75d;
			else if(vecPEX <= -3 && vecPEX > -4)
				vecMulti = 0.50d;
			else if(vecPEX <= -4 && vecPEX >= -5)
				vecMulti = 0.25d;
			else vecMulti = 0d; 
			ent.setVelocity(new Vector(vecPEX, 0.6d, vecPEZ).multiply(vecMulti));
		}
		this.constructIglooGround(loc);
		this.constructIglooFirstFloor(loc);
		this.constructIglooSecondFloor(loc);
		this.constructIglooThirdFloor(loc);
		Bukkit.getScheduler().runTaskLater(WardenRevolt.getInstance(), new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for(BlockDataPos blockLoc : iglooStructure){
					ConfigList.world.getBlockAt(blockLoc.getLocation()).setType(blockLoc.getMaterial());
					ConfigList.world.getBlockAt(blockLoc.getLocation()).setData(blockLoc.getData());
				}
			}
		}, 100);
	}
	
	@SuppressWarnings("deprecation")
	private void constructIglooGround(Location loc){
		for(int x = -2; x <= 2; x++){
			for(int z = -2; z <= 2; z++){
				Location loc2 = new Location(ConfigList.world, loc.getX()+x, loc.getY(), loc.getZ()+z);
				iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
				ConfigList.world.getBlockAt(loc2).setType(Material.SNOW_BLOCK);
			}
		}
		for(int x = -4; x <= -3; x++){
			for(int z = -1; z <= 1; z++){
				Location loc2 = new Location(ConfigList.world, loc.getX()+x, loc.getY(), loc.getZ()+z);
				iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
				ConfigList.world.getBlockAt(loc2).setType(Material.SNOW_BLOCK);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void constructIglooFirstFloor(Location loc){
		for(int x = -4; x <= -2; x++){
			Location loc2 = new Location(ConfigList.world, loc.getX()+x, loc.getY()+1, loc.getZ()-1);
			Location loc3 = new Location(ConfigList.world, loc.getX()+x, loc.getY()+1, loc.getZ()+1);
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc3).getType(), loc3, ConfigList.world.getBlockAt(loc3).getData()));
			ConfigList.world.getBlockAt(loc2).setType(Material.SNOW_BLOCK);
			ConfigList.world.getBlockAt(loc3).setType(Material.SNOW_BLOCK);
		}
		for(int x = -2; x <= 2; x++){
			Location loc2 = new Location(ConfigList.world, loc.getX()+x, loc.getY()+1, loc.getZ()+2);
			Location loc3 = new Location(ConfigList.world, loc.getX()+x, loc.getY()+1, loc.getZ()-2);
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc3).getType(), loc3, ConfigList.world.getBlockAt(loc3).getData()));
			ConfigList.world.getBlockAt(loc2).setType(Material.SNOW_BLOCK);
			ConfigList.world.getBlockAt(loc3).setType(Material.SNOW_BLOCK);
		}
		for(int z = -1; z <= 1; z++){
			Location loc2 = new Location(ConfigList.world, loc.getX()+2, loc.getY()+1, loc.getZ()+z);
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
			ConfigList.world.getBlockAt(loc2).setType(Material.SNOW_BLOCK);
		}
		Location loc2 = new Location(ConfigList.world, loc.getX(), loc.getY()+1, loc.getZ());
		Location loc3 = new Location(ConfigList.world, loc.getX()-4, loc.getY()+1, loc.getZ());
		iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
		iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc3).getType(), loc3, ConfigList.world.getBlockAt(loc3).getData()));
		ConfigList.world.getBlockAt(loc2).setType(Material.REDSTONE_TORCH_ON);
		ConfigList.world.getBlockAt(loc3).setType(Material.BARRIER);
	}

	@SuppressWarnings("deprecation")
	private void constructIglooSecondFloor(Location loc){
		for(int x = -4; x <= -2; x++){
			Location loc2 = new Location(ConfigList.world, loc.getX()+x, loc.getY()+2, loc.getZ());
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
			ConfigList.world.getBlockAt(loc2).setType(Material.SNOW_BLOCK);
		}
		Location loc2 = new Location(ConfigList.world, loc.getX()-2, loc.getY()+2, loc.getZ()-1);
		Location loc3 = new Location(ConfigList.world, loc.getX()-2, loc.getY()+2, loc.getZ()+1);
		iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
		iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc3).getType(), loc3, ConfigList.world.getBlockAt(loc3).getData()));
		ConfigList.world.getBlockAt(loc2).setType(Material.SNOW_BLOCK);
		ConfigList.world.getBlockAt(loc3).setType(Material.SNOW_BLOCK);
		for(int x = -1; x <= 1; x++){
			Location loc4 = new Location(ConfigList.world, loc.getX()+x, loc.getY()+2, loc.getZ()+2);
			Location loc5 = new Location(ConfigList.world, loc.getX()+x, loc.getY()+2, loc.getZ()-2);
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc4).getType(), loc4, ConfigList.world.getBlockAt(loc4).getData()));
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc5).getType(), loc5, ConfigList.world.getBlockAt(loc5).getData()));
			ConfigList.world.getBlockAt(loc4).setType(Material.SNOW_BLOCK);
			ConfigList.world.getBlockAt(loc5).setType(Material.SNOW_BLOCK);
		}
		for(int z = -1; z <= 1; z++){
			Location loc4 = new Location(ConfigList.world, loc.getX()+2, loc.getY()+2, loc.getZ()+z);
			iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc4).getType(), loc4, ConfigList.world.getBlockAt(loc4).getData()));
			ConfigList.world.getBlockAt(loc4).setType(Material.SNOW_BLOCK);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void constructIglooThirdFloor(Location loc) {
		for(int x = -1; x <= 1; x++){
			for(int z = -1; z <= 1; z++){
				Location loc2 = new Location(ConfigList.world, loc.getX()+x, loc.getY()+3, loc.getZ()+z);
				iglooStructure.add(new BlockDataPos(ConfigList.world.getBlockAt(loc2).getType(), loc2, ConfigList.world.getBlockAt(loc2).getData()));
				ConfigList.world.getBlockAt(loc2).setType(Material.SNOW_BLOCK);
			}
		}
	}
	
	@Override
	public WizardType getWizardType() {
		return WizardType.GELATO;
	}
	
	@Override
	public int getWizardTypeID() {
		return 1;
	}
	
	private class BlockDataPos {
		private Material mat;
		private Location loc;
		private byte data;
		
		public BlockDataPos(Material material, Location loc, byte data) {
			this.mat = material;
			this.loc = loc;
			this.data = data;
		}
		
		public byte getData() {
			return data;
		}
		
		public Location getLocation() {
			return loc;
		}
		
		public Material getMaterial() {
			return mat;
		}
	}

}
