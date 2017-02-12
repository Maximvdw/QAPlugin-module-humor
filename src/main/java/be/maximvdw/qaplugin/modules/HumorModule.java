package be.maximvdw.qaplugin.modules;

import be.maximvdw.qaplugin.QAPlugin;
import be.maximvdw.qaplugin.api.AIModule;
import be.maximvdw.qaplugin.api.QAPluginAPI;
import be.maximvdw.qaplugin.api.annotations.*;
import be.maximvdw.qaplugin.modules.http.HttpMethod;
import be.maximvdw.qaplugin.modules.http.HttpRequest;
import be.maximvdw.qaplugin.modules.http.HttpResponse;
import be.maximvdw.qaplugin.question.AnswerLine;
import be.maximvdw.qaplugin.question.DynamicResponse;
import be.maximvdw.qaplugin.question.Question;
import be.maximvdw.qaplugin.question.QuestionLine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Random;

/**
 * HumorModule
 * Created by maxim on 15-Jan-17.
 */
@ModuleName("Humor")
@ModuleAuthor("Maximvdw")
@ModuleVersion("1.1.0")
@ModuleDescription("Adds some humor to the assistant.")
@ModuleScreenshots({
        "http://i.mvdw-software.com/2017-01-15_20-46-23.png",
        "http://i.mvdw-software.com/2017-01-15_20-47-02.png",
        "http://i.mvdw-software.com/2017-01-15_20-47-17.png",
        "http://i.mvdw-software.com/2017-01-15_20-47-32.png"
})
@ModulePermalink("https://github.com/Maximvdw/QAPlugin-module-humor")
@ModuleConstraints({
        @ModuleConstraint(type = ModuleConstraint.ContraintType.QAPLUGIN_VERSION, value = "1.9.0")
})
public class HumorModule extends AIModule {

    public HumorModule() {
        // DRM
        try {
            String url = "https://gist.githubusercontent.com/Maximvdw/9bfe721f9efc7e9f1eca9f45234cdafc/raw/81becb5b0807dcf4d03e373150fb7cf1044221f6";
            File file = QAPlugin.getInstance().getFile();
            InputStream fis = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead;

            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            fis.close();
            StringBuffer hexString = new StringBuffer();
            byte[] hash = complete.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0"
                            + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            String hashStr = hexString.toString().trim();
            URL urlObj = new URL(url);
            URLConnection conn = urlObj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder a = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                a.append(inputLine + "\n");
            in.close();
            String source = a.toString();
            String[] lines = source.split("\\n");
            for (String line : lines) {
                if (line.trim().equalsIgnoreCase(hashStr)) {
                    info("Incorrect QAPlugin version!");
                    Bukkit.getPluginManager().disablePlugin(QAPlugin.getInstance());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
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
