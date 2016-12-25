package fr.alexandre1156.wardensrevolt.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

public class ItemCreator {
	
	public static Builder createItem(Material material) {
		return new ItemCreator().new Builder(material);
	}
	
	public static Builder createItem(Material material, short data) {
		return new ItemCreator().new Builder(material, data);
	}
	
	public class Builder {
		
		private ItemStack item;
		private ItemMeta meta;
		private ArrayList<String> lore;
		private short data;
		
		public Builder(Material material) {
			item = new ItemStack(material);
			meta = item.getItemMeta();
			lore = Lists.newArrayList();
			data = 0;
		}
		
		public Builder(Material material, short data) {
			item = new ItemStack(material, 1, data);
			meta = item.getItemMeta();
			lore = Lists.newArrayList();
			this.data = data;
		}
		
		public Builder setCustomName(String customName){
			meta.setDisplayName(customName);
			return this;
		}
		
		public Builder addLineLore(String text){
			lore.add(text);
			return this;
		}
		
		public ItemStack build(){
			if(!lore.isEmpty())
				meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}
		
	}
	
}
