package de.frag99.machine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import de.frag99.miners.DataMiner;
import de.frag99.tokenizer.Tokenizer;
import de.frag99.words.Symbol;
import de.frag99.words.Vowel;
import de.frag99.words.Word;

public class DoubleRhyme {
	
	public static void main(String[] args) {
		//args[0] : Eingabewort
		//args[1] : double vowel classic
		//args[2] : sprache
		String userInput = "";
		String rhymeType = "";
		String lang = "de";
		String function = "RHYME";
		
		//TODO DEBUG
		args[0] = "Krankenhaus";
		args[1] = "double";
		args[2] = "de";
		args[3] = "RHYME"; //RHYME XML REPARSE DEBUG
		
		
		
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
				
				int rhymeCounter = 0;
				
				if(userInput != null) {
					
					try {
					//versuche erst, word komplett zu finden

						String[] words = userInput.split("\\s+");
						
						Word wholeLine = DataMiner.getIPAto(words[0]);
						
						for(int j = 1; j<words.length; j++) {
							//TODO DataMiner.getIPAto(words[j]) can be null if no word is found
							wholeLine.append(DataMiner.getIPAto(words[j]));
							
						}
					
						//hier mit wholeLine weiterarbeiten 
						ArrayList<String> results = new ArrayList<>();
						ArrayList<Vowel> allVowelSymbols;
						ArrayList<String> resfirstWord = new ArrayList<>();
						ArrayList<String> ressecondWord = new ArrayList<>();
						
							switch (rhymeType) {
							case "double":
								System.out.println("Doppelreime werden gesucht...");

								
								results = DataMiner.getDoubleRhymesTo(wholeLine.getSymbols());
								 //first word can be vowel rhyme
								 //second word must be double rhyme
								allVowelSymbols = wholeLine.getVowels();
								ArrayList<Symbol> lastSyllSym = wholeLine.getLastSyll();
								
								
								for(int k = 0; k<allVowelSymbols.size()-1; k++) {
									//firstword: 0 - k //last index is excluded
									//secondword: k+1 - allVowelSymbols.size()-1
									resfirstWord = (DataMiner.getVowelRhymesTo(allVowelSymbols.subList(0, k+1)));
									
									List<Vowel> partOfSecond = allVowelSymbols.subList(k+1, allVowelSymbols.size()-1); //last syllable missing
									ArrayList<Symbol> second = new ArrayList<>();
									for(Symbol s : partOfSecond) {
										second.add(s);
									}
									second.addAll(lastSyllSym);
									
									ressecondWord = DataMiner.getDoubleRhymesTo(second);
									
									int index = 0;
									while(index < resfirstWord.size() && index < ressecondWord.size()) {
										results.add(resfirstWord.get(index) + " " + ressecondWord.get(index));
										index++;
										rhymeCounter++;
									}	
								}
								
								for(String s : results) {
									System.out.println(s);
									rhymeCounter++;
								}
								
								
								printNo(rhymeCounter);

								break;
							case "vowel":
								//für jede silbenkombination reime suchen
								System.out.println("Vokalreime werden gesucht...");
								allVowelSymbols = wholeLine.getVowels();	
								
								results = DataMiner.getVowelRhymesTo(allVowelSymbols);
								//solution for whole word
								
								
								for(int k = 0; k<allVowelSymbols.size()-1; k++) {
									//firstword: 0 - k //last index is excluded
									//secondword: k+1 - allVowelSymbols.size()-1
									resfirstWord = (DataMiner.getVowelRhymesTo(allVowelSymbols.subList(0, k+1)));
									ressecondWord = (DataMiner.getVowelRhymesTo(allVowelSymbols.subList(k+1, allVowelSymbols.size())));
									int index = 0;
									while(index < resfirstWord.size() && index < ressecondWord.size()) {
										results.add(resfirstWord.get(index) + " " + ressecondWord.get(index));
										index++;
										rhymeCounter++;
									}	
								}
								
								for(String s : results) {
									System.out.println(s);
									rhymeCounter++;
								}
								
								printNo(rhymeCounter);

								
								break;
							case "classic":
								System.out.println("Klassikreime werden gesucht..." + System.lineSeparator());
								
								
								
								results = DataMiner.getClassicRhymesTo(wholeLine);
								
								for(String res : results) {
									System.out.println(res);
									rhymeCounter++;
								}
								printNo(rhymeCounter);
								break;
								
							default:
								System.out.println("Usage: java -jar <word> <double|vowel|classic>");
								System.out.println("Example: java -jar doublerhyme.jar Hochhaus double");
								break;
							}


					} catch (ParserConfigurationException | SAXException | IOException e) {
						// TODO: handle exception
						e.printStackTrace();
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
					System.out.println("Start reparsing");
				
					try {
						DataMiner.reparseDatabase();
					} catch (UnsupportedEncodingException | XMLStreamException | FactoryConfigurationError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				
			}else if(function.equals("DEBUG")){
				//DEBUG ONLY
				
				try {
					
					
					Tokenizer t = new Tokenizer("n̩");
					Word w1 = t.tokenize();
					Word w2 = DataMiner.getIPAto("Eisen");
					Tokenizer t2 = new Tokenizer("n̩");
					Word w3 = t2.tokenize();
					if(w1 != null) {
						if(w2 != null) {
							System.out.println(w1);
							System.out.println(w2);
							System.out.println(w1.vowelRhymesWith(w2));
							System.out.println(w1.classicRhymesWith(w2));

							
							for(Symbol s : w1.getLastSyll()) {
								System.out.println(s.symb);
								
							}
							for(Symbol s : w2.getLastSyll()) {
								System.out.println(s.symb);
								
							}
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
