package com.bam.spotsurf.fragnav;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.support.v4.app.Fragment;

import com.bam.spotsurf.objects.Time;
import com.bam.spotsurf.R;
import com.bam.spotsurf.objects.Date;
import com.tyczj.extendedcalendarview.CalendarProvider;
import com.tyczj.extendedcalendarview.Event;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class SL_TimeFragment extends Fragment implements View.OnClickListener{

    //UI References
    private EditText startDateEtxt;
    private EditText endDateEtxt;
    private Spinner startTimeSpinner;
    private Spinner endTimeSpinner;

    private Button pricingButton;

    String address;
    String garageCode;
    boolean isCompact, isGarage;
    Date startDate, endDate;
    Time startTime, endTime;
    Button showCalendarButton;
    boolean isAllDay;
    Calendar newCalendar;




    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private Calendar endDateCal = Calendar.getInstance();
    private Calendar startDateCal = Calendar.getInstance();
    private Calendar eventCal = Calendar.getInstance();


    public static SL_TimeFragment newInstance() {

        SL_TimeFragment fragment = new SL_TimeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_2_1, container, false);


        Bundle extras= this.getArguments();
        address = extras.getString("address");
        garageCode  = extras.getString("garageCode");
        isCompact  = extras.getBoolean("isCompact");
        isGarage  = extras.getBoolean("isGarage");


        isAllDay=true;
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


        startDateEtxt = (EditText) rootView.findViewById(R.id.etxt_startdate);
        startDateEtxt.setInputType(InputType.TYPE_NULL);
        startDateEtxt.requestFocus();

        endDateEtxt = (EditText) rootView.findViewById(R.id.etxt_enddate);
        endDateEtxt.setInputType(InputType.TYPE_NULL);

       startTimeSpinner = (Spinner) rootView.findViewById(R.id.spinner_starttime);

        endTimeSpinner = (Spinner) rootView.findViewById(R.id.spinner_endtime);




        pricingButton = (Button) rootView.findViewById(R.id.link_pricing);
       showCalendarButton = (Button) rootView.findViewById(R.id.event_cal_button);


        startDateEtxt.setOnClickListener(this);
        endDateEtxt.setOnClickListener(this);
        pricingButton.setOnClickListener(this);
        showCalendarButton.setOnClickListener(this);



        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        newCalendar = Calendar.getInstance();




        startDatePickerDialog = new DatePickerDialog(rootView.getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDateCal.set(year, monthOfYear, dayOfMonth);
                startDateEtxt.setText(dateFormatter.format(startDateCal.getTime()));
                String startDateString= startDateEtxt.getText().toString();
                String[] tokens = startDateString.split("/");
                startDate= new Date(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[0]),Integer.parseInt(tokens[2]));


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));




        endDatePickerDialog = new DatePickerDialog(rootView.getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endDateCal.set(year, monthOfYear, dayOfMonth);
                endDateEtxt.setText(dateFormatter.format(endDateCal.getTime()));
                String endDateString= endDateEtxt.getText().toString();
                String[] tokens = endDateString.split("/");
                endDate= new Date(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[0]),Integer.parseInt(tokens[2]));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

