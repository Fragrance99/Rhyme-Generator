package de.frag99.words;

import de.frag99.tokenizer.Token;

public class Vowel extends Symbol{

	//gives the syllable position

	private boolean isAcc;
	
	

	public Vowel(Token token, String symb, boolean isAcc) {
		super(token, symb);
		this.isAcc = isAcc;
	}
	
	public boolean rhymesWith(Symbol v) {
		//Maybe: check for accentation, reason not to: words with only one syllable are by default accentuated
		if(this.getOrigToken().getRhymeClas() == RhymeClass.OTHER) {
			return this.isEqualTo(v);
		}
		return this.getOrigToken().getRhymeClas() == v.getOrigToken().getRhymeClas();
	}
	
	public boolean isEqualTo(Symbol v) {
		//Maybe: check for accentation, reason not to: words with only one syllable are by default accentuated
		return this.origToken == v.getOrigToken();
	}
	
	public boolean isAccentuated() {
		return isAcc;
	}
	
	

	
}
