package com.jmarser.app_practicas01.albunes.view;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.User;

import java.util.ArrayList;

public interface AlbunesView {

    void setGetAllUsers(ArrayList<User> listadoUsuarios);

    void setGetAllAlbunes(ArrayList<Album> listadoAlbunes);

}
