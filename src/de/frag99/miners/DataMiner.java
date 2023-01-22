package de.frag99.miners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.SAXException;

import de.frag99.tokenizer.Tokenizer;
import de.frag99.words.Word;
import de.frag99.xml.PageHandler;

public class DataMiner {

	
	
	public static final String resourcePath = "D:\\Dokumente\\eclipse-workspace\\DoubleRhyme\\src\\de\\frag99\\resources\\de_WORDS.txt";
	
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
		saxParser.parse("G:\\XML DUMP\\dewiktionary-latest-pages-articles.xml", new PageHandler());
		
	}

	
	public static void reparseDatabase() throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException, FactoryConfigurationError {
		
//		OutputStream outputStream = new FileOutputStream(new File("G:\\XML DUMP\\newData.xml"));
//		
//		XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
//				new OutputStreamWriter(outputStream, "UTF-8"));
//				
//
//		ArrayList<String> doubleRhymes = new ArrayList<>();
//		
//		//<normales Wort>,<Wort in Lautschrift>,... e.g. user vorgeschlagen
//		FileInputStream inputStream = null;
//		Scanner sc = null;
//		
//		try {			
//			inputStream = new FileInputStream(resourcePath);
//			sc = new Scanner(inputStream, "UTF-8");			
//					
//			while(sc.hasNextLine()) {				
//				String line = sc.nextLine();				
//				ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(";")));
//				
//				Tokenizer tempT = new Tokenizer(data.get(1));
//				
//				Word tempW = new Word();
//				tempW = tempT.tokenize();
//				if(tempW.)					
//			}			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}	
//
//		//also rhymes with itself
//		return doubleRhymes;
//	
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
