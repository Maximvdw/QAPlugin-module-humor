package be.maximvdw.qaplugin.modules;

import be.maximvdw.qaplugin.api.AIModule;
import be.maximvdw.qaplugin.api.QAPluginAPI;
import be.maximvdw.qaplugin.api.annotations.ModuleAuthor;
import be.maximvdw.qaplugin.api.annotations.ModuleDescription;
import be.maximvdw.qaplugin.api.annotations.ModuleName;
import be.maximvdw.qaplugin.api.annotations.ModuleVersion;
import be.maximvdw.qaplugin.modules.http.HttpMethod;
import be.maximvdw.qaplugin.modules.http.HttpRequest;
import be.maximvdw.qaplugin.modules.http.HttpResponse;
import be.maximvdw.qaplugin.question.AnswerLine;
import be.maximvdw.qaplugin.question.DynamicResponse;
import be.maximvdw.qaplugin.question.Question;
import be.maximvdw.qaplugin.question.QuestionLine;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Random;

/**
 * HumorModule
 * Created by maxim on 15-Jan-17.
 */
@ModuleName("Humor")
@ModuleAuthor("Maximvdw")
@ModuleVersion("1.0.0")
@ModuleDescription("Adds some humor to the assistant.")
public class HumorModule extends AIModule {

    public HumorModule() {
        Question yoMammaJoke = new Question()
                .addQuestion(new QuestionLine("|required:type|word|yo mama"))
                .addQuestion(new QuestionLine("|required:type|word|your mama"))
                .addQuestion(new QuestionLine("|required:type|word|yomama"))
                .addQuestion(new QuestionLine("|required:type|word|yo moma"))
                .addQuestion(new QuestionLine("|required:type|word|yomoma"))
                .addQuestion(new QuestionLine("|required:type|word|yo momma"))
                .addQuestion(new QuestionLine("|required:type|word|yomomma"))
                .addQuestion(new QuestionLine("|required:type|word|your momma"))
                .addQuestion(new QuestionLine("|required:type|word|your mom"))
                .addQuestion(new QuestionLine("|required:type|word|your mother"))
                .addQuestion(new QuestionLine("|required:type|word|your mamma"))
                .addQuestion(new QuestionLine("|required:type|word|yo mamma"))
                .addQuestion(new QuestionLine("|required:what|word|tell"))
                .addQuestion(new QuestionLine("|required:what|word|give"))
                .addQuestion(new QuestionLine("|required:what|word|make"))
                .addQuestion(new QuestionLine("|required:what|word|do"))
                .addQuestion(new QuestionLine("|word|joke"))
                .withMinimumMatches(3)
                .withDynamicResponse(new DynamicResponse() {
                    @Override
                    public AnswerLine getResponse(Player player, String s) {
                        return new AnswerLine(getRandomYoMammaJoke());
                    }
                });
        QAPluginAPI.addQuestion(yoMammaJoke);

        Question chuckNorrisJoke = new Question()
                .addQuestion(new QuestionLine("|required:type|word|chuck norris"))
                .addQuestion(new QuestionLine("|required:type|word|chucknorris"))
                .addQuestion(new QuestionLine("|required:type|word|norris"))
                .addQuestion(new QuestionLine("|required:what|word|tell"))
                .addQuestion(new QuestionLine("|required:what|word|give"))
                .addQuestion(new QuestionLine("|required:what|word|make"))
                .addQuestion(new QuestionLine("|required:what|word|do"))
                .addQuestion(new QuestionLine("|word|joke"))
                .addQuestion(new QuestionLine("|word|fact"))
                .withMinimumMatches(3)
                .withDynamicResponse(new DynamicResponse() {
                    @Override
                    public AnswerLine getResponse(Player player, String s) {
                        return new AnswerLine(getRandomChuckNorrisJoke());
                    }
                });
        QAPluginAPI.addQuestion(chuckNorrisJoke);

        Question catFact = new Question()
                .addQuestion(new QuestionLine("|required:type|word|cat"))
                .addQuestion(new QuestionLine("|required:what|word|tell"))
                .addQuestion(new QuestionLine("|required:what|word|give"))
                .addQuestion(new QuestionLine("|required:what|word|make"))
                .addQuestion(new QuestionLine("|required:what|word|do"))
                .addQuestion(new QuestionLine("|word|fact"))
                .withMinimumMatches(3)
                .withDynamicResponse(new DynamicResponse() {
                    @Override
                    public AnswerLine getResponse(Player player, String s) {
                        return new AnswerLine(getRandomCatFact());
                    }
                });
        QAPluginAPI.addQuestion(catFact);

        Question joke = new Question()
                .addQuestion(new QuestionLine("|required:what|word|tell"))
                .addQuestion(new QuestionLine("|required:what|word|give"))
                .addQuestion(new QuestionLine("|required:what|word|make"))
                .addQuestion(new QuestionLine("|required:what|word|do"))
                .addQuestion(new QuestionLine("|word|joke"))
                .withMinimumMatches(2)
                .withDynamicResponse(new DynamicResponse() {
                    @Override
                    public AnswerLine getResponse(Player player, String s) {
                        Random random = new Random();
                        int idx = random.nextInt(3);
                        switch (idx){
                            case 0:
                                return new AnswerLine(getRandomYoMammaJoke());
                            case 1:
                            case 2:
                                return new AnswerLine(getRandomJoke());
                            case 3:
                                return new AnswerLine(getRandomChuckNorrisJoke());
                        }
                        return null;
                    }
                });
        QAPluginAPI.addQuestion(joke);
    }

    /**
     * Get a random joke
     *
     * @return random joke
     */
    public String getRandomJoke() {
        try {
            String url = "http://tambal.azurewebsites.net/joke/random";
            HttpResponse response = new HttpRequest(url)
                    .method(HttpMethod.GET)
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject responseObj = (JSONObject) parser.parse(response.getSource());
            if (responseObj.containsKey("joke")) {
                return (String) responseObj.get("joke");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Get a random "Yo Mamma" joke
     *
     * @return random joke
     */
    public String getRandomYoMammaJoke() {
        try {
            String url = "http://api.yomomma.info/";
            HttpResponse response = new HttpRequest(url)
                    .method(HttpMethod.GET)
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject responseObj = (JSONObject) parser.parse(response.getSource());
            if (responseObj.containsKey("joke")) {
                return (String) responseObj.get("joke");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Get a random Chuck Norris joke
     *
     * @return random joke
     */
    public String getRandomChuckNorrisJoke() {
        try {
            String url = "https://api.icndb.com/jokes/random";
            HttpResponse response = new HttpRequest(url)
                    .method(HttpMethod.GET)
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject responseObj = (JSONObject) parser.parse(response.getSource());
            if (responseObj.containsKey("value")) {
                return (String) ((JSONObject) responseObj.get("value")).get("joke");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Get a random cat fact
     *
     * @return random fact
     */
    public String getRandomCatFact() {
        try {
            String url = "http://catfacts-api.appspot.com/api/facts";
            HttpResponse response = new HttpRequest(url)
                    .method(HttpMethod.GET)
                    .execute();
            JSONParser parser = new JSONParser();
            JSONObject responseObj = (JSONObject) parser.parse(response.getSource());
            if (responseObj.containsKey("success")) {
                return (String) ((JSONArray)responseObj.get("facts")).get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
