package com.bam.spotsurf.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;


import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.mobileconnectors.cognito.Record;
import com.bam.spotsurf.fragnav.BaseFragment;
import com.bam.spotsurf.fragnav.MS_MainFragment;
import com.bam.spotsurf.fragnav.RateSpotFragment;
import com.bam.spotsurf.fragnav.SL_Fragment;
import com.bam.spotsurf.fragnav.SS_MainFragment;
import com.bam.spotsurf.R;
import com.bam.spotsurf.objects.UserSettings;
//import com.bam.spotsurf.bottombar.BottomBar;
//import com.bam.spotsurf.bottombar.OnMenuTabClickListener;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    //Defining Variables
    /** Class name for log messages. */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** Bundle key for saving/restoring the toolbar title. */
    private static final String BUNDLE_KEY_TOOLBAR_TITLE = "title";

    /** The identity manager used to keep track of the current user account. */
    private IdentityManager identityManager;

    /** The toolbar view control. */
    Toolbar toolbar;

    /** The helper class used to toggle the left navigation drawer open and closed. */
    private ActionBarDrawerToggle drawerToggle;

    /** Data to be passed between fragments. */
    private Bundle fragmentBundle;

    private Button signOutButton;

    //private BottomNavigation bottomNavigation;
    //BottomBar mBottomBar;
    BottomNavigationView bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Obtain a reference to the mobile client. It is created in the Application class,
        // but in case a custom Application class is not used, we initialize it here if necessary.
        AWSMobileClient.initializeMobileClientIfNecessary(this);

        // Obtain a reference to the mobile client. It is created in the Application class.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // Obtain a reference to the identity manager.
        identityManager = awsMobileClient.getIdentityManager();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set up the activity to use this toolbar. As a side effect this sets the Toolbar's title
        // to the activity's title.
        setSupportActionBar(toolbar);


       // toolbarTop = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbarTop);
        /*

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        bottomNavigation= (BottomNavigation) findViewById(R.id.BottomNavigation);
        if(bottomNavigation!=null) {
            bottomNavigation.setMenuItems(R.menu.menu_bottombar);
            bottomNavigation.setDefaultSelectedIndex(0);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, SS_MainFragment.newInstance()).addToBackStack(null).commit();

        }


        bottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int i, int i1) {
                if(i==R.id.bb_menu_spotsurf){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, SS_MainFragment.newInstance()).addToBackStack(null).commit();
                }
                else if(i==R.id.bb_menu_spotlord){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, SL_Fragment.newInstance()).addToBackStack(null).commit();
                }
                else if(i==R.id.bb_menu_myspots){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, MS_MainFragment.newInstance()).addToBackStack(null).commit();
                }
                else if(i==R.id.bb_menu_settings){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, RateSpotFragment.newInstance()).addToBackStack(null).commit();
                }
            }

            @Override
            public void onMenuItemReselect(@IdRes int i, int i1) {

            }
        });

        */
/*
        mBottomBar = (BottomBar)findViewById(R.id.bottomBar);






        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId==R.id.tab_spotsurf){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, SS_MainFragment.newInstance()).addToBackStack(null).commit();
                }
                else if(tabId==R.id.tab_spotlord){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, SL_Fragment.newInstance()).addToBackStack(null).commit();
                }
                else if(tabId==R.id.tab_myspots){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, MS_MainFragment.newInstance()).addToBackStack(null).commit();
                }
                else if(tabId==R.id.tab_settings){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, RateSpotFragment.newInstance()).addToBackStack(null).commit();
                }

            }
        });

        mBottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {

                if(tabId==R.id.tab_spotsurf){
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame, SS_MainFragment.newInstance()).addToBackStack(null).commit();
                }
                else if(tabId==R.id.tab_spotlord){
                   // getSupportFragmentManager().beginTransaction().replace(R.id.frame, SL_Fragment.newInstance()).addToBackStack(null).commit();
                }
                else if(tabId==R.id.tab_myspots){
                   // getSupportFragmentManager().beginTransaction().replace(R.id.frame, MS_MainFragment.newInstance()).addToBackStack(null).commit();
                }
                else if(tabId==R.id.tab_settings){
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame, RateSpotFragment.newInstance()).addToBackStack(null).commit();
                }
            }
        });
*/

        bottomView = (BottomNavigationView) findViewById(R.id.bottom_navigation);







        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bb_menu_spotsurf:
                        bottomView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
                        bottomView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, SS_MainFragment.newInstance()).addToBackStack(null).commit();
                        break;
                    case R.id.bb_menu_spotlord:
                        bottomView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
                        bottomView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, SL_Fragment.newInstance()).addToBackStack(null).commit();
                        break;
                    case R.id.bb_menu_myspots:
                        bottomView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
                        bottomView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, MS_MainFragment.newInstance()).addToBackStack(null).commit();
                        break;
                    case R.id.bb_menu_settings:
                        bottomView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
                        bottomView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, RateSpotFragment.newInstance()).addToBackStack(null).commit();
                        break;

                }

                return true;

            }

        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, SS_MainFragment.newInstance()).addToBackStack(null).commit();




    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!AWSMobileClient.defaultMobileClient().getIdentityManager().isUserSignedIn()) {
            // In the case that the activity is restarted by the OS after the application
            // is killed we must redirect to the splash activity to handle the sign-in flow.
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        //setupSignInButtons();

        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();
        /*

        // pause/resume Mobile Analytics collection
        awsMobileClient.handleOnResume();
        // register settings changed receiver.
        LocalBroadcastManager.getInstance(this).registerReceiver(settingsChangedReceiver,
                new IntentFilter(UserSettings.ACTION_SETTINGS_CHANGED));
                */
        updateColor();
        syncUserSettings();
    }

