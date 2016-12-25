package fr.alexandre1156.wardensrevolt.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;

import fr.alexandre1156.wardensrevolt.config.ConfigList;
import fr.alexandre1156.wardensrevolt.wizard.Wizard;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import net.minecraft.server.v1_11_R1.PlayerInteractManager;

public class WizardRegistry {

	static HashMap<Integer, Class<? extends Wizard>> wizardTypes = Maps.newHashMap();
	
	@SuppressWarnings("deprecation")
	public static void registerWizardType(Class<? extends Wizard> wizardClazz){
		int worldPos = 0;
		for(int i = 0; i < Bukkit.getWorlds().size(); i++){
			if(Bukkit.getWorlds().get(i).getName().equals(ConfigList.world.getName()))
				worldPos = i;
		}
		try {
			int wizardTypeID = (int) wizardClazz.getMethod("getWizardTypeID")
					.invoke(wizardClazz.getConstructor(CraftPlayer.class).newInstance(new CraftPlayer((CraftServer) Bukkit.getServer(),
							new EntityPlayer(MinecraftServer.getServer(), 
									MinecraftServer.getServer().getWorldServer(worldPos), 
									new GameProfile(UUID.randomUUID(), Utils.randomString(10)),
									new PlayerInteractManager(((CraftWorld) ConfigList.world).getHandle().b())))));
			Iterator<Integer> typesWizard = wizardTypes.keySet().iterator();
			while(typesWizard.hasNext()){
				Integer wizardType = typesWizard.next();
				if(wizardType == wizardTypeID){
					Utils.consoleMessage("L'ID de la class "+wizardClazz+" est déjà pris ! ", ChatColor.YELLOW);
					Utils.consoleMessage("Génération d'une nouvelle...", ChatColor.YELLOW);
					wizardTypeID = WizardUtils.randomID();
				}
			}
			wizardTypes.put(wizardTypeID, wizardClazz);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<Integer, Class<? extends Wizard>> getAllWizardTypes(){
		return wizardTypes;
	}
	
}
