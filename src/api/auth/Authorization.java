package api.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.userdata.UserData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Authorization {
    private final UserData data;
    private final HttpClient client;
    private String code;
    private String token;
    private final int waitingResponseTime = 10000;

    public Authorization(UserData data) {
        this.data = data;
        this.client = HttpClient.newBuilder().build();
        this.code = null;
    }

    public void applicationAuthorization() throws IOException, InterruptedException, Exception {
        Server server = new Server();
        server.set();
        server.start();

        try {
            sendRequest(buildGetRequest());

            System.out.println("waiting for code...");
            while(!server.isAccept()) {
                Thread.sleep(50);
            }

            checkCode(server.getReq());
        } catch (Exception e) {
            server.stop();
            throw new Exception();
        }
        server.stop();
    }

    public void getAccessToken() throws Exception {
        System.out.println("Making http request for access_token...");

        if (this.code == null) {
            throw new Exception();
        }
        HttpResponse resp = sendRequest(buildPostRequest());
        this.token = getTokenJson(resp.body().toString());
    }

    public String getToken() {
        return this.token;
    }

    private String getTokenJson(String json) {
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        String token = jo.get("access_token").getAsString();
        return token;
    }

    private HttpRequest buildGetRequest() {
        System.out.println("use this link to request the access code:");

        String authURI = new StringBuilder()
                .append(data.getDomain())
                .append(data.getPath()).toString();
        String authBody = new StringBuilder()
                .append(data.concatKeyVal("client_id", data.getClientId()))
                .append("&")
                .append(data.concatKeyVal("redirect_uri", data.getRedirectUri()))
                .append("&")
                .append(UserData.concatKeyVal("response_type", "code")).toString();
        String authURIBody = new StringBuilder()
                .append(authURI)
                .append("?")
                .append(authBody).toString();

        System.out.println(authURIBody);

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(authURIBody))
                .GET()
                .build();
        return request;
    }

    private HttpRequest buildPostRequest() {
        String accessURI = new StringBuilder()
                .append(data.getDomain())
                .append("/api/token").toString();
        String accessBody = new StringBuilder()
                .append(UserData.concatKeyVal("grant_type", "authorization_code"))
                .append("&")
                .append(UserData.concatKeyVal("code", this.code))
                .append("&")
                .append(UserData.concatKeyVal("redirect_uri", data.getRedirectUri())).toString();
        String encode = Base64.getEncoder()
                .encodeToString((data.getClientId() + ":" + data.getSecretId()).getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(accessURI))
                .POST(HttpRequest.BodyPublishers.ofString(accessBody))
                .header("Authorization", "Basic " + encode)
                .build();
        return request;
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws InterruptedException, IOException {
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        return resp;
    }

    private void checkCode(String code) throws Exception {
        if (code == null) {
            System.out.println("there is no answer from server");
            throw new Exception();
        }

        String[] keyval = code.split("=");

        if (keyval[0].equals("error")) {
            System.out.println("error was received");
            throw new Exception();
        }

        this.code = keyval[1];
        System.out.println("code received");
    }
}
