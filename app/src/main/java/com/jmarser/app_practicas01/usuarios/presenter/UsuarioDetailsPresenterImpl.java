package com.jmarser.app_practicas01.usuarios.presenter;

import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.api.models.Task;
import com.jmarser.app_practicas01.usuarios.interactor.UsuariosInteractor;
import com.jmarser.app_practicas01.usuarios.view.UsuarioDetailsView;
import com.jmarser.app_practicas01.utils.ErrorView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;

public class UsuarioDetailsPresenterImpl implements UsuarioDetailsPresenter, UsuariosInteractor.OnGetPostsCallBack, UsuariosInteractor.OnGetTodosCallBack, UsuariosInteractor.OnErrorServer{

    @Inject
    UsuariosInteractor usuariosInteractor;

    @Nullable
    @Inject
    UsuarioDetailsView usuarioDetailsView;

    @Nullable
    @Inject
    ErrorView errorView;

    @Inject
    public UsuarioDetailsPresenterImpl() {
    }

    @Override
    public void getPosts(int userId) {
        usuariosInteractor.getPostsForUserID(userId, this, this);
    }

    @Override
    public void onSuccessGetPostsForUserId(ArrayList<Post> listadoPosts) {
        usuarioDetailsView.setPosts(listadoPosts);
    }

    @Override
    public void onErrorGetPosts() {
        errorView.showError();
    }

    @Override
    public void getTodos() {
        usuariosInteractor.getTodos(this, this);
    }

    @Override
    public void onSuccessGetTodos(ArrayList<Task> listadoTasks) {
        usuarioDetailsView.setTodos(listadoTasks);
    }

    @Override
    public void onErrorGetTodos() {
        errorView.showError();
    }

    @Override
    public void onErrorServer() {
        errorView.errorServer();
    }

}
