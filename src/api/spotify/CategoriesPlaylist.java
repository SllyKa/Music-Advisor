package api.spotify;

import model.userdata.UserData;

import java.io.IOException;
import java.net.http.HttpResponse;

public class CategoriesPlaylist extends Categories {
    CategoriesPlaylist(String path, String body) {
        super(path, body);
    }


    @Override
    public String get() {
        try {
            HttpResponse<String> resp = sendRequest(buildRequest(uri, body));
            return resp.body();

        } catch (IOException | InterruptedException e) {
            System.out.println("HttpRespond error");
            return null;
        }
    }

    public static class NewBuilder {
        private String catName;
        private String uri = "/v1/browse/categories";
        private final StringBuilder body = new StringBuilder("?");

        public NewBuilder category(String category) {
            this.catName = category;
            return this;
        }

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

        public CategoriesPlaylist build() throws Exception {
            if (body.length() > 1) {
                body.deleteCharAt(body.length() - 1);
            } else {
                body.deleteCharAt(0);
            }

            if (catList.size() == 0) {
                System.out.println("Call categories first");
                throw new Exception();
            }

            String catId = searchId(catName);

            if (catId == null) {
                System.out.println("Unknown category name.");
                throw new Exception();
            }
            this.uri = uri + "/" + catId + "/playlists";
            return new CategoriesPlaylist(uri, body.toString());
        }

        private String searchId(String catName) {
            for (int i = 0; i < catList.size(); i++) {
                if (catList.get(i).getCatName().equals(catName)) {
                    return catList.get(i).getCatId();
                }
            }
            return null;
        }
    }
}
