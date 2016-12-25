package fr.alexandre1156.wardensrevolt;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
import fr.alexandre1156.wardensrevolt.entity.Canardier;
import fr.alexandre1156.wardensrevolt.entity.EnergySwirl;
import fr.alexandre1156.wardensrevolt.entity.EnergyVortex;
import fr.alexandre1156.wardensrevolt.entity.Judokas;
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
import fr.alexandre1156.wardensrevolt.scheduler.stage.StageThree;
import fr.alexandre1156.wardensrevolt.scheduler.stage.StageTwo;
import fr.alexandre1156.wardensrevolt.utils.StageRegistry;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import fr.alexandre1156.wardensrevolt.utils.WizardRegistry;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;
import fr.alexandre1156.wardensrevolt.wizard.Wizard;
import fr.alexandre1156.wardensrevolt.wizard.WizardElectro;
import fr.alexandre1156.wardensrevolt.wizard.WizardGelato;
import fr.alexandre1156.wardensrevolt.wizard.WizardInferno;
import fr.alexandre1156.wardensrevolt.wizard.WizardOceany;
import fr.alexandre1156.wardensrevolt.wizard.WizardSesterno;
import fr.alexandre1156.wardensrevolt.wizard.WizardTenebro;
import fr.alexandre1156.wardensrevolt.wizard.WizardTerrana;
import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.EntityTypes;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import net.minecraft.server.v1_11_R1.RegistryMaterials;

public class WardenRevolt extends JavaPlugin{

	private static WardenRevolt instance;
	private static GameTimer gameTimer;
	private static WRScoreboard board;
	private static StageOne stageOne;
	private static StageTwo stageTwo;
	private static StageThree stageThree;
	private static WardenConfig config;
	
	//Source : https://forum.epicube.fr/threads/wardens-revolt.124587/
	@Override
	public void onEnable() {
		instance = this;
		WardenConfig.init(this.getDataFolder().getPath()+"/config.yml");
		config = new WardenConfig();
		ConfigList.init();
		
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
			board.addScore(0, ChatColor.RESET.toString());
			board.addScore(1, ChatColor.BLUE+"Wizards "+ChatColor.RESET+": "+ChatColor.YELLOW+WizardUtils.getAllWizards().size()+" / 5");
			board.addScore(4, ChatColor.RESET.toString()+ChatColor.RESET.toString());
			board.addScore(3, "En attente de joueurs...");
			board.addScore(2, ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString());
		}
		
		this.saveDefaultConfig();
		
		WizardRegistry.registerWizardType(WizardGelato.class);
		WizardRegistry.registerWizardType(WizardElectro.class);
		WizardRegistry.registerWizardType(WizardSesterno.class);
		WizardRegistry.registerWizardType(WizardTenebro.class);
		WizardRegistry.registerWizardType(WizardTerrana.class);
		WizardRegistry.registerWizardType(WizardOceany.class);
		WizardRegistry.registerWizardType(WizardInferno.class);
		
		Stuff.init();
		
		for(LivingEntity ent : ConfigList.world.getLivingEntities()){
			if(ent.getCustomName() != null){
				if(Utils.isMobTagExist(ent.getCustomName()))
						ent.setHealth(0.0D);
				if(ent.getCustomName().equals("CanardierAS") || ent.getCustomName().equals(Stuff.WARDEN_TAG+" 1") || 
						ent.getCustomName().equals(Stuff.WARDEN_TAG+" 2") || 
						ent.getCustomName().equals(Stuff.WARDEN_TAG+" 3") || 
						ent.getCustomName().equals(Stuff.WARDEN_TAG+" 4"))
					ent.setHealth(0.0D);
			}
		}
		
		new WardenCommand();
		stageOne = new StageOne(this);
		stageTwo = new StageTwo(this);
		stageThree = new StageThree(this);
		StageRegistry.registreStage(stageOne);
		StageRegistry.registreStage(stageTwo);
		StageRegistry.registreStage(stageThree);
		gameTimer = new GameTimer();
		
		ConfigList.world.setGameRuleValue("doMobLoot", "false");
		//J'ai des fuites de mémoires
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onLoad() {
//		EntityTypes.b.a(150, new MinecraftKey("WardenWitch"), WardenWitch.class);
//		EntityTypes.b.a(149, new MinecraftKey("Judokas"), Judokas.class);
//		EntityTypes.b.a(148, new MinecraftKey("WardenBat"), WardenBat.class);
		this.registerEntity("WardenWitch", 66, WardenWitch.class);
		this.registerEntity("Judokas", 23, Judokas.class);
		this.registerEntity("WardenBat", 35, Canardier.class);
		this.registerEntity("EnergySwirl", 34, EnergySwirl.class);
		this.registerEntity("EnergyVortex", 36, EnergyVortex.class);
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
		sword = Utils.addUnbreakableTag(sword);
		//sword = Utils.changeAttackSpeedValue(sword, 0);
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
			p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
		}
		board.updateValue(1, ChatColor.YELLOW+"→Trouvez où est caché Warden !");
		board.updateValue(3, ChatColor.BLACK+"Aveuglants : 0");
		board.updateValue(4, ChatColor.DARK_GREEN+"Mages : 0");
		board.updateValue(5, ChatColor.RED+"Tueurs : 0");
		board.updateValue(6, ChatColor.GREEN+"Zombies : 0");
		board.addScore(7, ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString());
		this.nextStage(WardenStage.STAGE_THREE.getID(), null); // FIXME A CHANGER PAR STAGE ONE
	}
	
	public void nextStage(int IDOfThenewStage, Object prevStage){
		if(!StageRegistry.getAllStages().containsKey(IDOfThenewStage)) {
			Utils.consoleMessage("Aucun stage n'a l'ID "+IDOfThenewStage+" !", ChatColor.RED);
			return;
		}
		if(prevStage != null){
			if(prevStage instanceof Stage)
				((Stage) prevStage).isActive = false;
		}
		if(StageRegistry.getAllStages().get(IDOfThenewStage) instanceof Stage){
			((Stage) StageRegistry.getAllStages().get(IDOfThenewStage)).isActive = true;
			((Stage) StageRegistry.getAllStages().get(IDOfThenewStage)).start();
		}
	}
	
	public WardenConfig getWardenConfig(){
		return config;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public void registerEntity(String name, int id, Class<? extends EntityInsentient> customClass) {
        MinecraftKey key = new MinecraftKey(name);
        try {
            ((RegistryMaterials) getPrivateStatic(EntityTypes.class, "b")).a(id, key, customClass);
            ((Set) getPrivateStatic(EntityTypes.class, "d")).add(key);
            ((List) getPrivateStatic(EntityTypes.class, "g")).set(id, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    @SuppressWarnings("rawtypes")
    private static Object getPrivateStatic(Class clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }

	
}

