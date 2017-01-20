package com.bam.spotsurf.fragnav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bam.spotsurf.R;

/**
 * Created by bmerm on 8/1/2016.
 */
public class MP_Fragment extends Fragment{

    public static MP_Fragment newInstance() {

        MP_Fragment fragment = new MP_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_3, container, false);
        return rootView;
    }
}

