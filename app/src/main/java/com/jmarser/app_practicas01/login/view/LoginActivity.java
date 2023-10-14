package com.jmarser.app_practicas01.login.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jmarser.app_practicas01.databinding.ActivityLoginBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;
import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.login.presenter.LoginPresenter;
import com.jmarser.app_practicas01.main.MainActivity;
import com.jmarser.app_practicas01.utils.Constantes;
import com.jmarser.app_practicas01.utils.ErrorView;

import java.util.Objects;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginView, ErrorView, View.OnClickListener {

    private ActivityLoginBinding binding;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Ocultamos la actionBar de la actividad
        Objects.requireNonNull(getSupportActionBar()).hide();

        initInjection();
        initListeners();
    }

    /**
     * Método con el que cargamos dagger para implementar la inyección de dependencias
     * */
    private void initInjection(){
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, this))
                .sharedPreferencesModule(new SharedPreferencesModule(this))
                .build();

        appComponent.inject(this);
    }

    /**
     * Método en el que inicializaremos todos los listener que tengamos en nuestra actividad
     * */
    private void initListeners(){
        binding.btnLogin.setOnClickListener(this);
        //Gestionamos que cuando el TextInputLayout pierda el foco elimine el error que muestre
        binding.tilEmail.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    binding.tilEmail.setError(null);
                }
            }
        });
        binding.tilPassword.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    binding.tilPassword.setError(null);
                }
            }
        });
    }

    private void showProgressBar(){
        binding.btnLogin.setVisibility(View.INVISIBLE);
        binding.pbLogin.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.pbLogin.setVisibility(View.INVISIBLE);
    }

    private void clearFormLogin(){
        binding.tilEmail.getEditText().setText("");
        binding.tilPassword.getEditText().setText("");
        binding.tilEmail.getEditText().requestFocus();
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if(idView == binding.btnLogin.getId()){
            showProgressBar();
            presenter.tryToLogin(binding.tilEmail, binding.tilPassword);
        }
    }

    @Override
    public void goToLogin(String email, String password) {
        Toast.makeText(this, "Login correcto", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(Constantes.INTENT_EMAIL, email);
        intent.putExtra(Constantes.INTENT_PASSWORD, password);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError() {
        hideProgressBar();
    }

    @Override
    public void showErrorMessage(String message) {
        hideProgressBar();
        clearFormLogin();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorServer() {

    }
}