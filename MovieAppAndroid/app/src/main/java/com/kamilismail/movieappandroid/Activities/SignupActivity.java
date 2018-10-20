package com.kamilismail.movieappandroid.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
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
import com.kamilismail.movieappandroid.dictionery.Constants;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;
import com.kamilismail.movieappandroid.helpers.SelfSigningClientBuilder;

import java.net.HttpCookie;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.eLogin)
    EditText _loginText;
    @BindView(R.id.ePassword)
    EditText _passwordText;
    @BindView(R.id.ePassword2)
    EditText _passwordText2;
    @BindView(R.id.bSignUp)
    Button _loginButton;
    @BindView(R.id.tCancel)
    TextView _cancelText;
    @BindView(R.id.mProgressBarProfile)
    ProgressBar progressBar;

    private SessionController sessionController;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.sessionController = new SessionController(getApplicationContext());
        if (sessionController.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_signup);
            ButterKnife.bind(this);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            progressBar.setVisibility(View.GONE);

            /*TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f, 0.0f, 0.0f);
            animation.setDuration(10000);
            animation.setRepeatCount(100);
            _imageView.startAnimation(animation);*/

            _loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signUp();
                }
            });

            _cancelText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
    }

    private void signUp() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        String login = _loginText.getText().toString();
        String password = _passwordText.getText().toString();

        JsonObject obj = new JsonObject();
        obj.addProperty("username", login);
        obj.addProperty("password", password);
        obj.addProperty("role", "user");

        Retrofit retrofit = RetrofitBuilder.createRetrofit(getApplicationContext());

        ApiUser apiUser = retrofit.create(ApiUser.class);
        Call<BooleanDTO> call = apiUser.createNewUser(obj);

        call.enqueue(new Callback<BooleanDTO>() {
            @Override
            public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                String cookiesHeader = response.headers().get("Set-Cookie");
                List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
                for (HttpCookie cookie : cookies) {
                    msCookieManager.getCookieStore().add(null, cookie);
                }
                String sessionToken = cookies.get(0).toString();
                sessionController.createLoginSession(sessionToken);
                BooleanDTO booleanDTO = response.body();
                if (booleanDTO.getResult())
                    onLoginSuccess();
                else
                    onLoginFailed();
            }

            @Override
            public void onFailure(Call<BooleanDTO> call, Throwable t) {
                onLoginFailed();
            }
        });
    }

    private Boolean validate() {
        String login = _loginText.getText().toString();
        String password = _passwordText.getText().toString();
        String password2 = _passwordText2.getText().toString();

        if (login.isEmpty()) {
            _loginText.setError("This field cannot be empty");
            return false;
        }
        if (password.isEmpty()) {
            _passwordText.setError("This field cannot be empty");
            return false;
        }
        if (password2.isEmpty()) {
            _passwordText2.setError("This field cannot be empty");
            return false;
        }
        if (!password.equals(password2)) {
            _passwordText2.setError("Passwords are not the same");
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
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT);
    }
}
