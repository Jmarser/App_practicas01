package com.jmarser.app_practicas01.login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.jmarser.app_practicas01.databinding.ActivitySplashBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;

import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.login.presenter.LoginPresenter;
import com.jmarser.app_practicas01.main.MainActivity;
import com.jmarser.app_practicas01.utils.Constantes;
import com.jmarser.app_practicas01.utils.ErrorView;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity implements SplashView, ErrorView {

    private ActivitySplashBinding binding;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ocultamos la actionBar de la actividad
        getSupportActionBar().hide();
        // Quitamos la barra de notificaciones del sistema
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initInjection();

        // Programamos que pasados unos segundos realicemos la acción de login
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.splashLogin();
            }
        }, 3000);
    }

    private void initInjection(){
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, this))
                .sharedPreferencesModule(new SharedPreferencesModule(this))
                .build();

        appComponent.inject(this);
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToMain(String email, String password) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra(Constantes.INTENT_EMAIL, email);
        intent.putExtra(Constantes.INTENT_PASSWORD, password);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void errorServer() {

    }
}