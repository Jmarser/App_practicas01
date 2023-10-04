package com.jmarser.app_practicas01.di.appComponent;


import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.login.view.LoginActivity;
import com.jmarser.app_practicas01.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);
}
