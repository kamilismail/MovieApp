package com.kamilismail.movieappandroid.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kamilismail.movieappandroid.DTO.DiscoverDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.adapters.NowPlayingRecyclerViewAdapter;
import com.kamilismail.movieappandroid.adapters.PopularMoviesRecyclerViewAdapter;
import com.kamilismail.movieappandroid.adapters.PopularSeriesRecyclerViewAdapter;
import com.kamilismail.movieappandroid.adapters.UpcomingMoviesRecyclerViewAdapter;
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
        getData(view);
        return view;
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment discoverFragment = new DiscoverFragment();
        return discoverFragment;
    }

    private void getData(final View view) {
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
                DiscoverDTO discoverDTO = response.body();
                onSuccess(discoverDTO, view);
            }

            @Override
            public void onFailure(Call<DiscoverDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private void onSuccess(DiscoverDTO discoverDTO, final View view) {
        RecyclerView recyclerView = view.findViewById(R.id.nowplayingList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new NowPlayingRecyclerViewAdapter(discoverDTO.getNowplaying(), recyclerView));

        RecyclerView recyclerView2 = view.findViewById(R.id.popularMoviesList);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper2 = new PagerSnapHelper();
        snapHelper2.attachToRecyclerView(recyclerView2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(new PopularMoviesRecyclerViewAdapter(discoverDTO.getPopularMovies(), recyclerView2));

        RecyclerView recyclerView3 = view.findViewById(R.id.popularSeriesList);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper3 = new PagerSnapHelper();
        snapHelper3.attachToRecyclerView(recyclerView3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        recyclerView3.setAdapter(new PopularSeriesRecyclerViewAdapter(discoverDTO.getPopularSeries(), recyclerView3));

        RecyclerView recyclerView4 = view.findViewById(R.id.upcomingMoviesList);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper4 = new PagerSnapHelper();
        snapHelper4.attachToRecyclerView(recyclerView4);
        recyclerView4.setItemAnimator(new DefaultItemAnimator());
        recyclerView4.setAdapter(new UpcomingMoviesRecyclerViewAdapter(discoverDTO.getUpcomingMovies(), recyclerView4));
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
