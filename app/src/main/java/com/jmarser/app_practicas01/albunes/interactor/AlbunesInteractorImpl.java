package com.jmarser.app_practicas01.albunes.interactor;

import android.content.SharedPreferences;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.api.wsApi.WsApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbunesInteractorImpl implements AlbunesInteractor{

    @Inject
    WsApi wsApi;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public AlbunesInteractorImpl() {
    }


    @Override
    public void getAllUsers(OnGetAllUsersCallBack callBack, OnErrorServer errorServer) {
        Call<List<User>> call = wsApi.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessGetAllUsers(new ArrayList<User>(response.body()));
                    callBack.onSuccessGetAllUsers(null);
                    //errorServer.onErrorServer();
                }else{
                    callBack.onErrorGetAllUsers();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void getAllAlbunes(OnGetAllAlbunesCallBack callBack, OnErrorServer errorServer) {
        Call<List<Album>> call = wsApi.getAllAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessGetAllAlbunes(new ArrayList<Album>(response.body()));
                    //callBack.onSuccessGetAllAlbunes(null);
                }else{
                    callBack.onErrorGetAllAlbunes();
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void getAlbunesForUserID(int userId, OnGetAllAlbunesCallBack callBack, OnErrorServer errorServer) {
        Call<List<Album>> call = wsApi.getAlbumsForUser(userId);
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessGetAllAlbunes(new ArrayList<>(response.body()));
                }else{
                    callBack.onErrorGetAllAlbunes();
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }
}
