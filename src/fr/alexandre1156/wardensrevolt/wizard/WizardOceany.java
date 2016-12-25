package fr.alexandre1156.wardensrevolt.wizard;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;

import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;

public class WizardOceany extends Wizard{

	public WizardOceany(CraftPlayer cp) {
		super(cp);
		inventoryItem = ItemCreator.createItem(Material.WATER_BUCKET)
				.setCustomName(ChatColor.DARK_BLUE+"Oceany")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"La magicienne de l'eau")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Vague dévastatrice "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 15 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"HydroCanon "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 6 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"HydroNinja "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.build();
		inventoryItemPosition = 11;
	}

	@Override
	public void giveKitItems() {
		
	}

	@Override
	protected void useFirstSpell() {
		
	}

	@Override
	protected void useSecondSpell() {
		
	}

	@Override
	protected void useThirdSpell() {
		
	}

	@Override
	public WizardType getWizardType() {
		return WizardType.OCEANY;
	}

	@Override
	public int getWizardTypeID() {
		return 3;
	}

}
