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

	private final String outputFile = "G:\\XML DUMP\\out.txt";
	String currWord;
	boolean isInTitle = false;
	boolean isInText = false;
	private StringBuilder content = new StringBuilder();
	
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

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("title")) {
			isInTitle = false;
		}
		
		if(qName.equals("text")) {
			isInText = false;
			
			//{{Sprache|Deutsch}} {{Lautschrift|<iwas>}}
			if(content.toString().contains("{{Sprache|Deutsch}}")) {
				
				if(!(currWord.charAt(0) == '-')) {
					Pattern pattern = Pattern.compile("\\{\\{Lautschrift\\|(.*?)\\}\\}");
					Matcher matcher = pattern.matcher(content);
						if(matcher.find()) {
							if(!matcher.group(1).isBlank() && !matcher.group(1).equals("…")) {
								System.out.println(currWord+";"+matcher.group(1));
								addLineTo(currWord+";"+matcher.group(1)+System.lineSeparator(), outputFile);
							}
							
							
						}
				}
				
				
			}
			content.setLength(0);
		}
	}

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
