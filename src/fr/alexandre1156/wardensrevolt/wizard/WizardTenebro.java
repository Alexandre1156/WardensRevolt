package fr.alexandre1156.wardensrevolt.wizard;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;

import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;

public class WizardTenebro extends Wizard{

	public WizardTenebro(CraftPlayer cp) {
		super(cp);
		inventoryItem = ItemCreator.createItem(Material.SKULL_ITEM, (short) 3)
				.setCustomName(ChatColor.BLACK+"Tenebro")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"La mage sombre")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tornade noire "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Teleportation "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 25 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Lame sombre "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 2 sec de recharge")
				.build();
		inventoryItemPosition = 16;
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
		return WizardType.TENEBRO;
	}

	@Override
	public int getWizardTypeID() {
		return 5;
	}

}
