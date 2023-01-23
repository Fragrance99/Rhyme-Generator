package de.frag99.words;

import de.frag99.tokenizer.Token;

public class Symbol {

	
	public String symb;
	public Token origToken;
	
	
	public Symbol(Token token, String symb) {
		this.origToken = token;
		this.symb = symb;
	}
	
	public boolean rhymesWith(Symbol s) {
		if(this.symb.equals(s.symb)) {
			return true;
		}
		
		return false;
	}

	public Token getOrigToken() {
		return origToken;
	}

	public String getSymb() {
		return symb;
	}
	
	
}
