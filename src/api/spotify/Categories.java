package api.spotify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.userdata.UserData;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static view.SpotifyAPIViewObject.checkJsonStr;

public class Categories extends SpotifyAPI {

    protected static List<CatName> catList = new ArrayList<>();

    public Categories(String path, String body) {
        super(path, body);
    }

    class CatName {
        private final String catId;
        private final String catName;

        CatName(String catId, String catName) {
            this.catId = catId;
            this.catName = catName;
        }

        public String getCatId() {
            return this.catId;
        }

        public String getCatName() {
            return this.catName;
        }
    }

    @Override
    public String get() {
        try {
            HttpResponse<String> resp = sendRequest(buildRequest(uri, body));
            getCategories(resp.body());
            return resp.body();

        } catch (IOException | InterruptedException e) {
            System.out.println("HttpRespond error");
            return null;
        }
    }

    private void getCategories(String jstr) {
        if (!checkJsonStr(jstr)) {
            return ;
        }

        JsonObject jo = JsonParser.parseString(jstr).getAsJsonObject();
        JsonArray items = jo.get("categories").getAsJsonObject().get("items").getAsJsonArray();
        for (int i = 0; i < items.size(); i++) {
            JsonObject item = items.get(i).getAsJsonObject();
            putCategories(item);
        }
    }

    private void putCategories(JsonObject item) {
        String catName = item.get("name").getAsString();
        String catId = item.get("id").getAsString();
        CatName catobj = new CatName(catId, catName);
        catList.add(catobj);
    }

    public static class NewBuilder {
        private final String uri = "/v1/browse/categories";
        private final StringBuilder body = new StringBuilder("?");

        public NewBuilder country(String country) {
            body.append(UserData.concatKeyVal("country", country))
                    .append("&");
            return this;
        }

        public NewBuilder limit(int limit) {
            body.append(UserData.concatKeyVal("limit", Integer.toString(limit)))
                    .append("&");
            return this;
        }

        public NewBuilder offset(int offset) {
            body.append(UserData.concatKeyVal("offset", Integer.toString(offset)))
                    .append("&");
            return this;
        }

        public Categories build() {
            if (body.length() > 1) {
                body.deleteCharAt(body.length() - 1);
            } else {
                body.deleteCharAt(0);
            }
            return new Categories(uri, body.toString());
        }
    }
}
