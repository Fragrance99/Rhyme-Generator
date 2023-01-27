package de.frag99.words;

import java.util.ArrayList;

import de.frag99.tokenizer.Token;
import de.frag99.tokenizer.TokenType;

public class Word {

	private ArrayList<Symbol> symbols = new ArrayList<>();
	//symbols can also be vowels
	
	
	public Word(ArrayList<Token> tokens, ArrayList<String> rawSymbols) {
		setSymbols(tokens, rawSymbols);
	}
	
	public Word() {
		
	}
	
	public void append(Word w) {
		for(Symbol sym : w.getSymbols()) {
			symbols.add(sym);
		}
	}
	
	public void setSymbols(ArrayList<Token> tokens, ArrayList<String> rawSymbols) {
		
		int symbolIndex = 0;
		for(Token t : tokens) {
			
			switch (t.type) {
			case VOWEL:
				symbols.add(new Vowel(t, rawSymbols.get(symbolIndex)));
				
				break;			
			case CONSONANT:
				
				
				symbols.add(new Symbol(t, rawSymbols.get(symbolIndex)));
				break;		
			case EMPH:
				
				//symbols.add(new Symbol(t, rawSymbols.get(symbolIndex)));
				break;
			case OTHER:
				//nix mache
				break;
			default:
				break;
			}
			symbolIndex++;
		}
	}
	
	public boolean vowelRhymesWith(Word w2) {
		ArrayList<Vowel> vowels1 = new ArrayList<>();
		ArrayList<Vowel> vowels2 = new ArrayList<>();
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
						return false;
					}
				}
			}else {
				return false;
			}
		}else {
			return false;
		}
			
		
		
		
		return true;
		//for w1 to rhym with w2
		//w1 has to have the same amount of vowels and
		//w1 has to have the same vowels in each syllable
		
	}
	
	public ArrayList<Symbol> getLastSyll(){
		ArrayList<Symbol> lastSyllable = new ArrayList<>();
		
		int i = getIndexOfLastVowel();
		if(i>=0) {
			if(i>0) {
				if(symbols.get(i-1).getOrigToken().getTokenType() == TokenType.VOWEL) {
					lastSyllable.add(symbols.get(i-1));
				}
				
				
			}
			while(i<symbols.size()) {
				
				lastSyllable.add(symbols.get(i));
				//immernoch in lautschrift
				i++;
			}
		}else {
			return symbols;
		}
			
		return lastSyllable;
	}
	
	public boolean classicRhymesWith(Word w2) {
		
		ArrayList<Symbol> lastSyllable1 = this.getLastSyll();
		if(this.getNoOfVowels() == 0) {
			lastSyllable1.add(0, new Vowel(Token.VOWEL_ANY, ""));
			
		}
		
		ArrayList<Symbol> lastSyllable2 = w2.getLastSyll();
		if(w2.getNoOfVowels() == 0) {
			lastSyllable2.add(0, new Vowel(Token.VOWEL_ANY, ""));
			
		}

			if(lastSyllable1.size() == lastSyllable2.size()) {
				
				for(int i = 0; i<lastSyllable1.size(); i++) {
					if(!(lastSyllable1.get(i).rhymesWith(lastSyllable2.get(i)))) {
						//TODO 					
						return false;
					}
				}
			}else {
				return false;
			}
		return true;
	}
	
	
	public ArrayList<Vowel> getVowels(){
		ArrayList<Vowel> vowels = new ArrayList<>();
		for(Symbol sym : symbols) {
			if(sym instanceof Vowel) {
				vowels.add((Vowel) sym);
			}
		}
		return vowels;
	}
	
	private int getIndexOfLastVowel() {
		for(int i = symbols.size()-1; i>=0; i--) {
			if(symbols.get(i).getOrigToken().type == TokenType.VOWEL) {
				return i;
			}
		}
		return -1;
	}
	
	public ArrayList<Symbol> getSymbols() {
		return symbols;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Symbol s : symbols) {
			sb.append(s.origToken + ", (" + s.symb + "); ");
		}
		return sb.toString();
	}
	
	public int getNoOfVowels() {
		int i = 0;
		for(Symbol s : symbols) {
			if(s.getOrigToken().type == TokenType.VOWEL) {
				i++;
			}
		}
		return i;
	}
	
	public boolean isEmpty() {
		return symbols.isEmpty();
	}
	
}
