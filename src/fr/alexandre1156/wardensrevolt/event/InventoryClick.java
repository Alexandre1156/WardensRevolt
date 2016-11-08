package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getInventory().getName().equals(ChatColor.GOLD + "Kits") && e.getCurrentItem() != null) {
			e.setCancelled(true);
			switch (e.getCurrentItem().getType()) {
			case BLAZE_ROD:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi le Mage de Feu !");
				if (WizardUtils.isAWizard(p))
					WizardUtils.getWizard(p).setWizardType(WizardType.INFERNO);
				else
					WizardUtils.addWizard(p, WizardType.INFERNO);
				p.getInventory().setItem(8, Stuff.INFERNO_KIT_ITEM);
				break;

			case WATER_BUCKET:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi la magicienne de l'Eau !");
				if (WizardUtils.isAWizard(p))
					WizardUtils.getWizard(p).setWizardType(WizardType.OCEANY);
				else
					WizardUtils.addWizard(p, WizardType.OCEANY);
				p.getInventory().setItem(8, Stuff.OCEANY_KIT_ITEM);
				break;

			case DIRT:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi la magicienne de la Terre !");
				if (WizardUtils.isAWizard(p))
					WizardUtils.getWizard(p).setWizardType(WizardType.TERRANA);
				else
					WizardUtils.addWizard(p, WizardType.TERRANA);
				p.getInventory().setItem(8, Stuff.TERRANA_KIT_ITEM);
				break;

			case NETHER_STAR:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi le Mage de du Tonerre !");
				if (WizardUtils.isAWizard(p))
					WizardUtils.getWizard(p).setWizardType(WizardType.ELECTRO);
				else
					WizardUtils.addWizard(p, WizardType.ELECTRO);
				p.getInventory().setItem(8, Stuff.ELECTRO_KIT_ITEM);
				break;

			case ICE:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi le Mage de la Glace !");
				if (WizardUtils.isAWizard(p))
					WizardUtils.getWizard(p).setWizardType(WizardType.GELATO);
				else
					WizardUtils.addWizard(p, WizardType.GELATO);
				p.getInventory().setItem(8, Stuff.GELATO_KIT_ITEM);
				break;
			case NAME_TAG:
				//Ne fait rien, permet juste d'éviter l'erreur
				break;
			default:
				p.sendMessage(ChatColor.DARK_RED + "TU AS CLIQUé SUR UN ITEM INEXISTANT ?!?!");
				break;
			}
			WardenRevolt.getInstance().getScoreboard().updateValue(1, ChatColor.BLUE+"Wizards "+ChatColor.RESET+": "+ChatColor.YELLOW+WizardUtils.getAllWizards().size()+" / 5");
		} else if(e.getInventory().getType().equals(InventoryType.PLAYER))
			e.setCancelled(true);
		
	}

}
