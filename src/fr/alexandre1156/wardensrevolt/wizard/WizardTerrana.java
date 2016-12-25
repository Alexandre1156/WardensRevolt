package fr.alexandre1156.wardensrevolt.wizard;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;

import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;

public class WizardTerrana extends Wizard{

	public WizardTerrana(CraftPlayer cp) {
		super(cp);
		inventoryItem = ItemCreator.createItem(Material.DIRT)
				.setCustomName(ChatColor.GRAY+"Terrana")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"La magicienne de la terre")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tremblement de terre ultime "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 20 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Effondrement "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 10 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Explosion sismique "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
				""+ChatColor.GREEN+" 10 secs de recharge")
				.build();
		inventoryItemPosition = 12;
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
		return WizardType.TERRANA;
	}

	@Override
	public int getWizardTypeID() {
		return 6;
	}

}
