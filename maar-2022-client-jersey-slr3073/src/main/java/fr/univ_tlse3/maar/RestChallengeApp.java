package fr.univ_tlse3.maar;

import org.javatuples.Pair;
import org.xml.sax.InputSource;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.function.BiFunction;

public class RestChallengeApp implements IRestChallenge {
    private static final String URI = "https://rest-challenge-2022.herokuapp.com";
    private static final Long min = 0L;
    private static final Long max = 4294967296L;

    public static void main(String[] args) {
        RestChallengeApp app = new RestChallengeApp();
        System.out.println(app.find(app.getToken_jaxb(), min, max, app::guess_jsonb));
        System.out.println(app.find(app.getToken_jaxp(), min, max, app::guess_jsonb));
        System.out.println(app.find(app.getToken_jaxb(), min, max, app::guess_jsonp));
        System.out.println(app.find(app.getToken_jaxp(), min, max, app::guess_jsonp));
    }

    @Override
    public String getToken_jaxb() {
        Client client = ClientBuilder.newClient();
        // Oui je sais je n'ai pas mis mon Bean dans le get() car je n'arrive pas à le faire fonctionner
        // autrement que ci-dessous
        String response = client.target(URI).path("init").queryParam("quad", "SLR")
                .request().accept(MediaType.TEXT_XML).get(String.class);
        try {
            JAXBContext context = JAXBContext.newInstance(Token.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(response);
            Token parsedToken = (Token) unmarshaller.unmarshal(reader);
            return parsedToken.token;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getToken_jaxp() {
        Client client = ClientBuilder.newClient();
        String response = client.target(URI).path("init").queryParam("quad", "SLR")
                .request().accept(MediaType.TEXT_XML).get(String.class);

        try {
            InputSource inputXML = new InputSource(new StringReader(response));
            XPath xPath = XPathFactory.newInstance().newXPath();
            return xPath.evaluate("/Token/token", inputXML);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pair<String, Integer> guess_jsonb(String token, long guess) {
        Client client = ClientBuilder.newClient();
        //alors qu'ici ça marche très bien ...
        HintMessage response = client.target(URI).path("try").queryParam("token", token).queryParam("guess", guess)
                .request().accept(MediaType.APPLICATION_JSON).get(HintMessage.class);

        switch (response.status) {
            case "too-small":
                return new Pair<>(response.status, -1);
            case "too-large":
                return new Pair<>(response.status, 1);
            case "Bravo-again":
            case "Bravo-top100":
            case "Bravo":
                return new Pair<>(response.status, 0);
            default:
                return new Pair<>(null, null);
        }
    }

    @Override
    public Pair<String, Integer> guess_jsonp(String token, long guess) {
        Client client = ClientBuilder.newClient();
        JsonObject response = client.target(URI).path("try").queryParam("token", token).queryParam("guess", guess)
                .request().accept(MediaType.APPLICATION_JSON).get(JsonObject.class);

        switch (response.get("status").toString()) {
            case "\"too-small\"":
                return new Pair<>(response.get("status").toString(), -1);
            case "\"too-large\"":
                return new Pair<>(response.get("status").toString(), 1);
            case "\"Bravo-again\"":
            case "\"Bravo-top100\"":
            case "\"Bravo\"":
                return new Pair<>(response.get("status").toString(), 0);
            default:
                return new Pair<>(null, null);
        }
    }

    @Override
    public String find(String token, long min, long max, BiFunction<String, Long, Pair<String, Integer>> guessFunction) {
        long numberToGuess = min + (max - min) / 2;
        Pair<String, Integer> guess = guessFunction.apply(token, numberToGuess);

        if (guess.getValue0() == null) return null;

        if (guess.getValue1() == 1L) return find(token, min, numberToGuess, guessFunction);
        if (guess.getValue1() == -1L) return find(token, numberToGuess, max, guessFunction);
        return guess.getValue0();
    }
}
