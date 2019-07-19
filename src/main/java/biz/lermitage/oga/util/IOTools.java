package biz.lermitage.oga.util;

import biz.lermitage.oga.cfg.Definitions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * IO tools.
 *
 * @author Jonathan Lermitage
 * @version 1
 */
@SuppressWarnings("WeakerAccess")
public class IOTools {
    
    private static Gson GSON = new GsonBuilder().create();
    
    public static Definitions readDefinitionsFromUrl(URL url) throws IOException {
        String definitionsAsString = readContentFromUrl(url);
        return GSON.fromJson(definitionsAsString, Definitions.class);
    }
    
    public static String readContentFromUrl(URL url) throws IOException {
        StringBuilder content = new StringBuilder();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line = rd.readLine();
            while (line != null) {
                content.append(line);
                line = rd.readLine();
            }
        }
        return content.toString();
    }
}
