package com.kamilismail.movieappandroid.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.fragments.DiscoverFragment;
import com.kamilismail.movieappandroid.fragments.MovieDetailsFragment;
import com.kamilismail.movieappandroid.fragments.NotificationFragment;
import com.kamilismail.movieappandroid.fragments.ProfileFragment;
import com.kamilismail.movieappandroid.fragments.SearchFragment;
import com.kamilismail.movieappandroid.fragments.TVFragment;
import com.kamilismail.movieappandroid.helpers.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        DiscoverFragment.SendArgumentsAndLaunchFragment, TVFragment.SendArgumentsAndLaunchFragment,
        SearchFragment.SendArgumentsAndLaunchFragment {

    @BindView(R.id.bottom_navigation_menu)
    BottomNavigationView mBottomNavigationView;
    private SessionController sessionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        this.sessionController = new SessionController(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mItemSelectedListener);

        // Launch initial fragment.
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame, DiscoverFragment.newInstance(), DiscoverFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    // Create navigation item selected click listener.
    private BottomNavigationView.OnNavigationItemSelectedListener mItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                    final FragmentManager supportFragmentManager = getSupportFragmentManager();
                    switch (item.getItemId()) {
                        // Launch Home Fragment.
                        case R.id.action_discover:
                            item.setChecked(true);
                            final Fragment discoverFragment = supportFragmentManager.findFragmentByTag(DiscoverFragment.TAG);
                            if (discoverFragment == null) {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, DiscoverFragment.newInstance(), DiscoverFragment.TAG)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, discoverFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Launch Locations Fragment.
                        case R.id.action_tv:
                            item.setChecked(true);
                            final Fragment tvFragment = supportFragmentManager.findFragmentByTag(TVFragment.TAG);
                            if (tvFragment == null) {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, TVFragment.newInstance(), TVFragment.TAG)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, tvFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;

                        // Launch Menu Fragment.
                        case R.id.action_search:
                            item.setChecked(true);
                            final Fragment searchFragment = supportFragmentManager.findFragmentByTag(SearchFragment.TAG);
                            if (searchFragment == null) {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, SearchFragment.newInstance(), SearchFragment.TAG)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, searchFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        case R.id.action_notification:
                            item.setChecked(true);
                            final Fragment notificationFragment = supportFragmentManager.findFragmentByTag(NotificationFragment.TAG);
                            if (notificationFragment == null) {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, NotificationFragment.newInstance(), NotificationFragment.TAG)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, notificationFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        case R.id.action_user:
                            item.setChecked(true);
                            final Fragment profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG);
                            if (profileFragment == null) {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, ProfileFragment.newInstance(), ProfileFragment.TAG)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame, profileFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                    }
                    return false;
                }

            };

    @Override
    public void logoutUser() {
        sessionController.logoutUser();
    }

    @Override
    public void passMovieData(String id, String title) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("title", title);
        movieDetailsFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frame, movieDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
