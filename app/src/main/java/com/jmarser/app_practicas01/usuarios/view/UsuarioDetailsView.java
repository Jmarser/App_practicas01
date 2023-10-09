package com.jmarser.app_practicas01.usuarios.view;

import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.api.models.Task;

import java.util.ArrayList;

public interface UsuarioDetailsView {

    void setPosts(ArrayList<Post> listadoPosts);

    void setTodos(ArrayList<Task> listadoTodos);
}
