package com.bam.spotsurf.fragnav;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bam.spotsurf.application.Application;
import com.bam.spotsurf.R;
import com.bam.spotsurf.objects.Spot;

import java.util.List;

/**
 * Created by bmerm on 8/6/2016.
 */
public class SL_ListFragment extends Fragment implements View.OnClickListener{


    ListView currentList;
    ListView pastList;
    List<Spot> currentSpotLList;
    List<Spot> pastSpotLList;
    Button newPostButton;
    Application mApp;

    public static SL_ListFragment newInstance() {

        SL_ListFragment fragment = new SL_ListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2_list, container, false);
        mApp = ((Application) getActivity().getApplication());
        currentSpotLList= mApp.getCurrentSpotLList();
        pastSpotLList= mApp.getPastSpotLList();

        currentList = (ListView) rootView.findViewById(R.id.current_list);
        pastList= (ListView) rootView.findViewById(R.id.past_list);

        //setListViewHeightBasedOnItems(currentList);
        //setListViewHeightBasedOnItems(pastList);




        SL_ArrayAdapter currentAdapter = new SL_ArrayAdapter(getContext(), currentSpotLList);
        currentList.setAdapter(currentAdapter);

        currentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set title
                alertDialogBuilder.setTitle("Spot Details");

                // set dialog message
                alertDialogBuilder
                        .setMessage(currentSpotLList.get(position).toString())
                        .setCancelable(false)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


        SL_ArrayAdapter pastAdapter = new SL_ArrayAdapter(getContext(), pastSpotLList);
        pastList.setAdapter(pastAdapter);

        pastList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                // set title
                alertDialogBuilder.setTitle("Spot Details");

                // set dialog message
                alertDialogBuilder
                        .setMessage(pastSpotLList.get(position).toString())
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
        });



        newPostButton= (Button)rootView.findViewById(R.id.post_new);
        newPostButton.setOnClickListener(this);



        return rootView;
    }
    @Override
    public void onClick(View view){

        if(view== newPostButton){

            Fragment fragment= SL_Fragment.newInstance();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }


}

