package fr.alexandre1156.wardensrevolt.wizard;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;

public class WizardSesterno extends Wizard {

	public WizardSesterno(CraftPlayer cp) {
		super(cp);
		inventoryItem = ItemCreator.createItem(Material.SAPLING)
				.setCustomName(ChatColor.GREEN+"Sesterno")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"La mage de la nature")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Racines "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Lianes "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 6 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tranch'herbe "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 1 sec de recharge")
				.build();
		inventoryItemPosition = 15;
	}

	@Override
	public void giveKitItems() {
		ItemStack itemOne = new ItemStack(Material.SAPLING);
		ItemMeta itemOneMeta = itemOne.getItemMeta();
		itemOneMeta.setDisplayName(ChatColor.GREEN+"Racines");
		itemOneMeta.setLore(Arrays.asList(new String[]{""}));
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
		return WizardType.SYSTERNO;
	}

	@Override
	public int getWizardTypeID() {
		return 4;
	}

}