/*
            startTimePickerDialog = new TimePickerDialog(rootView.getContext(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    startTimeCal.set(selectedHour, selectedMinute);
                    startTimeEtxt.setText(selectedHour + ":" + selectedMinute);
                    String startTimeString = startTimeEtxt.getText().toString();
                    String[] tokens = startTimeString.split(":");
                    startTime = new Time(Integer.parseInt(tokens[0]),false);
                }


            }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

            endTimePickerDialog = new TimePickerDialog(rootView.getContext(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    endTimeCal.set(selectedHour, selectedMinute);
                    endTimeEtxt.setText(selectedHour + ":" + selectedMinute);
                    String endTimeString = endTimeEtxt.getText().toString();
                    String[] tokens = endTimeString.split(":");
                    endTime = new Time(Integer.parseInt(tokens[0]),false);
                }


            }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);*/

            final List<String> startTimeList = new ArrayList<String>();




        /*startTimeSpinner.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int firstChoice=1;
                if (startDate.getDay() == newCalendar.get(Calendar.DAY_OF_MONTH) & startDate.getMonth() == newCalendar.get(Calendar.MONTH) & startDate.getYear() == newCalendar.get(Calendar.YEAR)) {
                    firstChoice = newCalendar.get(Calendar.HOUR);
                }
                if (newCalendar.get(Calendar.HOUR_OF_DAY) > 12) {
                    String am_pm = "PM";
                    for (int i = firstChoice; i < 13; i++) {
                        startTimeList.add(String.valueOf(i) + " " + am_pm);
                    }
                } else {
                    String am_pm = "AM";
                    for (int j = 0; j < 2; j++) {
                        for (int i = firstChoice; i < 13; i++) {
                            startTimeList.add(String.valueOf(i) + " " + am_pm);
                        }
                        am_pm = "PM";
                    }
                }


                return true;
            }
        });*/
        String am_pm = "AM";
        for (int j = 0; j < 2; j++) {
            for (int i = 1; i < 13; i++) {
                startTimeList.add(String.valueOf(i) + " " + am_pm);
            }
            am_pm = "PM";
        }


        // Spinner Drop down elements


        // Creating adapter for spinner
        ArrayAdapter<String> startDataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, startTimeList);

        // Drop down layout style - list view with radio button
        startDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        startTimeSpinner.setAdapter(startDataAdapter);

        final AdapterView.OnItemSelectedListener startTimeSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {
                String selectedTime= startTimeList.get(position);
                String [] timeTokens=selectedTime.split(" ");
                if(timeTokens[1].equals("AM"))
                    startTime= new Time(Integer.parseInt(timeTokens[0]),true);
                else
                    startTime= new Time(Integer.parseInt(timeTokens[0]),false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };

        // Spinner click listener
        startTimeSpinner.setOnItemSelectedListener(startTimeSelectedListener);


        final List<String> endTimeList = new ArrayList<String>();


        /*endTimeSpinner.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // Spinner Drop down elements
                if (!startTime.isAM()) {
                    String am_pm = "PM";
                    for (int i = startTime.getHour(); i < 13; i++) {
                        endTimeList.add(String.valueOf(i) + " " + am_pm);
                    }
                } else {
                    String am_pm = "AM";
                    for (int j = 0; j < 2; j++) {
                        for (int i = startTime.getHour(); i < 13; i++) {
                            endTimeList.add(String.valueOf(i) + " " + am_pm);
                        }
                        am_pm = "PM";
                    }
                }
                return true;
            }

        });*/
        am_pm = "AM";
        for (int j = 0; j < 2; j++) {
            for (int i = 1; i < 13; i++) {
                endTimeList.add(String.valueOf(i) + " " + am_pm);
            }
            am_pm = "PM";
        }


        // Creating adapter for spinner
        ArrayAdapter<String> endDataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, endTimeList);

        // Drop down layout style - list view with radio button
        endDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        endTimeSpinner.setAdapter(endDataAdapter);

        final AdapterView.OnItemSelectedListener endTimeSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {
                String selectedTime= endTimeList.get(position);
                String [] timeTokens=selectedTime.split(" ");
                if(timeTokens[1].equals("AM"))
                    endTime= new Time(Integer.parseInt(timeTokens[0]),true);
                else
                    endTime= new Time(Integer.parseInt(timeTokens[0]),false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };

        // Spinner click listener
        endTimeSpinner.setOnItemSelectedListener(endTimeSelectedListener);





        return rootView;

    }

    @Override
    public void onClick(View view) {
        if(view == startDateEtxt) {
            startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            startDatePickerDialog.show();


        } else if(view == endDateEtxt) {
            endDatePickerDialog.getDatePicker().setMinDate(startDateCal.getTimeInMillis());
            endDatePickerDialog.show();

        } else if(view== showCalendarButton){

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.calendar_view, null);

            //ExtendedCalendarView calendar = (ExtendedCalendarView)view.findViewById(R.id.calendar);


            /*ContentValues values = new ContentValues();
            values.put(CalendarProvider.COLOR, Event.COLOR_RED);
            values.put(CalendarProvider.DESCRIPTION, "Some Description");
            values.put(CalendarProvider.LOCATION, "Some location");
            values.put(CalendarProvider.EVENT, "Event name");
            Calendar cal = Calendar.getInstance();

            cal.set(startDayYear, startDayMonth, startDayDay, startTimeHour, startTimeMin);
            values.put(CalendarProvider.START, cal.getTimeInMillis());
            values.put(CalendarProvider.START_DAY, julianDay);
            TimeZone tz = TimeZone.getDefault();

            cal.set(endDayYear, endDayMonth, endDayDay, endTimeHour, endTimeMin);
            int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

            values.put(CalendarProvider.END, cal.getTimeInMillis());
            values.put(CalendarProvider.END_DAY, endDayJulian);

            Uri uri = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, values);*/

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            //alert.setMessage("Times");

            builder.setTitle("Calendar View");

            builder.setView(dialoglayout);


           builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });


            builder.show();










        } else if (view ==pricingButton) {
            Fragment fragment= SL_PriceFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("address", address);
            bundle.putString("garageCode", garageCode);
            bundle.putBoolean("isCompact", isCompact);
            bundle.putBoolean("isGarage", isGarage);
            bundle.putSerializable("startDate",startDate);
            bundle.putSerializable("endDate", endDate);
            bundle.putSerializable("startTime",startTime);
            bundle.putSerializable("endTime",endTime);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }

    }


}