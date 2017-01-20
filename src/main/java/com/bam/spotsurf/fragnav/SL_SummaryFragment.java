package com.bam.spotsurf.fragnav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bam.spotsurf.application.Application;
import com.bam.spotsurf.R;
import com.bam.spotsurf.objects.Date;
import com.bam.spotsurf.objects.Price;
import com.bam.spotsurf.objects.Spot;
import com.bam.spotsurf.objects.Time;

/**
 * Created by bmerm on 8/1/2016.
 */
public class SL_SummaryFragment extends Fragment implements View.OnClickListener {

    String address;
    String garageCode;
    boolean isCompact, isGarage;
    Date startDate, endDate;
    Time startTime, endTime;
    Price spotPrice;
    boolean isAllDay;
    EditText summaryEtxt;
    Button postSpotButton;
    Spot newSpot;



    public static SL_SummaryFragment newInstance() {

        SL_SummaryFragment fragment = new SL_SummaryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2_3, container, false);


        Bundle args = this.getArguments();
        address = args.getString("address");
        garageCode = args.getString("garageCode");
        isCompact = args.getBoolean("isCompact");
        isGarage = args.getBoolean("isGarage");
        startDate = (Date) args.getSerializable("startDate");
        endDate = (Date) args.getSerializable("endDate");
        startTime = (Time) args.getSerializable("startTime");
        endTime = (Time) args.getSerializable("endTime");
        isAllDay = args.getBoolean("isAllDay");
        spotPrice = (Price) args.getSerializable("spotPrice");

        newSpot = new Spot(address, isCompact, isGarage);
        newSpot.setGarageCode(garageCode);
        newSpot.setStartDate(startDate);
        newSpot.setEndDate(endDate);
        newSpot.setStartTime(startTime);
        newSpot.setEndTime(endTime);
        newSpot.setPrice(spotPrice);



            summaryEtxt = (EditText) rootView.findViewById(R.id.etxt_summary);
            summaryEtxt.setText(newSpot.toString());

            postSpotButton = (Button) rootView.findViewById(R.id.link_post_spot);
            postSpotButton.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick (View view){
            if (view == postSpotButton) {
                Application mApp = ((Application) getActivity().getApplication());
                mApp.getCurrentSpotLList().add(newSpot);
                mApp.getGrandSpotList().add(newSpot);
                Fragment fragment = SS_MainFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }

}


