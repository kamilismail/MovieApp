package com.kamilismail.movieappandroid.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    private SendArgumentsAndLaunchFragment mCallback;
    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();
        void startFavouritesFragment();
        void startWantToWatchFragment();
        void startRatingsFragment();
        void startChangePswFragment();
        void deleteAccountFragment();
    }

    public static String TAG = "ProfileFragment";
    private SessionController sessionController;

    @BindView(R.id.username)
    TextView _username;
    @BindView(R.id.bWantToWatch)
    Button bWantToWatch;
    @BindView(R.id.bRatings)
    Button bRatings;
    @BindView(R.id.bFavourites)
    Button bFavourites;
    @BindView(R.id.bSignout)
    Button bSignout;
    @BindView(R.id.bChange)
    Button bChange;
    @BindView(R.id.bDelete)
    Button bDelete;

    public ProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        this.sessionController = new SessionController(getContext());
        String str = sessionController.getUsername();
        _username.setText(str);

        bWantToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startWantToWatchFragment();
            }
        });

        bRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startRatingsFragment();
            }
        });

        bFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startFavouritesFragment();
            }
        });

        bSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.logoutUser();
            }
        });

        bChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startChangePswFragment();
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.deleteAccountFragment();
            }
        });

        return view;
    }

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (SendArgumentsAndLaunchFragment) context;
        } catch (ClassCastException e) {

        }
    }
}
