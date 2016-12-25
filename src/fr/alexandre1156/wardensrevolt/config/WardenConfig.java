package fr.alexandre1156.wardensrevolt.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.alexandre1156.wardensrevolt.Stuff;
import fr.alexandre1156.wardensrevolt.event.PlayerJoin;
import fr.alexandre1156.wardensrevolt.utils.Utils;

public class WardenConfig {

	private static File confFile;
	//private static FileConfiguration conf;
	private static List<String> configLine;

	public static void init(String file) {
		confFile = new File(file);
		try {
			configLine = Files.readAllLines(confFile.toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(configLine.isEmpty())
			Utils.consoleMessage("Le config.yml est vide ?!", ChatColor.RED);
	}

	public void setValue(String section, Object newValue) {
		String obj = "";
		if(section.contains("."))
			obj = getSectionValue(section);
		else
			obj = String.valueOf(getValue(section, ValueTypes.STRING));
		int pos = getPosition(obj, false, true);
		if(newValue.equals(getValue(section, ValueTypes.NOTHING)))
			Utils.consoleMessage("La valeur "+newValue+" est la même que l'ancienne valeur de la section "+section, ChatColor.YELLOW);
		StringBuilder sb = new StringBuilder(obj);
		for(int i = obj.length()-1; i > 0; i--){
			if(sb.charAt(i) != ':'){
				obj = sb.substring(0, i);
			} else
				break;
		}
		try{
			configLine.set(pos, obj+" "+newValue);
		} catch(ArrayIndexOutOfBoundsException e){
			ConfigError ce = ConfigError.builder().addLine("> La valeur de la section "+ChatColor.BLUE+""+ChatColor.BOLD+section+ChatColor.RESET+" n'as pas pu être changé !").build();
			PlayerJoin.addError(ce);
		}
		try {
			Files.write(confFile.toPath(), configLine, StandardCharsets.UTF_8);
			ConfigList.init();
	    } catch (IOException e) {
	        ConfigError ce = ConfigError.builder().addLine("> Le fichier de config n'as pas pu être modifié !").build();
	        PlayerJoin.addError(ce);
	    }
	}
	
	private String getLine(String section, boolean isMultipleSection, boolean strict){
		for(int i = 0; i < configLine.size(); i++){
			if(isMultipleSection){
				if(configLine.get(i).equals(section+":"))
					if(!configLine.get(i).startsWith("#"))
						return configLine.get(i);
			} else if(strict){
				if(configLine.get(i).equals(section)){
					if(!configLine.get(i).startsWith("#"))
						return configLine.get(i);
				}
			} else if(configLine.get(i).contains(section)){
				if(!configLine.get(i).startsWith("#"))
					return configLine.get(i);
			}
		}
		return null;
	}
	
	private int getPosition(String section, boolean isMultipleSection, boolean strict){
		for(int i = 0; i < configLine.size(); i++){
			if(isMultipleSection){
				if(configLine.get(i).equals(section+":"))
					return i;
			} else if(strict){
				if(configLine.get(i).equals(section))
					return i;
			} else if(configLine.get(i).contains(section)){
				if(!configLine.get(i).startsWith("#"))
					return i;
			}
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(String section, ValueTypes theTypeOfTheValue){
		String line = "";
		if(section.contains("."))
			line = getSectionValue(section);
		else
			line = getLine(section, false, false);
		if(line == null){
			ConfigError ce = ConfigError.builder().addLine("> La section "+ChatColor.BOLD+""+ChatColor.BLUE+section+ChatColor.RESET+
					" n'as pas pu être trouvé !").build();
			PlayerJoin.addError(ce);
			return null;
		}
		StringBuilder sb = new StringBuilder(line);
		String value = "";
		int i = 0;
		//Retourne ce qu'il y a après le ":"
		String sectionDebug = "";
		for(int k = i; k < sb.length(); k++){
			if(sb.charAt(k) == ':'){
				i = k+1;
				break;
			} else
				sectionDebug = sectionDebug+sb.charAt(k);
		}
		//recupère la valeur après le ":"
		try{
			sb.charAt(i);
		} catch(StringIndexOutOfBoundsException e){
			ConfigError ce = ConfigError.builder().addLine("> La section "+ChatColor.BOLD+""+ChatColor.BLUE+section+ChatColor.RESET+
					" ne contient pas de valeur !").build();
			PlayerJoin.addError(ce);
		}
		for(int j = i; j < sb.length(); j++){
			if(sb.charAt(j) != ' '){
				value = sb.substring(j).toString();
				break;
			}
		}
			
		switch(theTypeOfTheValue){
		case BOOLEAN:
			if(value.equals("true") || value.equals("false"))
				return (T) Boolean.valueOf(value);
			else {
				ConfigError ce = ConfigError.builder().addLine("> La valeur de "+ChatColor.BLUE+""+ChatColor.BOLD+sectionDebug+ChatColor.RESET+" devrait être de type "+ValueTypes.BOOLEAN.toString()).build();
				PlayerJoin.addError(ce);
			}
			break;
		case INT:
			try{
				if(value.matches(".*\\d+.*"))
					return (T) Integer.valueOf(value);
				else {
					ConfigError ce = ConfigError.builder().addLine("> La valeur de "+ChatColor.BLUE+""+ChatColor.BOLD+sectionDebug+ChatColor.RESET+" devrait être de type "+ValueTypes.INT.toString()).build();
				}
			} catch(NumberFormatException e){
				ConfigError ce = ConfigError.builder().addLine("> La section "+ChatColor.BLUE+ChatColor.BOLD+sectionDebug+ChatColor.RESET+" a un problème de valeur : "+ChatColor.UNDERLINE+value).build();
				PlayerJoin.addError(ce);
			}
			break;
		case NOTHING:
			return (T) value;
		case STRING:
			if(!value.equals("true") || !value.equals("false") || !value.matches(".*\\d+.*"))
				return (T) String.valueOf(value);
			else {
				ConfigError ce = ConfigError.builder().addLine("> La valeur de "+ChatColor.BLUE+""+ChatColor.BOLD+sectionDebug+ChatColor.RESET+" devrait être de type "+ValueTypes.STRING.toString()).build();
				PlayerJoin.addError(ce);
			}
			break;
		}
		return null;
	}
	
	private String getSectionValue(String section){
		String[] ss = section.split("\\.");
		//On vérifie si wardenHidePosition 1 x existe dans le config.yml (peut être faux avec 'x')
		Validate.notNull(getLine(ss[0], true, false), "La section "+ss[0]+" n'existe pas !");
		String lastSection = "";
		for(int j = 0; j < ss.length; j++){
			if(j+1 == ss.length)
				lastSection = ss[j];
		}
		int positionStr = getPosition(ss[0], true, false);
		int posAdded = 1;
		String whiteSpace = "   ";
		int posSection = 1;
		String actualSection = ss[0];
		while(positionStr+posAdded != configLine.size()){
			if(configLine.get(positionStr+posAdded).contains(whiteSpace+lastSection) && posSection == ss.length-1){
				return configLine.get(positionStr+posAdded);
			} else if(configLine.get(positionStr+posAdded).contains(whiteSpace)){
				if(configLine.get(positionStr+posAdded).contains(whiteSpace+ss[posSection])){
					whiteSpace = whiteSpace+"   ";
					posSection++;
					positionStr+=posAdded;
					posAdded = 0;
					actualSection = ss[posSection];
				}
			} else {
				ConfigError ce = ConfigError.builder().addLine("> La section "+ChatColor.BOLD+""+ChatColor.BLUE+actualSection+ChatColor.RESET+
						" des section "+ChatColor.BOLD+""+section+" n'as pas était trouvé !").build();
				PlayerJoin.addError(ce);
				break;
			}
			posAdded++;
		}
		return null;
	}
	
	public int getList(String section){
		Validate.notNull(this.getLine(section, true, false));
		int numberOfList = 0;
		String whiteSpace = "   ";
		int sectionPos = this.getPosition(section, true, false);
		int lineAdded = 1;
		while(sectionPos+lineAdded != configLine.size()){
			String line = configLine.get(sectionPos+lineAdded);
			if(line.contains(whiteSpace)){
				if(line.endsWith(":")){
					numberOfList++;
				}
			} else
				break;
			lineAdded++;
		}
		return numberOfList;
	}
	
	public void reloadConfig(Player p){
		try {
			configLine = Files.readAllLines(confFile.toPath(), StandardCharsets.UTF_8);
			p.sendMessage(Stuff.PLUGIN_TAG+ChatColor.GREEN+" Le fichier de config a bien été reload !");
		} catch (IOException e) {
			e.printStackTrace();
			p.sendMessage(Stuff.PLUGIN_TAG+ChatColor.RED+" Le fichier ne config n'a pas pu être reload ! Regardez les logs du serveur pour savoir d'où vient l'erreur !");
		}
	}
	
	public enum ValueTypes{
		INT, STRING, BOOLEAN, NOTHING;
		
		public String toString(){
			switch(this){
			case BOOLEAN:
				return "ValueTypes.BOOLEAN";
			case INT:
				return "ValueTypes.INT";
			case NOTHING:
				return "ValueTypes.NOTHING";
			case STRING:
				return "ValueTypes.STRING";
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
