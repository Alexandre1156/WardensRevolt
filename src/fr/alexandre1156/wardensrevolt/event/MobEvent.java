package fr.alexandre1156.wardensrevolt.event;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.Witch;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Maps;

import fr.alexandre1156.wardensrevolt.Stuff;

public class MobEvent implements Listener{
	
	private PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 100, 0);
	private PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 100, 0);
	private HashMap<Zombie, Integer> zombieBurnBySpeel = Maps.newHashMap();
	private long lastTime = System.currentTimeMillis();

	@EventHandler
	public void onWitchDropPotion(ProjectileLaunchEvent e){
		if(e.getEntity() instanceof SplashPotion)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityIsHitByEntity(EntityDamageByEntityEvent e){
		Entity damager = e.getDamager();
		Entity victim = e.getEntity();
		if(victim instanceof Player){
			Player p = (Player) victim;
			if(damager instanceof WitherSkeleton && damager.getCustomName() != null && damager.getCustomName().equals(Stuff.BLINDING_TAG)){
				p.getActivePotionEffects().clear();
				p.addPotionEffect(slow);
				p.addPotionEffect(blind);
				p.damage(2d);
			} else if(damager instanceof Zombie && damager.getCustomName() != null && damager.getCustomName().equals(Stuff.ZOMBIE_TAG))
				p.damage(8d);
			else if(damager instanceof Witch && damager.getCustomName() != null && damager.getCustomName().equals(Stuff.WIZARD_TAG))
				p.setVelocity(p.getLocation().getDirection().multiply(-1));
			else if(damager instanceof WitherSkeleton && damager.getCustomName() != null && (damager.getCustomName().equals(Stuff.WARDEN_TAG+" 1") ||
					damager.getCustomName().equals(Stuff.WARDEN_TAG+" 2") ||
					damager.getCustomName().equals(Stuff.WARDEN_TAG+" 3") ||
					damager.getCustomName().equals(Stuff.WARDEN_TAG+" 4")))
				p.damage(8d);
			else if(damager instanceof LightningStrike)
				p.damage(12d);
			e.setCancelled(true);
		} else if(damager instanceof Fireball && ((Fireball) damager).getShooter() instanceof Player && victim instanceof LivingEntity){
			e.setCancelled(true);
			((LivingEntity) victim).damage(2d);
		}
	}
	
	@EventHandler
	public void onPlayerManipulateArmorStand(PlayerArmorStandManipulateEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onZombieBurn(EntityCombustEvent e){
		long currentTime = System.currentTimeMillis();
		if(currentTime >= (lastTime + 1*1000)){ //Si 1 seconde est passé
			lastTime = System.currentTimeMillis();
			Iterator<Zombie> zombies = zombieBurnBySpeel.keySet().iterator();
			while(zombies.hasNext()){
				Zombie zombie = zombies.next();
				zombieBurnBySpeel.put(zombie, zombieBurnBySpeel.get(zombie)-1);
				if(zombieBurnBySpeel.get(zombie) == 0){
					zombie.setFireTicks(0);
					zombies.remove();
				}
			}
		}
		if(e.getEntity() instanceof Zombie){
			Zombie zombie = (Zombie) e.getEntity();
			if(zombie.getLocation().getBlock().getType() == Material.FIRE){
				this.zombieBurnBySpeel.put(zombie, 8);
				zombie.setFireTicks(9999);
			}
			if(!zombieBurnBySpeel.containsKey(zombie.getUniqueId()))
				e.setCancelled(true);
		}
	}
	
}
