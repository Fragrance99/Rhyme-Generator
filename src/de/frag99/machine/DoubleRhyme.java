package de.frag99.machine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import de.frag99.miners.DataMiner;
import de.frag99.words.Word;

public class DoubleRhyme {
	
	public static void main(String[] args) {
		//args[0] : Eingabewort
		//args[1] : wert für doppelreim(0), vokalreim(1), standard reim(2)
		//args[2] : sprache



		
		args[0] = "Krankenhaus";								//<----------USER EINGABE
		args[1] = "classic";
		args[2] = "de";
		args[3] = "RHYME"; //XML, REPARSE, RHYME				//<----------PARSE NEW DATABASE FLAG
		
		long startTime = System.currentTimeMillis();
		if(args[3] == "RHYME") {
			
			int i = 0;
			
			String userInput = args[0];
			if(userInput != null) {
				
				
				if(userInput.isBlank()) {
					System.out.println("Wort nicht gefunden");
				}else {

					switch (args[1]) {
					case "double":
						System.out.println("Doppelreime werden gesucht...");
						try {
							for(String res : DataMiner.findDoubleRhymesTo(userInput)) {
								System.out.println(res);
								i++;
							}
						} catch (ParserConfigurationException | SAXException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case "vowel":
						System.out.println("Vokalreime werden gesucht...");
						try {
							for(String res : DataMiner.getVowelRhymesTo(userInput)) {
								System.out.println(res);
								i++;
							}
						} catch (SAXException | IOException | ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						break;
					case "classic":
						
						try {
							for(String res : DataMiner.findClassicRhymesTo(userInput)) {
								System.out.println(res);
								i++;
							}
						} catch (ParserConfigurationException | SAXException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						break;
					default:
						break;
					}
					
					if(i==0) {
						System.out.println("Keine Reime gefunden oder Wort nicht in der Datenbank");
					}else {
						System.out.println(i + " Reime gefunden");
					}
					
					
					
					
				}
			}
			
			
			
		}else if(args[3] == "XML"){
			try {
				DataMiner.parseXML();
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(args[3] =="REPARSE"){
			
			
				try {
					DataMiner.reparseDatabase();
				} catch (UnsupportedEncodingException | XMLStreamException | FactoryConfigurationError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			
		}else {
			//DEBUG ONLY
			Word w1 = DataMiner.findIPAto("pst");
			Word w2 = DataMiner.findIPAto("Obst");
			System.out.println(w1);
			System.out.println(w2);
			System.out.println(w1.vowelRhymesWith(w2));
			System.out.println(w1.classicRhymesWith(w2));
			System.out.println(w1);
			System.out.println(w2);
		}

		
		System.out.println((System.currentTimeMillis()-startTime)/1000.0f + " Sekunden");
		
		
		
	}
	
	
	
}
