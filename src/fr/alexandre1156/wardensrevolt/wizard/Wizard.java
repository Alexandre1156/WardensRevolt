package fr.alexandre1156.wardensrevolt.wizard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

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

	public Wizard(CraftPlayer cp, WizardType wizardType) {
		super((CraftServer) Bukkit.getServer(), cp.getHandle());
		this.type = wizardType;
		if(itemCooldown == null)
			itemCooldown = new ItemCooldown();
		itemCooldown.addWizard(getUniqueId());
		listener = new WizardListener(this);
		WardenRevolt.getInstance().getServer().getPluginManager().registerEvents(listener, WardenRevolt.getInstance());
	}
	
	public WizardType getWizardType() {
		return type;
	}
	
	public void setWizardType(WizardType type){
		this.type = type;
	}

	public void switchDiamondActived() {
		int i = this.getInventory().first(Material.DIAMOND);
		ItemStack is = this.getInventory().getItem(i);
		boolean diamondCooldown = this.itemCooldown.isItemHasCooldown(getUniqueId(), is);
		if(!diamondCooldown && !diamondActived){
			diamondActived = true;
			is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			WizardUtils.hasActivedTheDiamond();
			Bukkit.broadcastMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+this.getName()+ChatColor.RESET+""+ChatColor.BLUE+" veut utiliser l'Anti-Cheat ! "
					+ChatColor.RESET+" "+ChatColor.GRAY+"("+WizardUtils.getNumberOfDiamondActived()+"/"+Bukkit.getOnlinePlayers().size()+")");
		} else if(!diamondCooldown && diamondActived){
			diamondActived = false;
			is.removeEnchantment(Enchantment.ARROW_DAMAGE);
			WizardUtils.hasDesactivedTheDiamond();
			Bukkit.broadcastMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+this.getName()+ChatColor.RESET+""+ChatColor.RED+" ne veut plus utiliser l'Anti-Cheat. "
					+ChatColor.RESET+" "+ChatColor.GRAY+"("+WizardUtils.getNumberOfDiamondActived()+"/"+Bukkit.getOnlinePlayers().size()+")");
		}
		this.itemCooldown.addItemCooldown(getUniqueId(), is, 2);
	}
	
	public boolean isDiamondActived(){
		return diamondActived;
	}
	
	public abstract void consumneKitItem(int itemPos);
	
	public abstract void giveKitItems();
	
	protected abstract void onItemCooldownFinish(ItemCooldownFinishEvent e);
	
	protected abstract void useFirstSpell();
	
	protected abstract void useSecondSpell();
	
	protected abstract void useThirdSpell();
	
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
