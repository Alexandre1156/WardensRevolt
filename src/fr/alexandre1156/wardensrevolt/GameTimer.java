package fr.alexandre1156.wardensrevolt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.alexandre1156.wardensrevolt.utils.Utils;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;

public class GameTimer extends Thread{

	private volatile Thread blinker;
	private int mins;
	private int secs;
	private boolean timerStarted = false;
	
	public void start(){
		blinker = new Thread(this);
		mins = 0;
		secs = 30;
		blinker.start();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run(){
		Thread thread = Thread.currentThread();
		while(blinker == thread){
			if(Bukkit.getOnlinePlayers().size() == 1){ //5
				if(!timerStarted){
					timerStarted = true;
					WardenRevolt.getInstance().getScoreboard().updateValue(3, ChatColor.GREEN+"     "+secs+ChatColor.RESET+" Seconde(s)");
					WardenRevolt.getInstance().getScoreboard().updateValue(4, ChatColor.GREEN+"     "+mins+ChatColor.RESET+" Minutes(s)");
					WardenRevolt.getInstance().getScoreboard().addScore("Temps restant : ", 5);
					WardenRevolt.getInstance().getScoreboard().addScore(ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString()+ChatColor.RESET.toString(), 6);
				}
				WardenRevolt.getInstance().getScoreboard().updateValue(3, ChatColor.GREEN+"     "+secs+ChatColor.RESET+" Seconde(s)");
				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(secs == 30 || secs == 20 || secs == 10 || secs == 5 || secs == 4 || secs == 3 || secs == 2){
					Bukkit.broadcastMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" La partie commence dans "+ChatColor.GREEN+secs+ChatColor.YELLOW+" secondes !");
					Utils.playSoundToAllPlayer(Sound.BLOCK_NOTE_BASS, 1, 0);
				}
				if(secs == 1){
					Bukkit.broadcastMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" La partie commence dans "+ChatColor.GREEN+secs+ChatColor.YELLOW+" seconde !");
					Utils.playSoundToAllPlayer(Sound.BLOCK_NOTE_BASS, 1, 0);
				}
				secs--;
				if(WizardUtils.getAllWizards().size() == 1){ //5
					if(secs > 5)
						secs = 5;
				}
				if(secs == 0){
					if(WizardUtils.getAllWizards().size() < 1){ //5
						for(Player p : Bukkit.getOnlinePlayers()){
							if(!WizardUtils.isAWizard(p)){
								WizardUtils.assignRandomWizardType(p);
								p.closeInventory();
								break;
							}
						}
					}
					Utils.playSoundToAllPlayer(Sound.BLOCK_NOTE_BASS, 1, 1);
					this.stopTheTimer();
					WardenRevolt.getInstance().startGame();
				}
			} else if(timerStarted){
				timerStarted = false;
				WardenRevolt.getInstance().getScoreboard().updateValue(3, "En attente de joueurs...");
				WardenRevolt.getInstance().getScoreboard().updateValue(4, ChatColor.RESET.toString());
				WardenRevolt.getInstance().getScoreboard().resetScore(5);
				WardenRevolt.getInstance().getScoreboard().resetScore(6);
				secs = 30;
			}
		}
	}
	
	public void stopTheTimer(){
		blinker = null;
	}
	
	/*
	 * Format : {mins} : {secs}
	 */
	public String getTimeRemaining() {
		return mins+" : "+secs;
		
	}
	
	
	
}
