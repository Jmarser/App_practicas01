package com.jmarser.app_practicas01.di.appComponent;


import com.jmarser.app_practicas01.albunes.view.AlbunesFragment;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.login.view.LoginActivity;
import com.jmarser.app_practicas01.login.view.SplashActivity;
import com.jmarser.app_practicas01.main.MainActivity;
import com.jmarser.app_practicas01.portadas.view.PortadasFragment;
import com.jmarser.app_practicas01.usuarios.view.PostDetailsActivity;
import com.jmarser.app_practicas01.usuarios.view.UsuarioDetailActivity;
import com.jmarser.app_practicas01.usuarios.view.UsuariosFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, SharedPreferencesModule.class})
public interface AppComponent {

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(UsuariosFragment usuariosFragment);

    void inject(AlbunesFragment albunesFragment);

    void inject(PortadasFragment portadasFragment);

    void inject(UsuarioDetailActivity usuarioDetailActivity);

    void inject(PostDetailsActivity postDetailsActivity);
}
