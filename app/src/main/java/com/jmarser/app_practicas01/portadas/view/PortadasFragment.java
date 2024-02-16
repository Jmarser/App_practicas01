package com.jmarser.app_practicas01.portadas.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.jmarser.app_practicas01.R;
import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.Portada;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.databinding.FragmentPortadasBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;
import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.ConnectionModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.portadas.adapters.PortadasAdapter;
import com.jmarser.app_practicas01.portadas.presenter.PortadasPresenter;
import com.jmarser.app_practicas01.utils.ErrorView;

import java.util.ArrayList;

import javax.inject.Inject;


public class PortadasFragment extends Fragment implements PortadasView, ErrorView, View.OnClickListener,AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    PortadasPresenter portadasPresenter;

    private FragmentPortadasBinding binding;
    private ArrayList<User> usuarios;
    private ArrayList<Album> albumes;
    private Album albumSelect = null;
    private ArrayList<Portada> portadas;
    private PortadasAdapter portadasAdapter;
    private int userId = -1;
    private int albumId = -1;


    public PortadasFragment() {
        // Required empty public constructor
    }

    public static PortadasFragment newInstance() {
        PortadasFragment fragment = new PortadasFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPortadasBinding.inflate(inflater, container, false);
        getActivity().setTitle("Portadas");

        initinjection();

        initListener();

        cabecera();

        binding.pbRecyclerPortadas.setVisibility(View.VISIBLE);
        portadasPresenter.getAllUsers();
        portadasPresenter.getAllAlbumes();
        portadasPresenter.getAllPortadas();

        return binding.getRoot();
    }

    private void initinjection(){
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, getContext()))
                .connectionModule(new ConnectionModule())
                .sharedPreferencesModule(new SharedPreferencesModule(getContext()))
                .build();

        appComponent.inject(this);
    }

    private void initListener(){
        binding.spnAutoresPortadas.setOnItemSelectedListener(this);
        binding.spnAlbunesPortadas.setOnItemSelectedListener(this);
        binding.srlPortadas.setOnRefreshListener(this);
    }

    private void initSpinnerUsuarios(){
        String[] nombreUsuarios;
        if(usuarios != null && usuarios.size() > 0){
            nombreUsuarios = new String[usuarios.size() + 1];
            nombreUsuarios[0] = "Todos los usuarios";
            for(int i = 0; i < usuarios.size(); i++){
                nombreUsuarios[i + 1] = usuarios.get(i).getUsername();
            }
        }else{
            nombreUsuarios = new String[]{"No hay usuarios disponibles"};
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nombreUsuarios);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnAutoresPortadas.setAdapter(adapterSpinner);
    }

    private void initSpinnerAlbumes(){
        String[] nombreAlbunes;
        if(albumes != null && albumes.size() > 0){
            nombreAlbunes = new String[albumes.size() + 1];
            nombreAlbunes[0] = "Todos los albumes";
            for(int i = 0; i < albumes.size(); i++){
                nombreAlbunes[i + 1] = albumes.get(i).getTitle();
            }
        }else{
            nombreAlbunes = new String[]{"No hay albumes disponibles"};
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, nombreAlbunes);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnAlbunesPortadas.setAdapter(adapterSpinner);
    }

    private void initRecyclerPortadas(){
        binding.pbRecyclerPortadas.setVisibility(View.GONE);
        if(portadas != null && portadas.size() > 0){
            binding.rvPortadas.setLayoutManager(new GridLayoutManager(getContext(), 2));
            portadasAdapter = new PortadasAdapter(portadas);
            binding.rvPortadas.setAdapter(portadasAdapter);
            binding.tvRvPortadasEmpty.setVisibility(View.GONE);
            binding.srlPortadas.setRefreshing(false);
            binding.tvNumPortadas.setText(""+portadasAdapter.getItemCount());
        }else{
            binding.tvRvPortadasEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void cabecera(){
        if (albumSelect != null){
            binding.textCabeceraPortada.setText("Portadas album: " + albumSelect.getTitle());
        }else{
            binding.textCabeceraPortada.setText("Todas las portadas");
        }
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if(itemId == binding.layoutError.btnRetry.getId()){
            binding.layoutError.clError.setVisibility(View.GONE);
            binding.pbRecyclerPortadas.setVisibility(View.VISIBLE);
            portadasPresenter.getAllUsers();
            if(userId != -1){
                portadasPresenter.getAlbumesForUserID(userId);
            }else{
                portadasPresenter.getAllAlbumes();
            }
            if(albumId != -1){
                portadasPresenter.getPortadasForAlbumID(albumId);
            }else{
                portadasPresenter.getAllPortadas();
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idItem = parent.getId();

        if (idItem == binding.spnAutoresPortadas.getId()){
            if(position == 0){
                userId = -1;
                portadasPresenter.getAllAlbumes();
            }else{
                userId = usuarios.get(position - 1).getId();
                portadasPresenter.getAlbumesForUserID(userId);
            }
        }

        if (idItem == binding.spnAlbunesPortadas.getId()){
            if(position == 0){
                albumId = -1;
                albumSelect = null;
                portadasPresenter.getAllPortadas();
            }else{
                albumSelect = albumes.get(position - 1);
                albumId = albumSelect.getId();
                portadasPresenter.getPortadasForAlbumID(albumId);
            }
            cabecera();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRefresh() {
        if(albumId != -1){
            portadasPresenter.getPortadasForAlbumID(albumId);
        }else{
            portadasPresenter.getAllPortadas();
        }
        binding.srlPortadas.setRefreshing(false);
    }

    @Override
    public void setUsuarios(ArrayList<User> usuarios) {
        this.usuarios = usuarios;
        initSpinnerUsuarios();
    }

    @Override
    public void setAlbumes(ArrayList<Album> albumes) {
        if(this.albumes != null){
            this.albumes.clear();
        }
        this.albumes = albumes;
        initSpinnerAlbumes();
    }

    @Override
    public void setPortadas(ArrayList<Portada> portadas) {
        if(this.portadas != null){
            this.portadas.clear();
        }
        this.portadas = portadas;
        initRecyclerPortadas();
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

}