package fr.alexandre1156.wardensrevolt;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.google.common.collect.Maps;

import fr.alexandre1156.wardensrevolt.utils.Utils;

public class WRScoreboard {

	private ScoreboardManager manager;
	private Scoreboard scoreboard;
	private Objective obj;
	private HashMap<Integer, Score> newScores;
	
	public WRScoreboard(DisplaySlot slot, String displayName, String typeOfObj){
		manager = Bukkit.getScoreboardManager();
		scoreboard = manager.getNewScoreboard();
		obj = scoreboard.registerNewObjective(Utils.randomString(10), typeOfObj);
		obj.setDisplaySlot(slot);
		obj.setDisplayName(displayName);
		newScores = Maps.newHashMap();
	}
	
	public WRScoreboard(DisplaySlot slot, String displayName, String typeOfObj, String scoreboardName){
		manager = Bukkit.getScoreboardManager();
		scoreboard = manager.getNewScoreboard();
		obj = scoreboard.registerNewObjective(scoreboardName, typeOfObj);
		obj.setDisplaySlot(slot);
		obj.setDisplayName(displayName);
		newScores = Maps.newHashMap();
	}
	
	public void addScore(String textToDisplay, int theScore){
		this.newScores.put(theScore, this.obj.getScore(textToDisplay));
		((Score)this.newScores.get(theScore)).setScore(theScore);
	}
	
	public void resetScore(int positionOfTheScore){
		this.obj.getScoreboard().resetScores(((Score)this.newScores.get(positionOfTheScore)).getEntry());
	}
	
	public void displayScoreboard(){
		for(Player online : Bukkit.getOnlinePlayers())
			online.setScoreboard(scoreboard);
	}
	
	/*
	 * Merci à Hokop Developpments (https://www.youtube.com/channel/UCd5xkp7dTNDxWUY9j4sTXWg)
	 */
	public void updateValue(int position, String value){
		int place = ((Score)this.newScores.get(position)).getScore();
		this.obj.getScoreboard().resetScores(((Score)this.newScores.get(position)).getEntry());
		this.newScores.replace(position, this.obj.getScore(value));
		((Score)this.newScores.get(position)).setScore(position);
	}
	
}
