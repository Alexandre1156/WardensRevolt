package fr.alexandre1156.wardensrevolt;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import fr.alexandre1156.wardensrevolt.command.ReloadCommand;
import fr.alexandre1156.wardensrevolt.command.WardenCommand;
import fr.alexandre1156.wardensrevolt.command.WorldCommand;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.config.WardenConfig;
import fr.alexandre1156.wardensrevolt.entity.WardenWitch;
import fr.alexandre1156.wardensrevolt.event.FoodChange;
import fr.alexandre1156.wardensrevolt.event.InventoryClick;
import fr.alexandre1156.wardensrevolt.event.MobEvent;
import fr.alexandre1156.wardensrevolt.event.PlayerBreakBlock;
import fr.alexandre1156.wardensrevolt.event.PlayerDie;
import fr.alexandre1156.wardensrevolt.event.PlayerDropItem;
import fr.alexandre1156.wardensrevolt.event.PlayerInteract;
import fr.alexandre1156.wardensrevolt.event.PlayerJoin;
import fr.alexandre1156.wardensrevolt.event.PlayerMove;
import fr.alexandre1156.wardensrevolt.event.PlayerPlaceBlock;
import fr.alexandre1156.wardensrevolt.event.PlayerQuit;
import fr.alexandre1156.wardensrevolt.event.ServerReload;
import fr.alexandre1156.wardensrevolt.scheduler.stage.Stage;
import fr.alexandre1156.wardensrevolt.scheduler.stage.Stage.WardenStage;
import fr.alexandre1156.wardensrevolt.scheduler.stage.StageOne;
import fr.alexandre1156.wardensrevolt.scheduler.stage.StageTwo;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;
import fr.alexandre1156.wardensrevolt.wizard.Wizard;
import net.minecraft.server.v1_11_R1.EntityTypes;

public class WardenRevolt extends JavaPlugin{

	private static WardenRevolt instance;
	private static GameTimer gameTimer;
	private static WRScoreboard board;
	private static StageOne stageOne;
	private static StageTwo stageTwo;
	private static WardenConfig config;
	
