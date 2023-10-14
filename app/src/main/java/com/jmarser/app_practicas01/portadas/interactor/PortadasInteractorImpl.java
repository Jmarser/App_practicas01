package com.jmarser.app_practicas01.portadas.interactor;

import android.content.SharedPreferences;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.Portada;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.api.wsApi.WsApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortadasInteractorImpl implements PortadasInteractor{

    @Inject
    WsApi wsApi;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public PortadasInteractorImpl() {
    }

    @Override
    public void getAllUsers(OnGetAllUsersCallBack callBack, OnErrorServer errorServer) {
        wsApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    callBack.onSuccessGetAllUsers(new ArrayList<User>(response.body()));
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
    public void getAllAlbumes(OnGetAllAlbunesCallBack callBack, OnErrorServer errorServer) {
        wsApi.getAllAlbums().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()){
                    callBack.onSuccessGetAllAlbumes(new ArrayList<Album>(response.body()));
                }else{
                    callBack.onErrorGetAllAlbumes();
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void getAlbumesForUserID(int userId, OnGetAllAlbunesCallBack callBack, OnErrorServer errorServer) {
        wsApi.getAlbumsForUser(userId).enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()){
                    callBack.onSuccessGetAllAlbumes(new ArrayList<Album>(response.body()));
                }else{
                    callBack.onErrorGetAllAlbumes();
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void getAllPortadas(OnGetAllPortadasCallBack callBack, OnErrorServer errorServer) {
        wsApi.getPortadas().enqueue(new Callback<List<Portada>>() {
            @Override
            public void onResponse(Call<List<Portada>> call, Response<List<Portada>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessGetAllPortadas(new ArrayList<Portada>(response.body()));
                }else{
                    callBack.onErrorGetAllPortadas();
                }
            }

            @Override
            public void onFailure(Call<List<Portada>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }

    @Override
    public void getPortadasForAlbumID(int albumId, OnGetAllPortadasCallBack callBack, OnErrorServer errorServer) {
        wsApi.getPortadasForAlbum(albumId).enqueue(new Callback<List<Portada>>() {
            @Override
            public void onResponse(Call<List<Portada>> call, Response<List<Portada>> response) {
                if (response.isSuccessful()){
                    callBack.onSuccessGetAllPortadas(new ArrayList<Portada>(response.body()));
                }else{
                    callBack.onErrorGetAllPortadas();
                }
            }

            @Override
            public void onFailure(Call<List<Portada>> call, Throwable t) {
                errorServer.onErrorServer();
            }
        });
    }
}
