package maar.tp3.exo4;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Exo4DOM {

	public void printNode(Node node) {
		System.out.println(node);
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			printNode(n);
		}
	}

	public static void main (String [] args) throws ParserConfigurationException, SAXException, IOException, URISyntaxException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(new File("src/main/resources/exo1/EB_IS_2009.xml"));

		Exo4DOM printer = new Exo4DOM();
		printer.printNode(doc);
	}
}
