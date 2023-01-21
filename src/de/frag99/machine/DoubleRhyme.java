package de.frag99.machine;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.frag99.miners.DataMiner;
import de.frag99.words.Word;

public class DoubleRhyme {
	
	public static void main(String[] args) {
		//args[0] : Eingabewort
		//args[1] : wert für doppelreim(0), vokalreim(1), standard reim(2)
		//args[2] : sprache



		
		args[0] = "Krankenhaus";						//<----------USER EINGABE
		args[1] = "double";
		args[2] = "de";
		args[3] = ""; //XML					//<----------PARSE NEW DATABASE FLAG
		
		if(args[3] != "XML") {
			long startTime = System.currentTimeMillis();
			int i = 0;
			
			Word userWord = DataMiner.findIPAto(args[0]); 
			if(userWord != null) {
				
				System.out.println(userWord.toString()); //DEBUG
				if(userWord.toString().isBlank()) {
					System.out.println("Wort nicht gefunden");
				}else {
					
					
					
					switch (args[1]) {
					case "double":
						System.out.println("Doppelreime werden gesucht...");
						for(String res : DataMiner.findDoubleRhymesTo(userWord)) {
							System.out.println(res);
							i++;
						}
						break;
					case "vowel":
						System.out.println("Vokalreime werden gesucht...");
						for(String res : DataMiner.findVowelRhymesTo(userWord)) {
							System.out.println(res);
							i++;
						}
						
						break;
					case "classic":
						System.out.println("Klassikreime werden gesucht...");
						for(String res : DataMiner.findClassicRhymesTo(userWord)) {
							System.out.println(res);
							i++;
						}
						
						
						
						break;
					default:
						break;
					}
					
					System.out.println(i + " Reime gefunden");
					
					
					
				}
			}
			
			System.out.println((System.currentTimeMillis()-startTime)/1000.0f + " Sekunden");
			
		}else {
			try {
				DataMiner.parseXML();
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		
		
		
		
	}
	
	
	
}