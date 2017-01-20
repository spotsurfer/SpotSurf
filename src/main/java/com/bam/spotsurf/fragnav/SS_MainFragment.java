package com.bam.spotsurf.fragnav;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bam.spotsurf.activity.MainActivity;
import com.bam.spotsurf.googleutils.DirectionsJSONParser;
import com.bam.spotsurf.googleutils.GeocodeJSONParser;
import com.bam.spotsurf.googleutils.PlaceJSONParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.bam.spotsurf.R;
import hollowsoft.slidingdrawer.OnDrawerCloseListener;
import hollowsoft.slidingdrawer.OnDrawerOpenListener;
import hollowsoft.slidingdrawer.OnDrawerScrollListener;

import com.bam.spotsurf.application.Application;
import com.bam.spotsurf.objects.Date;
import com.bam.spotsurf.objects.Spot;
import com.bam.spotsurf.objects.Time;


import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import hollowsoft.slidingdrawer.SlidingDrawer;


public class SS_MainFragment extends Fragment implements OnDrawerOpenListener, OnDrawerCloseListener, OnDrawerScrollListener, ActivityCompat.OnRequestPermissionsResultCallback, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{

    MapView mapView;
    Toolbar toolbar;
    List <Spot> grandList;
    boolean surf=false;
    String distance = "";
    String duration = "";

    //UI References
    Button confirmButton;
    SlidingDrawer drawer, spotdrawer;
    TextView spotTitle, spotInfo;
    double cLongitude,cLatitude, latitude, longitude;
    LatLng elatLng,slatLng,clatLng,latLng;
    private GoogleApiClient googleApiClient;





    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private GoogleMap map;
    private ImageButton filterButton, favoriteButton;
    private AutoCompleteTextView locationSearch;
    private PlacesTask placesTask;
    private ParserPlacesTask parserTask;
    private LinkedHashMap<Spot,Double> rankedSpots;
    private PopupWindow pw;
    private View popupLayout;
    private Button reserveButton;
    int spotIndex=0;
    int surfIndex=0;
    int spotPointer=0;


    private boolean isCompact, isGarage;
    private Date startDate, endDate;
    private Time startTime, endTime;
    private boolean isAllDay;
    private Calendar newCalendar;
    private RadioGroup sizeGroup;
    private RadioGroup typeGroup;
    private EditText startDateEtxt;
    private EditText endDateEtxt;
    private Spinner startTimeSpinner;
    private Spinner endTimeSpinner;
    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;

    private SimpleDateFormat dFormatter;

    private Calendar calEndDate = Calendar.getInstance();
    private Calendar calStartDate = Calendar.getInstance();



    public static SS_MainFragment newInstance() {

        SS_MainFragment fragment = new SS_MainFragment();
        return fragment;
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    final View rootView = inflater.inflate(R.layout.frag_mapview, container, false);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    MapsInitializer.initialize(this.getActivity());
    mapView = (MapView) rootView.findViewById(R.id.map);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);

    rankedSpots = new LinkedHashMap<Spot,Double>();


    //localityMap= new HashMap<Double,Double>();

    toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
    View toolbar_view = getActivity().getLayoutInflater().inflate(R.layout.frag_mapview_toolbar,toolbar, false);
    toolbar.addView(toolbar_view);



    locationSearch = (AutoCompleteTextView) toolbar_view.findViewById(R.id.search_text);
    locationSearch.setThreshold(1);

