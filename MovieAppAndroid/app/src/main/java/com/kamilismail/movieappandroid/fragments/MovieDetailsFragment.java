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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kamilismail.movieappandroid.DTO.DiscoverDTO;
import com.kamilismail.movieappandroid.DTO.search_movies.GetMovieDTO;
import com.kamilismail.movieappandroid.DTO.search_movies.Result;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.activities.LoginActivity;
import com.kamilismail.movieappandroid.connection.ApiDiscover;
import com.kamilismail.movieappandroid.connection.ApiSearch;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MovieDetailsFragment extends Fragment {

    public static String TAG = "MovieDetailsFragment";
    private SessionController sessionController;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    private String id;
    private String title;

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.poster)
    ImageView mPoster;
    @BindView(R.id.avarageRating)
    TextView mAvarageRating;
    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.addFav)
    Button mAddFav;
    @BindView(R.id.addWantToWatch)
    Button mWantToWatch;
    @BindView(R.id.tvChanel)
    TextView mTVChanel;
    @BindView(R.id.tvlogo)
    ImageView mTVLogo;
    @BindView(R.id.release)
    TextView mRelease;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.tvDate)
    TextView mTVDate;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        this.sessionController = new SessionController(getContext());
        Bundle args = this.getArguments();
        this.id = args.getString("id");
        this.title = args.getString("title");
        ButterKnife.bind(this, view);
        mTitle.setText(this.title);
        getData(view);
        return view;
    }

    public static MovieDetailsFragment newInstance() {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        return movieDetailsFragment;
    }

    private void getData(final View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSearch.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiSearch apiSearch = retrofit.create(ApiSearch.class);

        String cookie = sessionController.getCookie();
        Call<GetMovieDTO> call = apiSearch.getMovie(cookie, this.id);
        call.enqueue(new Callback<GetMovieDTO>() {
            @Override
            public void onResponse(Call<GetMovieDTO> call, Response<GetMovieDTO> response) {
                GetMovieDTO result = response.body();
                if (result == null) {
                    sessionController.logoutUser();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                onSuccess(result, view);
            }

            @Override
            public void onFailure(Call<GetMovieDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private void onSuccess(GetMovieDTO result, final View view) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + result.getResults().
                getPosterPath()).resize(350,550).into(mPoster);
        mAvarageRating.setText("Rating: " + result.getResults().getVoteAverage());
        //mRatingBar.setRating(result.getRating());
        mRelease.setText("Release date: " + result.getResults().getReleaseDate());
        mDescription.setText("Description:\n\t" + result.getResults().getOverview());
        mTVChanel.setText(result.getChanel());
        if (!result.getHour().isEmpty())
            mTVDate.setText(result.getHour());
        else
            mTVDate.setText("No tv emission info. You can set a reminder.");
    }

    private void onFailed() {
        Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Toast.makeText(getContext(), "Zaladowano Movie details", Toast.LENGTH_SHORT);
    }
}
