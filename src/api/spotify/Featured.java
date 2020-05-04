package api.spotify;

import model.userdata.UserData;

public class Featured extends SpotifyAPI {

    public Featured(String uri, String body) {
        super(uri, body);
    }

    public static class NewBuilder {
        private final String uri = "/v1/browse/featured-playlists";
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

        public Featured build() {
            if (body.length() > 1) {
                body.deleteCharAt(body.length() - 1);
            } else {
                body.deleteCharAt(0);
            }
            return new Featured(uri, body.toString());
        }
    }
}