/*
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ss_toolbar, menu);
        return true;
    }


   @Override
   public boolean onOptionsItemSelected(final MenuItem item) {
       // Handle action bar item clicks here excluding the home button.
       switch (item.getItemId()) {
           // action with ID action_refresh was selected
           case R.id.action_signout:
               // The user is currently signed in with a provider. Sign out of that provider.
               identityManager.signOut();
               startActivity(new Intent(this, SignInActivity.class));
               finish();
               break;
           default:
               break;
       }


       return super.onOptionsItemSelected(item);
   }

    @Override
    protected void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        // Save the title so it will be restored properly to match the view loaded when rotation
        // was changed or in case the activity was destroyed.
        if (toolbar != null) {
            bundle.putCharSequence(BUNDLE_KEY_TOOLBAR_TITLE, toolbar.getTitle());
        }
    }
    /*

    private final BroadcastReceiver settingsChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Received settings changed local broadcast. Update theme colors.");
            updateColor();
        }
    };
 */

    /**
     * Initializes the sign-in and sign-out buttons.
     */
    /*
    private void setupSignInButtons() {

        signOutButton = (Button) findViewById(R.id.button_signout);
        signOutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(final View view) {
        if (view == signOutButton) {
            // The user is currently signed in with a provider. Sign out of that provider.
            identityManager.signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        // ... add any other button handling code here ...

    }*/

    @Override
    protected void onPause() {
        super.onPause();

        // Obtain a reference to the mobile client.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // pause/resume Mobile Analytics collection
        /*
        awsMobileClient.handleOnPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(settingsChangedReceiver);
        */
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = this.getSupportFragmentManager();


        if (fragmentManager.getBackStackEntryCount() == 0) {

            if (fragmentManager.findFragmentByTag(BaseFragment.class.getSimpleName()) == null) {
                final Class fragmentClass = BaseFragment.class;
                // if we aren't on the home fragment, navigate home.
                final Fragment fragment = Fragment.instantiate(this, fragmentClass.getName());

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, fragment, fragmentClass.getSimpleName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();

                // Set the title for the fragment.
                final ActionBar actionBar = this.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(getString(R.string.app_name));
                }
                return;
            }
        }
        super.onBackPressed();
    }

    private void syncUserSettings() {
        // sync only if user is signed in
        if (AWSMobileClient.defaultMobileClient().getIdentityManager().isUserSignedIn()) {
            final UserSettings userSettings = UserSettings.getInstance(getApplicationContext());
            userSettings.getDataset().synchronize(new DefaultSyncCallback() {
                @Override
                public void onSuccess(final Dataset dataset, final List<Record> updatedRecords) {
                    super.onSuccess(dataset, updatedRecords);
                    Log.d(LOG_TAG, "successfully synced user settings");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //updateColor();
                        }
                    });
                }
            });
        }
    }

    public void updateColor() {
        /*
        //Update Later

        final UserSettings userSettings = UserSettings.getInstance(getApplicationContext());
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
                userSettings.loadFromDataset();
                return null;
            }

            @Override
            protected void onPostExecute(final Void aVoid) {
                toolbar.setTitleTextColor(userSettings.getTitleTextColor());
                toolbar.setBackgroundColor(userSettings.getTitleBarColor());
                final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                if (fragment != null) {
                    final View fragmentView = fragment.getView();
                    if (fragmentView != null) {
                        fragmentView.setBackgroundColor(userSettings.getBackgroudColor());
                    }
                }
            }
        }.execute();
        */
    }

    /**
     * Stores data to be passed between fragments.
     * @param fragmentBundle fragment data
     */
    public void setFragmentBundle(final Bundle fragmentBundle) {
        this.fragmentBundle = fragmentBundle;
    }

    /**
     * Gets data to be passed between fragments.
     * @return fragmentBundle fragment data
     */
    public Bundle getFragmentBundle() {
        return this.fragmentBundle;
    }




}
