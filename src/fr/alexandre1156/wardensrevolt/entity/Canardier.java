package fr.alexandre1156.wardensrevolt.entity;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_11_R1.event.CraftEventFactory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.utils.ItemCreator;
import fr.alexandre1156.wardensrevolt.utils.Utils;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.ControllerMove;
import net.minecraft.server.v1_11_R1.EntityArrow;
import net.minecraft.server.v1_11_R1.EntityCreature;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntityTippedArrow;
import net.minecraft.server.v1_11_R1.EntityVex;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.IRangedEntity;
import net.minecraft.server.v1_11_R1.MathHelper;
import net.minecraft.server.v1_11_R1.PathfinderGoal;
import net.minecraft.server.v1_11_R1.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_11_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_11_R1.PathfinderGoalTarget;
import net.minecraft.server.v1_11_R1.SoundEffects;
import net.minecraft.server.v1_11_R1.Vec3D;
import net.minecraft.server.v1_11_R1.World;

public class Canardier extends EntityVex implements IRangedEntity{

	private EntityInsentient b;
	private ArmorStand as;
	
	@SuppressWarnings("rawtypes")
	public Canardier(World arg0) {
		super(arg0);
		
		HashSet goalB = (HashSet) Utils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
		HashSet goalC = (HashSet) Utils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
		HashSet targetB = (HashSet) Utils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
		HashSet targetC = (HashSet) Utils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
		
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 1.0, 60, 20.0f));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(5, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, true, EntityVex.class));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
        
        this.goalSelector.a(7, new PathA());
        this.goalSelector.a(8, new d());
        this.targetSelector.a(2, new b(this));
        
        this.moveController = new PathC(this);
        this.noclip = false;
        
        this.setInvisible(true);
        
        //this.setInvisible(true);
	}
	
