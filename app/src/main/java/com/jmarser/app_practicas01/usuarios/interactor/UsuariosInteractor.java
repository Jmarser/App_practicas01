package com.jmarser.app_practicas01.usuarios.interactor;

import com.jmarser.app_practicas01.api.models.User;

import java.util.ArrayList;

public interface UsuariosInteractor {

    void getUsers(OnGetUsersCallBack callBack);

    interface OnGetUsersCallBack{
        void onSuccessGetUsers(ArrayList<User> listadoUsuarios);
        void onErrorGetUsers();
        void onErrorServer();
    }
}
