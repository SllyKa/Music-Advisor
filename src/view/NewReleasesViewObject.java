package view;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class NewReleasesViewObject extends SpotifyAPIViewObject {

    public NewReleasesViewObject(String data) {
        super(data);
    }

    @Override
    public List<String> makeView() {
        if (!checkJsonStr(data)) {
            return null;
        }

        List<String> viewObjs = new ArrayList<>();

        JsonObject jo = JsonParser.parseString(this.data).getAsJsonObject();
        JsonArray items = jo.get("albums").getAsJsonObject().getAsJsonArray("items");
        for (int i = 0; i < items.size(); i++) {
            StringBuilder obj = new StringBuilder();

            JsonObject item = items.get(i).getAsJsonObject();
            obj.append(getAlbumName(item)).append("\n");
            obj.append(getArtists(item.getAsJsonArray("artists"))).append("\n");
            obj.append(getURL(item)).append("\n");
            obj.append("\n");
            viewObjs.add(obj.toString());
        }
        return viewObjs;
    }

    private String getURL(JsonObject item) {
        return item.get("external_urls").getAsJsonObject().get("spotify").getAsString();
    }

    private String getAlbumName(JsonObject item) {
        return item.get("name").getAsString();
    }

    private String getArtists(JsonArray artists) {
        StringBuilder obj = new StringBuilder();
        obj.append("[");
        for (int j = 0; j < artists.size(); j++) {
            obj.append(artists.get(j).getAsJsonObject().get("name").getAsString());
            if (j != artists.size() - 1) {
                obj.append(", ");
            }
        }
        obj.append("]");
        return obj.toString();
    }
}
