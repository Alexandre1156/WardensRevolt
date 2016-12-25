package fr.alexandre1156.wardensrevolt.wizard;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpellItem extends ItemStack {
	
	private int cooldown;
	
	public SpellItem(Material material) {
		super(material);
	}
	
	public SpellItem(Material material, short damage) {
		super(material, 1, damage);
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
}
