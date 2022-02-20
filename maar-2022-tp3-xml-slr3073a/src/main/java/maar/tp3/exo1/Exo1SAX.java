package maar.tp3.exo1;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Exo1SAX extends DefaultHandler {
	public ArrayList<Integer> nbH = new ArrayList<>();
	public ArrayList<Integer> nbF = new ArrayList<>();
	public ArrayList<String> journals = new ArrayList<>();
	public boolean inJournal = false;

	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (qName.equals("journal")){
			nbH.add(0);
			nbF.add(0);
		}
		if(qName.equals("titleISI"))inJournal = true;

		if(qName.equals("researcher"))
			if(attributes.getValue(0).equals("m"))
				nbH.set(nbH.size() - 1, nbH.get(nbH.size() - 1) + 1);
			else
				nbF.set(nbF.size() - 1, nbF.get(nbF.size() - 1) + 1);
	}

	public void endElement(String uri, String localName, String qName) {
		if(qName.equals("titleISI")) inJournal = false;
	}

	public void characters(char[] ch, int start, int length) {
		journals.add(String.copyValueOf(ch, start, length));
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		Exo1SAX instanceSAX = new Exo1SAX();
        saxParser.parse(new File("src/main/resources/exo1/EB_IS_2009.xml"), instanceSAX);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		for (int i = 0; i < instanceSAX.nbH.size(); i++) {
			float pcH = (float) instanceSAX.nbH.get(i) / (instanceSAX.nbH.get(i) + instanceSAX.nbF.get(i)) * 100;
			float pcF = (float) instanceSAX.nbF.get(i) / (instanceSAX.nbH.get(i) + instanceSAX.nbF.get(i)) * 100;
			System.out.println(instanceSAX.journals.get(i) + " : (H) " + df.format(pcH) + " % (F) " + df.format(pcF) + " %");
		}

	}
}
