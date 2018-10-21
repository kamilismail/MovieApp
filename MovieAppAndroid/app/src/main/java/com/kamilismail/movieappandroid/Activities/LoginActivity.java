package com.kamilismail.movieappandroid.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.firebase.iid.FirebaseInstanceId;
import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.UserDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.connection.ApiUser;
import com.kamilismail.movieappandroid.dictionery.Constants;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;
import com.kamilismail.movieappandroid.helpers.SelfSigningClientBuilder;

import java.net.HttpCookie;
import java.util.List;

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
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

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
            _loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login(v);
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

    private void login(final View view) {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        String login = _loginText.getText().toString();
        String password = _passwordText.getText().toString();
        final String credentials = "Basic " + Base64.encodeToString((login + ":" + password).getBytes(), Base64.NO_WRAP);
        Retrofit retrofit = RetrofitBuilder.createRetrofit(getApplicationContext());
        ApiUser apiUser = retrofit.create(ApiUser.class);
        Call <UserDTO> call = apiUser.getUser(credentials);

        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                String cookiesHeader = response.headers().get("Set-Cookie");
                List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
                for (HttpCookie cookie : cookies) {
                    msCookieManager.getCookieStore().add(null, cookie);
                }
                String sessionToken = cookies.get(0).toString();
                sessionController.createLoginSession(sessionToken);
                UserDTO userDTO = response.body();
                sessionController.saveUsername(userDTO.getUsername());
                onLoginSuccess(view);
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                onLoginFailed();
            }
        });
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

    private void onLoginSuccess(final View view) {
        sendFirebaseID(view);
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendFirebaseID(final View view) {
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                String token = FirebaseInstanceId.getInstance().getToken();
                // Used to get firebase token until its null so it will save you from null pointer exeption
                while(token == null) {
                    token = FirebaseInstanceId.getInstance().getToken();
                    sessionController.saveFirebaseToken(token);
                    Log.d("New firebase token: ", token);
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
            }
        }.execute();

        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());
        ApiUser apiUser = retrofit.create(ApiUser.class);
        String cookie = sessionController.getCookie();
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

    private void onLoginFailed() {
        _loginButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT);
    }
}
