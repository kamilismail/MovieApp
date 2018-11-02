package com.kamilismail.movieappandroid.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        this.sessionController = new SessionController(getContext());
        _username.setText(sessionController.getUsername());

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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mCallback.logoutUser();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        if (sessionController.getRole().equals("user")) {
            bChange.setVisibility(View.VISIBLE);
            bDelete.setVisibility(View.VISIBLE);

            bChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.startChangePswFragment();
                }
            });

            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to sign out?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteAccount();
                                    mCallback.logoutUser();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        } else {
            bChange.setVisibility(View.GONE);
            bDelete.setVisibility(View.GONE);
        }

        return view;
    }

    private void deleteAccount() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
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
