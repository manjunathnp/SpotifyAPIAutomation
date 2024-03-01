package com.spotify.oath2.tests;

import com.spotify.oath2.api.applicationApi.PlaylistAPI;
import com.spotify.oath2.pojo.Error;
import com.spotify.oath2.pojo.Playlist;
import com.spotify.oath2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayListTests {
    @Test
    public void validatePlayListCreation() throws FileNotFoundException {
        Playlist requestPlaylist = playListBuilder("TestPlaylist5",
                                                    "This is test Playlist", false);

        Response response=PlaylistAPI.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 201);

        Playlist responsePlaylist=response.as(Playlist.class);
        assertPlayListEqual(responsePlaylist, requestPlaylist);
    }

    @Test
    public void validateGetPlaylistDetails() throws FileNotFoundException {
        Playlist requestPlaylist = playListBuilder("TestPlaylist5",
                                                "This is test Playlist", false);

        Response response=PlaylistAPI.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), 200);

        Playlist responsePlaylist=response.as(Playlist.class);
        assertGetPlayListEqual(responsePlaylist,requestPlaylist);

    }

    @Test
    public void validateUpdatePlaylistDetails() throws FileNotFoundException {
        Playlist requestPlaylist = playListBuilder("Updated Playlist Name number 3",
                                                "Updated playlist description3", false);

        Response response = PlaylistAPI.update(requestPlaylist,DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(), 200);

    }

    @Test
    public void NTValidatePlayListCreationWithNoName() throws FileNotFoundException {
        Playlist requestPlaylist = playListBuilder("",
                "New playlist description", false);
        Response response=PlaylistAPI.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 400);
        Error error=response.as(Error.class);
        assertError(error, 400, "Missing required field: name");
    }

    @Test
    public void NTValidateUnableToCreatePLUsingInvalidToken() throws FileNotFoundException {
        String invalidToken="123321";
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist_new").
                setDescription("New Playlist_new Description").
                setPublic(false);

        Response response=PlaylistAPI.post(requestPlaylist, invalidToken);
        assertThat(response.statusCode(), equalTo(401));

        Error error=response.as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }

    public Playlist playListBuilder(String name, String desc, boolean _public){
        return new Playlist().
                setName(name).
                setDescription(desc).
                setPublic(_public);
    }

    public void assertPlayListEqual(Playlist responsePlaylist, Playlist requestPlaylist){

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    public void assertGetPlayListEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(true));
    }

    public void assertError(Error error, int expectedStatusCode, String expectedMsg){
        assertThat(error.getError().getStatus(), equalTo(expectedStatusCode));
        assertThat(error.getError().getMessage(), equalTo(expectedMsg));
    }

}
