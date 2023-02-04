package de.frag99.miners;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PathOrganizer {

	private static Hashtable<String, String> resourcePaths = new Hashtable<>();
	
	public static void createTable() {
		resourcePaths.put("deutsch", "DE_WORDS.xml");
		resourcePaths.put("english", "EN_WORDS.xml");
		resourcePaths.put("français", "FR_WORDS.xml");
	}
	
	public static InputStream getStreamTo(String language) {
		InputStream is = PathOrganizer.class.getResourceAsStream("/de/frag99/resources/WORDLISTS.zip");
		ZipInputStream zipStream = new ZipInputStream(is);
		try {
			ZipEntry nextEntry = zipStream.getNextEntry();
			while(nextEntry != null) {
				if(nextEntry.getName().equals( resourcePaths.get(language) )) {
					return zipStream;
				}
				nextEntry = zipStream.getNextEntry();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return zipStream;
	}
	
	public static int languageCount() {
		return resourcePaths.size();
	}
	
	public static Hashtable<String, String> getResourcePaths(){
		return resourcePaths;
	}
	
	
	
}