//	public EntityInsentient o() {
//        return this.b;
//    }
	
	public void a(EntityInsentient entityinsentient) {
        this.b = entityinsentient;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setPosition(double d0, double d1, double d2) {
		super.setPosition(d0, d1, d2);
		this.as = null;
        if(ConfigList.world != null) {
        	as = ConfigList.world.spawn(new Location(ConfigList.world, locX, locY, locZ), ArmorStand.class);
	        ItemStack skull = ItemCreator.createItem(Material.SKULL_ITEM).setCustomName("CanardierASSkull").build();
	        ItemStack bow = ItemCreator.createItem(Material.BOW).setCustomName("CanardierASBow").build();
	        ItemStack chestPlate = ItemCreator.createItem(Material.LEATHER_CHESTPLATE).setCustomName("CanardierASChestplate").build();
	        ItemStack leggings = ItemCreator.createItem(Material.LEATHER_LEGGINGS).setCustomName("CanardierASLeggings").build();
	        ItemStack boots = ItemCreator.createItem(Material.LEATHER_BOOTS).setCustomName("CanardierASBoots").build();
	        as.setHelmet(skull);
	        as.setBoots(boots);
	        as.setLeggings(leggings);
	        as.setChestplate(chestPlate);
	        as.setItemInHand(bow);
	        as.setBasePlate(false);
			as.setArms(true);
			//as.setInvulnerable(true);
			as.setMaxHealth(60d);
			as.setHealth(60d);
			as.setMarker(true);
			as.setCustomName("CanardierAS");
			as.setHeadPose(new EulerAngle(0.5, 0, 0));
			as.setLeftLegPose(new EulerAngle(0.5, 0, 0));
			as.setRightLegPose(new EulerAngle(0.5, 0, 0));
        }
	}
	
	@Override
	public void die() {
		super.die();
		if(as != null)
			as.setHealth(0d);
	}
	
//	@Override
//	public void A_() {
//		super.A_();
//		if(as != null) {
//			as.setVelocity(new Location(ConfigList.world, locX, locY, locZ).subtract(as.getLocation()).toVector());
//			((CraftArmorStand) as).getHandle().yaw = this.yaw;
//			((CraftArmorStand) as).getHandle().pitch = this.pitch;
//		}
//	}
	
	
	@Override
	protected void checkBlockCollisions() {
		super.checkBlockCollisions();
		Utils.spawnParticle(EnumParticle.CLOUD, true, new Location(ConfigList.world, this.locX, locY, locZ), .2f, .2f, .2f, 0.1f, 20, null, null);
		if(as != null) {
			as.setVelocity(new Location(ConfigList.world, locX, locY, locZ).subtract(as.getLocation()).toVector());
			((CraftArmorStand) as).getHandle().yaw = this.yaw;
			((CraftArmorStand) as).getHandle().pitch = this.pitch;
		}
	}

	@Override
	public void a(EntityLiving entityliving, float f2) {
		EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, this);
        entitytippedarrow.a(this, f2);
		EntityArrow entityarrow = entitytippedarrow;
        double d0 = entityliving.locX - this.locX;
        double d1 = entityliving.getBoundingBox().b + (double)(entityliving.length / 3.0f) - entityarrow.locY;
        double d2 = entityliving.locZ - this.locZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224, d2, 1.6f, 14 - this.world.getDifficulty().a() * 4);
        EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(this, this.getItemInMainHand(), entityarrow, 0.8f);
        if (event.isCancelled()) {
            event.getProjectile().remove();
            return;
        }
        if (event.getProjectile() == entityarrow.getBukkitEntity()) {
            this.world.addEntity(entityarrow);
        }
        this.a(SoundEffects.fV, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		
        //FIXME La fleche shooter se prend dans l'armorstand - La position de l'AS est tjr la même
        //ConfigList.world.spawnArrow(new Location(ConfigList.world, this.locX, this.locY+1, this.lastZ), new Vector(d0, d1+d3* 0.20000000298023224, d2), 0.6f, 12f);
	}
	
	class PathA
    extends PathfinderGoal {
        public PathA() {
            this.a(1);
        }

        @Override
        public boolean a() {
            return Canardier.this.getGoalTarget() != null && !Canardier.this.getControllerMove().a() && Canardier.this.random.nextInt(7) == 0 ? Canardier.this.h(Canardier.this.getGoalTarget()) > 4.0 : false;
        }

        @Override
        public boolean b() {
            if (Canardier.this.getControllerMove().a() && Canardier.this.dj() && Canardier.this.getGoalTarget() != null && Canardier.this.getGoalTarget().isAlive()) {
                return true;
            }
            return false;
        }

        @Override
        public void c() {
            EntityLiving entityliving = Canardier.this.getGoalTarget();
            Vec3D vec3d = entityliving.g(1.0f);
            Canardier.this.moveController.a(vec3d.x, vec3d.y, vec3d.z, 1.0);
            Canardier.this.a(true);
            Canardier.this.a(SoundEffects.hd, 1.0f, 1.0f);
        }

        @Override
        public void d() {
            Canardier.this.a(false);
        }

        @Override
        public void e() {
            EntityLiving entityliving = Canardier.this.getGoalTarget();
            if (Canardier.this.getBoundingBox().c(entityliving.getBoundingBox())) {
                Canardier.this.B(entityliving);
                Canardier.this.a(false);
            } else {
                double d0 = Canardier.this.h(entityliving);
                if (d0 < 9.0) {
                    Vec3D vec3d = entityliving.g(1.0f);
                    Canardier.this.moveController.a(vec3d.x, vec3d.y, vec3d.z, 1.0);
                }
            }
        }
    }

	
	class b
    extends PathfinderGoalTarget {
        public b(EntityCreature entitycreature) {
            super(entitycreature, false);
        }

        @Override
        public boolean a() {
            if (Canardier.this.b != null && Canardier.this.b.getGoalTarget() != null && this.a(Canardier.this.b.getGoalTarget(), false)) {
                return true;
            }
            return false;
        }

        @Override
        public void c() {
            Canardier.this.setGoalTarget(Canardier.this.b.getGoalTarget(), EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET, true);
            super.c();
        }
    }

	class PathC
    extends ControllerMove {
        public PathC(EntityVex entityvex) {
            super(entityvex);
        }

        @Override
        public void c() {
            if (this.h == ControllerMove.Operation.MOVE_TO) {
                double d0 = this.b - Canardier.this.locX;
                double d1 = this.c - Canardier.this.locY;
                double d2 = this.d - Canardier.this.locZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if ((d3 = (double)MathHelper.sqrt(d3)) < Canardier.this.getBoundingBox().a()) {
                    this.h = ControllerMove.Operation.WAIT;
                    Canardier.this.motX *= 0.5;
                    Canardier.this.motY *= 0.5;
                    Canardier.this.motZ *= 0.5;
                } else {
                    Canardier.this.motX += d0 / d3 * 0.05 * this.e;
                    Canardier.this.motY += d1 / d3 * 0.05 * this.e;
                    Canardier.this.motZ += d2 / d3 * 0.05 * this.e;
                    if (Canardier.this.getGoalTarget() == null) {
                        Canardier.this.aN = Canardier.this.yaw = (- (float)MathHelper.c(Canardier.this.motX, Canardier.this.motZ)) * 57.295776f;
                    } else {
                        double d4 = Canardier.this.getGoalTarget().locX - Canardier.this.locX;
                        double d5 = Canardier.this.getGoalTarget().locZ - Canardier.this.locZ;
                        Canardier.this.aN = Canardier.this.yaw = (- (float)MathHelper.c(d4, d5)) * 57.295776f;
                    }
                }
            }
        }
    }

    class d extends PathfinderGoal {
        public d() {
            this.a(1);
        }

        @Override
        public boolean a() {
            if (!Canardier.this.getControllerMove().a() && Canardier.this.random.nextInt(7) == 0) {
                return true;
            }
            return false;
        }

        @Override
        public boolean b() {
            return false;
        }

        @Override
        public void e() {
            BlockPosition blockposition = Canardier.this.di();
            if (blockposition == null) {
                blockposition = new BlockPosition(Canardier.this);
            }
            int i2 = 0;
            while (i2 < 3) {
                BlockPosition blockposition1 = blockposition.a(Canardier.this.random.nextInt(15) - 7, Canardier.this.random.nextInt(11) - 5, Canardier.this.random.nextInt(15) - 7);
                if (Canardier.this.world.isEmpty(blockposition1)) {
                    Canardier.this.moveController.a((double)blockposition1.getX() + 0.5, (double)blockposition1.getY() + 0.5, (double)blockposition1.getZ() + 0.5, 0.25);
                    if (Canardier.this.getGoalTarget() != null) break;
                    Canardier.this.getControllerLook().a((double)blockposition1.getX() + 0.5, (double)blockposition1.getY() + 0.5, (double)blockposition1.getZ() + 0.5, 180.0f, 20.0f);
                    break;
                }
                ++i2;
            }
        }
    }
	
	

}
