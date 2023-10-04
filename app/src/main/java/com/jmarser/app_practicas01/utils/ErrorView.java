package com.jmarser.app_practicas01.utils;

/**
 * Interface que contiene los métodos que se van a repetir en casi todas las vistas de la aplicación
 * */
public interface ErrorView {

    void showError();

    void showErrorMessage(String message);

    void errorServer();
}
