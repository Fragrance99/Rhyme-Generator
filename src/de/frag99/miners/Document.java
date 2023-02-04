package de.frag99.miners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.frag99.words.Word;

	

public class Document {

	ArrayList<VowelCountClass> vowelCountClasses;
	static int debug;
	
	public Document(Word w, String fullWord, String ipaNotation) {
		
		vowelCountClasses = new ArrayList<>();
		vowelCountClasses.add(new VowelCountClass(w, fullWord, ipaNotation));
	}
	
	public void categorize(Word w, String fullWord, String ipaNotation) {
		boolean categorized = false;
		int index = 0;
		while(categorized==false && index<vowelCountClasses.size()) {
			if(vowelCountClasses.get(index).getNoOfVowels() == w.getNoOfVowels()) {
				vowelCountClasses.get(index).categorize(w, fullWord, ipaNotation);
				categorized = true;
			}
			index++;
		}
		
		if(categorized==false) {
			
			vowelCountClasses.add(new VowelCountClass(w, fullWord, ipaNotation));
		}
		
		
	}
	
	public void xmlParse(String path) throws FileNotFoundException,
			UnsupportedEncodingException, XMLStreamException, FactoryConfigurationError {
		OutputStream outputStream = new FileOutputStream(new File(path));
		
		XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
				new OutputStreamWriter(outputStream, "UTF-8"));
		
		
		out.writeStartDocument();
		out.writeCharacters(System.lineSeparator());
		out.writeStartElement("WORDS");
		
		
		int vowelCount = 1;
		while(!allPrinted()) {
			
			for(VowelCountClass vcc : vowelCountClasses) {
				if(vcc.getNoOfVowels() == vowelCount) {
					System.out.println(vowelCount);
					if(!vcc.wasPrinted()) {
						out.writeCharacters(System.lineSeparator());
						vcc.xmlParse(out);
						vcc.setPrinted();
						break;
					}
				}
			}
			vowelCount++;
		}
		out.writeCharacters(System.lineSeparator());
		out.writeEndElement();
		out.writeEndDocument();
		
		out.close();
	}
	
	private boolean allPrinted() {
		boolean allPrinted = true;
		for(VowelCountClass vcc : vowelCountClasses) {
			if(!vcc.wasPrinted()) {
				allPrinted = false;
				break;
			}
		}
		return allPrinted;
	}
	
}
