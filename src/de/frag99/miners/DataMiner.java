package de.frag99.miners;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import de.frag99.tokenizer.Tokenizer;
import de.frag99.words.Symbol;
import de.frag99.words.Vowel;
import de.frag99.words.Word;
import de.frag99.xml.PageHandler;
import de.frag99.xml.SaxTerminationException;
import de.frag99.xml.WordsHandlerClassic;
import de.frag99.xml.WordsHandlerDouble;
import de.frag99.xml.WordsHandlerFind;
import de.frag99.xml.WordsHandlerVowel;

public class DataMiner {

	
	
	private static final String resourcePath = "/de/frag99/resources/DE_WORDS.xml";
	
	private static final String wordsPath = "G:\\XML DUMP\\out.txt";	
	private static final String newXMLfile = "G:\\XML DUMP\\newData.xml"; //FOR PARSING
	
	private static final String wiktionaryDatabase = "G:\\XML DUMP\\dewiktionary-latest-pages-articles.xml"; //FOR PARSING
	
	public static ArrayList<String> getVowelRhymesTo(List<Vowel> allVowelSymbols) throws ParserConfigurationException, SAXException, IOException{
		
		StringBuilder sb = new StringBuilder();
		for(Vowel v : allVowelSymbols) {
			sb.append(v.getSymb());
		}
		String vowelString = sb.toString();
		
		Tokenizer t = new Tokenizer(vowelString);
		Word vowelWord = t.tokenize();
		
		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		WordsHandlerVowel whv = new WordsHandlerVowel();
		whv.setInputWord(vowelWord);
		
		InputStream is = DataMiner.class.getResourceAsStream(resourcePath);
		
		try {
			saxParser.parse(is, whv);
		} catch (SaxTerminationException e) {
			//parsing ended
		}
		//System.out.println(whv.getAllWords().size());
		return whv.getAllWords();
		
		
	}
		
	public static Word getIPAto(String inputWord) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		WordsHandlerFind whf = new WordsHandlerFind();
		whf.setInputWord(inputWord);
		
		InputStream is = DataMiner.class.getResourceAsStream(resourcePath);
		
		try {
			saxParser.parse(is, whf);
		} catch (SaxTerminationException e) {
			
		}
		
