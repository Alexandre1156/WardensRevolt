package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;

public class PlayerMove implements Listener{

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!WizardUtils.isAWizard(p))
			p.openInventory(Stuff.KITS_INVENTORY);
	}
	
}
