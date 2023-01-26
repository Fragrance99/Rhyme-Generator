package de.frag99.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.frag99.words.Word;

public class WordsHandlerClassic extends DefaultHandler{

	//suche word aus allen words in liste, gib die vowelrhymeclass aus, in der gesuchtes word ist
	
	private ArrayList<String> allWords = new ArrayList<>(); //all Words of the current VowRhyClass
	
	private String lastSyllable;
	
	private boolean inCorrectClasRhyClass = false;
	
	
	
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("Klassikreimsuche startet...");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Klassikreimsuche beendet...");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(qName.equals("ClasRhy")) {
			if(attributes.getValue(0).equals(lastSyllable)) {
				
				
				
				inCorrectClasRhyClass = true;
			}else {
				inCorrectClasRhyClass = false;
			}
		}
		
		if(qName.equals("Word")) {
			if(inCorrectClasRhyClass) {
				allWords.add(attributes.getValue(0));
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	}

	public ArrayList<String> getAllWords() {
		return allWords;
	}

	
	public void setlastSyllable(String lastSyllable) {
		this.lastSyllable = lastSyllable;
	}

	
}