		return whf.getFoundWord();
		
	}
	
	public static ArrayList<String> getClassicRhymesTo(Word inputWord) throws ParserConfigurationException, SAXException, IOException {

		ArrayList<String> res = new ArrayList<>();
		ArrayList<Symbol> lastSyllSym = inputWord.getLastSyll();
		StringBuilder sb = new StringBuilder();
		for(Symbol s : lastSyllSym) {
			sb.append(s.getSymb());
		}
		
		String lastSyll = sb.toString();
		
		InputStream is = DataMiner.class.getResourceAsStream(resourcePath);
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		
		
		if(!lastSyll.isEmpty()) {
			
			WordsHandlerClassic whc = new WordsHandlerClassic();
			whc.setlastSyllable(lastSyll);

			is = DataMiner.class.getResourceAsStream(resourcePath);
			
			try {
				saxParser.parse(is, whc);
			} catch (SaxTerminationException e) {
				//parsing ended
			}
			
			res = whc.getAllWords();
			
		}
		return res;
	}

	public static ArrayList<String> getDoubleRhymesTo(ArrayList<Symbol> allSymbols) throws ParserConfigurationException, SAXException, IOException {
		StringBuilder sb = new StringBuilder();
		for(Symbol v : allSymbols) {
			sb.append(v.getSymb());
		}
		String vowelString = sb.toString();
		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		WordsHandlerDouble whd = new WordsHandlerDouble();
		
		Tokenizer tokenizer = new Tokenizer(vowelString);
		Word symbolWord = tokenizer.tokenize();
		
		whd.setInputWord(symbolWord);
		
		InputStream is = DataMiner.class.getResourceAsStream(resourcePath);
		
		try {
			saxParser.parse(is, whd);
		} catch (SaxTerminationException e) {
			//parsing ended
		}
		
		
		return whd.getAllWords();
	}
	
	public static void reparseDatabase() throws UnsupportedEncodingException, XMLStreamException, FactoryConfigurationError  {
		
		//<normales Wort>,<Wort in Lautschrift>,... e.g. user vorgeschlagen
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		try {			
			inputStream = new FileInputStream(wordsPath);
			sc = new Scanner(inputStream, "UTF-8");			
			Document doc = null;

			if(sc.hasNextLine()) {
				String initialWord = sc.nextLine();
				ArrayList<String> initialData = new ArrayList<>(Arrays.asList(initialWord.split(";")));
				String initialWordRaw = initialData.get(0);
				String initialIpaNotation = initialData.get(1);
				Tokenizer tempT = new Tokenizer(initialIpaNotation);
				Word tempW = new Word(); 
				tempW = tempT.tokenize();
				doc = new Document(tempW, initialWordRaw, initialIpaNotation);
			}
			
			
				
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					if(line!="") {
						ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(";")));
						
						String wordRaw = data.get(0);
						String ipaNotation = data.get(1);
						
						Tokenizer tempT = new Tokenizer(ipaNotation);
						Word tempW = new Word(); 
						tempW = tempT.tokenize();
						
						//System.out.println(wordRaw); //TODO
						
						//tempW ist gelesenes Wort was zum vergleichen benötigt wird, um zu kopieren aber Variable line benutzen
						if(tempW.getNoOfVowels() > 0) {
							doc.categorize(tempW, wordRaw, ipaNotation);
						}else {
							//werden nichtbeachtet
							
						}

					}								
				}
				
			System.out.println("Datenstruktur erstellt");
			doc.xmlParse(newXMLfile);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	

		//also rhymes with itself
	}

	//old methods for the <word>;<ipa>... notation txt file
	public static Word findIPAto(String rawWord) {
		//normales Wort rein
		Word word = new Word();
		//<normales Wort>,<Wort in Lautschrift>,... e.g. user vorgeschlagen
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {			
			inputStream = new FileInputStream(resourcePath);
			sc = new Scanner(inputStream, "UTF-8");			
			boolean matchFound = false;			
			while(!matchFound && sc.hasNextLine()) {				
				String line = sc.nextLine();				
				ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(";")));
				if(data.get(0).equalsIgnoreCase(rawWord)) {				
					Tokenizer t = new Tokenizer(data.get(1));
					word = t.tokenize();			
					matchFound = true;
				}	
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// TODO: handle exception //lautschrift raten
		}	
		return word;
	}
	
	public static ArrayList<String> findVowelRhymesTo(Word w){
		ArrayList<String> vowelRhymes = new ArrayList<>();
		
		//<normales Wort>,<Wort in Lautschrift>,... e.g. user vorgeschlagen
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		try {			
			inputStream = new FileInputStream(resourcePath);
			sc = new Scanner(inputStream, "UTF-8");			
					
			while(sc.hasNextLine()) {				
				String line = sc.nextLine();				
				ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(";")));
				
				Tokenizer tempT = new Tokenizer(data.get(1));
				
				Word tempW = new Word();
				tempW = tempT.tokenize();
				
				if(w.vowelRhymesWith(tempW)) {
					
					vowelRhymes.add(data.get(0));
				}
					
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	

		//also rhymes with itself
		return vowelRhymes;
	}
	
	public static ArrayList<String> findClassicRhymesTo(Word w){
		ArrayList<String> classicRhymes = new ArrayList<>();
		
		//<normales Wort>,<Wort in Lautschrift>,... e.g. user vorgeschlagen
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		try {			
			inputStream = new FileInputStream(resourcePath);
			sc = new Scanner(inputStream, "UTF-8");			
			
			while(sc.hasNextLine()) {				
				String line = sc.nextLine();				
				ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(";")));
				
				Tokenizer tempT = new Tokenizer(data.get(1));
				
				Word tempW = new Word();
				tempW = tempT.tokenize();
				if(w.classicRhymesWith(tempW)) {
					classicRhymes.add(data.get(0));
				}
					
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	

		//also rhymes with itself
		return classicRhymes;
	}
	
	public static ArrayList<String> findDoubleRhymesTo(Word w) {
		ArrayList<String> doubleRhymes = new ArrayList<>();
		
		//<normales Wort>,<Wort in Lautschrift>,... e.g. user vorgeschlagen
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		try {			
			inputStream = new FileInputStream(resourcePath);
			sc = new Scanner(inputStream, "UTF-8");			
					
			while(sc.hasNextLine()) {				
				String line = sc.nextLine();				
				ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(";")));
				
				Tokenizer tempT = new Tokenizer(data.get(1));
				
				Word tempW = new Word();
				tempW = tempT.tokenize();
				if(w.vowelRhymesWith(tempW)) {
					if(w.classicRhymesWith(tempW)) {
						doubleRhymes.add(data.get(0));
					}
				}					
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	

		//also rhymes with itself
		return doubleRhymes;
	}

	public static void parseXML() throws ParserConfigurationException, SAXException, IOException {
		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		saxParser.parse(wiktionaryDatabase, new PageHandler());
		
	}

	

	
	
}