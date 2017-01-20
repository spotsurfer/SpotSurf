package com.bam.spotsurf.fragnav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bam.spotsurf.R;

/**
 * Created by bmerm on 8/11/2016.
 */
public class RateSpotFragment extends Fragment{

    public static RateSpotFragment newInstance() {

        RateSpotFragment fragment = new RateSpotFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.rate_frag, container, false);


        return rootView;
    }
}
