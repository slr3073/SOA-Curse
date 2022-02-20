package maar.tp3.exo2;

import maar.tp3.exo1.Exo1SAX;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import static javax.xml.stream.XMLStreamConstants.*;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.function.Function;

// Squelette inspiré de https://docs.oracle.com/cd/E13222_01/wls/docs92/xml/stax.html

public class Exo2Stax {

	private static void printName(String prefix, String uri, String localName) {
		if (uri != null && !("".equals(uri)) ) System.out.print("['"+uri+"']:");
		if (prefix != null && !("".equals(prefix))) System.out.print(prefix+":");
		if (localName != null) System.out.print(localName);
	}

	private static void printNamespace(String prefix, String uri) {
		System.out.print(" ");
		if (prefix == null)
			System.out.print("xmlns='"+uri+"'");
		else
			System.out.print("xmlns:"+prefix+"='"+uri+"'");
	}	

    /**
     * Fonction pour répéter une série de "xmlr.next()" en fonction d'un prédicat booléen
     * @param xmlr de type XMLStreamReader
     * @param fct prédicat booléen à appliquer sur chaque item du flot, jusqu'à-ce qu'il renvoie true
     * @return true ssi xmlr.next() a été exécuté au moins 1 fois ET fct(xmlr)==true au dernier appel
     *         false sinon (ce qui implique que xmlr.hasNext()==false)
     * @throws XMLStreamException
     */
	private static boolean nextUntil(XMLStreamReader xmlr, Function<XMLStreamReader, Boolean> fct) throws XMLStreamException {
		boolean lastResult = false;
		boolean doContinue = xmlr.hasNext();
		while(doContinue) {
			xmlr.next();
			lastResult = fct.apply(xmlr);
			doContinue = !lastResult && xmlr.hasNext();  
		}
		return lastResult;
	}
	
	private static void processStream(XMLStreamReader xmlr) throws XMLStreamException {
		// Ce booléen (qui est true par défaut) doit être mis à false avant d'appeler
		// nextUntil, pour éviter d'exécuter xmlr.next() une fois de trop.  
		boolean doNext = true;
		while(xmlr.hasNext()){
			switch (xmlr.getEventType()) {
			case XMLStreamConstants.START_ELEMENT:
				// xmlr.hasName() is true
				String prefix = xmlr.getPrefix();
				String uri = xmlr.getNamespaceURI();
				String localName = xmlr.getLocalName();
				System.out.print("<");
				printName(prefix, uri, localName);
				for (int idx=0; idx < xmlr.getNamespaceCount(); idx++) {
					String xmlns_prefix = xmlr.getNamespacePrefix(idx);
					String xmlns_uri = xmlr.getNamespaceURI(idx);
					printNamespace(xmlns_prefix, xmlns_uri);
				}
				for (int idx=0; idx < xmlr.getAttributeCount(); idx++) {
					String att_prefix = xmlr.getAttributePrefix(idx);
					String att_namespace = xmlr.getAttributeNamespace(idx);
					String att_localName = xmlr.getAttributeLocalName(idx);
					String att_value = xmlr.getAttributeValue(idx);
					System.out.print(" ");
					printName(att_prefix, att_namespace, att_localName);
					System.out.print("='" + att_value + "'");
				}
				System.out.print(">");

				// Skip immediately to the next END_ELEMENT or START_ELEMENT
				//doNext = false; nextUntil(xmlr, (x -> {int type = x.getEventType(); return (type == END_ELEMENT || type == START_ELEMENT);}));
                break;

			case XMLStreamConstants.END_ELEMENT:
				// xmlr.hasName() is true
				String end_prefix = xmlr.getPrefix();
				String end_uri = xmlr.getNamespaceURI();
				String end_localName = xmlr.getLocalName();
				System.out.print("</");
				printName(end_prefix, end_uri, end_localName);
				System.out.print(">");

				// Skip immediately to the next END_ELEMENT or START_ELEMENT
				//doNext = false; nextUntil(xmlr, (x -> {int type = x.getEventType(); return (type == END_ELEMENT || type == START_ELEMENT);}));
				break;

			case XMLStreamConstants.SPACE:

			case XMLStreamConstants.CHARACTERS:
				int start = xmlr.getTextStart();
				int length = xmlr.getTextLength();
				String text = new String(xmlr.getTextCharacters(), start, length);
				System.out.println(text);
				String content = text.trim();
				break;

			case XMLStreamConstants.PROCESSING_INSTRUCTION:
				System.out.print("<?");
				if (xmlr.hasText())
					System.out.print(xmlr.getText());
				System.out.print("?>");
				break;

			case XMLStreamConstants.CDATA:
				System.out.print("<![CDATA[");
				start = xmlr.getTextStart();
				length = xmlr.getTextLength();
				System.out.print(new String(xmlr.getTextCharacters(),
						start,
						length));
				System.out.print("]]>");
				break;

			case XMLStreamConstants.COMMENT:
				System.out.print("<!--");
				if (xmlr.hasText())
					System.out.print(xmlr.getText());
				System.out.print("-->");
				break;

			case XMLStreamConstants.ENTITY_REFERENCE:
				System.out.print(xmlr.getLocalName()+"=");
				if (xmlr.hasText())
					System.out.print("["+xmlr.getText()+"]");
				break;

			case XMLStreamConstants.START_DOCUMENT:
			/*	System.out.print("<?xml");
				System.out.print(" version='"+ (xmlr.getVersion() != null ? xmlr.getVersion() : "1.0") +"'");
				System.out.print(" encoding='"+ (xmlr.getCharacterEncodingScheme() != null ? xmlr.getCharacterEncodingScheme() : "utf-8") +"'");
				if (xmlr.isStandalone())
					System.out.print(" standalone='yes'");
				else
					System.out.print(" standalone='no'");
				System.out.println("?>"); */
				break;
			} // switch
			if (doNext) {
				xmlr.next();
			} else {
				doNext = true;
			}
		} // while
	} // void

	/**
	 * @param args
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws IOException, XMLStreamException {

		XMLInputFactory xmlif = XMLInputFactory.newInstance();

		XMLStreamReader xmlr = xmlif.createXMLStreamReader(new FileReader("src/main/resources/exo2/psd7003_pv.xml"));

		processStream(xmlr);

		xmlr.close();
	}
}
