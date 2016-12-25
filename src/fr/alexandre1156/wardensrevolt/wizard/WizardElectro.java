package fr.alexandre1156.wardensrevolt.wizard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;
import net.minecraft.server.v1_11_R1.EnumParticle;

public class WizardElectro extends Wizard{
	
	private PotionEffect speedEffect;

	public WizardElectro(CraftPlayer cp) {
		super(cp);
		speedEffect = new PotionEffect(PotionEffectType.SPEED, 100, 4);
		inventoryItem = ItemCreator.createItem(Material.NETHER_STAR)
				.setCustomName(ChatColor.YELLOW+"Electro")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage du tonnerre")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Tempête "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 15 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Éclair "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 5 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Sprint "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 20 secs de recharge")
				.build();
		inventoryItemPosition = 13;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void giveKitItems() {
		Wool wool = new Wool(DyeColor.YELLOW);
		spellItemOne = SpellItemCreator.createItem(wool.getItemType(), wool.getData())
				.setCustomName(ChatColor.YELLOW+"Tempête")
				.addDescription("Fais tomber deux éclairs sur deux monstres au hasard ")
				.addDamage(4)
				.setCooldown(15)
				.build();
		spellItemTwo = SpellItemCreator.createItem(Material.DOUBLE_PLANT)
				.setCustomName(ChatColor.YELLOW+""+ChatColor.BOLD+"Eclair")
				.addDescription("Fais tomber un éclair sur le monstre")
				.addDescription("touché par le rayon que vous lancez")
				.addDamage(2)
				.setCooldown(5)
				.build();
		spellItemThree = SpellItemCreator.createItem(Material.FEATHER)
				.setCustomName(ChatColor.BLUE+"Sprint")
				.addDescription("Vous donne du Speed 5")
				.addDuration(5)
				.setCooldown(20)
				.build();
		
		super.giveKitItems();
	}

	@Override
	protected void useFirstSpell() {
		ArrayList<LivingEntity> ents = new ArrayList<>();
		for(LivingEntity ent : ConfigList.world.getLivingEntities()){
			if(ent.getCustomName() != null){
				if(Utils.isMobTagExist(ent.getCustomName()))
					ents.add(ent);
			}
		}
		if(!ents.isEmpty()){
			int firstVictim = random.nextInt(ents.size());
			if(ents.size() >= 2){
				int secondVictim = random.nextInt(ents.size());
				while(secondVictim == firstVictim)
					secondVictim = random.nextInt(ents.size());
				ents.get(secondVictim).damage(8);
				ConfigList.world.strikeLightningEffect(ents.get(secondVictim).getLocation());
			}
			ents.get(firstVictim).damage(8);
			ConfigList.world.strikeLightningEffect(ents.get(firstVictim).getLocation());
		} else {
			this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" Il n'y a aucun ennemis à toucher !");
		}
	}

	@Override
	protected void useSecondSpell() {
		List<Block> blocks = this.getLineOfSight((Set<Material>)null, 100);
		//Utils.consoleMessage(blocks.size()+" $");
		Iterator<Block> iter = blocks.iterator();
		loop: {
			while(iter.hasNext()){
				Block block = iter.next();
				if(block.isEmpty()){
					Utils.spawnParticle(EnumParticle.BLOCK_DUST, true, block.getLocation(), 0.1f, 0.1f, 0.1f, 0.1f, 10, null, 41);
					for(Entity ent : ConfigList.world.getNearbyEntities(block.getLocation(), 1, 1, 1)){
						if(ent.getCustomName() != null){
							if(Utils.isMobTagExist(ent.getCustomName())){
								if(ent instanceof LivingEntity){
									((LivingEntity) ent).damage(4);
									ConfigList.world.strikeLightningEffect(((LivingEntity) ent).getLocation());
								}
								break loop;
							}
						}
					}
				} else {
					this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" Aucune cible !");
					break loop;
				}
			}
			this.sendMessage(Stuff.PLUGIN_TAG+ChatColor.YELLOW+" Aucune cible !");
		}
	}

	@Override
	protected void useThirdSpell() {
		this.addPotionEffect(speedEffect);
	}
	
	@Override
	public int getWizardTypeID() {
		return 0;
	}

	@Override
	public WizardType getWizardType() {
		return WizardType.ELECTRO;
	}

}
