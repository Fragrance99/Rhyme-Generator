package de.frag99.xml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PageHandler extends DefaultHandler{

	private static final String outputFile = "G:\\XML DUMP\\outFR.txt";
	
	private String currWord;
	private boolean isInTitle = false;
	private boolean isInText = false;
	private StringBuilder content = new StringBuilder();
	private int debug = 0;
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("--start parsing Document");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("--finished parsing Document");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(qName.equals("title")) {
			isInTitle = true;
		}
		
		if(qName.equals("text")) {
			isInText = true;
		}
		
	}

	//for french wiktionary databse
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("title")) {
			isInTitle = false;
		}
		
		if(qName.equals("text")) {
			isInText = false;
			
			//{{Sprache|Deutsch}} {{Lautschrift|<iwas>}}
			if(content.toString().contains("== {{langue|fr}} ==")) {
				System.out.println(currWord);
				if(!(currWord.charAt(0) == '-') //sprichwort
						&& !(currWord.charAt(currWord.length()-1) == '’')) { //genitiv
					Pattern pattern = Pattern.compile("\\{\\{pron\\|(.*?)\\|");
					Matcher matcher = pattern.matcher(content);
						if(matcher.find()) {
							
							if(!matcher.group(1).isBlank()
									
									//Ausnahmen wegen inkonsistenter Datenaufteilung
									&& !matcher.group(1).contains("…") //ipa nicht vorhanden
									&& !matcher.group(1).contains("=") //ipa nicht vorhanden
									&& !matcher.group(1).contains("...") //ipa nicht vorhanden
									/*&& !matcher.group(1).contains("/") //ipa nicht vorhanden */
									&& !matcher.group(1).contains(";") //ipa nicht vorhanden
									&& !matcher.group(1).contains("—") //ipa nicht vorhanden
									/* && !matcher.group(1).contains(" ")*/ )  { //ipa enthält leerzeichen
								
								if(!currWord.contains(":")) {
									//DEBUG System.out.println(currWord+";"+matcher.group(1));
									//<WORT>;<LAUTSCHRIFT>;<SOURCE = WIKT|USER>
									
									addLineTo(currWord+";"+matcher.group(1)+
											/* ";"+"WIKT"+ */
											System.lineSeparator() ,outputFile);
								}
								
							}
							
							
						}
				}
				
				
			}
			content.setLength(0);
		}
	}
	
//	//for english wiktionary databse
//	@Override
//	public void endElement(String uri, String localName, String qName) throws SAXException {
//		if(qName.equals("title")) {
//			isInTitle = false;
//		}
//		
//		if(qName.equals("text")) {
//			isInText = false;
//			
//			//{{Sprache|Deutsch}} {{Lautschrift|<iwas>}}
//			if(content.toString().contains("==English==")) {
//				
//				if(!(currWord.charAt(0) == '-') //sprichwort
//						&& !(currWord.charAt(currWord.length()-1) == '’')) { //genitiv
//					Pattern pattern = Pattern.compile("\\{\\{IPA\\|en\\|\\/(.*?)\\/");
//					Matcher matcher = pattern.matcher(content);
//						if(matcher.find()) {
//							if(!matcher.group(1).isBlank()
//									//Ausnahmen wegen inkonsistenter Datenaufteilung
//									&& !matcher.group(1).contains("…") //ipa nicht vorhanden
//									&& !matcher.group(1).contains("=") //ipa nicht vorhanden
//									&& !matcher.group(1).contains("...") //ipa nicht vorhanden
//									/*&& !matcher.group(1).contains("/") //ipa nicht vorhanden */
//									&& !matcher.group(1).contains(";") //ipa nicht vorhanden
//									&& !matcher.group(1).contains("—") //ipa nicht vorhanden
//									/* && !matcher.group(1).contains(" ")*/ )  { //ipa enthält leerzeichen
//								
//								
//								
//								if(!currWord.contains(":")) {
//									//DEBUG System.out.println(currWord+";"+matcher.group(1));
//									//<WORT>;<LAUTSCHRIFT>;<SOURCE = WIKT|USER>
//									
//									addLineTo(currWord+";"+matcher.group(1)+
//											/* ";"+"WIKT"+ */
//											System.lineSeparator() ,outputFile);
//								}
//								
//							}
//							
//							
//						}
//				}
//				
//				
//			}
//			content.setLength(0);
//		}
//	}

//	for german wiktionary databse 
//	public void endElement(String uri, String localName, String qName) throws SAXException {
//		if(qName.equals("title")) {
//			isInTitle = false;
//		}
//		
//		if(qName.equals("text")) {
//			isInText = false;
//			
//			//{{Sprache|Deutsch}} {{Lautschrift|<iwas>}}
//			if(content.toString().contains("{{Sprache|Deutsch}}")) {
//				
//				if(!(currWord.charAt(0) == '-') //sprichwort
//						&& !(currWord.charAt(currWord.length()-1) == '’')) { //genitiv
//					Pattern pattern = Pattern.compile("\\{\\{IPA\\}\\} \\{\\{Lautschrift\\|(.*?)\\}\\}");
//					Matcher matcher = pattern.matcher(content);
//						if(matcher.find()) {
//							if(!matcher.group(1).isBlank()
//									//Ausnahmen wegen inkonsistenter Datenaufteilung
//									&& !matcher.group(1).contains("…") //ipa nicht vorhanden
//									&& !matcher.group(1).contains("=") //ipa nicht vorhanden
//									&& !matcher.group(1).contains("...") //ipa nicht vorhanden
//									&& !matcher.group(1).contains("/") //ipa nicht vorhanden
//									&& !matcher.group(1).contains(";") //ipa nicht vorhanden
//									&& !matcher.group(1).contains("—") //ipa nicht vorhanden
//									&& !matcher.group(1).contains(" ")) { //ipa enthält leerzeichen
//								if(!currWord.contains(":")) {
//									//DEBUG System.out.println(currWord+";"+matcher.group(1));
//									//<WORT>;<LAUTSCHRIFT>;<SOURCE = WIKT|USER>
//									addLineTo(currWord+";"+matcher.group(1)+
//											/* ";"+"WIKT"+ */
//											System.lineSeparator() ,outputFile);
//								}
//								
//							}
//							
//							
//						}
//				}
//				
//				
//			}
//			content.setLength(0);
//		}
//	} 
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(isInTitle) {
			currWord = new String(ch, start, length);
		}else if(isInText) {
			content.append(new String(ch, start, length));
		}
	}
	
	
	public static void addLineTo(String line, String out) {
		try {
			Files.write(Paths.get(out), line.getBytes(StandardCharsets.UTF_8),
			        StandardOpenOption.CREATE,
			        StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
