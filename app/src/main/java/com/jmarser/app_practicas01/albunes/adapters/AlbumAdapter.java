package com.jmarser.app_practicas01.albunes.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.databinding.RowAlbumBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private ArrayList<Album> listadoAlbumes;
    private ArrayList<Album> listadoFiltrado;

    public AlbumAdapter(ArrayList<Album> listadoAlbunes) {
        this.listadoAlbumes = listadoAlbunes;
        listadoFiltrado = new ArrayList<>(listadoAlbunes);
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowAlbumBinding binding = RowAlbumBinding.inflate(layoutInflater, parent, false);
        return new AlbumViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.bindAlbum(listadoAlbumes.get(position));
    }

    @Override
    public int getItemCount() {
        return listadoAlbumes.size();
    }


    public void filtrado(String filtro){
        listadoAlbumes.clear();
        if(filtro.length() == 0){
            listadoAlbumes.addAll(listadoFiltrado);
        }else{
            List<Album> collection = listadoFiltrado.stream()
                    .filter(album -> album.getTitle().toLowerCase().contains(filtro.toLowerCase())).collect(Collectors.toList());
            listadoAlbumes.addAll(collection);
        }
        notifyDataSetChanged();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder{

        private RowAlbumBinding binding;

        public AlbumViewHolder(@NonNull RowAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindAlbum(Album album){
            binding.tvTitleAlbum.setText(album.getTitle());
        }
    }
}
