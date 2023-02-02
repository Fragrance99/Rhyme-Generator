package de.frag99.miners;

import java.util.Hashtable;

public class PathOrganizer {

	private static Hashtable<String, String> resourcePaths = new Hashtable<>();
	
	public static void createTable() {
		resourcePaths.put("deutsch", "/de/frag99/resources/DE_WORDS.xml");
		resourcePaths.put("english", "/de/frag99/resources/EN_WORDS.xml");
	}
	
	public static String getPathTo(String language) {
		return resourcePaths.get(language);
	}
	
	public static int languageCount() {
		return resourcePaths.size();
	}
	
	public static Hashtable<String, String> getResourcePaths(){
		return resourcePaths;
	}
	
	
	
}
