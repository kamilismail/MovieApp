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

import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;
import com.kamilismail.movieappandroid.DTO.DiscoverDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.adapters.NowPlayingRecyclerViewAdapter;
import com.kamilismail.movieappandroid.adapters.PopularMoviesRecyclerViewAdapter;
import com.kamilismail.movieappandroid.adapters.PopularSeriesRecyclerViewAdapter;
import com.kamilismail.movieappandroid.adapters.UpcomingMoviesRecyclerViewAdapter;
import com.kamilismail.movieappandroid.connection.ApiDiscover;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class DiscoverFragment extends Fragment {

    private SendArgumentsAndLaunchFragment mCallback;

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();

        void passMovieData(String id, String title);
    }

    public static String TAG = "DiscoverFragment";
    public static String TAG_DATE = "DiscoverFragmentDate";
    private SessionController sessionController;

    public DiscoverFragment() {
    }

    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout pullRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_discover, container, false);
        this.sessionController = new SessionController(getContext());
        ButterKnife.bind(this, view);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(view);
            }
        });


        if (!checkIfViewSaved(view))
            getData(view);
        return view;
    }

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    private boolean checkIfViewSaved(View view) {
        String str = sessionController.getFragmentState(TAG, TAG_DATE);
        if (str == null)
            return false;
        else {
            Gson gson = new Gson();
            DiscoverDTO discoverDTO = gson.fromJson(str, DiscoverDTO.class);
            onSuccess(discoverDTO, view);
            pullRefreshLayout.setRefreshing(false);
            return true;
        }
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());
        ApiDiscover apiDiscover = retrofit.create(ApiDiscover.class);
        String cookie = sessionController.getCookie();
        Call<DiscoverDTO> call = apiDiscover.getDiscovery(cookie);
        call.enqueue(new Callback<DiscoverDTO>() {
            @Override
            public void onResponse(Call<DiscoverDTO> call, Response<DiscoverDTO> response) {
                DiscoverDTO discoverDTO = response.body();
                try {
                    if (discoverDTO == null) {
                        mCallback.logoutUser();
                    }
                } catch (Exception e) {
                    mCallback.logoutUser();
                }
                Gson gson = new Gson();
                sessionController.saveFragmentState(TAG, gson.toJson(discoverDTO), TAG_DATE);
                onSuccess(discoverDTO, view);
                pullRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<DiscoverDTO> call, Throwable t) {
                onFailed(view);
            }
        });
    }

    private void onSuccess(DiscoverDTO discoverDTO, final View view) {
        try {
            RecyclerView recyclerView = setAdapters(R.id.nowplayingList, view);
            recyclerView.setAdapter(new NowPlayingRecyclerViewAdapter(discoverDTO.getNowplaying(), recyclerView, mCallback));
            ScrollingPagerIndicator recyclerIndicator = view.findViewById(R.id.indicator4);
            recyclerIndicator.attachToRecyclerView(recyclerView);

            RecyclerView recyclerView2 = setAdapters(R.id.popularMoviesList, view);
            recyclerView2.setAdapter(new PopularMoviesRecyclerViewAdapter(discoverDTO.getPopularMovies(), recyclerView2, mCallback));
            ScrollingPagerIndicator recyclerIndicator2 = view.findViewById(R.id.indicator);
            recyclerIndicator2.attachToRecyclerView(recyclerView2);

            RecyclerView recyclerView3 = setAdapters(R.id.popularSeriesList, view);
            recyclerView3.setAdapter(new PopularSeriesRecyclerViewAdapter(discoverDTO.getPopularSeries(), recyclerView3));
            ScrollingPagerIndicator recyclerIndicator3 = view.findViewById(R.id.indicator2);
            recyclerIndicator3.attachToRecyclerView(recyclerView3);

            RecyclerView recyclerView4 = setAdapters(R.id.upcomingMoviesList, view);
            recyclerView4.setAdapter(new UpcomingMoviesRecyclerViewAdapter(discoverDTO.getUpcomingMovies(), recyclerView4, mCallback));
            ScrollingPagerIndicator recyclerIndicator4 = view.findViewById(R.id.indicator3);
            recyclerIndicator4.attachToRecyclerView(recyclerView4);
        } catch (Exception e) {
            mCallback.logoutUser();
        }
    }

    private RecyclerView setAdapters(Integer recyclerId, final View view) {
        RecyclerView recyclerView = view.findViewById(recyclerId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return recyclerView;
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
