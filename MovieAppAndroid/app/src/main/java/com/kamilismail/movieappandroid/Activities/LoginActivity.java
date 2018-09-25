package com.kamilismail.movieappandroid.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.*;

import com.kamilismail.movieappandroid.HttpRequests;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.eLogin)
    EditText _loginText;
    @BindView(R.id.ePassword)
    EditText _passwordText;
    @BindView(R.id.bLogin)
    Button _loginButton;
    @BindView(R.id.tSignUp)
    TextView _signupText;
    @BindView(R.id.mProgressBarProfile)
    ProgressBar progressBar;
    //@BindView(R.id.background)
    //ImageView _imageView;

    private SessionController sessionController;
    private HttpRequests httpRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.sessionController = new SessionController(getApplicationContext());
        if (sessionController.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            progressBar.setVisibility(View.GONE);

            /*TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f, 0.0f, 0.0f);
            animation.setDuration(10000);
            animation.setRepeatCount(100);
            _imageView.startAnimation(animation);*/

            this.httpRequests = new HttpRequests(getApplicationContext());

            _loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            _signupText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
    }

    private void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        String login = _loginText.getText().toString();
        String password = _passwordText.getText().toString();

        String request = httpRequests.authenticateUser();
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = request;

        onLoginSuccess();

    }

    private Boolean validate() {
        String login = _loginText.getText().toString();
        String password = _passwordText.getText().toString();

        if (login.isEmpty()) {
            _loginText.setError("This field cannot be empty");
            return false;
        }
        if (password.isEmpty()) {
            _passwordText.setError("This field cannot be empty");
            return false;
        }
        return true;
    }

    private void onLoginSuccess() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onLoginFailed() {
        _loginButton.setEnabled(true);
    }
}
