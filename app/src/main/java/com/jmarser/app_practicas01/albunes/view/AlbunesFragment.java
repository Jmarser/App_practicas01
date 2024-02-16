package com.jmarser.app_practicas01.albunes.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.jmarser.app_practicas01.albunes.adapters.AlbumAdapter;
import com.jmarser.app_practicas01.albunes.presenter.AlbunesPresenter;
import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.databinding.FragmentAlbunesBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;
import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.ConnectionModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.utils.ErrorView;

import java.util.ArrayList;

import javax.inject.Inject;


public class AlbunesFragment extends Fragment implements AlbunesView, ErrorView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener, AlbumAdapter.OnMessageEmpty {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    AlbunesPresenter albunesPresenter;

    private FragmentAlbunesBinding binding;
    private ArrayList<User> listadoUsuarios;
    private ArrayList<Album> listadoAlbunes;
    private AlbumAdapter albumAdapter;
    private int userId = -1;
    private String albumFilter = "";


    public AlbunesFragment() {
        // Required empty public constructor
    }

    public static AlbunesFragment newInstance() {
        AlbunesFragment fragment = new AlbunesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlbunesBinding.inflate(inflater,container, false);

        getActivity().setTitle("Álbunes");

        initInjection();
        initListener();

        binding.pbRecyclerAlbunes.setVisibility(View.VISIBLE);
        albunesPresenter.getAllAlbunes();
        albunesPresenter.getAllUsers();

        return binding.getRoot();
    }

    private void initInjection(){
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, getContext()))
                .connectionModule(new ConnectionModule())
                .sharedPreferencesModule(new SharedPreferencesModule(getContext()))
                .build();

        appComponent.inject(this);
    }

    private void initListener(){
        binding.srlAlbunes.setOnRefreshListener(this);
        binding.spnAutores.setOnItemSelectedListener(this);
        binding.searchAlbum.setOnQueryTextListener(this);
    }

    private void initSpinnerUsuarios(){
        String[] nombreUsuarios;
        if(listadoUsuarios != null && listadoUsuarios.size() > 0){
            nombreUsuarios = new String[listadoUsuarios.size() + 1];
            nombreUsuarios[0] = "Todos los usuarios";
            for(int i = 0; i < listadoUsuarios.size(); i++){
                nombreUsuarios[i + 1] = listadoUsuarios.get(i).getUsername();
            }
        }else{
            nombreUsuarios = new String[]{"No hay usuarios disponibles"};
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nombreUsuarios);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnAutores.setAdapter(adapterSpinner);
    }

    private void initRecyclerAlbunes(){
        binding.pbRecyclerAlbunes.setVisibility(View.GONE);
        if(listadoAlbunes != null && listadoAlbunes.size() > 0){

            binding.rvAlbunes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            albumAdapter = new AlbumAdapter(listadoAlbunes, this);
            binding.rvAlbunes.setAdapter(albumAdapter);
            binding.tvNumAlbumes.setText(""+albumAdapter.getItemCount());
            hiddeMessageEmpty();

            // Si hay texto para el filtrado, solicitamos el filtrado.
            if(!albumFilter.isEmpty()){
                albumAdapter.getFilter().filter(albumFilter);
            }

        }else{
            showMessageEmpty();
        }
    }

    private void showMessageEmpty(){
        binding.tvRvAlbumEmpty.setVisibility(View.VISIBLE);
        binding.rvAlbunes.setVisibility(View.INVISIBLE);
    }

    private void hiddeMessageEmpty(){
        binding.tvRvAlbumEmpty.setVisibility(View.GONE);
        binding.rvAlbunes.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if(idView == binding.layoutError.btnRetry.getId()){
            binding.pbRecyclerAlbunes.setVisibility(View.VISIBLE);
            binding.layoutError.clError.setVisibility(View.GONE);
            albunesPresenter.getAllUsers();
            albunesPresenter.getAllAlbunes();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == binding.spnAutores.getId()){
            if (position == 0){
                userId = -1;
                albunesPresenter.getAllAlbunes();
            }else{
                userId = listadoUsuarios.get(position - 1).getId();
                albunesPresenter.getAlbunesForUserID(userId);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRefresh() {
        // Si no hay usuario seleccionado pedimos todos los albumes
        if(userId == -1){
            albunesPresenter.getAllAlbunes();
        }else{ // pedimos los albumes del usuario seleccionado.
            albunesPresenter.getAlbunesForUserID(userId);
        }

        binding.pbRecyclerAlbunes.setVisibility(View.VISIBLE);
        binding.srlAlbunes.setRefreshing(false);
    }

    @Override
    public void setGetAllUsers(ArrayList<User> listadoUsuarios) {
        if(this.listadoUsuarios != null){
            this.listadoUsuarios.clear();
        }
        this.listadoUsuarios = listadoUsuarios;
        initSpinnerUsuarios();
    }

    @Override
    public void setGetAllAlbunes(ArrayList<Album> listadoAlbunes) {
        if(this.listadoAlbunes != null){
            this.listadoAlbunes.clear();
        }
        this.listadoAlbunes = listadoAlbunes;
        initRecyclerAlbunes();
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        albumFilter = newText;
        albumAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onMessageEmpty(Boolean visible) {
        binding.tvNumAlbumes.setText(""+albumAdapter.getItemCount());
        if(visible){
            showMessageEmpty();
        }else{
            hiddeMessageEmpty();
        }
    }

}