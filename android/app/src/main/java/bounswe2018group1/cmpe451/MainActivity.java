package bounswe2018group1.cmpe451;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import bounswe2018group1.cmpe451.fragments.CreateFragment;
import bounswe2018group1.cmpe451.fragments.FeedFragment;
import bounswe2018group1.cmpe451.fragments.MapFragment;
import bounswe2018group1.cmpe451.fragments.ProfileFragment;
import bounswe2018group1.cmpe451.fragments.SearchFragment;
import bounswe2018group1.cmpe451.helpers.ClientAPI;

public class MainActivity extends AppCompatActivity {

    private FeedFragment fragmentFeed;
    private MapFragment fragmentMap;
    private ProfileFragment fragmentProfile;
    private SearchFragment fragmentSearch;
    private CreateFragment fragmentCreate;
    private InputMethodManager inputManager = null;
    private TabLayout tabLayout = null;
    private ClientAPI clientAPI = null;

    private boolean doubleBackToExitPressedOnce = false;
    private String sessionID;

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        // Messages from old activity (Login)
        sessionID = getIntent().getExtras().getString("SessionID");

        // Keyboard
        if(inputManager == null) inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Tabs
        if (tabLayout == null) tabLayout = findViewById(R.id.tabLayout);
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(this);

        tabLayout.addTab(tabLayout.newTab().setText("Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("Profıle"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Create"));

        // Create fragments
        fragmentFeed = new FeedFragment();
        fragmentMap = new MapFragment();
        fragmentProfile = new ProfileFragment();
        fragmentSearch = new SearchFragment();
        fragmentCreate = new CreateFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mainLayout, fragmentFeed, "Feed");
        transaction.add(R.id.mainLayout, fragmentMap, "Map");
        transaction.add(R.id.mainLayout, fragmentProfile, "Profile");
        transaction.add(R.id.mainLayout, fragmentSearch, "Search");
        transaction.add(R.id.mainLayout, fragmentCreate ,"Create");
        transaction.hide(fragmentMap);
        transaction.hide(fragmentProfile);
        transaction.hide(fragmentSearch);
        transaction.hide(fragmentCreate);
        transaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                // Remove keyboard while switching tabs
                if (getCurrentFocus() != null)
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(fragmentFeed);
                        break;
                    case 1:
                        replaceFragment(fragmentMap);
                        break;
                    case 2:
                        replaceFragment(fragmentProfile);
                        break;
                    case 3:
                        replaceFragment(fragmentSearch);
                        break;
                    case 4:
                        replaceFragment(fragmentCreate);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragmentFeed);
        transaction.hide(fragmentMap);
        transaction.hide(fragmentProfile);
        transaction.hide(fragmentSearch);
        transaction.hide(fragmentCreate);
        if (fragment != null) {
            transaction.show(fragment);
        }
        transaction.commit();
    }

}
