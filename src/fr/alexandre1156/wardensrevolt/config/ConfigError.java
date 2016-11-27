package fr.alexandre1156.wardensrevolt.config;

import java.util.ArrayList;

import org.bukkit.ChatColor;


public class ConfigError {
	
	private static ConfigError instance;
	private ArrayList<String> errorLogs = new ArrayList<>(); 

	public static ConfigErrorBuilder builder(){
		if(instance == null)
			instance = new ConfigError();
		return instance.new ConfigErrorBuilder();
	}
	
	class ConfigErrorBuilder{
		
		private ArrayList<String> lines = new ArrayList<>();
		
		private ConfigErrorBuilder(){}
		
		public ConfigErrorBuilder addLine(String line){
			line = line.replace("ValueTypes.BOOLEAN", ChatColor.GREEN+"Boolean (true ou false)");
			line = line.replace("ValueTypes.INT", ChatColor.GREEN+"Nombre");
			line = line.replace("ValueTypes.STRING", ChatColor.GREEN+"Chaîne de Caractères");
			lines.add(line);
			return this;
		}
		
		public ConfigError build(){
			return new ConfigError(this);
		}
		
	}
	
	private ConfigError(ConfigErrorBuilder builder){
		this.errorLogs = builder.lines;
	}
	
	private ConfigError(){}
	
	public ArrayList<String> getLogs(){
		return errorLogs;
	}
	
}
