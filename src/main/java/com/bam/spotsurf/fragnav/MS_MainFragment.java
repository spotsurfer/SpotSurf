package com.bam.spotsurf.fragnav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bam.spotsurf.R;
import com.bam.spotsurf.application.Application;
import com.bam.spotsurf.objects.Spot;
import com.bam.spotsurf.objects.SpotCard;
import com.bam.spotsurf.utils.SpotViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmerm on 10/27/2016.
 */
public class MS_MainFragment extends Fragment{

    List <Spot> currentReservations;

    public static MS_MainFragment newInstance() {

        MS_MainFragment fragment = new MS_MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_3, container, false);

        List<SpotCard> spotCard = fillSpotDesciption();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        SpotViewAdapter adapter = new SpotViewAdapter(spotCard, getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    private List<SpotCard> fillSpotDesciption(){

        List<SpotCard> data = new ArrayList<SpotCard>();

        Application mApp = ((Application) getActivity().getApplication());
        currentReservations= mApp.getCurrentSpotSList();

        for(Spot spot :currentReservations) {
            data.add(new SpotCard(spot.getAddress(), "Active" , R.drawable.noimage));
        }

        return data;

    }


}
