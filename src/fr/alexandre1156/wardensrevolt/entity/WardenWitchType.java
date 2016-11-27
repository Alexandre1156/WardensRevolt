package fr.alexandre1156.wardensrevolt.entity;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityTypes;
import net.minecraft.server.v1_11_R1.MinecraftKey;

public enum WardenWitchType {

	WARDENWITCH("Warden Witch", 150, WardenWitch.class, "warden_witch");

	private WardenWitchType(String name, int id, Class<? extends Entity> clazz, String name2) {
		addToMaps(clazz, name, id, name2);
	}

	public static void spawnEntity(Entity ent, Location loc) {
		ent.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld) loc.getWorld()).getHandle().addEntity(ent);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void addToMaps(Class<? extends Entity> clazz, String name, int id, String name2) {
		MinecraftKey key = new MinecraftKey(name);
//		((Map) getPrivateField("c", EntityTypes.class, null)).put(name, clazz);
//		((Map) getPrivateField("d", EntityTypes.class, null)).put(clazz, name);
//		((Map) getPrivateField("f", EntityTypes.class, null)).put(clazz, Integer.valueOf(id)); //comparer cb 1.10 et 1.11 field pour trouver erreur
		//EntityTypes.b.a(id, key, clazz);
		EntityTypes.d.add(key);
		((List) getPrivateField("g", EntityTypes.class, null)).set(id, name2);
	}

	public static Object getPrivateField(String fieldName, @SuppressWarnings("rawtypes") Class clazz, Object object) {
		Field field;
		Object o = null;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			o = field.get(object);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}

}
