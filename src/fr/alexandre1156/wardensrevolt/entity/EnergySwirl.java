package fr.alexandre1156.wardensrevolt.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import net.minecraft.server.v1_11_R1.EntityEvoker;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.GenericAttributes;
import net.minecraft.server.v1_11_R1.IRangedEntity;
import net.minecraft.server.v1_11_R1.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_11_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_11_R1.World;

public class EnergySwirl extends EntityEvoker implements IRangedEntity{

	@SuppressWarnings("rawtypes")
	public EnergySwirl(World world) {
		super(world);
		
		HashSet goalB = (HashSet) Utils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
		HashSet goalC = (HashSet) Utils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
		HashSet targetB = (HashSet) Utils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
		HashSet targetC = (HashSet) Utils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
		
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 1.0, 60, 3.0f));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 0.6));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 3.0f, 1.0f));
        this.goalSelector.a(4, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0f));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, EntityEvoker.class));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true).b(300));
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(60f);
	}

	
	@Override
	public void a(EntityLiving entLiv, float arg1) {
		Location loc = new Location(ConfigList.world, locX, locY ,locZ);
		Collection<Entity> ents = ConfigList.world.getNearbyEntities(loc, 4, 4, 4);
		Iterator<Entity> iter = ents.iterator();
		while(iter.hasNext()){
			Entity ent = iter.next();
			if(ent instanceof Player)
				((Player) ent).damage(6d);
		}
	}
	
	

}
