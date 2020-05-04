package view;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class SpotifyAPIViewObject implements MakeViewObjectStrategy {
    protected String data;

    SpotifyAPIViewObject(String data) {
        this.data = data;
    }

    public static boolean checkJsonStr(String str) {
        if (str == null) {
            return false;
        }
        JsonObject jo = JsonParser.parseString(str).getAsJsonObject();
        if (jo.has("error")) {
            System.out.println(jo.get("error").getAsJsonObject().get("message"));
            return false;
        }
        return true;
    }
}
