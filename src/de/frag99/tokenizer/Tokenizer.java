package de.frag99.tokenizer;

import java.util.ArrayList;

import de.frag99.words.Word;

public class Tokenizer {

	private StringBuilder input = new StringBuilder();
	private Token token;
	private boolean exhausted = false;
	private String rawSymbol;
	
	public Tokenizer(String rawInput) {
		
		this.input = this.input.append(rawInput);
		moveAhead();
		
	}
	
	private void moveAhead() {
		if(exhausted) {
			return;
		}
		
		if(input.length() == 0) {
			exhausted = true;
			return;
		}
		
		if(findNextToken()) {
			return;
		}
		
		exhausted = true;
		
		
	}
	
	private boolean findNextToken() {
		
		for(Token t : Token.values()){
			int end = t.endOfMatch(input.toString());
			
			
			if(end!=-1) {
				token = t;
				rawSymbol = input.substring(0, end);
				input.delete(0, end);
				return true;			
			}
		}
		return false;

	}

	public Word tokenize() {
		
		Word wordIPA = new Word();
		ArrayList<Token> tokens = new ArrayList<>();
		ArrayList<String> symbols = new ArrayList<>();
		
		while(!exhausted) {
			
			tokens.add(token);
			symbols.add(rawSymbol);
			moveAhead();
		}
		wordIPA.setSymbols(tokens, symbols);
		return wordIPA;
	}
	
	
}
