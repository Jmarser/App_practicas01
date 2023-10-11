package com.jmarser.app_practicas01.usuarios.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jmarser.app_practicas01.R;
import com.jmarser.app_practicas01.api.models.Post;
import com.jmarser.app_practicas01.api.models.Task;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.databinding.ActivityUsuarioDetailBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;
import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.usuarios.adapters.TasksAdapter;
import com.jmarser.app_practicas01.usuarios.adapters.UserDetailsAdapter;
import com.jmarser.app_practicas01.usuarios.presenter.UsuarioDetailsPresenter;
import com.jmarser.app_practicas01.utils.Constantes;
import com.jmarser.app_practicas01.utils.ErrorView;

import java.util.ArrayList;

import javax.inject.Inject;

public class UsuarioDetailActivity extends AppCompatActivity implements UsuarioDetailsView, ErrorView, View.OnClickListener, UserDetailsAdapter.OnItemPostClickListener, TasksAdapter.OnItemTaskClickListener {

    private ActivityUsuarioDetailBinding binding;
    private User user;
    private ArrayList<Post> listadoPosts;
    private UserDetailsAdapter userDetailsAdapter;
    private ArrayList<Task> listadoTodos;
    private TasksAdapter tasksAdapter;
    private boolean isSwipeRefresh1 = false;
    private ActionBar actionBar;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    UsuarioDetailsPresenter usuarioDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initInjection();
        initActionBar();

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable(Constantes.BUNDLE_USUARIO);

        if (user != null){
            setFields();
        }

        binding.pbRecyclerTodos.setVisibility(View.VISIBLE);
        binding.pbRecyclerPosts.setVisibility(View.VISIBLE);
        usuarioDetailsPresenter.getPosts(user.getId());
        usuarioDetailsPresenter.getTodos();

        initListeners();
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
        actionBar.setTitle("Detalles usuario");
    }

    private void setFields(){
        binding.tvNombre.setText(user.getName());
        binding.tvNombreUsuario.setText(user.getUsername());
    }

    private void initListeners(){
        binding.layoutError.btnRetry.setOnClickListener(this);
        binding.srlUserDetails.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                usuarioDetailsPresenter.getPosts(user.getId());
                binding.srlUserDetails.setRefreshing(false);
            }
        });
        binding.srlUserTodos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                usuarioDetailsPresenter.getTodos();
                binding.srlUserTodos.setRefreshing(false);
            }
        });
    }

    private void initRecyclerPosts(){
        binding.pbRecyclerPosts.setVisibility(View.GONE);
        if(listadoPosts != null && listadoPosts.size() > 0){
            userDetailsAdapter = new UserDetailsAdapter(listadoPosts, this);
            //userDetailsAdapter.filtrarPostsForUser(user);
            if(userDetailsAdapter.getItemCount() > 0){
                binding.rvDetailsUser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                binding.rvDetailsUser.setAdapter(userDetailsAdapter);
                binding.tvDetailsUserEmpty.setVisibility(View.GONE);
            }else{
                binding.tvDetailsUserEmpty.setVisibility(View.VISIBLE);
            }
        }else{
            binding.tvDetailsUserEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerTodos(){
        binding.pbRecyclerTodos.setVisibility(View.GONE);
        if(listadoTodos != null && listadoTodos.size() > 0){
            tasksAdapter = new TasksAdapter(listadoTodos, this);
            tasksAdapter.filtrarTasksForUser(user);
            if(tasksAdapter.getItemCount() > 0){
                binding.rvTodosUser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                binding.rvTodosUser.setAdapter(tasksAdapter);
                binding.tvTodosEmpty.setVisibility(View.GONE);
            }else{
                binding.tvTodosEmpty.setVisibility(View.VISIBLE);
            }
        }else{
            binding.tvTodosEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usuario_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.create_post){
            //Toast.makeText(this, "Pulsado el menu de crear", Toast.LENGTH_LONG).show();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constantes.BUNDLE_USUARIO, user);

            Intent intent = new Intent(this, CreatePostActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if (idView == binding.layoutError.btnRetry.getId()){
            binding.layoutError.clError.setVisibility(View.GONE);
            binding.pbRecyclerTodos.setVisibility(View.VISIBLE);
            binding.pbRecyclerPosts.setVisibility(View.VISIBLE);
            usuarioDetailsPresenter.getPosts(user.getId());
            usuarioDetailsPresenter.getTodos();
        }
    }

    @Override
    public void onItemPostClickListener(Post post) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constantes.BUNDLE_POST, post);

        Intent intent = new Intent(this, PostDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void setPosts(ArrayList<Post> listadoPosts) {
        this.listadoPosts  =listadoPosts;
        initRecyclerPosts();
    }

    @Override
    public void setTodos(ArrayList<Task> listadoTodos) {
        this.listadoTodos = listadoTodos;
        initRecyclerTodos();
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
    public void onItemTaskClickListener(Task task) {
        task.setCompleted(!task.isCompleted());
        tasksAdapter.notifyDataSetChanged();
    }
}