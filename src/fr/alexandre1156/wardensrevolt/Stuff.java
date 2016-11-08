package fr.alexandre1156.wardensrevolt;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/*
 * Class contenant plein de choses, comme des inventaires, des messages...
 */
public class Stuff {

	public static Inventory KITS_INVENTORY;
	public static ItemStack CHANGE_KIT;
	public static final String PLUGIN_TAG = ChatColor.DARK_AQUA+"[Warden's Revolt]";
	public static final String WARDEN_TAG = ChatColor.RED+""+ChatColor.BOLD+"[Warden]"+ChatColor.RESET;
	public static ItemStack INFERNO_KIT_ITEM;
	public static ItemStack OCEANY_KIT_ITEM;
	public static ItemStack TERRANA_KIT_ITEM;
	public static ItemStack ELECTRO_KIT_ITEM;
	public static ItemStack GELATO_KIT_ITEM;
	
	protected static void init(){
		createChangeKitItem();
		createKitItems();
		createKitInventory();
	}
	
	private static void createKitInventory(){
		KITS_INVENTORY = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.GOLD+"Kits");
		
		KITS_INVENTORY.addItem(INFERNO_KIT_ITEM);
		KITS_INVENTORY.addItem(OCEANY_KIT_ITEM);
		KITS_INVENTORY.addItem(TERRANA_KIT_ITEM);
		KITS_INVENTORY.addItem(ELECTRO_KIT_ITEM);
		KITS_INVENTORY.addItem(GELATO_KIT_ITEM);
	}

	private static void createChangeKitItem(){
		CHANGE_KIT = new ItemStack(Material.NAME_TAG);
		ItemMeta meta = CHANGE_KIT.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW+"Changer de Kit");
		meta.setLore(Arrays.asList(new String[]{ChatColor.YELLOW+"Clic droit pour changer de kit !"}));
		CHANGE_KIT.setItemMeta(meta);
	}
	
	private static void createKitItems(){
		INFERNO_KIT_ITEM = new ItemStack(Material.BLAZE_ROD);
		OCEANY_KIT_ITEM = new ItemStack(Material.WATER_BUCKET);
		TERRANA_KIT_ITEM = new ItemStack(Material.DIRT);
		ELECTRO_KIT_ITEM = new ItemStack(Material.NETHER_STAR);
		GELATO_KIT_ITEM = new ItemStack(Material.ICE);
		
		ItemMeta infernoMeta = INFERNO_KIT_ITEM.getItemMeta();
		ItemMeta oceanyMeta = INFERNO_KIT_ITEM.getItemMeta();
		ItemMeta terranaMeta = INFERNO_KIT_ITEM.getItemMeta();
		ItemMeta electroMeta = INFERNO_KIT_ITEM.getItemMeta();
		ItemMeta gelatoMeta = INFERNO_KIT_ITEM.getItemMeta();
		
		infernoMeta.setDisplayName(ChatColor.RED+"Inferno");
		oceanyMeta.setDisplayName(ChatColor.DARK_BLUE+"Oceany");
		terranaMeta.setDisplayName(ChatColor.GRAY+"Terrana");
		electroMeta.setDisplayName(ChatColor.YELLOW+"Electro");
		gelatoMeta.setDisplayName(ChatColor.BLUE+"Gelato");
		
		infernoMeta.setLore(Arrays.asList(new String[]{ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage du feu",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Déflagration de feu "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 15 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Boule de feu "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 3 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Saut flamboyant "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 10 secs de recharge"}));
		oceanyMeta.setLore(Arrays.asList(new String[]{ChatColor.GRAY+""+ChatColor.ITALIC+"La magicienne de l'eau",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Vague dévastatrice "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 15 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"HydroCanon "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 6 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"HydroNinja "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 20 secs de recharge"}));
		terranaMeta.setLore(Arrays.asList(new String[]{ChatColor.GRAY+""+ChatColor.ITALIC+"La magicienne de la terre",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Tremblement de terre ultime "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 20 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Effondrement "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 10 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Explosion sismique "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 10 secs de recharge"}));
		electroMeta.setLore(Arrays.asList(new String[]{ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage du tonnerre",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Tempête "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 15 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Éclair "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 5 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Sprint "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 20 secs de recharge"}));
		gelatoMeta.setLore(Arrays.asList(new String[]{ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage de la glace",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Freeze "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 20 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Tornade gelée"+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 15 secs de recharge",
		ChatColor.DARK_RED+""+ChatColor.BOLD+"Igloo "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 25 secs de recharge"}));
		
		INFERNO_KIT_ITEM.setItemMeta(infernoMeta);
		OCEANY_KIT_ITEM.setItemMeta(oceanyMeta);
		TERRANA_KIT_ITEM.setItemMeta(terranaMeta);
		ELECTRO_KIT_ITEM.setItemMeta(electroMeta);
		GELATO_KIT_ITEM.setItemMeta(gelatoMeta);
	}
	
	
	
	
	
}
