package maar.demos_cours_xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DemoDOM {

	public static void main(String[] args) {
		String filename = (args.length > 0) ? args[0] : "src/main/resources/demos_cours_xml/exemple.xml";
		try {
			// création d'une fabrique de constructeur de documents DOM
			DocumentBuilderFactory fabrique = DocumentBuilderFactory
					.newInstance();

			// création d'un constructeur de documents DOM
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();

			// lecture du contenu d'un fichier XML avec le constructeur pour
			// créer le document DOM correspondant
			File xmlFile = new File(filename);
			Document document = constructeur.parse(xmlFile);

			// TRAITEMENT DU DOCUMENT
			printDocument(document);
		} catch (ParserConfigurationException pce) {
			System.out.println("Erreur de configuration du parseur DOM");
		} catch (SAXException se) {
			System.out.println("Erreur lors du parsing du document");
		} catch (IOException ioe) {
			System.out.println("Erreur d'entrée/sortie");
		}
	} // Fin du main()

	public static void printNode(Node node) {
		System.out.println(node);
		// same as:
		// System.out.println("[" + node.getNodeName() + ": " + node.getNodeValue() + "]");

        if (node.hasChildNodes()) {
			// System.out.println(" a des éléments fils : {");
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node n = nodes.item(i);
				printNode(n);
			}
			// System.out.println("}");
		}
	}

	public static void printDocument(Document document) {
		Element racine = document.getDocumentElement();
		printNode(racine);
	}

}
