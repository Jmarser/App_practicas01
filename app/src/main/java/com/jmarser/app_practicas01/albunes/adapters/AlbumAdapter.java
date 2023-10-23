package com.jmarser.app_practicas01.albunes.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jmarser.app_practicas01.albunes.view.AlbunesView;
import com.jmarser.app_practicas01.api.models.Album;
import com.jmarser.app_practicas01.api.models.User;
import com.jmarser.app_practicas01.databinding.RowAlbumBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> implements Filterable {

    private ArrayList<Album> listadoAlbumes;
    private ArrayList<Album> listadoFiltrado;
    OnMessageEmpty onMessageEmpty;

    public AlbumAdapter(ArrayList<Album> listadoAlbunes, OnMessageEmpty onMessageEmpty) {
        this.listadoAlbumes = listadoAlbunes;
        this.listadoFiltrado = listadoAlbunes;
        this.onMessageEmpty = onMessageEmpty;
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
        return listadoAlbumes == null ? 0:listadoAlbumes.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            // Método que hace el filtrado
            @Override
            protected FilterResults performFiltering(CharSequence textFilter) {
                FilterResults filterResults = new FilterResults();

                if(textFilter == null || textFilter.length() == 0){ // no hay texto para el filtrado
                    filterResults.count = listadoFiltrado.size();
                    filterResults.values = listadoFiltrado;

                }else{ // hay texto para realizar el filtrado

                    ArrayList<Album> resultData = new ArrayList<>();

                    List<Album> collection = listadoFiltrado.stream()
                            .filter(album -> album.getTitle().toLowerCase().contains(textFilter.toString().toLowerCase())).collect(Collectors.toList());
                    resultData.addAll(collection);

                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }
            // Método que publica la respuesta
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listadoAlbumes = (ArrayList<Album>) results.values;
                onMessageEmpty.onMessageEmpty(listadoAlbumes.isEmpty());
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder{

        private RowAlbumBinding binding;

        public AlbumViewHolder(@NonNull RowAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindAlbum(Album album){
            binding.tvTitleAlbum.setText(album.getTitle());
        }
    }

    //Interface con la que vamos a comunicarnos con la vista para mostrar el mensaje de listado vacío
    public interface OnMessageEmpty{
        void onMessageEmpty(Boolean visible);
    }
}
