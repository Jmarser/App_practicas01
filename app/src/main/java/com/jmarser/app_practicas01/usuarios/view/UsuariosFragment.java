package com.jmarser.app_practicas01.usuarios.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.databinding.FragmentUsuariosBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;
import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.usuarios.adapters.UsuariosAdapter;
import com.jmarser.app_practicas01.usuarios.presenter.UsuariosPresenter;
import com.jmarser.app_practicas01.utils.ErrorView;

import java.util.ArrayList;

import javax.inject.Inject;


public class UsuariosFragment extends Fragment implements UsuariosView, ErrorView, UsuariosAdapter.OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private FragmentUsuariosBinding binding;
    private UsuariosAdapter usuariosAdapter;
    private ArrayList<User> listadoUsuarios;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    UsuariosPresenter usuariosPresenter;

    public UsuariosFragment() {
        // Required empty public constructor
    }

    public static UsuariosFragment newInstance() {
        UsuariosFragment fragment = new UsuariosFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsuariosBinding.inflate(inflater, container, false);

        getActivity().setTitle("Usuarios");

        initInjection();
        initListener();

        binding.pbRecyclerUsuarios.setVisibility(View.VISIBLE);
        usuariosPresenter.getUsers();

        return binding.getRoot();
    }

    private void initInjection(){
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, getContext()))
                .sharedPreferencesModule(new SharedPreferencesModule(getContext()))
                .build();

        appComponent.inject(this);
    }

    private void initListener(){
        binding.layoutError.btnRetry.setOnClickListener(this);
        binding.pullRefresh.setOnRefreshListener(this);
    }

    private void initRecyclerUsuarios(){
        binding.pbRecyclerUsuarios.setVisibility(View.GONE);

        if(listadoUsuarios != null && listadoUsuarios.size() > 0){
            binding.rvUsers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            usuariosAdapter = new UsuariosAdapter(listadoUsuarios, this);
            binding.rvUsers.setAdapter(usuariosAdapter);
        }else{
            binding.tvRecyclerEmpty.setVisibility(View.VISIBLE);
            binding.rvUsers.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if (idView == binding.layoutError.btnRetry.getId()){
            binding.layoutError.clError.setVisibility(View.GONE);
            binding.pbRecyclerUsuarios.setVisibility(View.VISIBLE);
            usuariosPresenter.getUsers();
        }
    }

    @Override
    public void onRefresh() {
        usuariosPresenter.getUsers();
        binding.pullRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClickListener(User user) {
        Toast.makeText(getContext(), user.getUsername(), Toast.LENGTH_LONG).show();
        /*Bundle bundle = new Bundle();
        bundle.putParcelable(Constantes.BUNDLE_USUARIO, user);

        Intent intent = new Intent(getActivity(), UsuarioDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);*/
    }

    @Override
    public void setUsers(ArrayList<User> listadoUsuarios) {
        this.listadoUsuarios = listadoUsuarios;
        initRecyclerUsuarios();
    }

    @Override
    public void showError() {
        initRecyclerUsuarios();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        initRecyclerUsuarios();
    }

    @Override
    public void errorServer() {
        binding.layoutError.clError.setVisibility(View.VISIBLE);
    }
}