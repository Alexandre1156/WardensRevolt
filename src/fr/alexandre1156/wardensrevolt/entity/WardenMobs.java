package fr.alexandre1156.wardensrevolt.entity;

import javax.annotation.CheckForNull;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;

import fr.alexandre1156.wardensrevolt.Stuff;

@SuppressWarnings("rawtypes")
public enum WardenMobs {

	WARDEN(0, ArmorStand.class, 100, Stuff.WARDEN_TAG), 
	/**Aveuglant*/
	BLINDING(1, WitherSkeleton.class, 20, Stuff.BLINDING_TAG), 
	WIZARD(2, WardenWitch.class, 20, Stuff.WIZARD_TAG), 
	KILLER(3, ZombieVillager.class, 20, Stuff.KILLER_TAG), 
	ZOMBIE(4, Zombie.class, 20, Stuff.ZOMBIE_TAG), 
	ZOMBIE_BAND(5, Zombie.class, 20, Stuff.ZOMBIE_BAND_TAG),
	RAPID(6, Zombie.class, 60, Stuff.RAPID_TAG), 
	JUDOKAS(7, Judokas.class, 60, Stuff.JUDOKAS_TAG), 
	CANARDIER(8, ArmorStand.class, 60, Stuff.CANARDIER_TAG), 
	ENERGY_VORTEX(9, EnergyVortex.class, 80, Stuff.ENERGY_VORTEX_TAG),
	REGENERATOR(10, WitherSkeleton.class, 60, Stuff.REGENERATOR_TAG),
	/**Tourbillon d'énergie*/
	ENERGY_SWIRL(11, EnergySwirl.class, 60, Stuff.ENERGY_SWIRL_TAG), 
	TRIFCAN(12, null, Integer.MAX_VALUE, null), 
	LIKAOS(13, null, Integer.MAX_VALUE, null), 
	CHEKAVIAH(14, null, Integer.MAX_VALUE, null), 
	BOBELY(15, null, Integer.MAX_VALUE, null), 
	CHEAT_WARDEN(16, Wither.class, Integer.MAX_VALUE, null);
	
	private int id;
	private Class clazz;
	private float health;
	private String tag;
	
	private WardenMobs(int id, Class clazz, float health, String tag){
		this.id = id;
		this.clazz = clazz;
		this.health = health;
		this.tag = tag;
	}

	@CheckForNull
	public Class getClazz() {
		return clazz;
	}
	
	public float getHealth() {
		return health;
	}
	
	public int getId() {
		return id;
	}
	
	@CheckForNull
	public String getTag() {
		return tag;
	}

	@CheckForNull
	public static WardenMobs getMobByTag(String customName) {
		WardenMobs mob = null;
		if(customName.equals(Stuff.BLINDING_TAG))
			mob = WardenMobs.BLINDING;
		else if(customName.equals(Stuff.JUDOKAS_TAG))
			mob = WardenMobs.JUDOKAS;
		else if(customName.equals(Stuff.KILLER_TAG))
			mob = WardenMobs.KILLER;
		else if(customName.equals(Stuff.RAPID_TAG))
			mob = WardenMobs.RAPID;
		else if(customName.equals(Stuff.WARDEN_TAG))
			mob = WardenMobs.WARDEN;
		else if(customName.equals(Stuff.WIZARD_TAG))
			mob = WardenMobs.WIZARD;
		else if(customName.equals(Stuff.ZOMBIE_TAG))
			mob = WardenMobs.ZOMBIE;
		else if(customName.equals(Stuff.ZOMBIE_BAND_TAG))
			mob = WardenMobs.ZOMBIE_BAND;
		else if(customName.equals(Stuff.CANARDIER_TAG))
			mob = WardenMobs.CANARDIER;
		else if(customName.equals(Stuff.ENERGY_SWIRL_TAG))
			mob = WardenMobs.ENERGY_SWIRL;
		else if(customName.equals(Stuff.REGENERATOR_TAG))
			mob = WardenMobs.REGENERATOR;
		else if(customName.equals(Stuff.ENERGY_VORTEX_TAG))
			mob = WardenMobs.ENERGY_VORTEX;
		else if(customName.equals(Stuff.CHEAT_WARDEN_TAG))
			mob = WardenMobs.CHEAT_WARDEN;
		else
			mob = null;
		return mob;
	}
	
}
