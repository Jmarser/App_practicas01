package com.jmarser.app_practicas01.albunes.interactor;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.User;

import java.util.ArrayList;

public interface AlbunesInteractor {

    void getAllUsers(OnGetAllUsersCallBack callBack, OnErrorServer errorServer);

    interface OnGetAllUsersCallBack{
        void onSuccessGetAllUsers(ArrayList<User> listadoUsuarios);
        void onErrorGetAllUsers();
    }

    void getAllAlbunes(OnGetAllAlbunesCallBack callBack, OnErrorServer errorServer);

    void getAlbunesForUserID(int userId, OnGetAllAlbunesCallBack callBack, OnErrorServer errorServer);

    interface OnGetAllAlbunesCallBack{
        void onSuccessGetAllAlbunes(ArrayList<Album> listadoAlbunes);
        void onErrorGetAllAlbunes();
    }

    interface OnErrorServer{
        void onErrorServer();
    }


}
