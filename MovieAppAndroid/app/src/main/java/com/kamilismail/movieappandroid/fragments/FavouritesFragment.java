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
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kamilismail.movieappandroid.DTO.FavouritesDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.adapters.FavouritesRecyclerViewAdapter;
import com.kamilismail.movieappandroid.connection.ApiFavourites;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavouritesFragment extends Fragment {

    private SendArgumentsAndLaunchFragment mCallback;

    @BindView(R.id.mProgressBarProfile)
    ProgressBar progressBar;
    @BindView(R.id.info)
    TextView nothingFound;
    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout pullRefreshLayout;

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();

        void passMovieData(String id, String title);
    }

    public static String TAG = "FavouritesFragment";
    public static String TAG_DATE = "FavouritesFragmentDate";
    private SessionController sessionController;

    public FavouritesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        this.sessionController = new SessionController(getContext());
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        nothingFound.setVisibility(View.GONE);

        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nothingFound.setVisibility(View.GONE);
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
            TypeToken<ArrayList<FavouritesDTO>> token = new TypeToken<ArrayList<FavouritesDTO>>() {
            };
            ArrayList<FavouritesDTO> favouritesDTOS = gson.fromJson(str, token.getType());
            onSuccess(favouritesDTOS, view);
            pullRefreshLayout.setRefreshing(false);
            return true;
        }
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiFavourites apiFavourites = retrofit.create(ApiFavourites.class);

        String cookie = sessionController.getCookie();
        Call<ArrayList<FavouritesDTO>> call = apiFavourites.getFavoutites(cookie);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ArrayList<FavouritesDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<FavouritesDTO>> call, Response<ArrayList<FavouritesDTO>> response) {
                ArrayList<FavouritesDTO> favouritesDTOS = response.body();
                onSuccess(favouritesDTOS, view);
                Gson gson = new Gson();
                sessionController.saveFragmentState(TAG, gson.toJson(favouritesDTOS), TAG_DATE);
                pullRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<FavouritesDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                onFailed(view);
            }
        });
    }

    private void onSuccess(ArrayList<FavouritesDTO> favouritesDTOS, final View view) {
        if (favouritesDTOS != null && !favouritesDTOS.isEmpty()) {
            RecyclerView recyclerView = view.findViewById(R.id.favList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setOnFlingListener(null);
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new FavouritesRecyclerViewAdapter(favouritesDTOS, recyclerView, mCallback));
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            nothingFound.setVisibility(View.VISIBLE);
        }
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
