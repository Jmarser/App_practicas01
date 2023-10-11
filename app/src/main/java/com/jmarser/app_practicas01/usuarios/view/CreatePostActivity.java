package com.jmarser.app_practicas01.usuarios.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.databinding.ActivityCreatePostBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;
import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.usuarios.presenter.CreatePostPresenter;
import com.jmarser.app_practicas01.utils.Constantes;
import com.jmarser.app_practicas01.utils.ErrorView;

import javax.inject.Inject;

public class CreatePostActivity extends AppCompatActivity implements CreatePostView, ErrorView, View.OnClickListener {

    private ActivityCreatePostBinding binding;
    private ActionBar actionBar;
    private User user;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    CreatePostPresenter createPostPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());


        initInjection();
        initActionBar();
        initListener();

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable(Constantes.BUNDLE_USUARIO);

        setContentView(binding.getRoot());
    }

    private void initInjection(){
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, this))
                .sharedPreferencesModule(new SharedPreferencesModule(this))
                .build();

        appComponent.inject(this);
    }

    private void initActionBar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Crear Post");
    }

    private void initListener(){
        binding.btnCrearPost.setOnClickListener(this);
        binding.layoutError.btnRetry.setOnClickListener(this);
        binding.tilTituloPost.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    binding.tilTituloPost.setError(null);
                }
            }
        });
        binding.tilMensajePost.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    binding.tilMensajePost.setError(null);
                }
            }
        });
    }

    private void createPost(){
        binding.pbCreatePost.setVisibility(View.VISIBLE);
        binding.btnCrearPost.setVisibility(View.INVISIBLE);
        createPostPresenter.createPost(binding.tilTituloPost, binding.tilMensajePost, user.getId());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == binding.btnCrearPost.getId()){
            createPost();
        }

        if (itemId == binding.layoutError.btnRetry.getId()){
            binding.layoutError.clError.setVisibility(View.GONE);
            createPost();
        }
    }

    @Override
    public void successCreatePost(Post post) {
        binding.pbCreatePost.setVisibility(View.INVISIBLE);
        binding.btnCrearPost.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Post creado correctamente.", Toast.LENGTH_LONG).show();
        onSupportNavigateUp();
    }

    @Override
    public void showError() {
        binding.pbCreatePost.setVisibility(View.INVISIBLE);
        binding.btnCrearPost.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void errorServer() {
        binding.pbCreatePost.setVisibility(View.GONE);
        binding.layoutError.clError.setVisibility(View.VISIBLE);
    }
}