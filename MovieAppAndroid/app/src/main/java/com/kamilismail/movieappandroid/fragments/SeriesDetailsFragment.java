package com.kamilismail.movieappandroid.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.kamilismail.movieappandroid.DTO.search_series.SeriesDetailsDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.adapters.SeriesSeasonsRecyclerViewAdapter;
import com.kamilismail.movieappandroid.connection.ApiSearch;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;
import com.kamilismail.movieappandroid.helpers.UnitConversionHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class SeriesDetailsFragment extends Fragment {

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();
    }

    public static String TAG = "SeriesDetailsFragment";
    private SessionController sessionController;
    private SendArgumentsAndLaunchFragment mCallback;

    private String id;
    private String title;

    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout pullRefreshLayout;
    @BindView(R.id.mProgressBarProfile)
    ProgressBar mProgressBar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.shareButton)
    ImageButton mShareButton;
    @BindView(R.id.poster)
    ImageView mPoster;
    @BindView(R.id.avarageRating)
    TextView mAvarageRating;
    @BindView(R.id.release)
    TextView mRelease;
    @BindView(R.id.lastRelease)
    TextView mLastRelease;
    @BindView(R.id.noEpisodes)
    TextView mNoEpisodes;
    @BindView(R.id.noSeasons)
    TextView mNoSeasons;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    public SeriesDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_series_details, container, false);
        ButterKnife.bind(this, view);
        this.sessionController = new SessionController(getContext());
        Bundle args = this.getArguments();
        this.id = args.getString("id");
        this.title = args.getString("title");
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setText(this.title);
        setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(view);
            }
        });
        getData(view);

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Check out this series:\n" + mTitle.getText().toString()
                        + ", " + mAvarageRating.getText().toString() + ".";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        return view;
    }

    private void setVisibility(int visibility) {
        mPoster.setVisibility(visibility);
        mAvarageRating.setVisibility(visibility);
        mRelease.setVisibility(visibility);
        mDescription.setVisibility(visibility);
        mShareButton.setVisibility(visibility);
        mLastRelease.setVisibility(visibility);
        mNoEpisodes.setVisibility(visibility);
        mNoSeasons.setVisibility(visibility);
        relativeLayout.setVisibility(visibility);
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiSearch apiSearch = retrofit.create(ApiSearch.class);

        String cookie = sessionController.getCookie();
        Call<SeriesDetailsDTO> call = apiSearch.getTVShow(cookie, this.id);
        call.enqueue(new Callback<SeriesDetailsDTO>() {
            @Override
            public void onResponse(Call<SeriesDetailsDTO> call, Response<SeriesDetailsDTO> response) {
                SeriesDetailsDTO result = response.body();
                if (result == null) {
                    mCallback.logoutUser();
                } else {
                    onSuccess(result, view);
                    pullRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<SeriesDetailsDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private void onSuccess(SeriesDetailsDTO result, final View view) {
        mAvarageRating.setText("Rating: " + result.getResult().getVoteAverage());
        mRelease.setText("First air date: \n" + result.getResult().getFirstAirDate());
        mLastRelease.setText("Last air date: \n" + result.getResult().getLastAirDate());
        mNoEpisodes.setText("Number of episodes: " + result.getResult().getNumberOfEpisodes());
        mNoSeasons.setText("Number of seasons: " + result.getResult().getNumberOfSeasons());
        mDescription.setText("Description:\n\t" + result.getResult().getOverview());
        try {
            RecyclerView recyclerView = setAdapters(R.id.seasonsList, view);
            recyclerView.setAdapter(new SeriesSeasonsRecyclerViewAdapter(result.getResult().getSeasons(), recyclerView, view));
            ScrollingPagerIndicator recyclerIndicator = view.findViewById(R.id.indicator);
            recyclerIndicator.attachToRecyclerView(recyclerView);
        } catch (Exception e) {
            mCallback.logoutUser();
        }
        UnitConversionHelper unitConversionHelper = new UnitConversionHelper();
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + result.getResult().getPosterPath())
                .resize((int) unitConversionHelper.convertDpToPixel(130, view.getContext()),
                        (int) unitConversionHelper.convertDpToPixel(220, view.getContext()))
                .into(mPoster, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                        setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
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

    private void onFailed() {
        Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
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
