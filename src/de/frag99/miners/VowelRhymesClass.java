package de.frag99.miners;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.frag99.tokenizer.TokenType;
import de.frag99.words.Symbol;
import de.frag99.words.Vowel;
import de.frag99.words.Word;

public class VowelRhymesClass {

	public ArrayList<Vowel> vowels;
	public ArrayList<ClassicRhymesClass> classicRhymeClasses;
	public Word exampleWord;
	
	
	public Word getExampleWord() {
		return exampleWord;
	}

	public VowelRhymesClass(Word w, String fullWord, String ipaNotation) {
		vowels = new ArrayList<>();
		exampleWord = w;
		
		for(Symbol sym : w.getSymbols()) {
			if(sym.getOrigToken().getTokenType() == TokenType.VOWEL) {
				vowels.add((Vowel)sym);
			}
		}
		
		classicRhymeClasses = new ArrayList<>();
		classicRhymeClasses.add(new ClassicRhymesClass(w, fullWord, ipaNotation));
		
	}

	public void categorize(Word w, String fullWord, String ipaNotation) {

		boolean categorized = false;
		int index = 0;
		while(categorized==false && index<classicRhymeClasses.size()) {
			if(classicRhymeClasses.get(index).getExampleWord().hasSameLastRelSyllAs(w)) {
				//richtige klasse -> weiter einordnen
				classicRhymeClasses.get(index).categorize(w, fullWord, ipaNotation);
				
				categorized = true;
			}
			index++;
		}
		
		if(categorized==false) {
			classicRhymeClasses.add(new ClassicRhymesClass(w, fullWord, ipaNotation));
		}
		
	}

	public void xmlParse(XMLStreamWriter out) throws XMLStreamException {
		out.writeStartElement("VowRhy");
		out.writeAttribute("vow", getTokens4XML());
		for(ClassicRhymesClass crc : classicRhymeClasses) {
			out.writeCharacters(System.lineSeparator());
			out.writeCharacters("\t\t");
			crc.xmlParse(out);
			
		}
		out.writeCharacters(System.lineSeparator());
		out.writeCharacters("\t");
		out.writeEndElement();
	}
	
	private String getTokens4XML() {
		StringBuilder sb = new StringBuilder();
		
		for(Vowel v : vowels) {
			if(v.isAccentuated()) {
				sb.append("'");
			}
			sb.append(v.getSymb() + ";");
		}
		sb.setLength(sb.length()-1);
		return sb.toString();
	}
	
}
