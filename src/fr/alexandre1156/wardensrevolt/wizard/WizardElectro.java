package fr.alexandre1156.wardensrevolt.wizard;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.event.custom.ItemCooldownFinishEvent;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;

public class WizardElectro extends Wizard{
	
	private PotionEffect speedEffect;

	public WizardElectro(CraftPlayer cp) {
		super(cp, WizardType.ELECTRO);
		speedEffect = new PotionEffect(PotionEffectType.SPEED, 5, 4);
	}

	@SuppressWarnings("static-access")
	@Override
	public void consumneKitItem(int itemPos) {
		this.getInventory().getItem(itemPos).removeEnchantment(Enchantment.ARROW_DAMAGE);
		switch(itemPos){
		case 1:
			if(!itemCooldown.isItemHasCooldown(getUniqueId(), this.getInventory().getItem(1)))
				this.itemCooldown.addItemCooldown(getUniqueId(), this.getInventory().getItem(1), 15);
			else
				this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+"Attends encore "+itemCooldown.getCooldownLeft(getUniqueId(), this.getInventory().getItem(1))+" seconde(s) !");
			this.useFirstSpell();
			break;
		case 2:
			if(!itemCooldown.isItemHasCooldown(getUniqueId(), this.getInventory().getItem(2)))
				this.itemCooldown.addItemCooldown(getUniqueId(), this.getInventory().getItem(2), 5);
			else
				this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+"Attends encore "+itemCooldown.getCooldownLeft(getUniqueId(), this.getInventory().getItem(2))+" seconde(s) !");
			this.useSecondSpell();
			break;
		case 3:
			if(!itemCooldown.isItemHasCooldown(getUniqueId(), this.getInventory().getItem(3)))
				this.itemCooldown.addItemCooldown(getUniqueId(), this.getInventory().getItem(3), 20);
			else
				this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+"Attends encore "+itemCooldown.getCooldownLeft(getUniqueId(), this.getInventory().getItem(3))+" seconde(s) !");
			this.useThirdSpell();
			break;
		}
	}

	@Override
	public void giveKitItems() {
		Wool wool = new Wool(DyeColor.YELLOW);
		ItemStack storm = wool.toItemStack(1);
		ItemMeta stormMeta = storm.getItemMeta();
		stormMeta.setDisplayName(ChatColor.YELLOW+"Tempête");
		stormMeta.setLore(Arrays.asList(new String[] {ChatColor.WHITE+"Fais tomber deux éclairs sur deux monstres au hasard ", ChatColor.RED+"Dégats : 4 Coeurs", ChatColor.GRAY+"Temps de recharge : 15 secs"}));
		stormMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		storm.setItemMeta(stormMeta);
		storm.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		
		ItemStack thunderBolt = new ItemStack(Material.DOUBLE_PLANT);
		ItemMeta boltMeta = thunderBolt.getItemMeta();
		boltMeta.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"Eclair");
		boltMeta.setLore(Arrays.asList(new String[] {ChatColor.WHITE+"Fais tomber un éclair sur le monstre", ChatColor.WHITE+"touché par le rayon que vous lancez", ChatColor.RED+"Dégats : 2 Coeurs", ChatColor.GRAY+"Temps de recharge : 5 secs"}));
		boltMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		thunderBolt.setItemMeta(boltMeta);
		thunderBolt.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		
		ItemStack speed = new ItemStack(Material.FEATHER);
		ItemMeta speedMeta = speed.getItemMeta();
		speedMeta.setDisplayName(ChatColor.BLUE+"Sprint");
		speedMeta.setLore(Arrays.asList(new String[] {ChatColor.BLUE+"Vous donne du Speed 5", ChatColor.GREEN+"Durée : 5 secs", ChatColor.GRAY+"Temps de recharge : 20 secs"}));
		speedMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		speed.setItemMeta(speedMeta);
		speed.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		
		this.getInventory().setItem(1, storm);
		this.getInventory().setItem(2, thunderBolt);
		this.getInventory().setItem(3, speed);
	}

	@Override
	protected void onItemCooldownFinish(ItemCooldownFinishEvent e) {
		if(e.getUuid().equals(this.getUniqueId())){
			e.getItem().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
	}

	@Override
	protected void useFirstSpell() {
		
	}

	@Override
	protected void useSecondSpell() {
		
	}

	@Override
	protected void useThirdSpell() {
		this.addPotionEffect(speedEffect);
	}

}
