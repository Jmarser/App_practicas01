package com.jmarser.app_practicas01.albunes.presenter;

import com.jmarser.app_practicas01.albunes.interactor.AlbunesInteractor;
import com.jmarser.app_practicas01.albunes.view.AlbunesView;
import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.utils.ErrorView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;

public class AlbunesPresenterImpl implements AlbunesPresenter, AlbunesInteractor.OnGetAllUsersCallBack, AlbunesInteractor.OnGetAllAlbunesCallBack, AlbunesInteractor.OnErrorServer{

    @Nullable
    @Inject
    AlbunesView albunesView;

    @Nullable
    @Inject
    ErrorView errorView;

    @Inject
    AlbunesInteractor albunesInteractor;

    @Inject
    public AlbunesPresenterImpl() {
    }

    @Override
    public void getAllUsers() {
        albunesInteractor.getAllUsers(this, this);
    }

    @Override
    public void onSuccessGetAllUsers(ArrayList<User> listadoUsuarios) {
        albunesView.setGetAllUsers(listadoUsuarios);
    }

    @Override
    public void onErrorGetAllUsers() {
        errorView.showError();
    }

    @Override
    public void getAllAlbunes() {
        albunesInteractor.getAllAlbunes(this, this);
    }

    @Override
    public void getAlbunesForUserID(int userId) {
        albunesInteractor.getAlbunesForUserID(userId, this, this);
    }

    @Override
    public void onSuccessGetAllAlbunes(ArrayList<Album> listadoAlbunes) {
        albunesView.setGetAllAlbunes(listadoAlbunes);
    }

    @Override
    public void onErrorGetAllAlbunes() {
        errorView.showError();
    }

    @Override
    public void onErrorServer() {
        errorView.errorServer();
    }
}
