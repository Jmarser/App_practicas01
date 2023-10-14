package com.jmarser.app_practicas01.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jmarser.app_practicas01.R;


public class NavFragment {

    /* Método con el que reemplazamos un fragment por otro */
    public static void replaceFragment(FragmentManager fm, Fragment fragment, String fragmentTag){
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, 0, 0, android.R.anim.fade_out);
        ft.replace(R.id.frame, fragment, fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commitAllowingStateLoss();
    }

    /* Método con el que agregamos un fragment a un frame */
    public static void addFragment(FragmentManager fm, Fragment fragment, String fragmentTag){
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, 0, 0, android.R.anim.fade_out);
        ft.add(R.id.frame, fragment, fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commitAllowingStateLoss();
    }

    /* Método con el que reemplazamos un fragment por otro en un frame*/
    public static void replaceFragmentAct(FragmentManager fm, int frameID, Fragment fragment, String fragmentTag){
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, 0, 0, android.R.anim.fade_out);
        ft.replace(frameID, fragment, fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commitAllowingStateLoss();
    }

    /* Método con el que agregamos un fragment a un frame */
    public static void addFragmentAct(FragmentManager fm, int frameID,Fragment fragment, String fragmentTag){
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, 0, 0, android.R.anim.fade_out);
        ft.add(frameID, fragment, fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commitAllowingStateLoss();
    }
}
