package de.frag99.words;

import de.frag99.tokenizer.Token;

public class Vowel extends Symbol{

	//gives the syllable position
	private int position;
	private RhymeClass rhymeClass;
	
	

	public Vowel(Token token, int pos) {
		super(token);
		this.position = pos;
	}
	
	public boolean rhymesWith(Vowel v) {
		//now: check if symbols are identical, later: check if in same RhymeClass
		if(this.origToken.equals(v.getOrigToken()) && this.position == v.getPosition()) {
			return true;
		}
		return false;
	}
	
	public int getPosition() {
		return position;
	}
	
}
