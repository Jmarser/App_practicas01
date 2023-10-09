package com.jmarser.app_practicas01.portadas.view;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.Portada;
import com.jmarser.app_practicas01.api.models.User;

import java.util.ArrayList;

public interface PortadasView {

    void setUsuarios(ArrayList<User> usuarios);

    void setAlbumes(ArrayList<Album> albumes);

    void setPortadas(ArrayList<Portada> portadas);
}
