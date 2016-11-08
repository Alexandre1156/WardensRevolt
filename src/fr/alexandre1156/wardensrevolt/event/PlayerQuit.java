package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;

public class PlayerQuit implements Listener{

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		WizardUtils.removeWizard(p);
		WardenRevolt.getInstance().getScoreboard().updateValue(1, ChatColor.BLUE+"Wizards "+ChatColor.RESET+": "+ChatColor.YELLOW+WizardUtils.getAllWizards().size()+" / 5");
	}
	
}
