package de.frag99.machine;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.frag99.miners.DataMiner;
import de.frag99.words.Word;

public class DoubleRhyme {

	public static void main(String[] args) {
		//args[0] : Eingabewort
		//args[1] : wert für doppelreim, vokalreim, standard reim
		//args[2] : sprache
		
//		long startTime = System.currentTimeMillis();
//		
//		try {
//			DataMiner.parseXML();
//		} catch (ParserConfigurationException | SAXException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(System.currentTimeMillis() - startTime);
		int i = 0;
		long startTime = System.currentTimeMillis();
		
		Word userWord = DataMiner.findIPAto("sehe"); //<----------
		
		
		
		System.out.println(userWord.toString());
		if(userWord.toString().equals("")) {
			System.out.println("Wort nicht gefunden");
		}else {
			System.out.println();
			
			for(String res : DataMiner.findVowelRhymesTo(userWord)) {
				System.out.println(res);
				i++;
			}
		}
		System.out.println(System.currentTimeMillis() - startTime);
		System.out.println(i);
		
	}
	
}
