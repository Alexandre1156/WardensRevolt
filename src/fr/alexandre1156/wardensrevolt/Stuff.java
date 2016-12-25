package fr.alexandre1156.wardensrevolt;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import fr.alexandre1156.wardensrevolt.utils.WizardRegistry;
import fr.alexandre1156.wardensrevolt.wizard.Wizard;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import net.minecraft.server.v1_11_R1.PlayerInteractManager;

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
	public static ItemStack itemKitSesterno;
	public static ItemStack itemKitTenebro;
	public static final String PLUGIN_TAG = ChatColor.DARK_AQUA+"[Warden's Revolt]"+ChatColor.RESET;
	public static final String WARDEN_CHAT_TAG = ChatColor.RED+""+ChatColor.BOLD+"[Warden]"+ChatColor.RESET;
	public static final String WARDEN_TAG = ChatColor.RED+"Warden";
	public static final String BLINDING_TAG = ChatColor.BLACK+"Aveuglant";
	public static final String WIZARD_TAG = ChatColor.DARK_GREEN+"Mage";
	public static final String KILLER_TAG = ChatColor.RED+"Tueur";
	public static final String ZOMBIE_TAG = ChatColor.GREEN+"Zombie";
	public static final String ZOMBIE_BAND_TAG = ChatColor.GREEN+"Zombie Team";
	public static final String RAPID_TAG = ChatColor.YELLOW+"Rapide";
	public static final String JUDOKAS_TAG = ChatColor.BLACK+"Judokas";
	public static final String CANARDIER_TAG = ChatColor.DARK_GRAY+"Canardier";
	public static final String ENERGY_SWIRL_TAG = ChatColor.AQUA+"Tourbillon d'énergie";
	public static final String REGENERATOR_TAG = ChatColor.LIGHT_PURPLE+"Regenerator";
	public static final String ENERGY_VORTEX_TAG = ChatColor.DARK_RED+"Vortex d'énergie";
	public static final String CHEAT_WARDEN_TAG = ChatColor.DARK_RED+"Cheat Warden";
	//A chaque ajout de nom de mob, le rajouter dans Utils.isMobTagExist() et WardenMobs.getMobByTag()
	public static final String WARDEN_STAGEONE_MESSAGE_START = WARDEN_CHAT_TAG+" Alors comme ça on pense pouvoir me vaincre ? Et bien, "+ChatColor.YELLOW+""+ChatColor.BOLD+"trouvez moi avant !";
	public static final String WARDEN_STAGEONE_MESSAGE_ONE = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Comment vous m'avez trouvé ?! Voici votre récompense : \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie.\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Mage\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous expulse 5 blocs plus loin sans vous faire de dégâts.\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_TWO = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Décidément, vous êtes plus tenaces que je ne le pensais ! Voici votre cadeau de votre temps perdu :  \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie.\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Mage\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous expulse 5 blocs plus loin sans vous faire de dégâts.\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_THREE = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Pourquoi vous vous acharnez ? Vous ne vallez rien du tout... Voici votre cadeau de consolation :  \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Tueur\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\"},{\"text\":\"+1 Mage\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous expulse 5 blocs plus loin sans vous faire de dégâts.\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_FOUR = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" Mais on en fait qu'à sa tête à ce que je vois ! Allez, c'est Noël avant l'heure :  \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Tueur\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\"},{\"text\":\"+1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie\",\"color\":\"green\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden retourne se cacher*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_FIVE = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" J'en ai marre ! Je vous envoie toute mon armée pour vous détuire : \",\"color\":\"none\",\"bold\":false},{\"text\":\"+1 Tueur\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"\",\"color\":\"green\"}]}}},{\"text\":\" , \",\"color\":\"none\"},{\"text\":\"+1 Aveuglant\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"S'il vous touche, il vous donne de la lenteur 2 et de l'aveuglement 1 tout en vous enlevant 1 cœur de votre vie\",\"color\":\"green\"}]}}},{\"text\":\" et \",\"color\":\"none\",\"bold\":false},{\"text\":\"+40 Zombies\",\"color\":\"yellow\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Des zombies normaux.\",\"color\":\"blue\"}]}}},{\"text\":\" !\",\"color\":\"none\",\"bold\":false},{\"text\":\" *Warden disparait*\",\"italic\":true,\"color\":\"none\"}]";
	public static final String WARDEN_STAGEONE_MESSAGE_FINAL = "[\"\",{\"text\":\"[Warden] \",\"color\":\"red\",\"bold\":true},{\"text\":\" A ce que je vois, mon armée de petits monstres n'as pas suffit... Et bien, passons maintenant aux choses sérieuses ! Quelles sont les personnes les plus détestés sur un serveur ? \",\"color\":\"none\",\"bold\":false},{\"text\":\"Les cheateurs ! \",\"color\":\"dark_green\",\"bold\":true}]";
	public static final String WARDEN_STAGETWO_MESSAGE_ONE = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" ♫ Et on vous tue, tue, tue, jusqu'au bout de la nuit ♫\",\"color\":\"none\",\"bold\":false}]";
	public static final String WARDEN_STAGETWO_MESSAGE_TWO = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" ♪ Eh ho, eh ho, ils sont au-bout-du rouleau ! ♫\",\"color\":\"none\",\"bold\":false}]";
	public static final String WARDEN_STAGETWO_MESSAGE_THREE = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" ♫ Au clair de la lune, mon ami Warden, prête-moi ton épée, pour calmer les ardeurs ♪\",\"color\":\"none\",\"bold\":false}]";
	public static final String WARDEN_STAGETWO_MESSAGE_FOUR = "[\"\",{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" ♪ I'm strong, da ba de da ba die ♪\",\"color\":\"none\",\"bold\":false}]";
	public static final String WARDEN_STAGETWO_MESSAGE_FINAL = "[{\"text\":\"[Warden]\",\"color\":\"red\",\"bold\":true},{\"text\":\" J'EN AI MARRE ! Je vais personnellement m'occuper de vous !\",\"color\":\"none\",\"bold\":false}]";
	public static final String WARDEN_STAGETHREE_MESSAGE_FOUR = "[\"\",{\"text\":\"[Warden] \",\"color\":\"red\",\"bold\":true},{\"text\":\"Pourquoi être tout seul quand on peut être \",\"color\":\"none\",\"bold\":false},{\"text\":\"quatre\",\"color\":\"red\"},{\"text\":\" !\",\"color\":\"none\"}]";
	public static final String CHEATWARDEN_STAGETHREE_MESSAGE_FINAL = "[{\"text\":\"[CheatWarden] \",\"color\":\"dark_red\",\"bold\":true},{\"text\":\"On va pouvoir enfin commencer à s'amuser !\",\"color\":\"red\",\"bold\":false}]";
	public static final String[] STAGETWO_WAVEONE_SPAWN_MESSAGE = {"[{\"text\":\">\",\"color\":\"dark_green\",\"bold\":true},{\"text\":\" Vague 1 :\",\"color\":\"green\",\"bold\":true}]]", "{\"text\":\" +20 Zombies Team\",\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Zombie avec \",\"color\":\"green\"},{\"text\":\"Resistance 1\",\"color\":\"gray\"},{\"text\":\" et \",\"color\":\"green\"},{\"text\":\"Epee en Pierre\",\"color\":\"gray\"}]}},\"bold\":false}", "{\"text\":\" +2 Rapides\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Bebe zombie avec de la \",\"color\":\"green\"},{\"text\":\"Vitesse 3 \",\"color\":\"blue\"},{\"text\":\"et une \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}}}", "[\"\",{\"text\":\" +1 Judoka\",\"color\":\"black\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Husk sautant et créant une retombée sismique qui vous envoie en l'air\",\"color\":\"green\"}]}}}]", "[\"\",{\"text\":\" +1 Regenerator\",\"color\":\"light_purple\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Wither Squelette avec de la \",\"color\":\"green\"},{\"text\":\"Regeneration 3\",\"color\":\"light_purple\"}]}}}]"};
	public static final String[] STAGETWO_WAVETWO_SPAWN_MESSAGE = {"[{\"text\":\">\",\"color\":\"dark_green\",\"bold\":true},{\"text\":\" Vague 2 :\",\"color\":\"green\",\"bold\":true}]]", "{\"text\":\" +30 Zombies Team\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Zombie avec \",\"color\":\"green\"},{\"text\":\"Resistance 1\",\"color\":\"gray\"},{\"text\":\" et \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}},\"color\":\"none\",\"bold\":false}", "{\"text\":\" +4 Rapides\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Bebe zombie avec de la \",\"color\":\"green\"},{\"text\":\"Vitesse 3 \",\"color\":\"blue\"},{\"text\":\"et une \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}}}", "[\"\",{\"text\":\" +2 Judokas\",\"color\":\"black\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Husk sautant et créant une retombée sismique qui vous envoie en l'air\",\"color\":\"green\"}]}}}]", "[\"\",{\"text\":\" +2 Regenerators\",\"color\":\"light_purple\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Wither Squelette avec de la \",\"color\":\"green\"},{\"text\":\"Regeneration 3\",\"color\":\"light_purple\"}]}}}]", "[\"\",{\"text\":\" +1 Canardier\",\"color\":\"dark_gray\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Steve volant et tirant des flèches\",\"color\":\"green\"}]}}}]"};
	public static final String[] STAGETWO_WAVETHREE_SPAWN_MESSAGE = {"[{\"text\":\">\",\"color\":\"dark_green\",\"bold\":true},{\"text\":\" Vague 3 :\",\"color\":\"green\",\"bold\":true}]", "{\"text\":\" +40 Zombies Team\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Zombie avec \",\"color\":\"green\"},{\"text\":\"Resistance 1\",\"color\":\"gray\"},{\"text\":\" et \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}},\"color\":\"none\",\"bold\":false}", "{\"text\":\" +6 Rapides\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Bebe zombie avec de la \",\"color\":\"green\"},{\"text\":\"Vitesse 3 \",\"color\":\"blue\"},{\"text\":\"et une \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}}}", "[\"\",{\"text\":\" +3 Judokas\",\"color\":\"black\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Husk sautant et créant une retombée sismique qui vous envoie en l'air\",\"color\":\"green\"}]}}}]", "[\"\",{\"text\":\" +3 Regenerators\",\"color\":\"light_purple\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Wither Squelette avec de la \",\"color\":\"green\"},{\"text\":\"Regeneration 3\",\"color\":\"light_purple\"}]}}}]", "[\"\",{\"text\":\" +2 Canardiers\",\"color\":\"dark_gray\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Steve volant et tirant des flèches\",\"color\":\"green\"}]}}}]", "[\"\",{\"text\":\" +1 Tourbillon d'énergie\",\"color\":\"aqua\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Evoker faisant des dégâts sur une zone de 4*4\",\"color\":\"green\"}]}}}]"};
	public static final String[] STAGETWO_WAVEFOUR_SPAWN_MESSAGE = {"[{\"text\":\">\",\"color\":\"dark_green\",\"bold\":true},{\"text\":\" Vague 4 :\",\"color\":\"green\",\"bold\":true}]", "{\"text\":\" +40 Zombies Team\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Zombie avec \",\"color\":\"green\"},{\"text\":\"Resistance 1\",\"color\":\"gray\"},{\"text\":\" et \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}},\"color\":\"none\",\"bold\":false}", "{\"text\":\" +8 Rapides\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Bebe zombie avec de la \",\"color\":\"green\"},{\"text\":\"Vitesse 3 \",\"color\":\"blue\"},{\"text\":\"et une \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}}}", "[\"\",{\"text\":\" +3 Judokas\",\"color\":\"black\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Husk sautant et créant une retombée sismique qui vous envoie en l'air\",\"color\":\"green\"}]}}}]", "[\"\",{\"text\":\" +5 Regenerators\",\"color\":\"light_purple\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Wither Squelette avec de la \",\"color\":\"green\"},{\"text\":\"Regeneration 3\",\"color\":\"light_purple\"}]}}}]", "[\"\",{\"text\":\" +4 Canardiers\",\"color\":\"dark_gray\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Steve volant et tirant des flèches\",\"color\":\"green\"}]}}}]", "[\"\",{\"text\":\" +2 Tourbillons d'énergie\",\"color\":\"aqua\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Evoker faisant des dégâts sur une zone de 4*4\",\"color\":\"green\"}]}}}]"};
	public static final String[] STAGETWO_WAVEFIVE_SPAWN_MESSAGE = {"[{\"text\":\">\",\"color\":\"dark_green\",\"bold\":true},{\"text\":\" Vague 5 :\",\"color\":\"green\",\"bold\":true}]", "{\"text\":\" +50 Zombies Team\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Zombie avec \",\"color\":\"green\"},{\"text\":\"Resistance 1\",\"color\":\"gray\"},{\"text\":\" et \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}},\"color\":\"none\",\"bold\":false}", "{\"text\":\" +10 Rapides\",\"color\":\"yellow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Bebe zombie avec de la \",\"color\":\"green\"},{\"text\":\"Vitesse 3 \",\"color\":\"blue\"},{\"text\":\"et une \",\"color\":\"green\"},{\"text\":\"Epee en pierre\",\"color\":\"gray\"}]}}}", "[\"\",{\"text\":\" +5 Judokas\",\"color\":\"black\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Husk sautant et créant une retombée sismique qui vous envoie en l'air\",\"color\":\"green\"}]}}}]", "[\"\",{\"text\":\" +5 Regenerators\",\"color\":\"light_purple\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Wither Squelette avec de la \",\"color\":\"green\"},{\"text\":\"Regeneration 3\",\"color\":\"light_purple\"}]}}}]", "[\"\",{\"text\":\" +5 Canardiers\",\"color\":\"dark_gray\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Steve volant et tirant des flèches\",\"color\":\"green\"}]}}}]", "[\"\",{\"text\":\" +4 Tourbillons d'énergie\",\"color\":\"aqua\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Evoker faisant des dégâts sur une zone de 4*4\",\"color\":\"green\"}]}}}]"};
	public static final String TIP_ONE = " [\"\",{\"text\":\"Tip\",\"color\":\"yellow\",\"bold\":true},{\"text\":\" : Tous les ennemis sont maintenant tuable.\",\"color\":\"gray\",\"bold\":false}]";
	
	protected static void init(){
		createChangeKitItem();
		createKitItems();
		createKitInventory();
	}
	
	@SuppressWarnings("deprecation")
	private static void createKitInventory(){
		inventoryKits = Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.GOLD+"Kits");
		
		int worldPos = 0;
		for(int i = 0; i < Bukkit.getWorlds().size(); i++){
			if(Bukkit.getWorlds().get(i).getName().equals(ConfigList.world.getName()))
				worldPos = i;
		}
		
		Iterator<Class<? extends Wizard>> wizardTypes = WizardRegistry.getAllWizardTypes().values().iterator();
		while(wizardTypes.hasNext()){
			Class<? extends Wizard> wizardType = wizardTypes.next();
			try {
				Wizard obj = (Wizard) wizardType.getConstructor(CraftPlayer.class).newInstance(new CraftPlayer((CraftServer) Bukkit.getServer(),
						new EntityPlayer(MinecraftServer.getServer(), 
								MinecraftServer.getServer().getWorldServer(worldPos), 
								new GameProfile(UUID.randomUUID(), Utils.randomString(10)),
								new PlayerInteractManager(((CraftWorld) ConfigList.world).getHandle().b()))));
				ItemStack itemInv = obj.inventoryItem;
				int itemInvPos = obj.inventoryItemPosition;
				if(inventoryKits.getItem(itemInvPos) != null){
					Utils.consoleMessage("Le slot "+itemInvPos+" est déjà pris ! L'ItemStack "+itemInv.getType()
					+" du WizardType "+wizardType.getSimpleName()+" sera dans une autre slot.", ChatColor.YELLOW);
					for(int i = 0; i < 26; i++){
						if(inventoryKits.getItem(1) == null)
							obj.inventoryItemPosition = i;
					}
				} else
					inventoryKits.setItem(itemInvPos, itemInv);
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException
					| InstantiationException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createChangeKitItem(){
		changeKit = ItemCreator.createItem(Material.NAME_TAG)
				.setCustomName(ChatColor.YELLOW+"Changer de Kit")
				.addLineLore(ChatColor.YELLOW+"Clic droit pour changer de kit !")
				.build();
	}
	
	private static void createKitItems(){
		itemKitInferno = ItemCreator.createItem(Material.BLAZE_ROD)
			.setCustomName(ChatColor.RED+"Inferno")
			.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage du feu")
			.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Déflagration de feu "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
					""+ChatColor.GREEN+" 15 secs de recharge")
			.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Boule de feu "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
					""+ChatColor.GREEN+" 3 secs de recharge")
			.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Saut flamboyant "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
					""+ChatColor.GREEN+" 10 secs de recharge")
			.build();
		itemKitOceany = ItemCreator.createItem(Material.WATER_BUCKET)
				.setCustomName(ChatColor.DARK_BLUE+"Oceany")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"La magicienne de l'eau")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Vague dévastatrice "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 15 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"HydroCanon "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 6 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"HydroNinja "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.build();
		itemKitTerrana = ItemCreator.createItem(Material.DIRT)
				.setCustomName(ChatColor.GRAY+"Terrana")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"La magicienne de la terre")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tremblement de terre ultime "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 20 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Effondrement "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 10 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Explosion sismique "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 10 secs de recharge")
				.build();
		itemKitEletro = ItemCreator.createItem(Material.NETHER_STAR)
				.setCustomName(ChatColor.YELLOW+"Electro")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage du tonnerre")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tempête "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 15 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Éclair "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 5 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Sprint "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.build();
		itemKitGelato = ItemCreator.createItem(Material.ICE)
				.setCustomName(ChatColor.BLUE+"Gelato")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage de la glace")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Freeze "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tornade gelée "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 15 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Igloo "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 25 secs de recharge")
				.build();
		itemKitSesterno = ItemCreator.createItem(Material.SAPLING)
				.setCustomName(ChatColor.GREEN+"Sesterno")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"La mage de la nature")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Racines "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Lianes "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 6 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tranch'herbe "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 1 sec de recharge")
				.build();
		itemKitTenebro = ItemCreator.createItem(Material.SKULL_ITEM, (short) 3)
				.setCustomName(ChatColor.BLACK+"Tenebro")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"La mage sombre")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tornade noire "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Teleportation "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 25 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Lame sombre "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 2 sec de recharge")
				.build();
	}
	
	
	
	
	
}
