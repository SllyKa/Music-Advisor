package api.spotify;

import model.userdata.UserData;

public class NewReleases extends SpotifyAPI {
    private NewReleases(String uri, String body) {
        super(uri, body);
    }

    public static class NewBuilder {
        private final String uri = "/v1/browse/new-releases";
        private StringBuilder body = new StringBuilder("?");

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

        public NewReleases build() {
            if (body.length() > 1) {
                body.deleteCharAt(body.length() - 1);
            } else {
                body.deleteCharAt(0);
            }
            return new NewReleases(uri, body.toString());
        }
    }
}
