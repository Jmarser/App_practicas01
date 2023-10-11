package com.jmarser.app_practicas01.api.wsApi;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.Comment;
import com.jmarser.app_practicas01.api.models.Portada;
import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.api.models.Task;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.utils.Constantes;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface que contiene todas las llamadas a la api
 * */
public interface WsApi {

    @GET(Constantes.GET_USERS)
    Call<List<User>> getAllUsers();

    @GET(Constantes.GET_ALBUMS)
    Call<List<Album>> getAllAlbums();

    @GET(Constantes.GET_ALBUMS_FOR_USER)
    Call<List<Album>> getAlbumsForUser(@Path("userId") int userId);

    @GET(Constantes.GET_PHOTOS)
    Call<List<Portada>> getPortadas();

    @GET(Constantes.GET_PORTADAS_FOR_ALBUM)
    Call<List<Portada>> getPortadasForAlbum(@Path("albumId") int albumId);

    @GET(Constantes.GET_POSTS)
    Call<List<Post>> getPostsForUserId(@Query("userId") int userId);

    @GET(Constantes.GET_TODOS)
    Call<List<Task>> getTasks();

    @GET(Constantes.GET_COMMENTS)
    Call<List<Comment>> getCommentsForPostId(@Query("postId") int postId);

    @FormUrlEncoded
    @POST(Constantes.CREATE_POSTS)
    Call<Post> createPost(@Field("title") String title, @Field("body") String body, @Field("userId") int userId);

    @FormUrlEncoded
    @PUT(Constantes.EDIT_POST)
    Call<Post> editPost(@Path("id") int postId,@Field("id") int id, @Field("title") String title, @Field("body") String body, @Field("userId") int userId);
}
