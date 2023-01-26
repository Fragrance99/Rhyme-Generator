package de.frag99.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.frag99.tokenizer.Tokenizer;
import de.frag99.words.Word;

public class WordsHandlerVowel extends DefaultHandler{

	//suche zu eingabewort die jeweilige vowelklasse
	
	private ArrayList<String> allWords = new ArrayList<>(); //all Words of the current VowRhyClass
	private Word inputWord;
	private int vowelCount;
	private boolean inCorrectVowCount = false;
	private boolean inCorrectVowRhy = false;
	
	
	@Override
	public void startDocument() throws SAXException {
		
		vowelCount = inputWord.getNoOfVowels();
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(qName.equals("VowCount")) {
			if(attributes.getValue(0).equals(""+vowelCount)) {
				
				inCorrectVowCount = true;
			}
			
			if(Integer.parseInt(attributes.getValue(0))>vowelCount) {
				throw new SaxTerminationException();
			}

		}
		
		if(inCorrectVowCount) {
			if(qName.equals("VowRhy")) {
				Tokenizer tokenizer = new Tokenizer(attributes.getValue(0));
				
				Word temp = tokenizer.tokenize();
				if(inputWord.vowelRhymesWith(temp)) {
					inCorrectVowRhy = true;
					
				}
				
			}
		}
		
		
		if(inCorrectVowRhy) {
			if(qName.equals("Word")) {
				allWords.add(attributes.getValue(0));
			}	
		}
		
	
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(inCorrectVowRhy) {
			if(qName.equals("VowRhy")) {
				throw new SaxTerminationException();
			}
		}
		

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	}

	public ArrayList<String> getAllWords() {
		return allWords;
	}

	public void setInputWord(Word inputWord) {
		this.inputWord = inputWord;
	}
	
	
}
