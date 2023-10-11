package com.jmarser.app_practicas01.usuarios.view;

import com.jmarser.app_practicas01.api.models.Post;

public interface CreatePostView {

    void successCreatePost(Post post);

    void successEditPost(Post post);
}
