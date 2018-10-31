package com.kamilismail.movieappandroid.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.connection.ApiUser;
import com.kamilismail.movieappandroid.fragments.ChangePswFragment;
import com.kamilismail.movieappandroid.fragments.DiscoverFragment;
import com.kamilismail.movieappandroid.fragments.FavouritesFragment;
import com.kamilismail.movieappandroid.fragments.MovieDetailsFragment;
import com.kamilismail.movieappandroid.fragments.NotificationFragment;
import com.kamilismail.movieappandroid.fragments.ProfileFragment;
import com.kamilismail.movieappandroid.fragments.RatingsFragment;
import com.kamilismail.movieappandroid.fragments.SearchFragment;
import com.kamilismail.movieappandroid.fragments.TVFragment;
import com.kamilismail.movieappandroid.fragments.WantToWatchFragment;
import com.kamilismail.movieappandroid.helpers.BottomNavigationViewHelper;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements
        DiscoverFragment.SendArgumentsAndLaunchFragment, TVFragment.SendArgumentsAndLaunchFragment,
        SearchFragment.SendArgumentsAndLaunchFragment, ProfileFragment.SendArgumentsAndLaunchFragment,
        WantToWatchFragment.SendArgumentsAndLaunchFragment, FavouritesFragment.SendArgumentsAndLaunchFragment,
        RatingsFragment.SendArgumentsAndLaunchFragment, ChangePswFragment.SendArgumentsAndLaunchFragment,
        NotificationFragment.SendArgumentsAndLaunchFragment {

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
        deleteFirebaseID(sessionController.getCookie());
        sessionController.logoutUser();
        LoginManager.getInstance().logOut();
        finish();
    }

    private void deleteFirebaseID(final String cookie) {
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try
                {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                    sessionController.saveFirebaseToken("");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                Retrofit retrofit = RetrofitBuilder.createRetrofit(getApplicationContext());
                ApiUser apiUser = retrofit.create(ApiUser.class);
                String token = sessionController.getFirebaseToken();
                Call<BooleanDTO> call = apiUser.setFirebaseID(cookie, token);
                call.enqueue(new Callback<BooleanDTO>() {
                    @Override
                    public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                        BooleanDTO favouritesDTOS = response.body();
                    }

                    @Override
                    public void onFailure(Call<BooleanDTO> call, Throwable t) {
                    }
                });
            }
        }.execute();
    }

    @Override
    public void startFavouritesFragment() {
        FavouritesFragment favouritesFragment = new FavouritesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, favouritesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startWantToWatchFragment() {
        WantToWatchFragment wantToWatchFragment = new WantToWatchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, wantToWatchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startRatingsFragment() {
        RatingsFragment ratingsFragment = new RatingsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, ratingsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startChangePswFragment() {
        ChangePswFragment changePswFragment = new ChangePswFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, changePswFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void deleteAccountFragment() {
//        WantToWatchFragment wantToWatchFragment = new WantToWatchFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame, wantToWatchFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
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
