package com.jmarser.app_practicas01.login.interactor;

public interface LoginInteractor {

    void tryToLogin(String email, String password, OnGetLoginCallBack callBack);

    interface OnGetLoginCallBack{
        void onSuccessLogin(String email, String password);
        void onErrorLogin();
    }
}
