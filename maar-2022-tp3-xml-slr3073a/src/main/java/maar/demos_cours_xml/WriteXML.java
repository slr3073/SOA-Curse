package maar.demos_cours_xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class WriteXML {

    public static void main(String[] args) {
    	String output = (args.length > 0) ? args[0] : "src/main/resources/demos_cours_xml/vins.xml";

        final String[][] vins = {{"Sancerre", "2004", "12"},
            {"Riesling d'Alsace", "2007", "6"}, {"Chablis", "2012", "12"}};

        try {
            // Création d'une instance de document
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            // Construction de l'arbre
            Element root = doc.createElement("Stock");
            for (int i = 0; i < vins.length; i++) {
                String[] vin = vins[i];
                Element eltVin = doc.createElement("vin");
                eltVin.setAttribute("appellation", vin[0]);
                eltVin.setAttribute("annee", vin[1]);
                Element eltQuantite = doc.createElement("quantite");
                eltQuantite.appendChild(doc.createTextNode(vin[2]));
                eltVin.appendChild(eltQuantite);
                root.appendChild(eltVin);
            }
            doc.appendChild(root);
            // Génération du fichier XML
            File ficXML = new File(output);
            BufferedWriter out = new BufferedWriter(new FileWriter(ficXML));
            generateXMLFile(out, doc);
            out.close();
            System.out.println("Successfully wrote " + output);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Implémentation naïve
     */
    private static void generateXMLFileNaive(BufferedWriter out, Node node)
            throws Exception {
        switch (node.getNodeType()) {
        // Traitement d'un noeud document : écriture de la ligne d'en-tête
        case Node.DOCUMENT_NODE:
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            generateXMLFileNaive(out, ((Document) node).getDocumentElement());
            break;
        // Traitement d'un noeud texte : affichage du texte
        case Node.TEXT_NODE:
            out.write(node.getNodeValue()); break;
        // Traitement d'un noeud élément : construction de l'élément
        case Node.ELEMENT_NODE:
            out.newLine();
            out.write("<" + node.getNodeName());
            NamedNodeMap attrs = node.getAttributes();
            // construction des attributs de l'élément
            for (int i = 0; i < attrs.getLength(); i++) {
                Node attr = attrs.item(i);
                out.write(" " + attr.getNodeName() + "=\"");
                out.write(attr.getNodeValue() + "\"");
            }
            out.write(">");
            // construction récursive des éléments fils de l'élément
            NodeList children = node.getChildNodes();
            if (children != null) {
                for (int i = 0; i < children.getLength(); i++) {
                    generateXMLFileNaive(out, children.item(i));
                }
            }
            out.write("</" + node.getNodeName() + ">");
            break;
        }
    }

    /**
     * Implémentation plus élégante, qui indente le XML produit
     */
    private static void generateXMLFile(BufferedWriter out, Document node)
            throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(node);
        StreamResult result = new StreamResult(out);
        transformer.transform(source, result);
    }

}
