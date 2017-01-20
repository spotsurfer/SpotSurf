package com.bam.spotsurf.fragnav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bam.spotsurf.R;
import com.bam.spotsurf.objects.Spot;

/**
 * Created by bmerm on 8/28/2016.
 */


public class SS_SpotInfoFragment extends Fragment implements View.OnClickListener{

    Spot bestSpot;
    String distanceToDest;
    String timeToDest;
    TextView spotInfo;
    Button cancelButton, reserveButton;

    public static SS_SpotInfoFragment newInstance() {

        SS_SpotInfoFragment fragment = new SS_SpotInfoFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.ss_spotinfo, container, false);

        Bundle bundle= new Bundle();
        bestSpot = (Spot) bundle.getSerializable("bestSpot");
        distanceToDest = bundle.getString("distance");
        timeToDest = bundle.getString("duration");

        spotInfo = (TextView) rootView.findViewById(R.id.spot_info_txt);
        spotInfo.setText(bestSpot.getAddress()+"\n\nDistance to Destination:" +distanceToDest+"\nTime to Destination:"+timeToDest);


        cancelButton = (Button)rootView.findViewById(R.id.cancel_button);
        reserveButton= (Button)rootView.findViewById(R.id.reserve_button);
        cancelButton.setOnClickListener(this);
        reserveButton.setOnClickListener(this);


        return rootView;
    }
    @Override
    public void onClick(View v){

    }


}
