package fr.alexandre1156.wardensrevolt.config;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import fr.alexandre1156.wardensrevolt.WardenRevolt;
import fr.alexandre1156.wardensrevolt.config.WardenConfig.ValueTypes;
import fr.alexandre1156.wardensrevolt.event.PlayerJoin;

public class ConfigList {

	/** Monde contenue dans le fichier de config*/
	public static World world;
	/** Position en X Y Z où doivent apparaitre les joueurs lors de leur connexion au serveur*/
	public static int[] hub = new int[3];
	/** Position en X Y Z où doivent être téléportés les joueurs lorsque le jeu commence*/
	public static int[] spawn = new int[3];
	/** TRUE = Warden apparait à des positions aléatoires
	 *  FALSE = Warden apparait à des positions définis*/
	public static boolean wardenHideRand;
	/** List contenant des positions X Y Z où Warden peut apparaitre
	 *  {@link #wardenHideRand} = FALSE*/
	public static ArrayList<int[]> wardenHidePosition = new ArrayList<>();
	/** Position en X Y Z et TAILLE où Warden peut apparaitre
	 *  {@link #wardenHideRand} = TRUE*/
	public static int[] wardenHideRandBorder = new int[4];
	/** TRUE = Les mobs apparaient à des positions aléatoires
	 *  FALSE = Les mobs apparaient à des positions définis*/
	public static boolean mobSpawnPosRand;
	/** List contenant des positions en X Y Z où les mobs peuvent apparaitre
	 *  {@link #mobSpawnRand} = FALSE*/
	public static ArrayList<int[]> mobSpawnPos = new ArrayList<>();
	/** Position en X Y Z et TAILLE où les mobs peuvent apparaitre
	 *  {@link #wardenHideRand} = TRUE*/
	public static int[] mobHideRandBorder = new int[4];
	/** Position en X Y Z où Warden apprait lors au Stage 3*/
	public static int[] wardenSpawnStageThree = new int[3];
	/** List limité à 4, contenant des position en X Y Z où les mobs peuvent spawn durant la phase 3*/
	public static ArrayList<int[]> mobSpawnPhaseThreeFourPillar = new ArrayList<>(4); 
	
	public static void init(){
		WardenConfig conf = WardenRevolt.getInstance().getWardenConfig();
		world = Bukkit.getWorld((String) conf.getValue("World", ValueTypes.STRING));
		hub[0] = conf.getValue("Hub.x", ValueTypes.INT);
		hub[1] = yPosFix(conf.getValue("Hub.y", ValueTypes.INT), "Hub");
		hub[2] = conf.getValue("Hub.z", ValueTypes.INT);
		spawn[0] = conf.getValue("Spawn.x", ValueTypes.INT);
		spawn[1] = yPosFix(conf.getValue("Spawn.y", ValueTypes.INT), "Spawn");
		spawn[2] = conf.getValue("Spawn.z", ValueTypes.INT);
		wardenHideRand = conf.getValue("WardenHideRandom", ValueTypes.BOOLEAN);
		int wardenPosList = conf.getList("WardenHidePosition");
		for(int i = 1; i <= wardenPosList; i++){
			int[] pos = new int[3];
			pos[0] = conf.getValue("WardenHidePosition."+i+".x", ValueTypes.INT);
			pos[1] = yPosFix(conf.getValue("WardenHidePosition."+i+".y", ValueTypes.INT), "WardenHidePosition."+i);
			pos[2] = conf.getValue("WardenHidePosition."+i+".z", ValueTypes.INT);
			wardenHidePosition.add(pos);
		}
		wardenHideRandBorder[0] = conf.getValue("WardenHideRandomBorder.x", ValueTypes.INT);
		wardenHideRandBorder[1] = yPosFix(conf.getValue("WardenHideRandomBorder.y", ValueTypes.INT), "WardenHideRandomBorder");
		wardenHideRandBorder[2] = conf.getValue("WardenHideRandomBorder.z", ValueTypes.INT);
		wardenHideRandBorder[3] = conf.getValue("WardenHideRandomBorder.size", ValueTypes.INT);
		mobSpawnPosRand = conf.getValue("MobSpawnPositionRandom", ValueTypes.BOOLEAN);
		int mobPosList = conf.getList("MobSpawnPosition");
		for(int j = 1; j <= mobPosList; j++){
			int[] pos = new int[3];
			pos[0] = conf.getValue("MobSpawnPosition."+j+".x", ValueTypes.INT);
			pos[1] = yPosFix(conf.getValue("MobSpawnPosition."+j+".y", ValueTypes.INT), "MobSpawnPosition."+j);
			pos[2] = conf.getValue("MobSpawnPosition."+j+".z", ValueTypes.INT);
			mobSpawnPos.add(pos);
		}
		mobHideRandBorder[0] = conf.getValue("MobHideRandomBorder.x", ValueTypes.INT);
		mobHideRandBorder[1] = yPosFix(conf.getValue("MobHideRandomBorder.y", ValueTypes.INT), "MobHideRandomBorder");
		mobHideRandBorder[2] = conf.getValue("MobHideRandomBorder.z", ValueTypes.INT);
		mobHideRandBorder[3] = conf.getValue("MobHideRandomBorder.size", ValueTypes.INT);
		wardenSpawnStageThree[0] = conf.getValue("WardenSpawnPhaseThree.x", ValueTypes.INT);
		wardenSpawnStageThree[1] = yPosFix(conf.getValue("WardenSpawnPhaseThree.y", ValueTypes.INT), "WardenSpawnPhaseThree");
		wardenSpawnStageThree[2] = conf.getValue("WardenSpawnPhaseThree.z", ValueTypes.INT);
		while(!world.getBlockAt(wardenSpawnStageThree[0], wardenSpawnStageThree[1], wardenSpawnStageThree[2]).isEmpty())
			wardenSpawnStageThree[1]+=1;
		for(int i = 1; i <= 5; i++){
			if(!world.getBlockAt(wardenSpawnStageThree[0], wardenSpawnStageThree[1]-i, wardenSpawnStageThree[2]).isEmpty()){
				wardenSpawnStageThree[1]+=1;
				i = 1;
			}
		}
		for(int i = 1; i <= 4; i++){
			int[] pos = new int[3];
			pos[0] = conf.getValue("MobSpawnPhaseThreeFourPillar."+i+".x", ValueTypes.INT);
			pos[1] = yPosFix(conf.getValue("MobSpawnPhaseThreeFourPillar."+i+".y", ValueTypes.INT), "MobSpawnPhaseThreeFourPillar");
			pos[2] = conf.getValue("MobSpawnPhaseThreeFourPillar."+i+".z", ValueTypes.INT);
			mobSpawnPhaseThreeFourPillar.add(pos);
		}
	}
	
	private static int yPosFix(int y, String sectionName){
		if(y < 0){
			y = 0;
			ConfigError ce = ConfigError.builder().addLine("La valeur "+ChatColor.GREEN+"Y"+ChatColor.RESET+" de "+ChatColor.BLUE+""+ChatColor.BOLD+sectionName+ChatColor.RESET+" ne peut pas être négatif !").build();
			PlayerJoin.addError(ce);
		} else if(y > 256){
			y = 256;
			ConfigError ce = ConfigError.builder().addLine("La valeur "+ChatColor.GREEN+"Y"+ChatColor.RESET+" de "+ChatColor.BLUE+""+ChatColor.BOLD+sectionName+ChatColor.RESET+" ne peut pas être au dessus de 256 !").build();
			PlayerJoin.addError(ce);
		}
		return y;
	}
	
}
