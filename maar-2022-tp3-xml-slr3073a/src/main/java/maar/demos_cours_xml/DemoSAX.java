package maar.demos_cours_xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class DemoSAX extends DefaultHandler {

	public static void main(String[] args) throws Exception {
		String filename = (args.length > 0) ? args[0] : "src/main/resources/demos_cours_xml/sommaire.xml";
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxparser = factory.newSAXParser();
		saxparser.parse(new File(filename), new DemoSAX());
	}

	// Remarque : ce code ne récupère pas le contenu des nœuds texte
	// => voir exercice 1

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attrs) {
		System.out.println(qName);
		for (int i = 0; i < attrs.getLength(); i++) {
			String name2 = attrs.getQName(i);
			String type = attrs.getType(i);
			String value = attrs.getValue(i);
			System.out.println("- " + name2 + " : " + type + " = " + value);
		}
	}

	@Override
	public void startDocument() {
		System.out.println("*** START");
	}

	@Override
	public void endDocument() {
		System.out.println("*** END");
	}
}
