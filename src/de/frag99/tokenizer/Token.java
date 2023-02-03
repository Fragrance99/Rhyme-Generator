package de.frag99.tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.frag99.words.RhymeClass;

public enum Token {

	//3 characters long
	VOWEL_ONS_LONG ("ɑ̃ː", TokenType.VOWEL, 			RhymeClass.VOWEL_ONS), //Nuance(2), Engangement(3)
	VOWEL_AIN_LONG ("ɛ̃ː", TokenType.VOWEL), //Refrain(2), Pointe(2)
	VOWEL_ONDS_LONG ("ɔ̃ː", TokenType.VOWEL,			RhymeClass.VOWEL_ONS), //Fond
	VOWEL_AI ("aɪ̯", TokenType.VOWEL, 				RhymeClass.VOWEL_AI), //Mai, sein
	VOWEL_AU ("aʊ̯", TokenType.VOWEL, 				RhymeClass.VOWEL_AU), //auf, Clown
	VOWEL_EY ("ɛɪ̯", TokenType.VOWEL, 				RhymeClass.VOWEL_EY), //hey, Lady(1)
	VOWEL_EU ("ɔɪ̯", TokenType.VOWEL, 				RhymeClass.VOWEL_EU), //neu, Boy
	VOWEL_OUGH ("ɔʊ̯", TokenType.VOWEL, 				RhymeClass.VOWEL_OUGH), //Show, Donut(1)
	VOWEL_UI ("ʊɪ̯", TokenType.VOWEL), //pfui
	
	//2 characters long
	VOWEL_AI_DUPLICATE ("aɪ", TokenType.VOWEL, 		RhymeClass.VOWEL_AI),
	VOWEL_OUGH_DUPLICATE3 ("ɔʊ", TokenType.VOWEL, 	RhymeClass.VOWEL_OUGH),
	VOWEL_OUGH_DUPLICATE2 ("oʊ", TokenType.VOWEL, 	RhymeClass.VOWEL_OUGH),
	VOWEL_OUGH_DUPLICATE ("əʊ", TokenType.VOWEL, 	RhymeClass.VOWEL_OUGH),
	VOWEL_EU_DUPLICATE3 ("oɪ", TokenType.VOWEL, 	RhymeClass.VOWEL_EU),
	VOWEL_EU_DUPLICATE ("eu", TokenType.VOWEL, 		RhymeClass.VOWEL_EU),
	VOWEL_EU_DUPLICATE2 ("ɔɪ", TokenType.VOWEL, 	RhymeClass.VOWEL_EU),
	VOWEL_EY_DUPLICATE ("eɪ", TokenType.VOWEL, 		RhymeClass.VOWEL_EY),
	VOWEL_EY_DUPLICATE2 ("ei", TokenType.VOWEL, 	RhymeClass.VOWEL_EY),
	VOWEL_AU_DUPLICATE ("aʊ", TokenType.VOWEL, 		RhymeClass.VOWEL_AU),
	VOWEL_A_LONG ("aː", TokenType.VOWEL, 			RhymeClass.VOWEL_A), //haben(1), Staat
	VOWEL_ER_SHORT ("ɐ̯", TokenType.VOWEL), //der, über(2)
	VOWEL_ONS ("ɑ̃", TokenType.VOWEL, 				RhymeClass.VOWEL_ONS), //Engagement(1)
	VOWEL_E_LONG ("eː", TokenType.VOWEL, 			RhymeClass.VOWEL_E), //Idee(2)
	VOWEL_AE_LONG ("ɛː", TokenType.VOWEL), //Fan, Dessert(2)
	VOWEL_OMB ("ɛ̃", TokenType.VOWEL), //timbrieren(1)
	VOWEL_IE ("iː", TokenType.VOWEL, 				RhymeClass.VOWEL_I), //die, Keyboard(1)
	VOWEL_I_SHORT ("i̯", TokenType.VOWEL), //Million(2)
	VOWEL_O_LONG ("oː", TokenType.VOWEL, 			RhymeClass.VOWEL_O), //Boot, Niveau(2)
	VOWEL_O_SHORT ("o̯", TokenType.VOWEL), //Toillette(1)
	VOWEL_ONDS ("ɔː", TokenType.VOWEL, 				RhymeClass.VOWEL_ONS), //Default(1)
	VOWEL_ONDS_SHORT ("ɔ̃", TokenType.VOWEL, 		RhymeClass.VOWEL_ONS), //Fondue(1)
	VOWEL_OE_LONG ("øː", TokenType.VOWEL, 			RhymeClass.VOWEL_OE), //gehören(2), Goethe(1)
	VOWEL_UH_LONG ("œː", TokenType.VOWEL, 			RhymeClass.VOWEL_OE), //Burger(1)
	VOWEL_AUH ("œ̃", TokenType.VOWEL), //Lundist(1)
	VOWEL_U_LONG ("uː", TokenType.VOWEL, 			RhymeClass.VOWEL_U), //zu, cool
	VOWEL_U_SHORT ("u̯", TokenType.VOWEL), //Linguistik(2)
	VOWEL_UE_LONG ("yː", TokenType.VOWEL,			RhymeClass.VOWEL_UE), //für, Revue(2)
	VOWEL_UE_SHORT ("y̑", TokenType.VOWEL), //Etui(2)
	
