package com.jmarser.app_practicas01.usuarios.interactor;

import android.util.Log;

import com.jmarser.app_practicas01.api.models.Comment;
import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.api.models.Task;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.api.wsApi.WsApi;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuariosInteractorImpl implements UsuariosInteractor{

    @Inject
    WsApi wsApi;

    @Inject
    public UsuariosInteractorImpl() {
    }

    @Override
    public void getUsers(OnGetUsersCallBack callBack, OnErrorServer errorServer) {
        Call<List<User>> call = wsApi.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessGetUsers(new ArrayList<User>(response.body()));
                    //callBack.onSuccessGetUsers(null);
                    //callBack.onErrorServer();
                }else{
                    callBack.onErrorGetUsers();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void getPostsForUserID(int userId, OnGetPostsCallBack callBack, OnErrorServer errorServer) {
        Call<List<Post>> call = wsApi.getPostsForUserId(userId);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessGetPostsForUserId(new ArrayList<Post>(response.body()));
                }else{
                    callBack.onErrorGetPosts();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void getTodos(OnGetTodosCallBack callBack, OnErrorServer errorServer) {
        Call<List<Task>> call = wsApi.getTasks();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessGetTodos(new ArrayList<Task>(response.body()));
                }else{
                    callBack.onErrorGetTodos();
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void getCommentsForpstId(int postId, OnGetCommentsCallBack callBack, OnErrorServer errorServer) {
        Call<List<Comment>> call = wsApi.getCommentsForPostId(postId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessGetComments(new ArrayList<Comment>(response.body()));
                }else{
                    callBack.onErrorGetComments();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void createPost(String title, String body, int userId, OnCreatePostCallBack callBack, OnErrorServer errorServer) {
        Call<Post> call = wsApi.createPost(title, body, userId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Log.i("POST_CREADO", response.body().getTitle() + " " + response.body().getBody());
                    callBack.onSuccessCreatePost(response.body());
                }else{
                    callBack.onErrorCreatePost();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void editPost(int id, String title, String body, int userId, OnEditPostCallBack callBack, OnErrorServer errorServer) {
        Call<Post> call = wsApi.editPost(id, id, title, body, userId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    Log.i("POST_EDITADO", response.body().getTitle() + " " + response.body().getBody());
                    callBack.onSuccessEditPost(response.body());
                }else{
                    callBack.onErrorEditPost();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }
}
