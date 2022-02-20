package fr.univ_tlse3.maar;

import org.javatuples.Pair;
import java.util.function.Function;
import java.util.function.BiFunction;

/**
 * Interface that provides methods to guess a number using a REST API
 */
public interface IRestChallenge {

    /**
     * Appelle le Web Service REST "/init" et récupère le contenu de la réponse XML
     * via une désérialisation en un JavaBean (X1)
     * @return le champ "token" fourni par le Web Service
     */
    String getToken_jaxb();

    /**
     * Appelle le Web Service REST "/init" et récupère le contenu de la réponse XML
     * via une encapsulation "bas-niveau" en org.w3c.dom.Document (X2)
     * @return le champ "token" fourni par le Web Service
     */
    String getToken_jaxp();

    /**
     * Appelle le Web Service REST "/try" et récupère le contenu de la réponse JSON
     * via une désérialisation en un JavaBean (J1)
     * @param token le token précédemment récupéré via une des méthodes "getToken_"
     * @param guess le nombre entier proposé
     * @return un tuple contenant le message retourné ainsi qu'un Integer, suivant la valeur du champ "status" :
     *         - -1 si "trop-petit" (le nombre entier proposé est trop petit)
     *         - 1 si "trop-grand" (le nombre entier proposé est trop grand)
     *         - 0 si "OK" (le nombre entier proposé est correct)
     *         - null dans tous les autres cas de figure
     */
    Pair<String, Integer> guess_jsonb(String token, long guess);

    /**
     * Appelle le Web Service REST "/try" et récupère le contenu de la réponse XML
     * via une encapsulation "bas-niveau" en javax.json.JsonObject ou javax.json.JsonArray (J2)
     * @param token le token précédemment récupéré via une des méthodes "getToken_"
     * @param guess le nombre entier proposé
     * @return un tuple contenant le message retourné ainsi qu'un Integer, suivant la valeur du champ "status" :
     *         - -1 si "trop-petit"
     *         - 1 si "trop-grand"
     *         - 0 si "OK"
     *         - null dans tous les autres cas de figure
     */
    Pair<String, Integer> guess_jsonp(String token, long guess);

    /*************************************************************************/

    /**
     * Fonction principale (récursive et d'ordre supérieur) qui a pour objectif de deviner le bon nombre
     * en utilisant une des méthodes "guess_" de l'interface
     * @param token le token précédemment récupéré via une des méthodes "getToken_"
     * @param min la valeur minimale de l'intervalle dans laquelle se situe le nombre à trouver
     * @param max la valeur maximale de l'intervalle dans laquelle se situe le nombre à trouver
     * @param guessFunction la fonction à utiliser pour faire des propositions sur le nombre à trouver
     * @return le message retourné par guess lorsque le bon nombre a été trouvé
     */
    String find(String token, long min, long max,
                BiFunction<String, Long, Pair<String, Integer>> guessFunction);
}
