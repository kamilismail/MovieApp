package com.kamilismail.movieappandroid.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    public static String TAG = "ProfileFragment";
    private SessionController sessionController;

    //@BindView(R.id.username)
    //TextView _username;

    public ProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //ButterKnife.bind(view);
        TextView _textUsername = (TextView) view.findViewById(R.id.username);
        this.sessionController = new SessionController(getContext());
        String str = sessionController.getUsername();
        _textUsername.setText(str);
        return view;
    }

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(getContext(), "Zaladowano Profile", Toast.LENGTH_SHORT);
    }
}
