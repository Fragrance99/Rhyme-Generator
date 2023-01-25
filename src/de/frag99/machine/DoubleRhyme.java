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
		String userInput = "";
		String rhymeType = "";
		String lang = "de";
		String function = "RHYME";
		
		if(args.length >= 2) {
			userInput = args[0];
			rhymeType = args[1];
			
			if(args.length >= 3) {
				lang = args[2];
				
				if(args.length>= 4) {
					function = args[3];
				}
			}
			
			
						
			long startTime = System.currentTimeMillis();
			if(function == "RHYME") {
				
				int i = 0;
				
				if(userInput != null) {
					
					
					if(userInput.isBlank()) {
						System.out.println("Wort nicht gefunden");
					}else {

						switch (rhymeType) {
						case "double":
							System.out.println("Doppelreime werden gesucht..." + System.lineSeparator());
							try {
								for(String res : DataMiner.findDoubleRhymesTo(userInput)) {
									System.out.println(res);
									i++;
								}
								printNo(i);
							} catch (ParserConfigurationException | SAXException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						case "vowel":
							System.out.println("Vokalreime werden gesucht..." + System.lineSeparator());
							try {
								for(String res : DataMiner.getVowelRhymesTo(userInput)) {
									System.out.println(res);
									i++;
								}
								printNo(i);
							} catch (SAXException | IOException | ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							break;
						case "classic":
							System.out.println("Klassikreime werden gesucht..." + System.lineSeparator());
							try {
								for(String res : DataMiner.findClassicRhymesTo(userInput)) {
									System.out.println(res);
									i++;
								}
								printNo(i);
							} catch (ParserConfigurationException | SAXException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							break;
						default:
							System.out.println("Usage: java -jar <word> <double|vowel|classic>");
							System.out.println("Example: java -jar doublerhyme.jar Hochhaus double");
							break;
						}
					
						
					}
				}
				
				
				
			}else if(function == "XML"){
				try {
					DataMiner.parseXML();
				} catch (ParserConfigurationException | SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(function =="REPARSE"){
				
				
					try {
						DataMiner.reparseDatabase();
					} catch (UnsupportedEncodingException | XMLStreamException | FactoryConfigurationError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				
			}else if(function.equals("DEBUG")){
				//DEBUG ONLY
				
				try {
					
					Word w1 = DataMiner.getIPAto("Obst");
					Word w2 = DataMiner.getIPAto("Obst");
					if(w1 != null) {
						if(w2 != null) {
							System.out.println(w1);
							System.out.println(w2);
							System.out.println(w1.vowelRhymesWith(w2));
							System.out.println(w1.classicRhymesWith(w2));
							System.out.println(w1);
							System.out.println(w2);
						}else {
							System.out.println("Zweites Wort nicht gefunden");
						}
					}else {
						System.out.println("Erstes Wort nicht gefunden");
					}
								
				} catch (ParserConfigurationException | SAXException | IOException e) {
					// TODO Auto-generated catch block
					
				}
				
			}else {
				System.out.println("Usage: java -jar <word> <double|vowel|classic>");
				System.out.println("Example: java -jar doublerhyme.jar Hochhaus double");
			}

			
			System.out.println((System.currentTimeMillis()-startTime)/1000.0f + " Sekunden");			
			
			
		}else {
			System.out.println("Usage: java -jar <word> <double|vowel|classic>");
			System.out.println("Example: java -jar doublerhyme.jar Hochhaus double");
		}
		
		
		
		
		
	}
	
	private static void printNo(int i) {
		if(i==0) {
			System.out.println("Keine Reime gefunden oder Wort nicht in der Datenbank");
		}else {
			System.out.println(i + " Reime gefunden");
		}
	}
	
}
