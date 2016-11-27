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

	public static Inventory inventoryKits;
	public static ItemStack changeKit;
	public static ItemStack itemKitInferno;
	public static ItemStack itemKitOceany;
	public static ItemStack itemKitTerrana;
	public static ItemStack itemKitEletro;
	public static ItemStack itemKitGelato;
	public static final String PLUGIN_TAG = ChatColor.DARK_AQUA+"[Warden's Revolt]";
	public static final String WARDEN_TAG = ChatColor.RED+""+ChatColor.BOLD+"[Warden]"+ChatColor.RESET;
	public static final String BLINDING_TAG = /*0ChatColor.BLACK+*/"L'aveuglant";
	public static final String WIZARD_TAG = ChatColor.DARK_GREEN+"Mages";
	public static final String KILLER_TAG = ChatColor.RED+"Tueur";
	public static final String ZOMBIE_TAG = ChatColor.GREEN+"Zombie";
	public static final String WARDEN_STAGEONE_MESSAGE_ONE = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Comment vous m'avez trouvé ?! Voici votre récompense : \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie.\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Mage\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous expulse 5 blocs plus loin sans vous faire de dégâts.\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_TWO = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Décidément, vous êtes plus tenaces que je ne le pensais ! Voici votre cadeau de votre temps perdu :  \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie.\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Mage\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous expulse 5 blocs plus loin sans vous faire de dégâts.\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_THREE = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Pourquoi vous vous acharnez ? Vous ne vallez rien du tout... Voici votre cadeau de consolation :  \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Tueur\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\"},{\"text\":\"+1 Mage\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous expulse 5 blocs plus loin sans vous faire de dégâts.\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_FOUR = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Mais on en fait qu'à sa tête à ce que je vois ! Allez, c'est Noël avant l'heure :  \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Tueur\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\"},{\"text\":\"+1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_FIVE = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\"J'en ai marre ! Je vous envoie toute mon armée pour vous détuire : \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Tueur\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"\",\"color\":\"green\"}]}}},{\"text\":\" , \",\"color\":\"none\"},{\"text\":\"+1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\",\"bold\":false},{\"text\":\"+40 Zombies\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Des zombies normaux.\",\"color\":\"blue\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden disparait*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_FINAL = "[\"\",{\"text\":\"[Warden] \",\"color\":\"red\",\"bold\":true},{\"text\":\"A ce que je vois, mon armée de petits monstres n'as pas suffit... Et bien, passons maintenant aux choses sérieuses ! Quelles sont les personnes les plus détestés sur un serveur ? \",\"color\":\"none\",\"bold\":false},{\"text\":\"Les cheateurs ! \",\"color\":\"dark_green\",\"bold\":true}]";
	public static final String TIP_ONE = " [\"\",{\"text\":\"Tip\",\"color\":\"yellow\",\"bold\":true},{\"text\":\" : Tous les ennemis sont maintenant tuable.\",\"color\":\"gray\",\"bold\":false}]";
	
	protected static void init(){
		createChangeKitItem();
		createKitItems();
		createKitInventory();
	}
	
	private static void createKitInventory(){
		inventoryKits = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.GOLD+"Kits");
		
		inventoryKits.addItem(itemKitInferno);
		inventoryKits.addItem(itemKitOceany);
		inventoryKits.addItem(itemKitTerrana);
		inventoryKits.addItem(itemKitEletro);
		inventoryKits.addItem(itemKitGelato);
	}

	private static void createChangeKitItem(){
		changeKit = new ItemStack(Material.NAME_TAG);
		ItemMeta meta = changeKit.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW+"Changer de Kit");
		meta.setLore(Arrays.asList(new String[]{ChatColor.YELLOW+"Clic droit pour changer de kit !"}));
		changeKit.setItemMeta(meta);
	}
	
	private static void createKitItems(){
		itemKitInferno = new ItemStack(Material.BLAZE_ROD);
		itemKitOceany = new ItemStack(Material.WATER_BUCKET);
		itemKitTerrana = new ItemStack(Material.DIRT);
		itemKitEletro = new ItemStack(Material.NETHER_STAR);
		itemKitGelato = new ItemStack(Material.ICE);
		
		ItemMeta infernoMeta = itemKitInferno.getItemMeta();
		ItemMeta oceanyMeta = itemKitInferno.getItemMeta();
		ItemMeta terranaMeta = itemKitInferno.getItemMeta();
		ItemMeta electroMeta = itemKitInferno.getItemMeta();
		ItemMeta gelatoMeta = itemKitInferno.getItemMeta();
		
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
		
		itemKitInferno.setItemMeta(infernoMeta);
		itemKitOceany.setItemMeta(oceanyMeta);
		itemKitTerrana.setItemMeta(terranaMeta);
		itemKitEletro.setItemMeta(electroMeta);
		itemKitGelato.setItemMeta(gelatoMeta);
	}
	
	
	
	
	
}
