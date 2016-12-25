package fr.alexandre1156.wardensrevolt.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.alexandre1156.wardensrevolt.wizard.Wizard;

public class WizardUtils {
	
	private static ArrayList<Wizard> wizardList = new ArrayList<>();
	private static int diamondActive;
	private static Random r = new Random();

	public static Wizard getWizard(Player p){
		for(Wizard wizard : wizardList){
			if(wizard.getName().equals(p.getName()))
				return wizard;
		}
		return null;
	}
	
	public static List<Wizard> getAllWizards(){
		return wizardList;
	}
	
	public static void addWizard(Player p, int wizardTypeID){
		Iterator<Entry<Integer, Class<? extends Wizard>>> typeWizards = WizardRegistry.wizardTypes.entrySet().iterator();
		while(typeWizards.hasNext()){
			Entry<Integer, Class<? extends Wizard>> wizardType = typeWizards.next();
			if(wizardType.getKey() == wizardTypeID){
				try {
					Constructor<? extends Wizard> wiz = wizardType.getValue().getConstructor(CraftPlayer.class);
					wizardList.add(wiz.newInstance(((CraftPlayer) p)));
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	public static void removeWizard(Player p){
		Iterator<Wizard> wizards = wizardList.iterator();
		while(wizards.hasNext()){
			Wizard wizard = wizards.next();
			if(wizard.getUniqueId().equals(p.getUniqueId())){
				wizards.remove();
			}
		}
	}
	
	public static int randomID(){
		return WizardRegistry.wizardTypes.size();
	}
	
	public static boolean isAWizard(Player p){
		for(Wizard wizard : wizardList){
			if(wizard.getUniqueId().equals(p.getUniqueId()))
				return true;
		}
		return false;
	}
	
	public static void hasActivedTheDiamond(){
		diamondActive++;
	}
	
	public static void hasDesactivedTheDiamond(){
		diamondActive--;
	}
	
	public static int getNumberOfDiamondActived(){
		return diamondActive;
	}
	
	public static void assignRandomWizardType(Player p){
		int types = r.nextInt(WizardRegistry.wizardTypes.size());
		addWizard(p, types);
	}
	
	public enum WizardType {
		INFERNO(2), OCEANY(3), TERRANA(6), ELECTRO(0), GELATO(1), SYSTERNO(4), TENEBRO(5);
		
		private int id;
		
		private WizardType(int ID){
			this.id = ID;
		}
		
		public int getID(){
			return id;
		}
	}
	
}
