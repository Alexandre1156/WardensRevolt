package fr.alexandre1156.wardensrevolt.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MobEvent implements Listener{
	
	private PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 100, 0);
	private PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 100, 0);

	@EventHandler
	public void onWitchDropPotion(ProjectileLaunchEvent e){
		if(e.getEntity() instanceof SplashPotion)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerIsHitByEntity(EntityDamageByEntityEvent e){
		Entity damager = e.getDamager();
		Entity victim = e.getEntity();
		if(victim instanceof Player){
			Player p = (Player) victim;
			if(damager instanceof Skeleton){
				e.setCancelled(true);
				p.getActivePotionEffects().clear();
				p.addPotionEffect(slow);
				p.addPotionEffect(blind);
				p.damage(2);
			} else if(damager instanceof Zombie){
				e.setCancelled(true);
				p.damage(8);
			} else if(damager instanceof Witch){
				e.setCancelled(true);
				p.setVelocity(p.getLocation().getDirection().multiply(-1));
			}
		}
	}
	
	@EventHandler
	public void onZombieBurn(EntityCombustEvent e){
		if(e.getEntity() instanceof Zombie)
			e.setCancelled(true);
	}
	
}
