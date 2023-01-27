package de.frag99.words;

import de.frag99.tokenizer.Token;

public class Vowel extends Symbol{

	//gives the syllable position
	
	private RhymeClass rhymeClass;
	private boolean isAcc;
	
	

	public Vowel(Token token, String symb) {
		super(token, symb);
		
	}
	
	public boolean rhymesWith(Symbol v) {
		//now: check if symbols are identical, later: check if in same RhymeClass

		if(this.origToken == v.getOrigToken()) {
			return true;
		}
		if(this.origToken == Token.VOWEL_ANY || v.origToken == Token.VOWEL_ANY) {
			return true;
		}
		return false;
	}
	
	
	
	

	
}