	//Source : https://forum.epicube.fr/threads/wardens-revolt.124587/
	@Override
	public void onEnable() {
		instance = this;
		
		this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerMove(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerBreakBlock(), this);
		this.getServer().getPluginManager().registerEvents(new MobEvent(), this);
		this.getServer().getPluginManager().registerEvents(new FoodChange(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerPlaceBlock(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDie(), this);
		this.getServer().getPluginManager().registerEvents(new WorldCommand(), this);
		this.getServer().getPluginManager().registerEvents(new ReloadCommand(), this);
		this.getServer().getPluginManager().registerEvents(new ServerReload(), this);
		
		if (board == null){
			board = new WRScoreboard(DisplaySlot.SIDEBAR, ChatColor.RED+"Warden's Revolt", "dummy");
			board.addScore(ChatColor.RESET.toString(), 0);
			board.addScore(ChatColor.BLUE+"Wizards "+ChatColor.RESET+": "+ChatColor.YELLOW+WizardUtils.getAllWizards().size()+" / 5", 1);
			board.addScore(ChatColor.RESET.toString()+ChatColor.RESET.toString(), 4);
			board.addScore("En attente de joueurs...", 3);
			board.addScore(ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString(), 2);
		}
		
		for(Entity ent : Bukkit.getWorlds().get(0).getEntities()){
			if(ent instanceof ArmorStand && ent.getCustomName() != null && ent.getCustomName().equals(ChatColor.RED+"Warden"))
				((ArmorStand) ent).setHealth(0);
			else if(ent instanceof Skeleton && ent.getCustomName() != null && ent.getCustomName().equals(ChatColor.BLACK+"L'aveuglant"))
				((Skeleton) ent).setHealth(0);
			else if(ent instanceof Zombie && ent.getCustomName() != null && ent.getCustomName().equals(ChatColor.RED+"Tueur"))
				((Zombie) ent).setHealth(0);
			else if(ent instanceof Witch && ent.getCustomName() != null && ent.getCustomName().equals(ChatColor.DARK_GREEN+"Mage"))
				((Witch) ent).setHealth(0);
			else if(ent instanceof Zombie && ent.getCustomName() != null && ent.getCustomName().equals(Stuff.ZOMBIE_TAG))
				((Zombie) ent).setHealth(0);
		}
		
		try {
            @SuppressWarnings("rawtypes")
            Class[] args = new Class[4];
            args[3] = String.class;
            args[2] = Class.class;
            args[1] = String.class;
            args[0] = int.class;
            Method a = EntityTypes.class.getDeclaredMethod("a", args);
            a.setAccessible(true);
            a.invoke(a, 150, "warden_witch", WardenWitch.class, "WardenWitch");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		Stuff.init();
		this.saveDefaultConfig();
		WardenConfig.init(this.getDataFolder().getPath()+"/config.yml");
		config = new WardenConfig();
		ConfigList.init();
		
		new WardenCommand();
		stageOne = new StageOne(this);
		gameTimer = new GameTimer();
		
		ConfigList.world.setGameRuleValue("doMobLoot", "false");
		//J'ai des fuites de mémoires
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public Plugin getPlugin(){
		return (Plugin) this;
	}
	
	public static WardenRevolt getInstance(){
		return instance;
	}

	public GameTimer getGameTimer() {
		return gameTimer;
		
	}
	
	public WRScoreboard getScoreboard(){
		return board;
	}
	
	public void startGame(){
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		sword = Utils.addUnbrakeableTag(sword);
		sword = Utils.changeAttackSpeedValue(sword, 0);
		ItemStack diamond = new ItemStack(Material.DIAMOND);
		ItemMeta diamondMeta = diamond.getItemMeta();
		diamondMeta.setDisplayName(ChatColor.DARK_BLUE+"Anti-Cheat");
		diamondMeta.setLore(Arrays.asList(new String[]{ChatColor.YELLOW+"Lance un rayon ultime qui inflige 30 Cœurs de dégâts à un monstre.",
				ChatColor.GRAY+"La moitié des joueurs doivent l'activer pour le lancer.",
				ChatColor.RED+"Faisable seulement "+ChatColor.BOLD+"une fois"+ChatColor.RESET+""+ChatColor.RED+" par PHASE."}));
		diamondMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		diamond.setItemMeta(diamondMeta);
		
		for(Wizard wizard : WizardUtils.getAllWizards())
			wizard.giveKitItems();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			p.getInventory().setItem(0, sword);
			p.getInventory().setItem(8, diamond);
			p.setHealth(20);
			p.setFoodLevel(20);
		}
		board.updateValue(1, ChatColor.YELLOW+"→Trouvez où est caché Warden !");
		board.updateValue(3, ChatColor.BLACK+"Aveuglants : "+Utils.getBlindingsNumber());
		board.updateValue(4, ChatColor.DARK_GREEN+"Mages : "+Utils.getWizardsNumber());
		board.updateValue(5, ChatColor.RED+"Tueurs : "+Utils.getKillersNumber());
		board.updateValue(6, ChatColor.GREEN+"Zombies : "+Utils.getZombiesNumber());
		board.addScore(ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString(), 7);
		this.nextStage(WardenStage.STAGE_ONE, null);
		//onAllMobKilled ne marche pas -> 1 killer et 1 witherskeleton ne devienne plus invisible
		//Les WardenWitch sont invisibles
	}
	
	public void nextStage(WardenStage newStage, Object prevStage){
		if(prevStage != null){
			if(prevStage instanceof Stage)
				((Stage) prevStage).isActive = false;
		}
		
		switch(newStage){
		case STAGE_ONE:
			stageOne.isActive = true;
			stageOne.start();
			break;
		case STAGE_TWO:
			if(stageTwo == null)
				stageTwo = new StageTwo(this);
			stageTwo.isActive = true;
			stageTwo.start();
			break;
		case STAGE_THREE:
			
			break;
		case STAGE_FOUR:
			
			break;
		case STAGE_FIVE:
			
			break;
		}
	}
	
	public WardenConfig getWardenConfig(){
		return config;
	}

	
}