    locationSearch.addTextChangedListener(new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            placesTask = new PlacesTask();
            placesTask.execute(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {

            locationSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        surf = true;

                        try {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }


                        String location = locationSearch.getText().toString();

                        //spotdrawer.setVisibility(View.VISIBLE);

                        String url = "https://maps.googleapis.com/maps/api/geocode/json?";

                        try {
                            // encoding special characters like space in the user input place
                            location = URLEncoder.encode(location, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String address = "address=" + location;

                        String sensor = "sensor=false";

                        // url , from where the geocoding data is fetched
                        url = url + address + "&" + sensor;

                        // Instantiating DownloadTask to get places from Google Geocoding service
                        // in a non-ui thread
                        DownloadSurfGeocodingTask downloadTask = new DownloadSurfGeocodingTask();

                        // Start downloading the geocoding places
                        downloadTask.execute(url);
                        return true;
                    }
                    return false;
                }
            });

        }
    });

    filterButton = (ImageButton) toolbar_view.findViewById(R.id.filter_button);
    filterButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // do something when the corky is clicked
        }
    });




   /* drawer = (SlidingDrawer) rootView.findViewById(R.id.drawer);

    drawer.setOnDrawerOpenListener(this);
    drawer.setOnDrawerCloseListener(this);
    drawer.setOnDrawerScrollListener(this);


    spotdrawer = (SlidingDrawer) rootView.findViewById(R.id.spotdrawer);
    spotdrawer.setVisibility(View.INVISIBLE);

    spotdrawer.setOnDrawerOpenListener(this);
    spotdrawer.setOnDrawerCloseListener(this);
    spotdrawer.setOnDrawerScrollListener(this);

    spotTitle= (TextView)rootView.findViewById(R.id.spothandle);
    spotInfo=(TextView)rootView.findViewById(R.id.spotcontent);



    isAllDay=true;
    dFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);




    startDateEtxt = (EditText) rootView.findViewById(R.id.etxt_startdate);
    startDateEtxt.setInputType(InputType.TYPE_NULL);

    endDateEtxt = (EditText) rootView.findViewById(R.id.etxt_enddate);
    endDateEtxt.setInputType(InputType.TYPE_NULL);

    startTimeSpinner = (Spinner) rootView.findViewById(R.id.spinner_starttime);

    endTimeSpinner = (Spinner) rootView.findViewById(R.id.spinner_endtime);

    startDateEtxt.setOnClickListener(this);
    endDateEtxt.setOnClickListener(this);

    newCalendar = Calendar.getInstance();

    sizeGroup= (RadioGroup)rootView.findViewById(R.id.radioGroup1);
    typeGroup= (RadioGroup)rootView.findViewById(R.id.radioGroup2);

    sizeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.compact_size) {
                isCompact = true;
            } else if (checkedId == R.id.all_size) {
                isCompact = false;
            }
        }
    });

    typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.driveway) {

                isGarage = false;

            } else if (checkedId == R.id.garage) {

                isGarage = true;

            }
        }
    });





    startDateDialog = new DatePickerDialog(rootView.getContext(), new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calStartDate.set(year, monthOfYear, dayOfMonth);
            startDateEtxt.setText(dFormatter.format(calStartDate.getTime()));
            String startDateString= startDateEtxt.getText().toString();
            String[] tokens = startDateString.split("/");
            startDate= new Date(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[0]),Integer.parseInt(tokens[2]));


        }

    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));




    endDateDialog = new DatePickerDialog(rootView.getContext(), new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calEndDate.set(year, monthOfYear, dayOfMonth);
            endDateEtxt.setText(dFormatter.format(calEndDate.getTime()));
            String endDateString= endDateEtxt.getText().toString();
            String[] tokens = endDateString.split("/");
            endDate= new Date(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[0]),Integer.parseInt(tokens[2]));
        }

    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    final List<String> startTimeList = new ArrayList<String>();


    String am_pm = "AM";
    for (int j = 0; j < 2; j++) {
        for (int i = 1; i < 13; i++) {
            startTimeList.add(String.valueOf(i) + " " + am_pm);
        }
        am_pm = "PM";
    }


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


 */

    return rootView;
}






        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            surf=false;

            map.getUiSettings().setMyLocationButtonEnabled(false);

            //enableMyLocation();

            Application mApp = ((Application) getActivity().getApplication());
            grandList = mApp.getGrandSpotList();



            for (Spot spot : grandList) {

                String location = spot.getAddress();


                if (location == null || location.equals("")) {
                    Toast.makeText(getContext(), "No Place is entered", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "https://maps.googleapis.com/maps/api/geocode/json?";

                try {
                    // encoding special characters like space in the user input place
                    location = URLEncoder.encode(location, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String address = "address=" + location;

                String sensor = "sensor=false";

                // url , from where the geocoding data is fetched
                url = url + address + "&" + sensor;

                // Instantiating DownloadTask to get places from Google Geocoding service
                // in a non-ui thread
                DownloadInitialGeocodingTask downloadTask = new DownloadInitialGeocodingTask();

                // Start downloading the geocoding places
                downloadTask.execute(url);


            }

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker arg0) {



                    initiatePopupWindow(getView());

                    TextView spotAddressTextView= (TextView)popupLayout.findViewById(R.id.address);
                    TextView spotRatingTextView= (TextView)popupLayout.findViewById(R.id.rating);
                    TextView spotTypeTextView=(TextView)popupLayout.findViewById(R.id.type_of_spot);
                    TextView spotAvailTextView=(TextView)popupLayout.findViewById(R.id.spot_availability);
                    TextView spotPriceTextView= (TextView)popupLayout.findViewById(R.id.price_of_spot);

                    reserveButton= (Button)popupLayout.findViewById(R.id.reserve_button);

                    for(Spot spot:grandList){
                        if(spot.getMarker().getTitle().equals(arg0.getTitle())){
                            spotAddressTextView.setText(spot.getAddress());
                            String priceText = "Price per spot: " + spot.getPrice().toString();
                            spotPriceTextView.setText(priceText);
                            String typeText;
                            if(spot.isGarage()){
                                typeText="Garage";
                                spotTypeTextView.setText(typeText);
                            }
                            else{
                                typeText="Driveway";
                                spotTypeTextView.setText(typeText);
                            }
                            String availText= "From: "+spot.getStartDate()+" - " +spot.getStartTime()+
                                    "\nTo: "+spot.getEndDate()+" - "+ spot.getEndTime();
                            spotAvailTextView.setText(availText);

                        }
                    }
                    for (Map.Entry<Spot,Double>  entry : rankedSpots.entrySet()) {
                        if(entry.getKey().getMarker().getTitle().equals(arg0.getTitle())){
                            int roundedRank= (int) Math.round(entry.getValue());
                            if(roundedRank>60)spotRatingTextView.setTextColor(getResources().getColor(R.color.green));
                            else spotRatingTextView.setTextColor(getResources().getColor(R.color.primary));
                            String roundedRatingText= String.valueOf(Math.round(entry.getValue()));
                            spotRatingTextView.setText(roundedRatingText);
                        }
                    }

                    // create a 300px width and 470px height PopupWindow
                    pw = new PopupWindow(popupLayout, 1000, 500, true);
                    // display the popup in the center
                    pw.showAtLocation(getView(), Gravity.CENTER, 0, 0);



                    //spotdrawer.setVisibility(View.VISIBLE);






                    return true;
                }

            });
        }

    private void initiatePopupWindow(View v) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            popupLayout = inflater.inflate(R.layout.popup,
                    (ViewGroup) getActivity().findViewById(R.id.popup_element));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
               Manifest.permission.ACCESS_FINE_LOCATION)) {
           // Enable the my location layer if the permission has been granted.
            enableMyLocation();
            } else {
             // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
            }
        }

    private void enableMyLocation(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (map != null) {
            // Access to the location has been granted to the app.
            map.setMyLocationEnabled(true);
        }

    }

    /*private LatLng findSpot(List<Spot> list, String location) {


    }*/


   /*@Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onDrawerOpened() {

    }

    @Override
    public void onDrawerClosed() {

    }

    @Override
    public void onScrollStarted() {

    }

    @Override
    public void onScrollEnded() {

    }

    //Getting current location
    private void getCurrentLocation() {
        map.clear();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (map != null) {
            // Access to the location has been granted to the app.
            map.setMyLocationEnabled(true);
            //Creating a location object
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                //Getting longitude and latitude
                cLongitude = location.getLongitude();
                cLatitude = location.getLatitude();

                //Creating a LatLng Object to store Coordinates
                clatLng = new LatLng(cLatitude, cLongitude);


                //Moving the camera
                //map.moveCamera(CameraUpdateFactory.newLatLng(clatLng));

                //Animating the camera
                //map.animateCamera(CameraUpdateFactory.zoomTo(15));

            }
        }
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }
    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();

    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception url download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
    /** A class, to download Places from Geocoding webservice */
    private class DownloadInitialGeocodingTask extends AsyncTask<String, Integer, String>{

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){

            // Instantiating ParserTask which parses the json data from Geocoding webservice
            // in a non-ui thread
            ParserInitialGeocodingTask parserTask = new ParserInitialGeocodingTask();

            // Start parsing the places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    /** A class to parse the Geocoding Places in non-ui thread */
    class ParserInitialGeocodingTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GeocodeJSONParser parser = new GeocodeJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a an ArrayList */
                places = parser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){


                for (int i = 0; i < list.size(); i++) {

                    // Creating a marker
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Getting a place from the places list
                    HashMap<String, String> hmPlace = list.get(i);

                    // Getting latitude of the place
                    double lat = Double.parseDouble(hmPlace.get("lat"));

                    // Getting longitude of the place
                    double lng = Double.parseDouble(hmPlace.get("lng"));

                    // Getting name
                    String name = hmPlace.get("formatted_address");

                    LatLng latLng = new LatLng(lat, lng);


                    // Setting the position for the marker
                    markerOptions.position(latLng);

                    // Setting the title for the marker
                    markerOptions.title(name);

                    IconGenerator tc = new IconGenerator(getContext());
                    tc.setStyle(IconGenerator.STYLE_DEFAULT);
                    Bitmap bmp = tc.makeIcon(String.valueOf(grandList.get(spotIndex).getPrice()));
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bmp));

                    // Placing a marker on the touched position
                    Marker marker = map.addMarker(markerOptions);


                    grandList.get(spotIndex).setMarker(marker);

                    if(i==0){
                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        map.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }



                }
                spotIndex++;

        }
    }

    /** A class, to download Places from Geocoding webservice */
    private class DownloadSurfGeocodingTask extends AsyncTask<String, Integer, String>{

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){

            // Instantiating ParserTask which parses the json data from Geocoding webservice
            // in a non-ui thread
            ParserSurfGeocodingTask parserTask = new ParserSurfGeocodingTask();

            // Start parsing the places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    /** A class to parse the Geocoding Places in non-ui thread */
    class ParserSurfGeocodingTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GeocodeJSONParser parser = new GeocodeJSONParser();

            try {
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a an ArrayList */
                places = parser.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {


            for (int i = 0; i < list.size(); i++) {
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);

                // Getting latitude of the place
                latitude = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                longitude = Double.parseDouble(hmPlace.get("lng"));

                // Getting name
                //String name = hmPlace.get("formatted_address");

                elatLng = new LatLng(latitude, longitude);

                //map.addMarker(new MarkerOptions().position(elatLng).title(name));
                //map.moveCamera(CameraUpdateFactory.newLatLngZoom(elatLng,15));
            }

            surfIndex = 0;
            rankedSpots.clear();

            slatLng = grandList.get(surfIndex).getMarker().getPosition();


            // map.moveCamera(CameraUpdateFactory.newLatLngZoom(slatLng, 15));

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(slatLng, elatLng);

            DownloadDistanceTask downloadDistanceTask = new DownloadDistanceTask();

            // Start downloading json data from Google Directions API
            downloadDistanceTask.execute(url);

        }
    }

    

    private void showTopSpots(){

        map.clear();
        String poop="";


        IconGenerator tc = new IconGenerator(getContext());

        int topThree=0;

        for (Map.Entry<Spot,Double>  entry : rankedSpots.entrySet()) {
            poop = poop + entry.getKey().getAddress() + ":   " + entry.getValue() + "\n";
            int roundedRank= (int) Math.round(entry.getValue());
            if(topThree<3) {
                tc.setStyle(IconGenerator.STYLE_GREEN);
                Bitmap bmp = tc.makeIcon(String.valueOf(roundedRank)); // pass the text you want
                Marker marker = map.addMarker(new MarkerOptions().position(entry.getKey().getMarker().getPosition()).title(entry.getKey().getMarker().getTitle()).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
                if (topThree == 0) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                }
            }
            else {
                tc.setStyle(IconGenerator.STYLE_DEFAULT);
                Bitmap bmp = tc.makeIcon(String.valueOf(roundedRank)); // pass the text you want
                map.addMarker(new MarkerOptions().position(entry.getKey().getMarker().getPosition()).title(entry.getKey().getMarker().getTitle()).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
                if (topThree == (rankedSpots.size()-1)) {
                    //spotInfo.setText(poop);
                    spotPointer = 3;
                    break;
                }
            }
            topThree++;
        }





    }



    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }


    // Fetches data from url passed
    private class DownloadDistanceTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserDistanceTask parserTask = new ParserDistanceTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserDistanceTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();



            if (result.size() < 1) {
                Toast.makeText(getContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if (j == 0) {    // Get distance from the list
                        distance = (String) point.get("distance");
                        continue;
                    } else if (j == 1) { // Get duration from the list
                        duration = (String) point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                String distanceTokens[]= distance.split(" ");
                distance=distanceTokens[0];
                String durationTokens[]= duration.split(" ");
                duration=durationTokens[0];


                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }


            double priceRating, localityRating, ratingRating, grandRating;


            priceRating = 100 - (100 * (grandList.get(surfIndex).getPrice().getValue() * grandList.get(surfIndex).getPrice().getValue())) / ((grandList.get(surfIndex).getPrice().getValue() * grandList.get(surfIndex).getPrice().getValue()) + 450);
            double distanceRating = 100 - (100 * (Double.parseDouble(distance) * Double.parseDouble(distance)) / ((Double.parseDouble(distance)*Double.parseDouble(distance)) + 2));
            double durationRating = 100 - (100 * (Double.parseDouble(duration) * Double.parseDouble(duration)) / ((Double.parseDouble(duration) * Double.parseDouble(duration)) + 450));
            localityRating = (distanceRating + durationRating) * .5;

            //ratingRating = 100; //temporary until spot rating feature done****

            grandRating = priceRating * .5 + localityRating * .5; //+ ratingRating * .2;

            rankedSpots.put(grandList.get(surfIndex), grandRating);

            if (surfIndex != (grandList.size()-1)) {

                surfIndex++;

                slatLng = grandList.get(surfIndex).getMarker().getPosition();



                // map.moveCamera(CameraUpdateFactory.newLatLngZoom(slatLng, 15));

                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(slatLng, elatLng);

                DownloadDistanceTask downloadDistanceTask = new DownloadDistanceTask();

                // Start downloading json data from Google Directions API
                downloadDistanceTask.execute(url);


            }
            rankedSpots=sortByValue(rankedSpots);
            showTopSpots();

        }

            //localityMap.put(Double.parseDouble(distance),Double.parseDouble(duration));

            // Drawing polyline in the Google Map for the i-th route
            //map.addPolyline(lineOptions);
    }

    // Fetches all places from GooglePlaces AutoComplete Web Service
    private class PlacesTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "AIzaSyCEcipQ-PTVVp3zdCrjem5DCPJkVMPGBXY";

            String input="";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = input+"&"+types+"&"+sensor+"&key="+key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;

            try{
                // Fetching the data from we service
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserPlacesTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }
    /** A class to parse the Google Places in JSON format */
    private class ParserPlacesTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception", e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };

            // Creating a SimpleAdapter for the AutoCompleteTextView
            SimpleAdapter adapter = new SimpleAdapter(getContext(), result, android.R.layout.simple_list_item_1, from, to);

            // Setting the adapter
            locationSearch.setAdapter(adapter);
            synchronized (adapter){
                adapter.notifyDataSetChanged();
            }
        }
    }

    private static <K, V extends Comparable<? super V>> LinkedHashMap<K, V>
    sortByValue( LinkedHashMap<K, V> map )
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );

        LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }







}





