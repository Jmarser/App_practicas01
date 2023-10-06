package com.jmarser.app_practicas01.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.jmarser.app_practicas01.R;
import com.jmarser.app_practicas01.albunes.view.AlbunesFragment;
import com.jmarser.app_practicas01.databinding.ActivityMainBinding;
import com.jmarser.app_practicas01.di.appComponent.AppComponent;
import com.jmarser.app_practicas01.di.appComponent.DaggerAppComponent;
import com.jmarser.app_practicas01.di.appModule.AppModule;
import com.jmarser.app_practicas01.di.appModule.SharedPreferencesModule;
import com.jmarser.app_practicas01.portadas.view.PortadasFragment;
import com.jmarser.app_practicas01.usuarios.view.UsuariosFragment;
import com.jmarser.app_practicas01.utils.NavFragment;

import java.util.Objects;
import java.util.Stack;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Inject
    SharedPreferences sharedPreferences;

    private ActivityMainBinding binding;

    // Pila para recoger y almacenar el historial de los fragments por los que se ha navegado del menú
    private Stack<Fragment> fragmentStack = new Stack<>();

    // Variable para controlar la salida de la app con doble toque
    private boolean doubleTouchExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Ocultamos la actionBar de la actividad
        Objects.requireNonNull(getSupportActionBar()).hide();

        initInjection();

        initListener();

        // Por defecto seleccionamos el fragment que se va a mostrar al iniciar la actividad
        if (savedInstanceState == null) {
            NavFragment.replaceFragment(getSupportFragmentManager(), new UsuariosFragment(), UsuariosFragment.class.getName());
        }
    }

    private void initInjection() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, this))
                .sharedPreferencesModule(new SharedPreferencesModule(this))
                .build();

        appComponent.inject(this);
    }

    private void initListener() {
        binding.navigationButton.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.menu_user:
                selectedFragment = new UsuariosFragment();
                break;
            case R.id.menu_albums:
                selectedFragment = new AlbunesFragment();
                break;
            case R.id.menu_galery:
                selectedFragment = new PortadasFragment();
                break;
        }

        // Almacenamos el fragment seleccinado en nuestra pila de navegación
        fragmentStack.push(selectedFragment);

        // Realizamos la navegación
        NavFragment.replaceFragment(getSupportFragmentManager(), selectedFragment, selectedFragment.getClass().getName());

        return true;
    }

    private void updateBottomNavigationView(Fragment fragment) {
        int itemPosition = -1;

        if (fragment instanceof UsuariosFragment) {
            itemPosition = 0;
        } else if (fragment instanceof AlbunesFragment) {
            itemPosition = 1;
        } else if (fragment instanceof PortadasFragment) {
            itemPosition = 2;
        }

        // Seleccionamos el item del menú para que se active
        binding.navigationButton.setSelectedItemId(binding.navigationButton.getMenu().getItem(itemPosition).getItemId());
    }

    @Override
    public void onBackPressed() {

        if (!fragmentStack.isEmpty() && fragmentStack.size() > 1) {
            fragmentStack.pop();
            Fragment fragmentPrevius = fragmentStack.pop(); //Obtenemos el último fragment
            NavFragment.replaceFragment(getSupportFragmentManager(), fragmentPrevius, fragmentPrevius.getClass().getName()); // navegamos hacia el fragment
            updateBottomNavigationView(fragmentPrevius); // seleccionamos el item del menú que corresponse al fragment
        } else {
            if (doubleTouchExit) {
                Toast.makeText(this, "Hasta pronto", Toast.LENGTH_LONG).show();
                super.onBackPressed();
                finish();
            }
            if (!doubleTouchExit) {
                Toast.makeText(this, "Presione dos veces para salir", Toast.LENGTH_LONG).show();
            }

            doubleTouchExit = true; // Cambiamos el valor de la variable

            // Si pasado un tiempo no se vuelve a pulsar el botón atrás se cambia el valor de la variable
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTouchExit = false;
                }
            }, 2000);

        }
    }
}