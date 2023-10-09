package com.jmarser.app_practicas01.portadas.presenter;

public interface PortadasPresenter {

    void getAllUsers();

    void getAllAlbumes();

    void getAlbumesForUserID(int userId);

    void getAllPortadas();

    void getPortadasForAlbumID(int albumId);
}
