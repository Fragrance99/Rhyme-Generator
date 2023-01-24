package de.frag99.miners;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import de.frag99.tokenizer.Tokenizer;
import de.frag99.words.Word;
import de.frag99.xml.PageHandler;

public class DataMiner {

	
	
	public static final String resourcePath = "D:\\Dokumente\\eclipse-workspace\\DoubleRhyme\\src\\de\\frag99\\resources\\de_WORDS.txt";
	public static final String newXMLfile = "G:\\XML DUMP\\newData.xml";
	public static final String wiktionaryDatabase = "G:\\XML DUMP\\dewiktionary-latest-pages-articles.xml";
	public static int debug = 0;
	
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

	
	public static void reparseDatabase() throws UnsupportedEncodingException, XMLStreamException, FactoryConfigurationError  {
		
		//<normales Wort>,<Wort in Lautschrift>,... e.g. user vorgeschlagen
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		try {			
			inputStream = new FileInputStream(resourcePath);
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

	

	
	
}
