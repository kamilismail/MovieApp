package com.kamilismail.movieappandroid.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.kamilismail.movieappandroid.fragments.SeriesDetailsFragment;
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
        NotificationFragment.SendArgumentsAndLaunchFragment, SeriesDetailsFragment.SendArgumentsAndLaunchFragment {

    @BindView(R.id.bottom_navigation_menu)
    BottomNavigationView mBottomNavigationView;
    private SessionController sessionController;

    final Fragment discoverFragment = new DiscoverFragment();
    final Fragment tvFragment = new TVFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment notificationFragment = new NotificationFragment();
    final Fragment profileFragment = new ProfileFragment();
    Fragment active = discoverFragment;
    Fragment temp = null;
    FragmentManager supportFragmentManager = getSupportFragmentManager();

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

        supportFragmentManager.beginTransaction().add(R.id.frame, discoverFragment, DiscoverFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    // Create navigation item selected click listener.
    private BottomNavigationView.OnNavigationItemSelectedListener mItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_discover:
                            item.setChecked(true);
                            supportFragmentManager.beginTransaction().hide(active).show(discoverFragment).commit();
                            active = discoverFragment;
                            return true;
                        case R.id.action_tv:
                            item.setChecked(true);
                            if (supportFragmentManager.findFragmentByTag(TVFragment.TAG) != null)
                                supportFragmentManager.beginTransaction().hide(active).show(tvFragment).commit();
                            else
                                supportFragmentManager.beginTransaction().add(R.id.frame, tvFragment, TVFragment.TAG)
                                        .addToBackStack(null).hide(active).commit();
                            active = tvFragment;
                            return true;
                        case R.id.action_search:
                            item.setChecked(true);
                            if (supportFragmentManager.findFragmentByTag(SearchFragment.TAG) != null)
                                supportFragmentManager.beginTransaction().hide(active).show(searchFragment).commit();
                            else
                                supportFragmentManager.beginTransaction().add(R.id.frame, searchFragment, SearchFragment.TAG)
                                        .addToBackStack(null).hide(active).commit();
                            active = searchFragment;
                            return true;
                        case R.id.action_notification:
                            item.setChecked(true);
                            if (supportFragmentManager.findFragmentByTag(NotificationFragment.TAG) != null)
                                supportFragmentManager.beginTransaction().hide(active).show(notificationFragment).commit();
                            else
                                supportFragmentManager.beginTransaction().add(R.id.frame, notificationFragment, NotificationFragment.TAG)
                                        .addToBackStack(null).hide(active).commit();
                            active = notificationFragment;
                            return true;
                        case R.id.action_user:
                            item.setChecked(true);
                            if (supportFragmentManager.findFragmentByTag(ProfileFragment.TAG) != null)
                                supportFragmentManager.beginTransaction().hide(active).show(profileFragment).commit();
                            else
                                supportFragmentManager.beginTransaction().add(R.id.frame, profileFragment, ProfileFragment.TAG)
                                        .addToBackStack(null).hide(active).commit();
                            active = profileFragment;
                            return true;
                    }
                    return false;
                }

            };

    @Override
    public void onBackPressed() {
        if (temp != null) {
            supportFragmentManager.beginTransaction().hide(active).show(temp).commit();
            active = temp;
            temp = null;
        } else {
            if (active == discoverFragment)
                finish();
            supportFragmentManager.beginTransaction().hide(active).show(discoverFragment).commit();
            active = discoverFragment;
            mBottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public void logoutUser() {
        deleteFirebaseID(sessionController.getCookie());
        try {
            LoginManager.getInstance().logOut();
            sessionController.logoutUser();
        } catch (Exception e) {}
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteFirebaseID(final String cookie) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                    sessionController.saveFirebaseToken("");
                } catch (IOException e) {
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
                    public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {}

                    @Override
                    public void onFailure(Call<BooleanDTO> call, Throwable t) {}
                });
            }
        }.execute();
    }

    @Override
    public void startFavouritesFragment() {
        FavouritesFragment favouritesFragment = new FavouritesFragment();
        temp = active;
        active = favouritesFragment;
        supportFragmentManager.beginTransaction().add(R.id.frame, favouritesFragment).hide(temp)
                .addToBackStack(null).commit();
    }

    @Override
    public void startWantToWatchFragment() {
        WantToWatchFragment wantToWatchFragment = new WantToWatchFragment();
        temp = active;
        active = wantToWatchFragment;
        supportFragmentManager.beginTransaction().add(R.id.frame, wantToWatchFragment).hide(temp)
                .addToBackStack(null).commit();
    }

    @Override
    public void startRatingsFragment() {
        RatingsFragment ratingsFragment = new RatingsFragment();
        temp = active;
        active = ratingsFragment;
        supportFragmentManager.beginTransaction().add(R.id.frame, ratingsFragment).hide(temp)
                .addToBackStack(null).commit();
    }

    @Override
    public void startChangePswFragment() {
        ChangePswFragment changePswFragment = new ChangePswFragment();
        temp = active;
        active = changePswFragment;
        supportFragmentManager.beginTransaction().add(R.id.frame, changePswFragment).hide(temp)
                .addToBackStack(null).commit();
    }

    @Override
    public void deleteAccountFragment() {
    }

    @Override
    public void passMovieData(String id, String title) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("title", title);
        movieDetailsFragment.setArguments(args);
        temp = active;
        active = movieDetailsFragment;
        supportFragmentManager.beginTransaction().add(R.id.frame, movieDetailsFragment).hide(temp)
                .addToBackStack(null).commit();
    }

    @Override
    public void passSeriesData(String id, String title) {
        SeriesDetailsFragment seriesDetailsFragment = new SeriesDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("title", title);
        seriesDetailsFragment.setArguments(args);
        temp = active;
        active = seriesDetailsFragment;
        supportFragmentManager.beginTransaction().add(R.id.frame, seriesDetailsFragment).hide(temp)
                .addToBackStack(null).commit();
    }
}
