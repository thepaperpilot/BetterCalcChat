import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

class BookHandler extends DefaultHandler {

final ArrayList<Chapter> chapters = new ArrayList<Chapter>();
private Chapter chapter;
private Section section;

/*
	* Every time the parser encounters the beginning of a new element,
	* it calls this method, which resets the string buffer
	*/
public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	if(qName.equalsIgnoreCase("CHAPTER")) {
		chapter = new Chapter();
		chapter.setName(attributes.getValue("NAME"));
	} else if(qName.equalsIgnoreCase("SECTION")) {
		section = new Section();
		section.setName(attributes.getValue("NAME"));
		section.setPre(attributes.getValue("PRE"));
		section.setLastProblem(attributes.getValue("NUM_EX"));
	}
}

/*
	* When the parser encounters the end of an element, it calls this method
	*/
public void endElement(String uri, String localName, String qName)
		throws SAXException {

	if(qName.equalsIgnoreCase("CHAPTER")) {
		chapters.add(chapter);
	} else if(qName.equalsIgnoreCase("SECTION")) {
		chapter.addSection(section);
	}
}
}
