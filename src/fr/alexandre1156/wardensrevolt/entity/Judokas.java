package fr.alexandre1156.wardensrevolt.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntityZombieHusk;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.GenericAttributes;
import net.minecraft.server.v1_11_R1.IRangedEntity;
import net.minecraft.server.v1_11_R1.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_11_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_11_R1.World;

public class Judokas extends EntityZombieHusk implements IRangedEntity {

	@SuppressWarnings("rawtypes")
	public Judokas(World arg0) {
		super(arg0);
		
		HashSet goalB = (HashSet) Utils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
		HashSet goalC = (HashSet) Utils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
		HashSet targetB = (HashSet) Utils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
		HashSet targetC = (HashSet) Utils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
		
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 1.0, 60, 3.0f));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(5, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
        
    	this.setCustomName(Stuff.JUDOKAS_TAG);
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0);
	}

	private BukkitTask task;
	private boolean alreadyStarted;
	private Vector vect = new Vector(0, 0.8, 0);
	
	@Override
	public void a(EntityLiving ent, float arg1) {
		this.motY = 0.9;
		if(!alreadyStarted){
			alreadyStarted = true;
			task = Bukkit.getScheduler().runTaskTimer(WardenRevolt.getInstance(), new Runnable(){
	
				@Override
				public void run() {
					if(onGround){
						Location loc = new Location(ConfigList.world, locX, locY ,locZ);
						Utils.spawnParticle(EnumParticle.BLOCK_DUST, true, loc, 2f, 0.1f, 2f, 0.1f, 60, null, 3);
						Collection<Entity> ents = ConfigList.world.getNearbyEntities(loc, 3, 3, 3);
						Iterator<Entity> iter = ents.iterator();
						while(iter.hasNext()){
							Entity ent = iter.next();
							if(ent instanceof Player)
								((Player) ent).setVelocity(vect);
						}
						alreadyStarted = false;
						task.cancel();
					}
				}
				
			}, 1, 2);
		}
	}

}
