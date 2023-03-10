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
		
		//for 1-syll words -> no acc markigns but they are of course accentuated
		boolean hasAccMarkings = false;
		boolean nextVowAcc = false;
		for(Token t : tokens) {
			if(t.getTokenType() == TokenType.EMPH) {
				hasAccMarkings = true;
				break;
			}
		}
		

		if(!hasAccMarkings) {
			symbols.add(new Symbol(Token.EMPH_MAIN, "ˈ")); //such that words without markings(=1 syll words) always have acc
			nextVowAcc = true;
		}
				
		int symbolIndex = 0;
		for(Token t : tokens) {
			
			switch (t.type) {
			case VOWEL:
				symbols.add(new Vowel(t, rawSymbols.get(symbolIndex), nextVowAcc));
				nextVowAcc = false;
				break;			
			case CONSONANT:
				symbols.add(new Symbol(t, rawSymbols.get(symbolIndex)));
				break;		
			case EMPH:
				nextVowAcc = true;
				symbols.add(new Symbol(t, "ˈ"));
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
			if(s.getOrigToken().getTokenType() == TokenType.VOWEL) {
				vowels1.add((Vowel) s);
			}
		}
		
		for(Symbol s : w2.getSymbols()) {
			if(s.getOrigToken().getTokenType() == TokenType.VOWEL) {
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
	}
	
	public boolean hasSameVowelsAs(Word w2) {
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
					if(!vowels1.get(i).isEqualTo(vowels2.get(i))) {
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
	

	
	public ArrayList<Symbol> getLastRelevantSyll(){
		//returns symbols starting from the penultimate vowel if the last vowel is not accentuated
		//otherwise returns symbols starting from the last vowel
		ArrayList<Symbol> lastSyllable = new ArrayList<>();
		
		int i = getIndexOfLastRelevantVowel();
		if(i>=0) {
			while(i<symbols.size()) {
				
				lastSyllable.add(symbols.get(i));
				//immernoch in lautschrift
				i++;
			}
		}else {
			return this.symbols;
		}
		
			
		return lastSyllable;
	}
	
	private int getIndexOfLastRelevantVowel() {
		
		for(int i = symbols.size()-1; i>=0; i--) {
			if(symbols.get(i).getOrigToken().type == TokenType.VOWEL) {
				if(!((Vowel)symbols.get(i)).isAccentuated()){
					for(int j = i-1; j>=0; j--) {
						if(symbols.get(j).getOrigToken().type == TokenType.VOWEL) {
							return j;
						}
					}
				}
				
				return i;
			}
		}
		return -1;
	}
	
	public boolean hasSameLastRelSyllAs(Word w2) {
		
		ArrayList<Symbol> lastSyllable1 = this.getLastRelevantSyll();
//		for(Symbol s : lastSyllable1) {
//			System.out.print(s.getOrigToken() + " ");
//		}
//		System.out.println("");
		ArrayList<Symbol> lastSyllable2 = w2.getLastRelevantSyll();
//		for(Symbol s : lastSyllable2) {
//			System.out.print(s.getOrigToken() + " ");
//		}
//		System.out.println("");
			if(lastSyllable1.size() == lastSyllable2.size()) {
				
				for(int i = 0; i<lastSyllable1.size(); i++) {
					if(!(lastSyllable1.get(i).isEqualTo(lastSyllable2.get(i)))) {
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
	
	public String getSymbolsAsString() {
		StringBuilder sb = new StringBuilder();
		for(Symbol s : symbols) {
			sb.append(s.getSymb());
		}
		return sb.toString();
	}
	
}
