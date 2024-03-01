package com.spotify.oath2.api.applicationApi;

import com.spotify.oath2.api.RestResource;
import com.spotify.oath2.pojo.Playlist;
import com.spotify.oath2.utils.ConfigLoader;
import io.restassured.response.Response;

import java.io.FileNotFoundException;

import static com.spotify.oath2.api.Route.PLAYLISTS;
import static com.spotify.oath2.api.Route.USERS;
import static com.spotify.oath2.api.TokenManager.getToken;
import static com.spotify.oath2.utils.ConfigLoader.getInstance;


public class PlaylistAPI {
    public static Response post(Playlist requestPlaylist) throws FileNotFoundException {
        return RestResource.post(USERS+"/"+ConfigLoader.getInstance().getUser() +PLAYLISTS,
                                 requestPlaylist,
                                 getToken());
    }

    public static Response post(Playlist requestPlaylist, String token) throws FileNotFoundException {
        return RestResource.post(USERS+"/"+ConfigLoader.getInstance().getUser()+PLAYLISTS,
                                 requestPlaylist,
                                 token);
    }

    public static Response get(String playListID){
        return RestResource.get(PLAYLISTS+"/"+playListID, getToken());
    }

    public static Response update(Playlist requestPlaylist, String playlistID){
        return RestResource.update(PLAYLISTS+"/"+playlistID,
                                    requestPlaylist, getToken());
    }
}

