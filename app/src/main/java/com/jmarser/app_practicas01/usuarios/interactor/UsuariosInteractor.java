package com.jmarser.app_practicas01.usuarios.interactor;

import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.api.models.Task;
import com.jmarser.app_practicas01.api.models.User;

import java.util.ArrayList;

public interface UsuariosInteractor {

    void getUsers(OnGetUsersCallBack callBack, OnErrorServer errorServer);

    interface OnGetUsersCallBack{
        void onSuccessGetUsers(ArrayList<User> listadoUsuarios);
        void onErrorGetUsers();
    }

    void getPostsForUserID(int userId, OnGetPostsCallBack callBack, OnErrorServer errorServer);

    interface OnGetPostsCallBack{
        void onSuccessGetPostsForUserId(ArrayList<Post> listadoPosts);
        void onErrorGetPosts();
    }

    void getTodos(OnGetTodosCallBack callBack, OnErrorServer errorServer);

    interface OnGetTodosCallBack{
        void onSuccessGetTodos(ArrayList<Task> listadoTasks);
        void onErrorGetTodos();
    }

    interface OnErrorServer{
        void onErrorServer();
    }
}