	VOWEL_EN_VERYSHORT ("n̩", TokenType.VOWEL),
	VOWEL_EM_VERYSHORT ("m̩", TokenType.VOWEL),
	VOWEL_EL_VERYSHORT ("l̩", TokenType.VOWEL),
	
	//1 characters long
	VOWEL_ER_VERYSHORT ("ɚ", TokenType.VOWEL, 		RhymeClass.VOWEL_A),
	
	VOWEL_A ("a", TokenType.VOWEL, 					RhymeClass.VOWEL_A), //das, Country(1)
	VOWEL_AEH ("ʌ", TokenType.VOWEL, 				RhymeClass.VOWEL_A), //butter(british)(2)
	VOWEL_ER ("ɐ", TokenType.VOWEL, 				RhymeClass.VOWEL_A), //über
	VOWEL_E ("e", TokenType.VOWEL, 					RhymeClass.VOWEL_E), //jedoch(1)
	VOWEL_EH ("ɘ", TokenType.VOWEL), 
	VOWEL_AE ("ɛ", TokenType.VOWEL, 				RhymeClass.VOWEL_E), //es, Camp
	VOWEL_AEOEH ("ɜ", TokenType.VOWEL, 				RhymeClass.VOWEL_OE), //bird(british)
	VOWEL_AO ("ɒ", TokenType.VOWEL),
	VOWEL_AEOEH_DUPLICATE ("ɝ", TokenType.VOWEL),
	VOWEL_AE_DUPLICATE ("æ", TokenType.VOWEL, 		RhymeClass.VOWEL_AE), //smash
	VOWEL_AE_SHORT ("ə", TokenType.VOWEL, 			RhymeClass.VOWEL_AE), //Alle(2)
	VOWEL_I ("i", TokenType.VOWEL, 					RhymeClass.VOWEL_I), //Minute(1)
	VOWEL_I_NASAL ("ĩ", TokenType.VOWEL), //
	VOWEL_I_DULL ("ɨ", TokenType.VOWEL),
	VOWEL_I_VERYSHORT ("ɪ", TokenType.VOWEL), //in
	VOWEL_UH_DULL ("ɯ", TokenType.VOWEL),
	VOWEL_O ("o", TokenType.VOWEL, 					RhymeClass.VOWEL_O), //Prozent
	VOWEL_OE_CLOSED ("ɤ", TokenType.VOWEL),
	VOWEL_ONDS_VERYSHORT ("ɔ", TokenType.VOWEL), //von
	VOWEL_ONDS_NASAL ("õ", TokenType.VOWEL, 		RhymeClass.VOWEL_ONS),
	VOWEL_ONG ("ɑ", TokenType.VOWEL), //calm
	VOWEL_OE ("ø", TokenType.VOWEL,		 			RhymeClass.VOWEL_OE), //öko(1)
	VOWEL_OE_LIGHT ("ɵ", TokenType.VOWEL),
	VOWEL_OE_LIGHT_OPEN ("ɞ", TokenType.VOWEL),
	VOWEL_AEOE ("ɶ", TokenType.VOWEL),
	VOWEL_OE_SHORT ("œ", TokenType.VOWEL), //können(1)
	VOWEL_U ("u", TokenType.VOWEL, 					RhymeClass.VOWEL_U), //Tourist(1)
	VOWEL_U_VERYSHORT ("ʊ", TokenType.VOWEL), //und
	VOWEL_UE ("y", TokenType.VOWEL, 				RhymeClass.VOWEL_UE), //Büro
	VOWEL_UE_VERYSHORT ("ʏ", TokenType.VOWEL), //müssen(1)
	VOWEL_UE_CLOSED("ũ", TokenType.VOWEL),
	VOWEL_UE_OPEN ("ʉ", TokenType.VOWEL),
	
	EMPH_MAIN ("ˈ", TokenType.EMPH),
	EMPH_SEC ("ˌ", TokenType.EMPH),
	
	SYLL_DELIMITER ("\\.", TokenType.OTHER),
	OPT_SYLL_START ("\\(", TokenType.OTHER),
	OPT_SYLL_END ("\\)", TokenType.OTHER),
	WS (" ", TokenType.OTHER),
	ESCAPE ("\\\\", TokenType.OTHER),
	CR ("\\r", TokenType.OTHER),
	LF ("\\n", TokenType.OTHER),
	SEMICOLON (";", TokenType.OTHER),
	
	CONSONANT (".?", TokenType.CONSONANT), //all other symbols, interpreted as consonants for now
	
	VOWEL_ANY ("", TokenType.VOWEL);
	
	
	public final Pattern pattern;
	public final String symbol;
	public final TokenType type;
	public final RhymeClass rhymeclass;
	
	public String getSymbol() {
		return symbol;
	}
	
	public RhymeClass getRhymeClas() {
		return this.rhymeclass;
	}
	
	Token(String regex, TokenType type){
		this.pattern = Pattern.compile("^" + regex);	
		this.rhymeclass = RhymeClass.OTHER;
		this.symbol = regex;
		this.type = type;
	}
	
	Token(String regex, TokenType type, RhymeClass rhymeclass){
		this.pattern = Pattern.compile("^" + regex);	
		this.rhymeclass = rhymeclass;
		this.symbol = regex;
		this.type = type;
	}
	
	public TokenType getTokenType() {
		return type;
	}
	
	int endOfMatch(String s) {
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            return m.end();
        }
        return -1;
    }
	
}
