package maar.tp3.exo3;

import maar.demos_cours_xml.TransfoXSLT;

import javax.xml.transform.TransformerException;

public class Exo3XSL {

    public static final String DIR = TransfoXSLT.RES + "exo3/";

    public static void main(String[] args) throws TransformerException {

        TransfoXSLT.transfoXSLT(DIR + "FilmProvider1.xsl", DIR + "FilmProvider_xsd.xml", DIR + "FilmProvider1_out.html");
        TransfoXSLT.transfoXSLT(DIR + "FilmProvider2.xsl", DIR + "FilmProvider_xsd.xml", DIR + "FilmProvider2_out.txt");
        TransfoXSLT.transfoXSLT(DIR + "FilmProvider3.xsl", DIR + "FilmProvider_xsd.xml", DIR + "FilmProvider3_out.html");
        // TransfoXSLT.transfoXSLT(DIR + "FilmProvider4.xsl", DIR + "FilmProvider_xsd.xml", DIR + "FilmProvider4_out.html");

    }
}
