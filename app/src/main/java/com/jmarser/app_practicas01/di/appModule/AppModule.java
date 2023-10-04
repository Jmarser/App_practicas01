package com.jmarser.app_practicas01.di.appModule;

import android.content.Context;

import androidx.annotation.Nullable;

import com.jmarser.app_practicas01.login.interactor.LoginInteractor;
import com.jmarser.app_practicas01.login.interactor.LoginInteractorImpl;
import com.jmarser.app_practicas01.login.presenter.LoginPresenter;
import com.jmarser.app_practicas01.login.presenter.LoginPresenterImpl;
import com.jmarser.app_practicas01.login.view.LoginActivity;
import com.jmarser.app_practicas01.login.view.LoginView;
import com.jmarser.app_practicas01.login.view.SplashActivity;
import com.jmarser.app_practicas01.login.view.SplashView;
import com.jmarser.app_practicas01.main.MainActivity;
import com.jmarser.app_practicas01.utils.ErrorView;

import dagger.Module;
import dagger.Provides;

@Module(includes = {SharedPreferencesModule.class})
public class AppModule {

    /* Propiedades */
    private SplashActivity splashActivity;
    private LoginActivity loginActivity;
    private MainActivity mainActivity;

    private Context context;


    /* Constructores */

    public AppModule() {
    }

    public AppModule(SplashActivity splashActivity, Context context) {
        this.splashActivity = splashActivity;
        this.context = context;
    }

    public AppModule(LoginActivity loginActivity, Context context) {
        this.loginActivity = loginActivity;
        this.context = context;
    }

    public AppModule(MainActivity mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
    }

    /* Views */

    @Nullable
    @Provides
    public SplashView splashActivity(){
        if(splashActivity != null){
            return splashActivity;
        }
        return null;
    }

    @Nullable
    @Provides
    public LoginView loginActivity(){
        if(loginActivity != null){
            return loginActivity;
        }
        return null;
    }

    @Nullable
    @Provides
    public MainActivity mainActivity(){
        if(mainActivity != null){
            return mainActivity;
        }
        return null;
    }

    @Nullable
    @Provides
    public ErrorView provideErrorView(){
        if(splashActivity != null){
            return splashActivity;
        }else if(loginActivity != null){
            return loginActivity;
        }
        return null;
    }

    /* Presenters */

    @Provides
    public LoginPresenter providesLoginPresenter(LoginPresenterImpl presenter){
        return presenter;
    }

    /* Interactors */

    @Provides
    public LoginInteractor providesLoginInteractor(LoginInteractorImpl interactor){
        return interactor;
    }
}
