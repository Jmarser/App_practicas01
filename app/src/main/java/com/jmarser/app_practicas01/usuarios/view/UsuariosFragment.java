package com.jmarser.app_practicas01.usuarios.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmarser.app_practicas01.R;


public class UsuariosFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuarios, container, false);
    }
}