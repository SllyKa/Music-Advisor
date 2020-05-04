package api.spotify;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class SpotifyAPI {
    protected static String token;
    protected static String apiDomain = "https://api.spotify.com";
    protected static final HttpClient client = HttpClient.newBuilder().build();
    protected String body;
    protected String uri;

    SpotifyAPI(String path, String body) {
        this.uri = this.apiDomain + path;
        this.body = body;
    }

    public static void setToken(String token1) {
        token = token1;
    }

    public static void setApiDomain(String apiDomain) {
        if (apiDomain == null) {
            return;
        }
        SpotifyAPI.apiDomain = apiDomain;
    }

    protected HttpRequest buildRequest(String uri, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .uri(URI.create(uri + body))
                .GET()
                .build();
        return request;
    }

    protected HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        return resp;
    }

    public String get() {
        try {
            HttpResponse<String> resp = sendRequest(buildRequest(uri, body));
            return resp.body();

        } catch (IOException | InterruptedException e) {
            System.out.println("HttpRespond error");
            return null;
        }
    }
}

