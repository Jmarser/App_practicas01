package com.jmarser.app_practicas01.usuarios.presenter;

import com.jmarser.app_practicas01.api.models.Comment;
import com.jmarser.app_practicas01.usuarios.interactor.UsuariosInteractor;
import com.jmarser.app_practicas01.usuarios.view.PostDetailsView;
import com.jmarser.app_practicas01.utils.ErrorView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;

public class PostDetailsPresenterImpl implements PostDetailsPresenter, UsuariosInteractor.OnGetCommentsCallBack, UsuariosInteractor.OnErrorServer{

    @Nullable
    @Inject
    PostDetailsView postDetailsView;

    @Nullable
    @Inject
    ErrorView errorView;

    @Inject
    UsuariosInteractor usuariosInteractor;

    @Inject
    public PostDetailsPresenterImpl() {
    }

    @Override
    public void getCommentsForPostId(int postId) {
        usuariosInteractor.getCommentsForpstId(postId, this, this);
    }

    @Override
    public void onSuccessGetComments(ArrayList<Comment> listadoComentarios) {
        postDetailsView.setComments(listadoComentarios);
    }

    @Override
    public void onErrorGetComments() {
        errorView.showError();
    }

    @Override
    public void onErrorServer() {
        errorView.errorServer();
    }

}
