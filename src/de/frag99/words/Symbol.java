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
		//suggestion: add rhyme classes for consonants
		return this.getSymb().equals(s.getSymb());
	}
	
	public boolean isEqualTo(Symbol s) {
		if(this.getSymb().equals(s.getSymb())) {
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
