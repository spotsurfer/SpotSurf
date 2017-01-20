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
public class SS_Fragment extends Fragment{

    public static SS_Fragment newInstance() {

        SS_Fragment fragment = new SS_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_1, container, false);
        return rootView;
    }
}
