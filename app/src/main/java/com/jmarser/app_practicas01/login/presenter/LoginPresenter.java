package com.jmarser.app_practicas01.login.presenter;

import com.google.android.material.textfield.TextInputLayout;

public interface LoginPresenter {

    void tryToLogin(TextInputLayout til_email, TextInputLayout til_password);
}
