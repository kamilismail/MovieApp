package com.kamilismail.movieappandroid.fragments;

import android.content.Context;
import android.net.Uri;
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

import com.kamilismail.movieappandroid.DTO.FavouritesDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.adapters.FavouritesRecyclerViewAdapter;
import com.kamilismail.movieappandroid.adapters.TVGuideRecyclerViewAdapter;
import com.kamilismail.movieappandroid.connection.ApiFavourites;
import com.kamilismail.movieappandroid.dictionery.Constants;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavouritesFragment extends Fragment {

    private SendArgumentsAndLaunchFragment mCallback;

    @BindView(R.id.mProgressBarProfile)
    ProgressBar progressBar;
    @BindView(R.id.info)
    TextView nothingFound;

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();
        void passMovieData(String id, String title);
    }

    public static String TAG = "FavouritesFragment";
    private SessionController sessionController;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        this.sessionController = new SessionController(getContext());
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.GONE);
        nothingFound.setVisibility(View.GONE);
        getData(view);
        return view;
    }

    public static FavouritesFragment newInstance() {
        FavouritesFragment favouritesFragment = new FavouritesFragment();
        return favouritesFragment;
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiFavourites apiFavourites = retrofit.create(ApiFavourites.class);

        String cookie = sessionController.getCookie();
        Call<ArrayList<FavouritesDTO>> call = apiFavourites.getFavoutites(cookie);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ArrayList <FavouritesDTO>>() {
            @Override
            public void onResponse(Call<ArrayList <FavouritesDTO>> call, Response<ArrayList <FavouritesDTO>> response) {
                ArrayList <FavouritesDTO> favouritesDTOS = response.body();
                if (favouritesDTOS.get(0) == null) {
                    mCallback.logoutUser();
                }
                onSuccess(favouritesDTOS, view);
            }

            @Override
            public void onFailure(Call<ArrayList <FavouritesDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                onFailed(view);
            }
        });
    }

    private void onSuccess(ArrayList <FavouritesDTO> favouritesDTOS, final View view) {
        if(favouritesDTOS.size() > 0) {
            RecyclerView recyclerView = view.findViewById(R.id.favList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
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
        Toast.makeText(view.getContext(), "Server error", Toast.LENGTH_SHORT);
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
