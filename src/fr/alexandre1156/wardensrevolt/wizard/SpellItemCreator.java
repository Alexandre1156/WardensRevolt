package fr.alexandre1156.wardensrevolt.wizard;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import fr.alexandre1156.wardensrevolt.utils.Utils;

public class SpellItemCreator {

	public static Builder createItem(Material material) {
		return new SpellItemCreator().new Builder(material);
	}
	
	public static Builder createItem(Material material, short data) {
		return new SpellItemCreator().new Builder(material, data);
	}
	
	public class Builder {
		
		private SpellItem item;
		private ItemMeta meta;
		private ArrayList<String> lore;
		private String description[];
		private float damage;
		private int time;
		private short data;
		
		public Builder(Material material) {
			item = new SpellItem(material);
			meta = item.getItemMeta();
			lore = Lists.newArrayList();
			data = 0;
			damage = 0;
			time = 0;
			description = new String[3];
		}
		
		public Builder(Material material, short data) {
			item = new SpellItem(material, data);
			meta = item.getItemMeta();
			lore = Lists.newArrayList();
			this.data = data;
			damage = 0;
			time = 0;
			description = new String[3];
		}
		
		public Builder setCustomName(String customName){
			meta.setDisplayName(customName);
			return this;
		}
		
		public Builder addDescription(String text){
			if(description[2] != null)
				Utils.consoleMessage("La description du SpeelItem ("+item.getType()+") ne doit pas exceder 3 lignes !", ChatColor.YELLOW);
			else if(description[0] == null)
				description[0] = ChatColor.WHITE+text;
			else if(description[1] == null)
				description[1] = ChatColor.WHITE+text;
			else if(description[2] == null)
				description[2] = ChatColor.WHITE+text;
			return this;
		}
		
		public Builder addDamage(float damage){
			this.damage = damage;
			return this;
		}
		
		public Builder addDuration(int duration){
			this.time = duration;
			return this;
		}
		
		public Builder setCooldown(int cooldown){
			item.setCooldown(cooldown);
			return this;
		}
		
		public SpellItem build(){
			for(int i = 0; i < description.length; i++){
				if(description[i] != null)
					lore.add(description[i]);
			}
			if(damage != 0)
				lore.add(ChatColor.RED+"Dégats : "+String.valueOf(damage)+" Coeurs");
			if(time != 0)
				lore.add(ChatColor.GREEN+"Durée : "+String.valueOf(time)+" secs");
			if(item.getCooldown() != 0)
				lore.add(ChatColor.GRAY+"Temps de recharge : "+String.valueOf(item.getCooldown())+" secs");
			else
				Utils.consoleMessage("Le cooldown du SpeelItem "+item.getType()+" est egal a 0 !", ChatColor.RED);
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}
		
	}
	
}
