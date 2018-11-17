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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.connection.ApiUser;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeleteAccountFragment extends Fragment {
    private SendArgumentsAndLaunchFragment mCallback;

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();
    }

    public static String TAG = "DeleteAccountFragment";
    private SessionController sessionController;

    @BindView(R.id.mProgressBarProfile)
    ProgressBar progressBar;
    @BindView(R.id.ePsw)
    EditText ePsw;
    @BindView(R.id.bDelete)
    Button bDelete;
    @BindView(R.id.tCancel)
    Button bCancel;
    @BindView(R.id.tPsw)
    TextView tPsw;

    public DeleteAccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_account, container, false);
        this.sessionController = new SessionController(getContext());
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.GONE);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete account?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteAccount(v);
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

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if (sessionController.getRole().toLowerCase().equals("facebook"))
            tPsw.setText("Confirm facebook mail");
        return view;
    }

    private Boolean validate() {
        String password = ePsw.getText().toString();

        if (password.isEmpty()) {
            ePsw.setError("This field cannot be empty");
            return false;
        }
        else return true;
    }

    private void deleteAccount(final View view) {
        if (validate()) {
            Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());
            ApiUser apiUser = retrofit.create(ApiUser.class);
            String cookie = sessionController.getCookie();
            JsonObject obj = new JsonObject();
            obj.addProperty("password", ePsw.getText().toString());
            Call<BooleanDTO> call;
            if (sessionController.getRole().toLowerCase().equals("facebook"))
                call = apiUser.deleteFacebookUser(cookie, obj);
            else
                call = apiUser.deleteUser(cookie, obj);
            progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<BooleanDTO>() {
                @Override
                public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                    BooleanDTO remindersDTOS = response.body();
                    if (remindersDTOS == null)
                        mCallback.logoutUser();
                    if (remindersDTOS.getResult())
                        onSuccess(view);
                    else onFailed(view);
                }

                @Override
                public void onFailure(Call<BooleanDTO> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    onFailed(view);
                }
            });
        }
    }

    private void onSuccess(final View view) {
        progressBar.setVisibility(View.GONE);
        mCallback.logoutUser();
    }

    private void onFailed(View view) {
        Toast.makeText(view.getContext(), "Server error", Toast.LENGTH_SHORT).show();
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
