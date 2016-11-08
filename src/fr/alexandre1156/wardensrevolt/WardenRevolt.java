package fr.alexandre1156.wardensrevolt;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import fr.alexandre1156.wardensrevolt.event.InventoryClick;
import fr.alexandre1156.wardensrevolt.event.PlayerBreakBlock;
import fr.alexandre1156.wardensrevolt.event.PlayerDropItem;
import fr.alexandre1156.wardensrevolt.event.PlayerInteract;
import fr.alexandre1156.wardensrevolt.event.PlayerJoin;
import fr.alexandre1156.wardensrevolt.event.PlayerMove;
import fr.alexandre1156.wardensrevolt.event.PlayerQuit;
import fr.alexandre1156.wardensrevolt.scheduler.stage.StageOne;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;

public class WardenRevolt extends JavaPlugin{

	private static WardenRevolt instance;
	private static GameTimer gameTimer;
	private static WRScoreboard board;
	private static StageOne stageOne;
	
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
		
		stageOne = new StageOne(this);
		gameTimer = new GameTimer();
		
		if (board == null){
			board = new WRScoreboard(DisplaySlot.SIDEBAR, ChatColor.RED+"Warden's Revolt", "dummy");
			board.addScore(ChatColor.RESET.toString(), 0);
			board.addScore(ChatColor.BLUE+"Wizards "+ChatColor.RESET+": "+ChatColor.YELLOW+WizardUtils.getAllWizards().size()+" / 5", 1);
			board.addScore(ChatColor.RESET.toString()+ChatColor.RESET.toString(), 4);
			board.addScore("En attente de joueurs...", 3);
			board.addScore(ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString(), 2);
		}
		
		Stuff.init();
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
		stageOne.startStageOne();
	}

	
}
