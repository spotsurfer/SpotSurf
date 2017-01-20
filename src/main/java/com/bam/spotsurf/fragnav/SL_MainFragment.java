package com.bam.spotsurf.fragnav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bam.spotsurf.application.Application;
import com.bam.spotsurf.objects.Spot;
import com.bam.spotsurf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmerm on 8/12/2016.
 */
public class SL_MainFragment extends Fragment {

    private EditText currentSpotDetails;
    private List<Spot> currentSpotLList;
    private Spinner spinner;
    private Application mApp;
    private Button addSpotButton, cancelSpotButton, editSpotButton;

    public static SL_MainFragment newInstance(){
        SL_MainFragment fragment = new SL_MainFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.sl_main, container, false);
        mApp = ((Application) getActivity().getApplication());
        currentSpotLList = mApp.getCurrentSpotLList();


        // Spinner element
        spinner = (Spinner) rootView.findViewById(R.id.spinner);

        addSpotButton= (Button) rootView.findViewById(R.id.add_spot_button);


        currentSpotDetails = (EditText) rootView.findViewById(R.id.etxt_current_details);
        currentSpotDetails.setVisibility(View.INVISIBLE);

        editSpotButton= (Button) rootView.findViewById(R.id.edit_spot_button);
        editSpotButton.setVisibility(View.INVISIBLE);

        cancelSpotButton = (Button) rootView.findViewById(R.id.cancel_spot_button);
        cancelSpotButton.setVisibility(View.INVISIBLE);


        View.OnClickListener addButtonListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= SL_Fragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
/*
        View.OnClickListener editButtonListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= SL_Fragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };


        View.OnClickListener cancelButtonListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove from current spot from list
                //are you sure dialog
                //notify renters
            }
        };


        */

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for(Spot spot:currentSpotLList){
            categories.add(spot.getAddress());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        final AdapterView.OnItemSelectedListener currentListingSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {
                Spot selectedSpot= currentSpotLList.get(position);
                currentSpotDetails.setVisibility(View.VISIBLE);
                editSpotButton.setVisibility(View.VISIBLE);
                cancelSpotButton.setVisibility(View.VISIBLE);
                currentSpotDetails.setText(selectedSpot.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };

        // Spinner click listener
        spinner.setOnItemSelectedListener(currentListingSelectedListener);

        addSpotButton.setOnClickListener(addButtonListener);


        return rootView;
    }
}
