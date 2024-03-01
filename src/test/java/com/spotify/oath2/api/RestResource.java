package com.spotify.oath2.api;

import com.spotify.oath2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oath2.api.Route.API;
import static com.spotify.oath2.api.Route.TOKEN;
import static com.spotify.oath2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response post(String path, Object requestPlaylist, String token){
        return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization", "Bearer "+token).
        when().
                post(path).
        then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response get(String path, String token){
        return given(getRequestSpec()).
                header("Authorization", "Bearer "+token).

        when().
                get(path).
        then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response update(String path, Object requestPlaylist, String token){
        return given(getRequestSpec()).
                header("Authorization", "Bearer "+token).
                body(requestPlaylist).

        when().
                put(path). //0qDbmkirbvbfY34eSS8ub2
        then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response postAccount(HashMap<String, String> formParams){
        return given(getAccountRequestSpec()).
                formParams(formParams).
        when().
                post(API+TOKEN).
        then().
                spec(getResponseSpec()).
                extract().
                response();
    }
}

