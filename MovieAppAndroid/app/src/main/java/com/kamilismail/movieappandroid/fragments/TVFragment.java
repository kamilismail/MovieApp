package com.kamilismail.movieappandroid.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kamilismail.movieappandroid.DTO.TVGuideDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.activities.LoginActivity;
import com.kamilismail.movieappandroid.adapters.TVGuideRecyclerViewAdapter;
import com.kamilismail.movieappandroid.connection.ApiTVGuide;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {

    public static String TAG = "TVFragment";
    private SessionController sessionController;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    private ProgressBar progressBar;

    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        this.sessionController = new SessionController(getContext());
        progressBar = view.findViewById(R.id.mProgressBarProfile);
        progressBar.setVisibility(View.GONE);
        getData(view);
        return view;
    }

    public static TVFragment newInstance() {
        TVFragment tvFragment = new TVFragment();
        return tvFragment;
    }

    private void getData(final View view) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiTVGuide.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiTVGuide apiTVGuide = retrofit.create(ApiTVGuide.class);

        String cookie = sessionController.getCookie();
        Call<ArrayList <TVGuideDTO>> call = apiTVGuide.getTVGuide(cookie);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ArrayList <TVGuideDTO>>() {
            @Override
            public void onResponse(Call<ArrayList <TVGuideDTO>> call, Response<ArrayList <TVGuideDTO>> response) {
                ArrayList <TVGuideDTO> movieDetailDTOS = response.body();
                if (movieDetailDTOS == null) {
                    sessionController.logoutUser();
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                onSuccess(movieDetailDTOS, view);
            }

            @Override
            public void onFailure(Call<ArrayList <TVGuideDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                onFailed(view);
            }
        });
    }

    private void onSuccess(ArrayList <TVGuideDTO> movieDetailDTOS, final View view) {
        RecyclerView recyclerView = view.findViewById(R.id.onTV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new TVGuideRecyclerViewAdapter(movieDetailDTOS, recyclerView));
        progressBar.setVisibility(View.GONE);
    }

    private void onFailed(View view) {
        Toast.makeText(view.getContext(), "Server error", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "Zaladowano TV", Toast.LENGTH_SHORT);
    }
}
