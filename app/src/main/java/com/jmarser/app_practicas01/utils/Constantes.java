package com.jmarser.app_practicas01.utils;

public class Constantes {

    // Fichero preferences
    public static final String PREFNAME = "Datos";

    //Datos Intents
    public static final String INTENT_EMAIL = "email";
    public static final String INTENT_PASSWORD = "password";
    public static final String BUNDLE_USUARIO = "usuario";
    public static final String BUNDLE_POST = "post";

    // URL servidor y Endpoints
    public static final String SERVER_URL = "https://jsonplaceholder.typicode.com/";
    public static final String GET_USERS = "users/";
    public static final String GET_POSTS = "posts/";
    public static final String GET_TODOS = "todos/";
    public static final String GET_COMMENTS = "comments/";
    public static final String GET_PHOTOS = "photos/";
    public static final String GET_ALBUMS = "albums/";
    public static final String GET_ALBUMS_FOR_USER = "users/{userId}/albums";
    public static final String GET_PORTADAS_FOR_ALBUM = "albums/{albumId}/photos";
    public static final String CREATE_POSTS = "posts/";
    public static final String EDIT_POST = "posts/{id}";
}
