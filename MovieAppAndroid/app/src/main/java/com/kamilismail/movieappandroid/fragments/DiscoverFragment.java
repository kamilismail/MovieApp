package com.kamilismail.movieappandroid.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kamilismail.movieappandroid.DTO.DiscoverDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.connection.ApiDiscover;

import java.net.HttpCookie;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {

    public static String TAG = "DiscoverFragment";
    private SessionController sessionController;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        this.sessionController = new SessionController(getContext());
        getData();
        return view;
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment discoverFragment = new DiscoverFragment();
        return discoverFragment;
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiDiscover.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDiscover apiDiscover = retrofit.create(ApiDiscover.class);

        String cookie = sessionController.getCookie();
        Call<DiscoverDTO> call = apiDiscover.getDiscovery(cookie);
        call.enqueue(new Callback<DiscoverDTO>() {
            @Override
            public void onResponse(Call<DiscoverDTO> call, Response<DiscoverDTO> response) {
                String cookiesHeader = response.headers().get("Set-Cookie");
                List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
                for (HttpCookie cookie : cookies) {
                    msCookieManager.getCookieStore().add(null, cookie);
                }
                String sessionToken = cookies.get(0).toString();
                sessionController.createLoginSession(sessionToken);
                DiscoverDTO discoverDTO = response.body();
                onSuccess(discoverDTO);
            }

            @Override
            public void onFailure(Call<DiscoverDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private void onSuccess(DiscoverDTO discoverDTO) {

    }

    private void onFailed() {
        Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Toast.makeText(getContext(), "Zaladowano Discovery", Toast.LENGTH_SHORT);
    }
}
