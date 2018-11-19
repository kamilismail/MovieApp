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


public class ChangePswFragment extends Fragment {
    private SendArgumentsAndLaunchFragment mCallback;

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();
    }

    public static String TAG = "ChangePswFragment";
    private SessionController sessionController;

    @BindView(R.id.mProgressBarProfile)
    ProgressBar progressBar;
    @BindView(R.id.ePassword)
    EditText ePassword;
    @BindView(R.id.ePassword2)
    EditText ePassword2;
    @BindView(R.id.bDelete)
    Button bDelete;
    @BindView(R.id.tCancel)
    Button bCancel;
    @BindView(R.id.eOldPsw)
    EditText eOldPsw;

    public ChangePswFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_psw, container, false);
        this.sessionController = new SessionController(getContext());
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.GONE);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to change password?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                changePassword(v);
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
        return view;
    }

    private Boolean validate() {
        String password0 = eOldPsw.getText().toString();
        String password1 = ePassword.getText().toString();
        String password2 = ePassword2.getText().toString();

        if (password0.isEmpty()) {
            ePassword.setError("This field cannot be empty");
            return false;
        }

        if (password1.isEmpty()) {
            ePassword.setError("This field cannot be empty");
            return false;
        }
        if (password2.isEmpty()) {
            ePassword2.setError("This field cannot be empty");
            return false;
        }
        if (password1.equals(password2))
            return true;
        else return false;
    }

    private void changePassword(final View view) {
        if (validate()) {
            Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());
            ApiUser apiUser = retrofit.create(ApiUser.class);
            String cookie = sessionController.getCookie();
            JsonObject obj = new JsonObject();
            obj.addProperty("old_password", eOldPsw.getText().toString());
            obj.addProperty("new_password", ePassword.getText().toString());

            Call<BooleanDTO> call = apiUser.changePassword(cookie, obj);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Password has been changed.\nYou'll be redirected to login screen.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mCallback.logoutUser();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
