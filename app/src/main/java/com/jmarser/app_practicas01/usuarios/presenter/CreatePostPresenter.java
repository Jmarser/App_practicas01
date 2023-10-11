package com.jmarser.app_practicas01.usuarios.presenter;

import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.app_practicas01.api.models.Post;

public interface CreatePostPresenter {

    void createPost(TextInputLayout til_titulo, TextInputLayout til_body, int userId);

    void editPost(TextInputLayout til_titulo, TextInputLayout til_body, Post post);
}
