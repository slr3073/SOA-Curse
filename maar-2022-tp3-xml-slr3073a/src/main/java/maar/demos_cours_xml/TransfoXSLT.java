package maar.demos_cours_xml;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult; // implante Result
import javax.xml.transform.stream.StreamSource; // implante Source

public class TransfoXSLT {

	public static final String RES = "src/main/resources/";

	public static void transfoXSLT(String xsl, String xml, String output)
			throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Source srcXSL = new StreamSource(new File(xsl));
		Transformer transformer = transformerFactory.newTransformer(srcXSL);
		Source source = new StreamSource(new File(xml));
		Result result = new StreamResult(new File(output));
		transformer.transform(source, result);
		System.out.println("Successfully wrote " + output);
	}

	public static void main(String[] args) {
		try {
		    final String dir = RES + "demos_cours_xml/";
			transfoXSLT(dir + "sommaire.xsl", dir + "sommaire.xml", dir + "sommaire_output.html");
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
