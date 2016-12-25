package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;
import fr.alexandre1156.wardensrevolt.wizard.Wizard;

public class PlayerInteract implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		boolean alreadyClicked = false;
		Action action = e.getAction();
		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)){
			if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInOffHand() != null){
				Wizard wiz = WizardUtils.getWizard(p);
				if(p.getInventory().getHeldItemSlot() == 1 || p.getInventory().getHeldItemSlot() == 2 || p.getInventory().getHeldItemSlot() == 3)
					wiz.consumneKitItem(p.getInventory().getHeldItemSlot());
				switch(p.getInventory().getItemInMainHand().getType()){
				case NAME_TAG:
					p.openInventory(Stuff.inventoryKits);
					alreadyClicked = true;
					break;
				case DIAMOND:
					wiz.switchDiamondActived();
					alreadyClicked = true;
					break;
				default:
					alreadyClicked = false;
					break;
				}
				if(!alreadyClicked){
					switch(p.getInventory().getItemInOffHand().getType()){
					case NAME_TAG:
						p.openInventory(Stuff.inventoryKits);
					case DIAMOND:
						wiz.switchDiamondActived();
						break;
					default: 
						break;
					}
				}
			}
			if(action.equals(Action.RIGHT_CLICK_BLOCK) && p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() == Material.FIREBALL){
				e.setCancelled(true);
			}
		}
	}
	
}
