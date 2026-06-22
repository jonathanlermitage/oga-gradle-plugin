package biz.lermitage.oga.util;

import biz.lermitage.oga.cfg.Definitions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * IO tools.
 *
 * @author Jonathan Lermitage
 * @version 1
 */
@SuppressWarnings("WeakerAccess")
public class IOTools {

    private static final Gson GSON = new GsonBuilder().create();

    public static Definitions readDefinitionsFromUrl(URI uri) throws IOException {
        String definitionsAsString = readContentFromUrl(uri);
        return GSON.fromJson(definitionsAsString, Definitions.class);
    }

    public static String readContentFromUrl(URI uri) throws IOException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("HTTP request interrupted", e);
        }
    }
}
