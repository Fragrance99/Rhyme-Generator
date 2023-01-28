package de.frag99.miners;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.frag99.words.Symbol;
import de.frag99.words.Word;

public class ClassicRhymesClass {

	ArrayList<String> lastSyllSymbols;
	ArrayList<WordXML> words;
	Word exampleWord;
	
	public ClassicRhymesClass(Word w, String fullWord, String ipaNotation) {
		exampleWord = w;
		lastSyllSymbols = new ArrayList<String>();
		for(Symbol sym : w.getLastRelevantSyll()) {
			lastSyllSymbols.add(sym.getSymb());
		}
		WordXML firstWord = new WordXML(w, fullWord, ipaNotation);

		words = new ArrayList<WordXML>();
		words.add(firstWord);
	}

	public Word getExampleWord() {
		return exampleWord;
	}

	
	public void categorize(Word w, String fullWord, String ipaNotation) {
		boolean found = false;
		int index = 0;
		while(found == false && index < words.size()) {
			if(words.get(index).wordObject.equals(w)) {
				found = true;
				System.out.println("hi");
			}
			index++;
		}
		
		if(found == false) {
			//kein duplikat
			
			words.add(new WordXML(w, fullWord, ipaNotation));
			
		}
		
	}

	public void xmlParse(XMLStreamWriter out) throws XMLStreamException {
		out.writeStartElement("ClasRhy");
		out.writeAttribute("lSyl", getSyll4XML());
		for(WordXML wxml : words) {
			out.writeCharacters(System.lineSeparator());
			out.writeCharacters("\t\t\t");
			wxml.xmlParse(out);
		}
		out.writeCharacters(System.lineSeparator());
		out.writeCharacters("\t\t");
		out.writeEndElement();
	}
	
	private String getSyll4XML() {
		StringBuilder sb = new StringBuilder();
		
		for(String s : lastSyllSymbols) {
			sb.append(s);
		}
		
		return sb.toString();
		
	}
}
