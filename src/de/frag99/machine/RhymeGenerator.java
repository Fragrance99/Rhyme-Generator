package de.frag99.machine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import de.frag99.gui.MainFrame;
import de.frag99.miners.DataMiner;
import de.frag99.miners.PathOrganizer;
import de.frag99.tokenizer.TokenType;
import de.frag99.tokenizer.Tokenizer;
import de.frag99.words.Symbol;
import de.frag99.words.Vowel;
import de.frag99.words.Word;

public class RhymeGenerator {

	public static String userInput = "";
	public static String rhymeType = "";
	public static String lang = "";
	public static String function = "RHYME"; // RHYME XML REPARSE DEBUG
	static MainFrame mainframe;
	
	public static void main(String[] args) {
		// args[0] : Eingabewort
		// args[1] : double vowel classic
		// args[2] : sprache
		
		mainframe = new MainFrame();
		PathOrganizer.createTable();
		if(!function.equals("RHYME")) {
			mainframe.addNotification("RHYME MODE NOT ENABLED");
		}
		

	}

	public static void start() {
		long startTime = System.currentTimeMillis();
		mainframe.resetNotification();
		if (function == "RHYME") {

			if (userInput != null) {
				if (!userInput.isBlank()) {

					try {
						// versuche erst, word komplett zu finden

						String[] words = userInput.split("\\s+");

						Word wholeLine = new Word();
						StringBuilder lineInput = new StringBuilder();
						
						for (int j = 0; j < words.length; j++) {
							// TODO DataMiner.getIPAto(words[j]) can be null if no word is found then
							// "guess" words
							
							Word toAppend = DataMiner.getIPAto(words[j]);
							
							if (toAppend == null) {
								mainframe.addNotification(words[j] + " ist nicht in der Datenbank, ignoriere");
							} else {
								lineInput.append(words[j] + " ");
								wholeLine.append(toAppend);
							}

						}
						
						
						if (!wholeLine.isEmpty()) {

							mainframe.addNotification("Reime auf: " + lineInput.toString());

							// hier mit wholeLine weiterarbeiten
							ArrayList<String> results = new ArrayList<>();
							ArrayList<Vowel> allVowelSymbols;
							ArrayList<String> resfirstWord = new ArrayList<>();
							ArrayList<String> ressecondWord = new ArrayList<>();

							switch (rhymeType) {
							case "double":

								results = DataMiner.getDoubleRhymesTo(wholeLine.getSymbols());
								// first word can be vowel rhyme
								// second word must be double rhyme
								allVowelSymbols = wholeLine.getVowels();
								ArrayList<Symbol> lastSyllSym = wholeLine.getLastRelevantSyll();
								
								//get no of vowels
								int noOfVowels = 0;
								for(Symbol s : lastSyllSym) {
									if(s.getOrigToken().getTokenType() == TokenType.VOWEL) {
										noOfVowels++;
									}
								}
								
								
								for (int k = 0; k < allVowelSymbols.size() - noOfVowels; k++) {
									// firstword: 0 - k //last index is excluded
									// secondword: k+1 - allVowelSymbols.size()-1
									resfirstWord = (DataMiner.getVowelRhymesTo(allVowelSymbols.subList(0, k + 1)));
									
									List<Vowel> partOfSecond = allVowelSymbols.subList(k + 1,
											allVowelSymbols.size() - noOfVowels); // last vowel/s missing | 1 or 2
									ArrayList<Symbol> second = new ArrayList<>();
									for (Symbol s : partOfSecond) {
										second.add(s);
									}
									second.addAll(lastSyllSym);

									ressecondWord = DataMiner.getDoubleRhymesTo(second);
									
									
									int index = 0;
									while (index < resfirstWord.size() && index < ressecondWord.size()) {
										results.add(resfirstWord.get(index) + " " + ressecondWord.get(index));
										index++;
									}
								}

								break;
							case "vowel":
								// für jede silbenkombination reime suchen
								allVowelSymbols = wholeLine.getVowels();

								results = DataMiner.getVowelRhymesTo(allVowelSymbols);
								// solution for whole word

								for (int k = 0; k < allVowelSymbols.size() - 1; k++) {
									// firstword: 0 - k //last index is excluded
									// secondword: k+1 - allVowelSymbols.size()-1
									resfirstWord = (DataMiner.getVowelRhymesTo(allVowelSymbols.subList(0, k + 1)));
									ressecondWord = (DataMiner.getVowelRhymesTo(
											allVowelSymbols.subList(k + 1, allVowelSymbols.size())));
									int index = 0;
									while (index < resfirstWord.size() && index < ressecondWord.size()) {
										results.add(resfirstWord.get(index) + " " + ressecondWord.get(index));
										index++;
									}
								}



								break;
							case "classic":
								
								results = DataMiner.getClassicRhymesTo(wholeLine);

								break;

							default:
								break;
							}
							mainframe.printText(results);
							
						}else {
							mainframe.resetTextField();
						}
					} catch (ParserConfigurationException | SAXException | IOException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}

		} else if (function == "XML") {
			try {
				DataMiner.parseXML();
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (function == "REPARSE") {
			System.out.println("Start reparsing");

			try {
				DataMiner.reparseDatabase();
			} catch (UnsupportedEncodingException | XMLStreamException | FactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (function.equals("DEBUG")) {
			// DEBUG ONLY

			try {

				Tokenizer t = new Tokenizer("naɪ̯t");
				
				Word w1 = DataMiner.getIPAto("idempotent");
				
				Word w3 = DataMiner.getIPAto("deine");
				Word w4 = DataMiner.getIPAto("Oma");
				w3.append(w4);
				System.out.println(w3);
				Tokenizer t2 = new Tokenizer("bəˈʁaɪ̯t");
				Word w2 = t2.tokenize();
				
				ArrayList<String> res = DataMiner.findClassicRhymesTo(w1);
				System.out.println(res.toString());
				
				System.out.println("r: " + w3.vowelRhymesWith(w1));
				if (w1 != null) {
					if (w2 != null) {
						System.out.println(w1);
						System.out.println(w2);
						System.out.println(w1.hasSameVowelsAs(w2));
						System.out.println(w1.hasSameLastRelSyllAs(w2));

						for (Symbol s : w1.getLastRelevantSyll()) {
							System.out.println(s.symb);

						}
						for (Symbol s : w2.getLastRelevantSyll()) {
							System.out.println(s.symb);

						}
					} else {
						System.out.println("Zweites Wort nicht gefunden");
					}
				} else {
					System.out.println("Erstes Wort nicht gefunden");
				}

			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block

			}

		}
		
		System.out.println((System.currentTimeMillis() - startTime) / 1000.0f + " Sekunden");
		mainframe.reenable();
		
	}

}
