package com.jmarser.app_practicas01.albunes.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmarser.app_practicas01.R;

public class AlbunesFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albunes, container, false);
    }
}