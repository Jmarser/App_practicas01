package com.jmarser.app_practicas01.usuarios.view;

import com.jmarser.app_practicas01.api.models.Comment;

import java.util.ArrayList;

public interface PostDetailsView {

    void setComments(ArrayList<Comment> listadoComentarios);
}
