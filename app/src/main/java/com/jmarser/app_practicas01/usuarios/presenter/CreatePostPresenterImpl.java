package com.jmarser.app_practicas01.usuarios.presenter;

import android.text.TextUtils;

import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.usuarios.interactor.UsuariosInteractor;
import com.jmarser.app_practicas01.usuarios.view.CreatePostView;
import com.jmarser.app_practicas01.utils.ErrorView;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

public class CreatePostPresenterImpl implements CreatePostPresenter, UsuariosInteractor.OnCreatePostCallBack, UsuariosInteractor.OnEditPostCallBack, UsuariosInteractor.OnErrorServer{



    @Nullable
    @Inject
    CreatePostView createPostView;

    @Nullable
    @Inject
    ErrorView errorView;

    @Inject
    UsuariosInteractor usuariosInteractor;

    @Inject
    public CreatePostPresenterImpl() {
    }

    @Override
    public void createPost(TextInputLayout til_titulo, TextInputLayout til_body, int userId) {
        String title = til_titulo.getEditText().getText().toString();
        String body = til_body.getEditText().getText().toString();
        Boolean titleIsEmpty = true;
        Boolean bodyIsEmpty = true;

        if(!TextUtils.isEmpty(title)){
            titleIsEmpty = false;
        }else{
            til_titulo.setError("Debe indicar un título");
            til_titulo.requestFocus();
        }

        if(!TextUtils.isEmpty(body)){
            bodyIsEmpty = false;
        }else{
            til_body.setError("Escriba el post");
            if(!titleIsEmpty){
                til_body.requestFocus();
            }
        }

        if(userId > 0 && !titleIsEmpty && !bodyIsEmpty){
            usuariosInteractor.createPost(title, body, userId, this, this);
        }else{
            errorView.showError();
        }
    }



    @Override
    public void onSuccessCreatePost(Post post) {
        createPostView.successCreatePost(post);
    }

    @Override
    public void onErrorCreatePost() {
        errorView.showError();
    }

    @Override
    public void editPost(TextInputLayout til_titulo, TextInputLayout til_body, Post post) {
        String title = til_titulo.getEditText().getText().toString();
        String body = til_body.getEditText().getText().toString();
        Boolean titleIsEmpty = true;
        Boolean bodyIsEmpty = true;

        if(!TextUtils.isEmpty(title)){
            titleIsEmpty = false;
        }else{
            til_titulo.setError("Debe indicar un título");
            til_titulo.requestFocus();
        }

        if(!TextUtils.isEmpty(body)){
            bodyIsEmpty = false;
        }else{
            til_body.setError("Escriba el post");
            if(!titleIsEmpty){
                til_body.requestFocus();
            }
        }

        if (post != null){
            usuariosInteractor.editPost(post.getId(), title, body, post.getUserId(), this, this);
        }else{
            errorView.showError();
        }
    }

    @Override
    public void onSuccessEditPost(Post post) {
        createPostView.successEditPost(post);
    }

    @Override
    public void onErrorEditPost() {
        errorView.showError();
    }

    @Override
    public void onErrorServer() {
        errorView.errorServer();
    }
}
