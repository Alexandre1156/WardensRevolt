package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
				if (WizardUtils.isAWizard(p)){
					WizardUtils.removeWizard(p);
					WizardUtils.addWizard(p, WizardType.INFERNO.getID());
				} else
					WizardUtils.addWizard(p, WizardType.INFERNO.getID());
				p.getInventory().setItem(8, Stuff.itemKitInferno);
				break;

			case WATER_BUCKET:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi la magicienne de l'Eau !");
				if (WizardUtils.isAWizard(p)){
					WizardUtils.removeWizard(p);
					WizardUtils.addWizard(p, WizardType.OCEANY.getID());
				} else
					WizardUtils.addWizard(p, WizardType.OCEANY.getID());
				p.getInventory().setItem(8, Stuff.itemKitOceany);
				break;

			case DIRT:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi la magicienne de la Terre !");
				if (WizardUtils.isAWizard(p)){
						WizardUtils.removeWizard(p);
						WizardUtils.addWizard(p, WizardType.TERRANA.getID());
				} else
					WizardUtils.addWizard(p, WizardType.TERRANA.getID());
				p.getInventory().setItem(8, Stuff.itemKitTerrana);
				break;

			case NETHER_STAR:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi le Mage du Tonerre !");
				if (WizardUtils.isAWizard(p)){
					WizardUtils.removeWizard(p);
					WizardUtils.addWizard(p, WizardType.ELECTRO.getID());
				} else
					WizardUtils.addWizard(p, WizardType.ELECTRO.getID());
				p.getInventory().setItem(8, Stuff.itemKitEletro);
				break;

			case ICE:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi le Mage de la Glace !");
				if (WizardUtils.isAWizard(p)){
					WizardUtils.removeWizard(p);
					WizardUtils.addWizard(p, WizardType.GELATO.getID());
				} else
					WizardUtils.addWizard(p, WizardType.GELATO.getID());
				p.getInventory().setItem(8, Stuff.itemKitGelato);
				break;
			case SAPLING:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi le Mage de la Nature !");
				if (WizardUtils.isAWizard(p)){
					WizardUtils.removeWizard(p);
					WizardUtils.addWizard(p, WizardType.SYSTERNO.getID());
				} else
					WizardUtils.addWizard(p, WizardType.SYSTERNO.getID());
				p.getInventory().setItem(8, Stuff.itemKitSesterno);
				break;
			case SKULL_ITEM:
				p.closeInventory();
				p.sendMessage(Stuff.PLUGIN_TAG + ChatColor.RED + " Tu as choisi le Mage Sombre !");
				if (WizardUtils.isAWizard(p)){
					WizardUtils.removeWizard(p);
					WizardUtils.addWizard(p, WizardType.TENEBRO.getID());
				} else
					WizardUtils.addWizard(p, WizardType.TENEBRO.getID());
				p.getInventory().setItem(8, Stuff.itemKitTenebro);
				break;
			case NAME_TAG:
				//Ne fait rien, permet juste d'éviter l'erreur
				break;
			case AIR:
				//Ne fait rien, permet juste d'éviter l'erreur
				break;
			default:
				p.sendMessage(ChatColor.DARK_RED + "TU AS CLIQUé SUR UN ITEM INEXISTANT ?!?!");
				break;
			}
			WardenRevolt.getInstance().getScoreboard().updateValue(1, ChatColor.BLUE+"Wizards "+ChatColor.RESET+": "+ChatColor.YELLOW+WizardUtils.getAllWizards().size()+" / 5");
		} else if(e.getInventory().getType().equals(InventoryType.CRAFTING) && !p.getGameMode().equals(GameMode.CREATIVE))
			e.setCancelled(true);
	}

}

