package com.jmarser.app_practicas01.usuarios.presenter;

import com.google.android.material.textfield.TextInputLayout;

public interface CreatePostPresenter {

    void createPost(TextInputLayout til_titulo, TextInputLayout til_body, int userId);
}
