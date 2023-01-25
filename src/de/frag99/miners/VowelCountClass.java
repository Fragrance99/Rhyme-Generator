package de.frag99.miners;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.frag99.words.Word;

public class VowelCountClass {

	public boolean printed = false;
	public int noOfVowels;
	public ArrayList<VowelRhymesClass> vowelRhymeClasses;
	static int debug = 0;
	
	public VowelCountClass(Word w, String fullWord, String ipaNotation) {
		noOfVowels = w.getNoOfVowels();
		vowelRhymeClasses = new ArrayList<>();
		vowelRhymeClasses.add(new VowelRhymesClass(w, fullWord, ipaNotation));
	}

	public void categorize(Word w, String fullWord, String ipaNotation) {
		boolean categorized = false;
		int index = 0;
		while(categorized==false && index<vowelRhymeClasses.size()) {
			
			if(vowelRhymeClasses.get(index).getExampleWord().vowelRhymesWith(w)) {
				//richtige klasse -> weiter einordnen
				
				vowelRhymeClasses.get(index).categorize(w, fullWord, ipaNotation);
				categorized = true;
			}
			index++;
		}
		
		if(categorized==false) {
			vowelRhymeClasses.add(new VowelRhymesClass(w, fullWord, ipaNotation));
		}
		
		
	}
	
	public void xmlParse(XMLStreamWriter out) throws XMLStreamException {

		out.writeStartElement("VowCount");
		out.writeAttribute("no", ""+noOfVowels);
		for(VowelRhymesClass vrc : vowelRhymeClasses) {
			out.writeCharacters(System.lineSeparator());
			out.writeCharacters("\t");
			vrc.xmlParse(out);
		}
		out.writeCharacters(System.lineSeparator());
		
		out.writeEndElement();
		
	}
	
	
	public int getNoOfVowels() {
		return noOfVowels;
	}

	public boolean wasPrinted() {
		return printed;
	}
	
	public void setPrinted() {
		this.printed = true;
	}
	
	
}
