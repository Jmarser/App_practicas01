package com.jmarser.app_practicas01.usuarios.presenter;

import androidx.annotation.Nullable;

import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.usuarios.interactor.UsuariosInteractor;
import com.jmarser.app_practicas01.usuarios.view.UsuariosView;
import com.jmarser.app_practicas01.utils.ErrorView;

import java.util.ArrayList;

import javax.inject.Inject;

public class UsuariosPresenterImpl implements UsuariosPresenter, UsuariosInteractor.OnGetUsersCallBack, UsuariosInteractor.OnErrorServer{

    @Inject
    UsuariosInteractor usuariosInteractor;

    @Nullable
    @Inject
    UsuariosView usuariosView;

    @Nullable
    @Inject
    ErrorView errorView;

    @Inject
    public UsuariosPresenterImpl() {
    }

    @Override
    public void getUsers() {
        usuariosInteractor.getUsers(this, this);
    }

    @Override
    public void onSuccessGetUsers(ArrayList<User> listadoUsuarios) {
        usuariosView.setUsers(listadoUsuarios);
    }

    @Override
    public void onErrorGetUsers() {
        errorView.showError();
    }

    @Override
    public void onErrorServer() {
        errorView.errorServer();
    }


}
