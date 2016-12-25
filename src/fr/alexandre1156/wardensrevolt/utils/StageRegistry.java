package fr.alexandre1156.wardensrevolt.utils;

import java.util.HashMap;

import com.google.common.collect.Maps;

import fr.alexandre1156.wardensrevolt.scheduler.stage.Stage;

public class StageRegistry {

	private static HashMap<Integer, Object> stages = Maps.newHashMap();
	
	public static void registreStage(Object instance){
		if(instance instanceof Stage){
			stages.put(stages.size(), instance);
		}
	}
	
	public static HashMap<Integer, Object> getAllStages(){
		return stages;
	}
}
