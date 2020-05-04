package view;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class CategoriesViewObject extends SpotifyAPIViewObject {

    public CategoriesViewObject(String data) {
        super(data);
    }

    @Override
    public List<String> makeView() {
        if (!checkJsonStr(this.data)) {
            return null;
        }

        List<String> viewObjs = new ArrayList<>();

        JsonObject jo = JsonParser.parseString(this.data).getAsJsonObject();
        JsonArray items = jo.get("categories").getAsJsonObject().get("items").getAsJsonArray();
        for (int i = 0; i < items.size(); i++) {
            StringBuilder obj = new StringBuilder();
            JsonObject item = items.get(i).getAsJsonObject();
            obj.append(getCat(item)).append("\n");
            viewObjs.add(obj.toString());
        }
        return viewObjs;
    }

    private String getCat(JsonObject item) {
        return item.get("name").getAsString();
    }
}
