package de.frag99.words;

import de.frag99.tokenizer.Token;

public class Symbol {

	//A symbol consists of multiple int combinations
	public String symb;
	public Token origToken;
	
	
	public Symbol(Token token, String symb) {
		this.origToken = token;
		this.symb = symb;
	}
	
	

	public Token getOrigToken() {
		return origToken;
	}

	public String getSymb() {
		return symb;
	}
	
	
}
