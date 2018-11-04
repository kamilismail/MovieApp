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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kamilismail.movieappandroid.DTO.TVGuideDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.adapters.TVGuideRecyclerViewAdapter;
import com.kamilismail.movieappandroid.connection.ApiTVGuide;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {

    private SendArgumentsAndLaunchFragment mCallback;

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();

        void passMovieData(String id, String title);
    }

    public static String TAG = "TVFragment";
    public static String TAG_DATE = "TVFragmentDate";
    private SessionController sessionController;
    @BindView(R.id.mProgressBarProfile)
    ProgressBar progressBar;
    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout pullRefreshLayout;

    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tv, container, false);
        ButterKnife.bind(this, view);
        this.sessionController = new SessionController(getContext());
        progressBar.setVisibility(View.VISIBLE);

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

    private boolean checkIfViewSaved(View view) {
        String str = sessionController.getFragmentState(TAG, TAG_DATE);
        if (str == null)
            return false;
        else {
            Gson gson = new Gson();
            TypeToken<ArrayList<TVGuideDTO>> token = new TypeToken<ArrayList<TVGuideDTO>>() {};
            ArrayList<TVGuideDTO> movieDetailDTOS = gson.fromJson(str, token.getType());
            onSuccess(movieDetailDTOS, view);
            pullRefreshLayout.setRefreshing(false);
            return true;
        }
    }

    public static TVFragment newInstance() {
        return new TVFragment();
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiTVGuide apiTVGuide = retrofit.create(ApiTVGuide.class);

        String cookie = sessionController.getCookie();
        Call<ArrayList<TVGuideDTO>> call = apiTVGuide.getTVGuide(cookie);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ArrayList<TVGuideDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<TVGuideDTO>> call, Response<ArrayList<TVGuideDTO>> response) {
                ArrayList<TVGuideDTO> movieDetailDTOS = response.body();
                if (movieDetailDTOS == null) {
                    mCallback.logoutUser();
                }
                Gson gson = new Gson();
                onSuccess(movieDetailDTOS, view);
                sessionController.saveFragmentState(TAG, gson.toJson(movieDetailDTOS), TAG_DATE);
                pullRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<TVGuideDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                onFailed(view);
            }
        });
    }

    private void onSuccess(ArrayList<TVGuideDTO> movieDetailDTOS, final View view) {
        RecyclerView recyclerView = view.findViewById(R.id.onTV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new TVGuideRecyclerViewAdapter(movieDetailDTOS, recyclerView, mCallback));
        progressBar.setVisibility(View.GONE);
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
