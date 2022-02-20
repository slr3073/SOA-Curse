package fr.univ_tlse3.maar;

import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.BiFunction;

import static org.junit.Assert.*;

public class RestChallengeAppTest {
    private final IRestChallenge app = new RestChallengeApp();

    @Test
    public void testGetTokenX1() {
        String token = app.getToken_jaxb();

        assertNotNull(token);
        System.out.println(token);
    }

    @Test
    public void testGetTokenX2() {
        String token = app.getToken_jaxp();

        assertNotNull(token);
        System.out.println(token);
    }

    @Test
    public void testGuessJ1() {
        testGuess(app::guess_jsonb);
    }

    @Test
    public void testGuessJ2() {
        testGuess(app::guess_jsonp);
    }

    private void testGuess(BiFunction<String, Long, Pair<String, Integer>> guess) {
        String token = "test";
        long guessValue = 0L;

        // Test with a fake token
        Pair<String, Integer> guessResult = guess.apply(token, guessValue);
        assertNotNull(guessResult);
        assertNull(guessResult.getValue1());

        // Test with a real token
        token = app.getToken_jaxb();
        guessResult = guess.apply(token, guessValue);
        assertNotNull(guessResult);
        assertNotNull(guessResult.getValue1());

        System.out.println(guessResult.getValue1() + " : " + guessResult.getValue0());
    }

    @Test
    public void testFind() {
        final long min = 0;
        final long max = 1L << 32;

        String result = app.find(app.getToken_jaxb(), min, max, app::guess_jsonb);
        Assert.assertNotNull(result);
        System.out.println(result);

        result = app.find(app.getToken_jaxp(), min, max, app::guess_jsonb);
        Assert.assertNotNull(result);
        System.out.println(result);

        result = app.find(app.getToken_jaxb(), min, max, app::guess_jsonp);
        Assert.assertNotNull(result);
        System.out.println(result);

        result = app.find(app.getToken_jaxp(), min, max, app::guess_jsonp);
        Assert.assertNotNull(result);
        System.out.println(result);
    }
}
