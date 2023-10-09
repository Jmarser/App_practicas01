package com.jmarser.app_practicas01.portadas.presenter;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.Portada;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.portadas.interactor.PortadasInteractor;
import com.jmarser.app_practicas01.portadas.view.PortadasView;
import com.jmarser.app_practicas01.utils.ErrorView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;

public class PortadasPresenterImpl implements PortadasPresenter, PortadasInteractor.OnGetAllUsersCallBack, PortadasInteractor.OnGetAllAlbunesCallBack, PortadasInteractor.OnGetAllPortadasCallBack, PortadasInteractor.OnErrorServer{

    @Nullable
    @Inject
    PortadasView portadasView;

    @Nullable
    @Inject
    ErrorView errorView;

    @Inject
    PortadasInteractor portadasInteractor;

    @Inject
    public PortadasPresenterImpl() {
    }


    @Override
    public void getAllUsers() {
        portadasInteractor.getAllUsers(this, this);
    }

    @Override
    public void onSuccessGetAllUsers(ArrayList<User> usuarios) {
        portadasView.setUsuarios(usuarios);
    }

    @Override
    public void onErrorGetAllUsers() {
        errorView.showError();
    }

    @Override
    public void getAllAlbumes() {
        portadasInteractor.getAllAlbumes(this, this);
    }

    @Override
    public void getAlbumesForUserID(int userId) {
        portadasInteractor.getAlbumesForUserID(userId, this, this);
    }

    @Override
    public void onSuccessGetAllAlbumes(ArrayList<Album> albumes) {
        portadasView.setAlbumes(albumes);
    }

    @Override
    public void onErrorGetAllAlbumes() {
        errorView.showError();
    }

    @Override
    public void getAllPortadas() {
        portadasInteractor.getAllPortadas(this, this);
    }

    @Override
    public void getPortadasForAlbumID(int albumId) {
        portadasInteractor.getPortadasForAlbumID(albumId, this, this);
    }

    @Override
    public void onSuccessGetAllPortadas(ArrayList<Portada> portadas) {
        portadasView.setPortadas(portadas);
    }

    @Override
    public void onErrorGetAllPortadas() {
        errorView.showError();
    }

    @Override
    public void onErrorServer() {
        errorView.errorServer();
    }
}
