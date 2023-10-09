package com.jmarser.app_practicas01.portadas.interactor;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.Portada;
import com.jmarser.app_practicas01.api.models.User;

import java.util.ArrayList;

public interface PortadasInteractor {

    void getAllUsers(OnGetAllUsersCallBack callBack, OnErrorServer errorServer);

    interface OnGetAllUsersCallBack{
        void onSuccessGetAllUsers(ArrayList<User> usuarios);
        void onErrorGetAllUsers();
    }

    void getAllAlbumes(OnGetAllAlbunesCallBack callBack, OnErrorServer errorServer);

    void getAlbumesForUserID(int userId, OnGetAllAlbunesCallBack callBack, OnErrorServer errorServer);

    interface OnGetAllAlbunesCallBack{
        void onSuccessGetAllAlbumes(ArrayList<Album> albumes);
        void onErrorGetAllAlbumes();
    }

    void getAllPortadas(OnGetAllPortadasCallBack callBack, OnErrorServer errorServer);

    void getPortadasForAlbumID(int albumId, OnGetAllPortadasCallBack callBack, OnErrorServer errorServer);

    interface OnGetAllPortadasCallBack{
        void onSuccessGetAllPortadas(ArrayList<Portada> portadas);
        void onErrorGetAllPortadas();
    }

    interface OnErrorServer{
        void onErrorServer();
    }
}
