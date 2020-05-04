package view;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class PlaylistViewObject extends SpotifyAPIViewObject{

    public PlaylistViewObject(String data) {
        super(data);
    }

    @Override
    public List<String> makeView() {
        if (!checkJsonStr(this.data)) {
            return null;
        }

        List<String> viewObjs = new ArrayList<>();

        JsonObject jo = JsonParser.parseString(this.data).getAsJsonObject();
        JsonArray items = jo.get("playlists").getAsJsonObject().getAsJsonArray("items");
        for (int i = 0; i < items.size(); i++) {
            StringBuilder obj = new StringBuilder();

            JsonObject item = items.get(i).getAsJsonObject();
            obj.append(getFeaturedName(item)).append("\n");
            obj.append(getURL(item)).append("\n");
            obj.append("\n");
            viewObjs.add(obj.toString());
        }
        return viewObjs;
    }

    private String getFeaturedName(JsonObject item) {
        return item.get("name").getAsString();
    }

    private String getURL(JsonObject item) {
        return item.get("external_urls").getAsJsonObject().get("spotify").getAsString();
    }
}
