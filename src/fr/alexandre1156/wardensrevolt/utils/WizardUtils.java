package fr.alexandre1156.wardensrevolt.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.alexandre1156.wardensrevolt.wizard.Wizard;
import fr.alexandre1156.wardensrevolt.wizard.WizardElectro;
import fr.alexandre1156.wardensrevolt.wizard.WizardGelato;
import fr.alexandre1156.wardensrevolt.wizard.WizardInferno;
import fr.alexandre1156.wardensrevolt.wizard.WizardOceany;
import fr.alexandre1156.wardensrevolt.wizard.WizardTerrana;

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
	
	public static void addWizard(Player p, WizardType wizardType){
		switch(wizardType){
		case ELECTRO:
			wizardList.add(new WizardElectro((CraftPlayer) p));
			break;
		case GELATO:
			wizardList.add(new WizardGelato((CraftPlayer) p));
			break;
		case INFERNO:
			wizardList.add(new WizardInferno((CraftPlayer) p));
			break;
		case OCEANY:
			wizardList.add(new WizardOceany((CraftPlayer) p));
			break;
		case TERRANA:
			wizardList.add(new WizardTerrana((CraftPlayer) p));
			break;
		}
		//wizardList.add(new Wizard((CraftPlayer) p, p.getName(), wizardType));
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
		int type = r.nextInt(5);
		switch(type){
		case 0:
			addWizard(p, WizardType.ELECTRO);
			break;
		case 1:
			addWizard(p, WizardType.GELATO);
			break;
		case 2:
			addWizard(p, WizardType.INFERNO);
			break;
		case 3:
			addWizard(p, WizardType.OCEANY);
			break;
		case 4:
			addWizard(p, WizardType.TERRANA);
			break;
		}
	}
	
	public enum WizardType {
		INFERNO, OCEANY, TERRANA, ELECTRO, GELATO 
	}
	
}
