package de.frag99.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WordsHandlerDouble extends DefaultHandler{

	//suche word aus allen words in liste, gib die vowelrhymeclass aus, in der gesuchtes word ist
	
	private ArrayList<String> allWords = new ArrayList<>(); //all Words of the current VowRhyClass
	

	private boolean matchFound = false;
	private String inputWord;
	
	
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("Doppelreimsuche startet...");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Doppelreimsuche beendet...");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(qName.equals("Word")) {
			
			allWords.add(attributes.getValue(0));
			
			if(inputWord.equalsIgnoreCase(attributes.getValue(0))) {
				//inputWort gefunden, also weiter ganze klasse ausgeben
				matchFound = true;
			}	
			

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("ClasRhy")) {
			
			if(matchFound) {
				//liste enthält alle wörter
				
				//exception beendet parsing
				throw new SaxTerminationException();
			}
			
			//kein match gefunden, neue liste beginnen, alte liste löschen
			allWords = new ArrayList<>();
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	}

	public ArrayList<String> getAllWords() {
		return allWords;
	}

	public void setInputWord(String inputWord) {
		this.inputWord = inputWord;
	}
	
	
}
