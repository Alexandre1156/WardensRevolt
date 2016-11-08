package fr.alexandre1156.wardensrevolt;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.event.Listener;

import fr.alexandre1156.wardensrevolt.utils.WizardUtils.WizardType;

public class Wizard extends CraftPlayer implements Listener{
	
	private String name;
	private WizardType type;

	public Wizard(CraftPlayer cp, String name, WizardType wizardType) {
		super((CraftServer) Bukkit.getServer(), cp.getHandle());
		this.name = name;
		this.type = wizardType;
	}
	
	public WizardType getWizardType() {
		return type;
	}
	
	public void setWizardType(WizardType type){
		this.type = type;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	


}
