package de.frag99.words;

import de.frag99.tokenizer.Token;

public class Vowel extends Symbol{

	//gives the syllable position
	
	private RhymeClass rhymeClass;
	private boolean isAcc;
	
	

	public Vowel(Token token, String symb, boolean isAcc) {
		super(token, symb);
		this.isAcc = isAcc;
	}
	
	public boolean rhymesWith(Symbol v) {
		//now: check if symbols are identical, later: check if in same RhymeClass
		//Maybe: check for accentation, reason not to: words with only one syllable are by default accentuated
		if(this.origToken == v.getOrigToken()) {
			return true;
		}
		return false;
	}
	
	public boolean isAccentuated() {
		return isAcc;
	}
	
	

	
}
