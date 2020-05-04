package commands.receiver;

import api.auth.Authorization;
import api.spotify.*;
import model.userdata.UserData;
import view.CategoriesViewObject;
import view.NewReleasesViewObject;
import view.PlaylistViewObject;
import view.ViewObject;

public class Actions {
    public void newAction(String header) {
        SpotifyAPI newReleases = new NewReleases.NewBuilder().limit(20).build();
        String resp = newReleases.get();
        ViewObject.setObject(new NewReleasesViewObject(resp));
    }

    public void featuredAction(String header) {
        SpotifyAPI featured = new Featured.NewBuilder().limit(20).build();
        String resp = featured.get();
        ViewObject.setObject(new PlaylistViewObject(resp));
    }

    public void categoriesAction(String header) {
        SpotifyAPI categories = new Categories.NewBuilder().build();
        String resp = categories.get();
        ViewObject.setObject(new CategoriesViewObject(resp));
    }

    public void playlistsAction(String header, String category) {
        try {
            SpotifyAPI categoryPlaylists = new CategoriesPlaylist.NewBuilder().category(category).limit(20).build();
            String resp = categoryPlaylists.get();
            ViewObject.setObject(new PlaylistViewObject(resp));
        } catch (Exception e) {

        }
    }

    public boolean AuthAction(String header, UserData data) {
        Authorization sptApi = new Authorization(data);

        try {
            sptApi.applicationAuthorization();
        } catch (Exception e) {
            System.out.println("Application authorization error");
            return false;
        }
        try {
            sptApi.getAccessToken();
        } catch (Exception e) {
            System.out.println("GetToken error");
            return false;
        }
        SpotifyAPI.setToken(sptApi.getToken());
        SpotifyAPI.setApiDomain(data.getApiDomain());
        System.out.println("Success!");
        return true;
    }

    public void exitAction(String header) {
        //output(header);
    }

    private void showHeader(String header) {
        System.out.println("---" + header.toUpperCase() + "---");
    }

    private void output(String header, String... list) {
        showHeader(header);
        for(int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }
    }
}
