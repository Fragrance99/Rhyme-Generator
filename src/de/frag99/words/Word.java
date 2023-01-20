package de.frag99.words;

import java.util.ArrayList;
import de.frag99.tokenizer.Token;

public class Word {

	private ArrayList<Symbol> symbols = new ArrayList<>();
	//symbols can also be vowels
	
	
	public Word(ArrayList<Token> tokens) {
		setSymbols(tokens);
	}
	
	public Word() {
		
	}
	
	public void setSymbols(ArrayList<Token> tokens) {
		int vowelPosition = 0;
		for(Token t : tokens) {
			switch (t.type) {
			case VOWEL:
				symbols.add(new Vowel(t, vowelPosition));
				
				vowelPosition++;
				break;			
			case CONSONANT:
				//consonants with multiple values such as ŋ̍, are added with 2 values
				symbols.add(new Symbol(t));
				break;		
			case EMPH:
				symbols.add(new Symbol(t));
				break;
			case OTHER:
				//nix mache
				break;
			default:
				break;
			}
			
		}
	}
	
	public boolean vowelRhymesWith(Word w2) {
		ArrayList<Vowel> vowels1 = new ArrayList<>();
		ArrayList<Vowel> vowels2 = new ArrayList<>();
		boolean doesRhyme = true;
		for(Symbol s : symbols) {
			if(s instanceof Vowel) {
				vowels1.add((Vowel) s);
			}
		}
		
		for(Symbol s : w2.getSymbols()) {
			if(s instanceof Vowel) {
				vowels2.add((Vowel) s);
			}
		}
		
		//check if vowels have identical values
		if(!vowels1.isEmpty()) {
			if(vowels1.size() == vowels2.size()) {
				for(int i = 0; i<vowels1.size(); i++) {
					if(!vowels1.get(i).rhymesWith(vowels2.get(i))) {
						doesRhyme = false;
					}
				}
			}else {
				doesRhyme = false;
			}
		}
		
		
		return doesRhyme;
		//for w1 to rhym with w2
		//w1 has to have the same amount of vowels and
		//w1 has to have the same vowels in each syllable
		
	}

	private ArrayList<Symbol> getSymbols() {
		return symbols;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Symbol s : symbols) {
			sb.append(s.origToken + " ");
		}
		return sb.toString();
	}
	
	
	
	
}
