package de.frag99.words;

import de.frag99.tokenizer.Token;

public class Symbol {

	//A symbol consists of multiple int combinations
	public int[] decIDs;
	public Token origToken;
	
	
	public Symbol(Token token) {
		this.origToken = token;
		int[] decIDs = token.symbolID.clone();
	}
	
	

	public Token getOrigToken() {
		return origToken;
	}

	
	
	
}
