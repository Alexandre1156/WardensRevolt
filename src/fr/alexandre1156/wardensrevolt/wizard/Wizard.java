package fr.alexandre1156.wardensrevolt.wizard;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.event.custom.ItemCooldownFinishEvent;
import fr.alexandre1156.wardensrevolt.scheduler.ItemCooldown;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;

@SuppressWarnings("static-access")
public abstract class Wizard extends CraftPlayer implements Listener{
	
	protected WizardType type;
	protected boolean diamondActived;
	protected static ItemCooldown itemCooldown;
	protected WizardListener listener;
	protected Random random;
	public ItemStack inventoryItem;
	public int inventoryItemPosition;
	protected SpellItem spellItemOne;
	protected SpellItem spellItemTwo;
	protected SpellItem spellItemThree;

	protected Wizard(CraftPlayer cp) {
		super((CraftServer) Bukkit.getServer(), cp.getHandle());
		this.type = getWizardType();
		if(itemCooldown == null)
			itemCooldown = new ItemCooldown();
		itemCooldown.addWizard(getUniqueId());
		listener = new WizardListener(this);
		WardenRevolt.getInstance().getServer().getPluginManager().registerEvents(listener, WardenRevolt.getInstance());
		random = new Random();
	}

	public final void switchDiamondActived() {
		int i = this.getInventory().first(Material.DIAMOND);
		ItemStack is = this.getInventory().getItem(i);
		boolean diamondCooldown = this.itemCooldown.isItemHasCooldown(getUniqueId(), is);
		if(!diamondCooldown && !diamondActived){
			diamondActived = true;
			is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			WizardUtils.hasActivedTheDiamond();
			Bukkit.broadcastMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" "+this.getName()+ChatColor.RESET+""+ChatColor.BLUE+" veut utiliser l'Anti-Cheat ! "
					+ChatColor.RESET+" "+ChatColor.GRAY+"("+WizardUtils.getNumberOfDiamondActived()+"/"+Bukkit.getOnlinePlayers().size()+")");
		} else if(!diamondCooldown && diamondActived){
			diamondActived = false;
			is.removeEnchantment(Enchantment.ARROW_DAMAGE);
			WizardUtils.hasDesactivedTheDiamond();
			Bukkit.broadcastMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" "+this.getName()+ChatColor.RESET+""+ChatColor.RED+" ne veut plus utiliser l'Anti-Cheat. "
					+ChatColor.RESET+" "+ChatColor.GRAY+"("+WizardUtils.getNumberOfDiamondActived()+"/"+Bukkit.getOnlinePlayers().size()+")");
		}
		this.itemCooldown.addItemCooldown(getUniqueId(), is, 2);
	}
	
	public final boolean isDiamondActived(){
		return diamondActived;
	}
	
	public final void consumneKitItem(int itemPos){
		if(itemPos == 1 || itemPos == 2 || itemPos == 3)
			this.getInventory().getItem(itemPos).removeEnchantment(Enchantment.ARROW_DAMAGE);
		switch(itemPos){
		case 1:
			if(!itemCooldown.isItemHasCooldown(getUniqueId(), this.getInventory().getItem(1))){
				this.itemCooldown.addItemCooldown(getUniqueId(), this.getInventory().getItem(1), spellItemOne.getCooldown());
				this.useFirstSpell();
			}else
				this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" Attends encore "+itemCooldown.getCooldownLeft(getUniqueId(), this.getInventory().getItem(1))+" seconde(s) !");
			break;
		case 2:
			if(!itemCooldown.isItemHasCooldown(getUniqueId(), this.getInventory().getItem(2))){
				this.itemCooldown.addItemCooldown(getUniqueId(), this.getInventory().getItem(2), spellItemTwo.getCooldown());
				this.useSecondSpell();
			} else
				this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" Attends encore "+itemCooldown.getCooldownLeft(getUniqueId(), this.getInventory().getItem(2))+" seconde(s) !");
			break;
		case 3:
			if(!itemCooldown.isItemHasCooldown(getUniqueId(), this.getInventory().getItem(3))) {
				this.itemCooldown.addItemCooldown(getUniqueId(), this.getInventory().getItem(3), spellItemThree.getCooldown());
				this.useThirdSpell();
			} else
				this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" Attends encore "+itemCooldown.getCooldownLeft(getUniqueId(), this.getInventory().getItem(3))+" seconde(s) !");
			break;
		default:
			//Do nothing
		}
	}
	
	public void giveKitItems(){
		spellItemOne.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		spellItemTwo.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		spellItemThree.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		ItemMeta meta = spellItemOne.getItemMeta();
		ItemMeta meta2 = spellItemTwo.getItemMeta();
		ItemMeta meta3 = spellItemThree.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta3.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		spellItemOne.setItemMeta(meta);
		spellItemTwo.setItemMeta(meta2);
		spellItemThree.setItemMeta(meta3);
		this.getInventory().setItem(1, spellItemOne);
		this.getInventory().setItem(2, spellItemTwo);
		this.getInventory().setItem(3, spellItemThree);
	}
	
	protected final void onItemCooldownFinish(ItemCooldownFinishEvent e){
		if(e.getUuid().equals(this.getUniqueId()))
			e.getItem().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
	}
	
	protected abstract void useFirstSpell();
	
	protected abstract void useSecondSpell();
	
	protected abstract void useThirdSpell();
	
	public WizardType getWizardType(){return null;}
	
	public abstract int getWizardTypeID();
	
	public class WizardListener implements Listener {
		private Wizard wiz;
		
		public WizardListener(Wizard wizard) {
			this.wiz = wizard;
		}
		
		@EventHandler
		public void onItemCooldownFinish(ItemCooldownFinishEvent e){
			wiz.onItemCooldownFinish(e);
		}
	}
	


}
