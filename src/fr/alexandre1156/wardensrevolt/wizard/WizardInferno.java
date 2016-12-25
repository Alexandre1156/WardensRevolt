package fr.alexandre1156.wardensrevolt.wizard;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;
import net.minecraft.server.v1_11_R1.EnumParticle;

public class WizardInferno extends Wizard{
	
	private	PotionEffect fireResistance;

	public WizardInferno(CraftPlayer cp) {
		super(cp);
		inventoryItem = ItemCreator.createItem(Material.BLAZE_ROD)
				.setCustomName(ChatColor.RED+"Inferno")
				.addLineLore(ChatColor.GRAY+""+ChatColor.ITALIC+"Le mage du feu")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Déflagration de feu "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 15 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Boule de feu "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 3 secs de recharge")
				.addLineLore(ChatColor.DARK_RED+""+ChatColor.BOLD+"Saut flamboyant "+ChatColor.RESET+""+ChatColor.WHITE+">"+ChatColor.RESET+
						""+ChatColor.GREEN+" 10 secs de recharge")
				.build();
		inventoryItemPosition = 10;
		spellItemOne = SpellItemCreator.createItem(Material.BLAZE_ROD)
				.setCustomName(ChatColor.DARK_RED+"Deflagration de feu")
				.addDescription("Fais un tourbillon de feu dans une zone de 2x2")
				.addDamage(4f)
				.addDuration(5)
				.setCooldown(15)
				.build();
		spellItemTwo = SpellItemCreator.createItem(Material.FIREBALL)
				.setCustomName(ChatColor.RED+"Boule de feu")
				.addDescription("Envoie une boule de feu")
				.addDamage(2f)
				.setCooldown(3)
				.build();
		spellItemThree = SpellItemCreator.createItem(Material.BLAZE_POWDER)
				.setCustomName(ChatColor.RED+""+ChatColor.BOLD+"Saut Flamboyant")
				.addDescription("Fait un saut de 4 blocs et met en feu")
				.addDescription("Une zone de 4x4 à l'endroit du saut")
				.setCooldown(10)
				.build();
		fireResistance = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 45, 0);
	}

	@Override
	protected void useFirstSpell() {
		int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				Utils.drawFilledCircleParticle(2, WizardInferno.this.getLocation(), EnumParticle.FLAME, 15);
				Collection<Entity> ents = WizardInferno.this.getNearbyEntities(2, 2, 2);
				for(Entity ent : ents){
					if(!(ent instanceof Player) && ent instanceof LivingEntity)
						((LivingEntity) ent).damage(8d);
				}
			}
			
		}, 0, 20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(task);
			}
			
		}, 100);
	}

	@Override
	protected void useSecondSpell() {
		SmallFireball fireball = ConfigList.world.spawn(getEyeLocation(), SmallFireball.class);
		fireball.setDirection(WizardInferno.this.getEyeLocation().getDirection());
		fireball.setVelocity(WizardInferno.this.getEyeLocation().getDirection());
		fireball.setIsIncendiary(false);
		fireball.setYield(0);
		fireball.setShooter(WizardInferno.this);
		fireball.setBounce(true);
		//WizardInferno.this.launchProjectile(fireball, WizardInferno.this.getEyeLocation().getDirection());
	}

	@Override
	protected void useThirdSpell() {
		Location lastLoc = this.getLocation();
		WizardInferno.this.setVelocity(new Vector(this.getVelocity().getX(), 0.8f, this.getVelocity().getZ()));
		this.addPotionEffect(fireResistance);
		for(int i = 1; i <= 4; i++)
			this.fireCircle(lastLoc, i);
		Bukkit.getScheduler().scheduleSyncDelayedTask(WardenRevolt.getInstance(), new Runnable(){

			@Override
			public void run() {
				for(int i = 1; i <= 4; i++)
					WizardInferno.this.clearFire(lastLoc, i);
			}
			
		}, 40);
	}

	private void fireCircle(Location center, int radius){
		int x = 0, y = radius, d = y - 1; 
		while(y >= x){
			Location[] locs = new Location[8];
			locs[0] = new Location(ConfigList.world, x+center.getX(), center.getY()-1, y+center.getZ());
			locs[1] = new Location(ConfigList.world, y+center.getX(), center.getY()-1, x+center.getZ());
			locs[2] = new Location(ConfigList.world, -x+center.getX(), center.getY()-1, y+center.getZ());
			locs[3] = new Location(ConfigList.world, -y+center.getX(), center.getY()-1, x+center.getZ());
			locs[4] = new Location(ConfigList.world, x+center.getX(), center.getY()-1, -y+center.getZ());
			locs[5] = new Location(ConfigList.world, y+center.getX(), center.getY()-1, -x+center.getZ());
			locs[6] = new Location(ConfigList.world, -x+center.getX(), center.getY()-1, -y+center.getZ());
			locs[7] = new Location(ConfigList.world, -y+center.getX(), center.getY()-1, -x+center.getZ());
			for(int i = 0; i < locs.length; i++){
				if(locs[i].getBlock().getRelative(BlockFace.UP).getType() == Material.AIR)
					locs[i].getBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
			}
			if(d >= 2*x){
				d -= 2*x+1;
				x++;
			} else if(d < 2 * (radius - y)) {
				d += 2*y-1;
				y--;
			} else {
				d += 2*(y-x-1);
				y--;
				x++;
			}
		}
	}
	
	private void clearFire(Location center, int radius){
		int x = 0, y = radius, d = y - 1; 
		while(y >= x){
			Location[] locs = new Location[8];
			locs[0] = new Location(ConfigList.world, x+center.getX(), center.getY()-1, y+center.getZ());
			locs[1] = new Location(ConfigList.world, y+center.getX(), center.getY()-1, x+center.getZ());
			locs[2] = new Location(ConfigList.world, -x+center.getX(), center.getY()-1, y+center.getZ());
			locs[3] = new Location(ConfigList.world, -y+center.getX(), center.getY()-1, x+center.getZ());
			locs[4] = new Location(ConfigList.world, x+center.getX(), center.getY()-1, -y+center.getZ());
			locs[5] = new Location(ConfigList.world, y+center.getX(), center.getY()-1, -x+center.getZ());
			locs[6] = new Location(ConfigList.world, -x+center.getX(), center.getY()-1, -y+center.getZ());
			locs[7] = new Location(ConfigList.world, -y+center.getX(), center.getY()-1, -x+center.getZ());
			for(int i = 0; i < locs.length; i++){
				if(locs[i].getBlock().getRelative(BlockFace.UP).getType() == Material.FIRE)
					locs[i].getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
			}
			if(d >= 2*x){
				d -= 2*x+1;
				x++;
			} else if(d < 2 * (radius - y)) {
				d += 2*y-1;
				y--;
			} else {
				d += 2*(y-x-1);
				y--;
				x++;
			}
		}
	}
	
	@Override
	public WizardType getWizardType() {
		return WizardType.INFERNO;
	}

	@Override
	public int getWizardTypeID() {
		return 2;
	}

}
