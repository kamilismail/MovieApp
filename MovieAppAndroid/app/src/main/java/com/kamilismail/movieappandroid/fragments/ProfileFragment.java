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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kamilismail.movieappandroid.DTO.UserPhotoDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.connection.ApiUser;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;
import com.kamilismail.movieappandroid.helpers.UnitConversionHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    public static String TAG_DATE = "ProfileFragmentDate";
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
    @BindView(R.id.profilePhoto)
    ImageView mProfilePhoto;
    @BindView(R.id.mProgressBarProfile)
    ProgressBar mProgressBarProfile;

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
            mProgressBarProfile.setVisibility(View.VISIBLE);
            if (!checkIfViewSaved(view))
                getData(view);
        }
        return view;
    }

    private boolean checkIfViewSaved(View view) {
        String str = sessionController.getFragmentState(TAG, TAG_DATE);
        if (str == null)
            return false;
        else {
            Gson gson = new Gson();
            UserPhotoDTO userPhotoDTO = gson.fromJson(str, UserPhotoDTO.class);
            UnitConversionHelper unitConversionHelper = new UnitConversionHelper();
            Picasso.get().load(userPhotoDTO.getPhoto())
                    .resize((int) unitConversionHelper.convertDpToPixel(130, view.getContext()),
                            (int) unitConversionHelper.convertDpToPixel(130, view.getContext()))
                    .into(mProfilePhoto);
            mProgressBarProfile.setVisibility(View.GONE);
            return true;
        }
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiUser apiUser = retrofit.create(ApiUser.class);

        String cookie = sessionController.getCookie();
        Call<UserPhotoDTO> call = apiUser.facebookPhoto(cookie);
        call.enqueue(new Callback<UserPhotoDTO>() {
            @Override
            public void onResponse(Call<UserPhotoDTO> call, Response<UserPhotoDTO> response) {
                UserPhotoDTO result = response.body();
                if (result == null) {
                    mCallback.logoutUser();
                } else {
                    UnitConversionHelper unitConversionHelper = new UnitConversionHelper();
                    Picasso.get().load(result.getPhoto())
                            .resize((int) unitConversionHelper.convertDpToPixel(130, view.getContext()),
                                    (int) unitConversionHelper.convertDpToPixel(130, view.getContext()))
                            .into(mProfilePhoto, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    mProgressBarProfile.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Exception e) {
                                }
                            });
                    Gson gson = new Gson();
                    sessionController.saveFragmentState(TAG, gson.toJson(result), TAG_DATE);
                    //pullRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<UserPhotoDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private void onFailed() {
        Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
    }

    private void deleteAccount() {
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
