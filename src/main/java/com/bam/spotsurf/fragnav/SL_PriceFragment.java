package com.bam.spotsurf.fragnav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.bam.spotsurf.R;
import com.bam.spotsurf.objects.Date;
import com.bam.spotsurf.objects.Price;
import com.bam.spotsurf.objects.Time;

/**
 * Created by bmerm on 8/1/2016.
 */
public class SL_PriceFragment extends Fragment implements View.OnClickListener{

    String address;
    String garageCode;
    boolean isCompact, isGarage;
    Date startDate, endDate;
    Time startTime, endTime;
    Price spotPrice, recommendedPrice, ownPrice;
    boolean isAllDay;
    String rate;

    private EditText recommendedPriceEtxt;
    private EditText ownPriceEtxt;
    private EditText rateEtxt;
    private RadioGroup groupRadio1;
    private Button summaryButton;

    public static SL_PriceFragment newInstance() {

        SL_PriceFragment fragment = new SL_PriceFragment();
        return fragment;
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.tab2_2, container, false);

            Bundle extras= this.getArguments();
            address = extras.getString("address");
            garageCode  = extras.getString("garageCode");
            isCompact  = extras.getBoolean("isCompact");
            isGarage  = extras.getBoolean("isGarage");
            startDate  =  (Date) extras.getSerializable("startDate");
            endDate  = (Date) extras.getSerializable("endDate");
            startTime  = (Time) extras.getSerializable("startTime");
            endTime  = (Time) extras.getSerializable("endTime");
            isAllDay  = extras.getBoolean("isAllDay");




            recommendedPriceEtxt= (EditText) rootView.findViewById(R.id.etxt_recommended_price);
            recommendedPrice=recommendedPriceAlgorithm();
            recommendedPriceEtxt.setText(recommendedPrice.toString());
            spotPrice=recommendedPrice;

            ownPriceEtxt= (EditText) rootView.findViewById(R.id.etxt_own_price);
            ownPriceEtxt.setVisibility(View.INVISIBLE);

            rateEtxt= (EditText)rootView.findViewById(R.id.etxt_rate);
            rateEtxt.setVisibility(View.INVISIBLE);

            groupRadio1=(RadioGroup)rootView.findViewById(R.id.radioGroup1);

            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


            summaryButton= (Button)rootView.findViewById(R.id.link_summary);
            summaryButton.setOnClickListener(this);


            groupRadio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if(checkedId==R.id.recommended_price)
                    {
                        ownPriceEtxt.setVisibility(View.INVISIBLE);
                        spotPrice=recommendedPrice;
                    }
                    else if(checkedId==R.id.own_price)
                    {
                        ownPriceEtxt.setVisibility(View.VISIBLE);
                        String ownPriceString=ownPriceEtxt.getText().toString();
                        if(!ownPriceString.equals("")) {
                            ownPrice = new Price(Double.parseDouble(ownPriceString), isAllDay);
                            spotPrice=ownPrice;
                        }
                        rateEtxt.setVisibility(View.VISIBLE);
                        rateEtxt.setText(rate);


                    }
                }
            });





            return rootView;
        }
        @Override
        public void onClick(View view){
            if (view == summaryButton) {
                Fragment fragment= SL_SummaryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putString("garageCode", garageCode);
                bundle.putBoolean("isCompact", isCompact);
                bundle.putBoolean("isGarage", isGarage);
                bundle.putSerializable("startDate",startDate);
                bundle.putSerializable("endDate", endDate);
                bundle.putSerializable("startTime",startTime);
                bundle.putSerializable("endTime",endTime);
                bundle.putBoolean("isAllDay", isAllDay);
                bundle.putSerializable("spotPrice", spotPrice);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        }
        private Price recommendedPriceAlgorithm(){
            if(isAllDay) {
                Price p = new Price(400.00,true);
                rate="$ per day";
                return p;
            }
            else{
                Price p = new Price(20.00,false);
                rate="$ per hour";
                return p;
            }
        }
}


