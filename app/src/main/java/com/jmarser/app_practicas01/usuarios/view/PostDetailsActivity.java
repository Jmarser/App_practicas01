package com.jmarser.app_practicas01.usuarios.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.jmarser.app_practicas01.api.models.Comment;
import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.databinding.ActivityPostDetailsBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;
import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.usuarios.adapters.CommentAdapter;
import com.jmarser.app_practicas01.usuarios.presenter.PostDetailsPresenter;
import com.jmarser.app_practicas01.utils.Constantes;
import com.jmarser.app_practicas01.utils.ErrorView;

import java.util.ArrayList;

import javax.inject.Inject;

public class PostDetailsActivity extends AppCompatActivity implements PostDetailsView, ErrorView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private ActivityPostDetailsBinding binding;
    private Post post;
    private ArrayList<Comment> listadoComentarios;
    private CommentAdapter commentAdapter;
    private ActionBar actionBar;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    PostDetailsPresenter postDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());

        initInjection();
        initToolBar();
        initListener();

        Bundle bundle = getIntent().getExtras();
        post = bundle.getParcelable(Constantes.BUNDLE_POST);

        if(post != null){
            setFields();
        }

        binding.pbRecyclerComments.setVisibility(View.VISIBLE);
        postDetailsPresenter.getCommentsForPostId(post.getId());

        setContentView(binding.getRoot());
    }

    private void initInjection(){
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, this))
                .sharedPreferencesModule(new SharedPreferencesModule(this))
                .build();

        appComponent.inject(this);
    }

    private void initToolBar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Detalles Post");
    }

    private void initListener(){
        binding.srlComments.setOnRefreshListener(this);
    }

    private void setFields(){
        binding.tvTituloPost.setText(post.getTitle());
        binding.tvBodyPost.setText(post.getBody());
    }

    private void initRecyclerComentarios(){
        binding.pbRecyclerComments.setVisibility(View.GONE);
        if(listadoComentarios != null && listadoComentarios.size() > 0){
            binding.rvCommentsPost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            commentAdapter = new CommentAdapter(listadoComentarios);
            binding.rvCommentsPost.setAdapter(commentAdapter);
            binding.tvCommentsEmpty.setVisibility(View.GONE);
        }else{
            binding.tvCommentsEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        postDetailsPresenter.getCommentsForPostId(post.getId());
        binding.srlComments.setRefreshing(false);
    }

    @Override
    public void setComments(ArrayList<Comment> listadoComentarios) {
        this.listadoComentarios = listadoComentarios;
        initRecyclerComentarios();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void errorServer() {
        binding.layoutError.clError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == binding.layoutError.btnRetry.getId()){
            binding.layoutError.clError.setVisibility(View.GONE);
            binding.pbRecyclerComments.setVisibility(View.VISIBLE);
            postDetailsPresenter.getCommentsForPostId(post.getId());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}