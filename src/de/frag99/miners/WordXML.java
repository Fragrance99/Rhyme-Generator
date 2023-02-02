package de.frag99.miners;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.frag99.tokenizer.Tokenizer;
import de.frag99.words.Symbol;
import de.frag99.words.Word;

public class WordXML {

	public Word wordObject;
	public String word;
	public String ipaNotation;
	public boolean userdefined;
	
	
	public WordXML(Word w, String fullWord, String ipaNotation) {
		this.wordObject = w;
		this.word = fullWord;
		this.ipaNotation = ipaNotation;
		this.userdefined = false;
		
	}
	
	public WordXML(String fullWord, String ipaNotation, String userdef) {
		this.word = fullWord;
		this.ipaNotation = ipaNotation;
		
		if(userdef.equals("1")) {
			this.userdefined = true;
		}else {
			this.userdefined = false;
		}
		
	}

	public void createWordObject() {
		Tokenizer tempT = new Tokenizer(ipaNotation);
		this.wordObject = tempT.tokenize();
	}

	public void xmlParse(XMLStreamWriter out) throws XMLStreamException {
		
		
		out.writeStartElement("Word");
		out.writeAttribute("w", word);
		
		out.writeAttribute("ipa", getIPANot());
		
//		if(userdefined) {
//			out.writeAttribute("usdef", "1");
//		}else {
//			out.writeAttribute("usdef", "0");
//		}
			
		out.writeEndElement();
	}
	
	public String getIPANot() {
		StringBuilder sb = new StringBuilder();
		for(Symbol sym : this.wordObject.getSymbols()) {
			sb.append(sym.getSymb());
		}
		return sb.toString();
	}
	
}
