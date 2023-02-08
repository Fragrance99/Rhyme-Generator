package de.frag99.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.frag99.tokenizer.Tokenizer;
import de.frag99.words.Word;

public class WordsHandlerFind extends DefaultHandler{

	private Word foundWord;
	private String inputWord;
	
	@Override
	public void startDocument() throws SAXException {
		
	}

	@Override
	public void endDocument() throws SAXException {
		
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(qName.equals("Word")) {

			if(inputWord.equalsIgnoreCase(attributes.getValue(0))) {
				//inputWort gefunden
				Tokenizer tempT = new Tokenizer(attributes.getValue(1));
				this.foundWord = tempT.tokenize();
				if(inputWord.equals(attributes.getValue(0))) {
					//foundword gleicht sich zu 100%
					throw new SaxTerminationException();
				}
				
			}
			
			
			
			

		}
	}
	
	public Word getFoundWord() {
		return foundWord;
	}

	public void setInputWord(String userInput) {
		this.inputWord = userInput;
	}
	

	
}
