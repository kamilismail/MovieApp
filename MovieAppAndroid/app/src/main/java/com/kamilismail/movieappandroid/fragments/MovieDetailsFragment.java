package com.kamilismail.movieappandroid.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.DiscoverDTO;
import com.kamilismail.movieappandroid.DTO.search_movies.GetMovieDTO;
import com.kamilismail.movieappandroid.DTO.search_movies.Result;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.activities.LoginActivity;
import com.kamilismail.movieappandroid.connection.ApiDiscover;
import com.kamilismail.movieappandroid.connection.ApiFavourites;
import com.kamilismail.movieappandroid.connection.ApiReminders;
import com.kamilismail.movieappandroid.connection.ApiSearch;
import com.kamilismail.movieappandroid.connection.ApiWantToWatch;
import com.kamilismail.movieappandroid.dictionery.Constants;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;
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

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();
    }
    private SendArgumentsAndLaunchFragment mCallback;

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
    @BindView(R.id.addReminder)
    Button mReminder;
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
        final View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, view);
        this.sessionController = new SessionController(getContext());
        Bundle args = this.getArguments();
        this.id = args.getString("id");
        this.title = args.getString("title");
        mTitle.setText(this.title);
        getData(view);

        mAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Add this title to favourites?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                favClicked(view);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        mWantToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Add this title to want to watch?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                wantsClicked(view);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        mReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Set up reminder for this title?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                reminderClicked(view);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return view;
    }

    public static MovieDetailsFragment newInstance() {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        return movieDetailsFragment;
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiSearch apiSearch = retrofit.create(ApiSearch.class);

        String cookie = sessionController.getCookie();
        Call<GetMovieDTO> call = apiSearch.getMovie(cookie, this.id);
        call.enqueue(new Callback<GetMovieDTO>() {
            @Override
            public void onResponse(Call<GetMovieDTO> call, Response<GetMovieDTO> response) {
                GetMovieDTO result = response.body();
                if (result == null) {
                    mCallback.logoutUser();
                } else
                    onSuccess(result, view);
            }

            @Override
            public void onFailure(Call<GetMovieDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private void onSuccess(GetMovieDTO result, final View view) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + result.getPosterPath()).resize(550,870).into(mPoster);
        mAvarageRating.setText("Rating: " + result.getAvarageRating());
        if (!result.getUserRating().isEmpty())
            mRatingBar.setRating(Float.valueOf(result.getUserRating()));
        if (result.getUserFav())
            mAddFav.setText("Delete from favourites");
        else
            mAddFav.setText("Add to favourites");
        if (result.getUserWantToWatch())
            mWantToWatch.setText("Delete from want to watch");
        else
            mWantToWatch.setText("want to watch");

        if(result.getUserReminder())
            mReminder.setText("Delete reminder");
        else
            mReminder.setText("Add reminder");

        mRelease.setText("Release date: " + result.getReleaseDate());
        mDescription.setText("Description:\n\t" + result.getOverview());
        mTVChanel.setText(result.getChanel());
        if (!result.getHour().isEmpty())
            mTVDate.setText(result.getHour());
        else {
            mTVDate.setText("No tv emission info");
            mTVChanel.setText("No tv emission info");
        }
    }

    private void onFailed() {
        Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (SendArgumentsAndLaunchFragment) context;
        } catch (ClassCastException e) {
        }
    }

    private void favClicked(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiFavourites apiFavourites = retrofit.create(ApiFavourites.class);

        String cookie = sessionController.getCookie();
        Call<BooleanDTO> call;
        if (this.mAddFav.getText().toString().toLowerCase().equals("add to favourites"))
            call = apiFavourites.addFavourite(cookie, this.id);
        else
            call = apiFavourites.deleteFavourite(cookie, this.id);

        call.enqueue(new Callback<BooleanDTO>() {
            @Override
            public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                BooleanDTO result = response.body();
                if (result == null) {
                    mCallback.logoutUser();
                } else {
                    if (mAddFav.getText().toString().toLowerCase().equals("Add to favourites"))
                        mAddFav.setText("Delete from favourites");
                    else
                        mAddFav.setText("Add to favourites");
                }
            }

            @Override
            public void onFailure(Call<BooleanDTO> call, Throwable t) {
            }
        });
    }

    private void wantsClicked(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiWantToWatch apiWantToWatch = retrofit.create(ApiWantToWatch.class);

        String cookie = sessionController.getCookie();
        Call<BooleanDTO> call;
        if (this.mWantToWatch.getText().toString().toLowerCase().equals("want to watch"))
            call = apiWantToWatch.addWant(cookie, this.id);
        else
            call = apiWantToWatch.deleteWant(cookie, this.id);

        call.enqueue(new Callback<BooleanDTO>() {
            @Override
            public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                BooleanDTO result = response.body();
                if (result == null) {
                    mCallback.logoutUser();
                } else {
                    if (mWantToWatch.getText().toString().toLowerCase().equals("want to watch"))
                        mWantToWatch.setText("Delete from want to watch");
                    else
                        mWantToWatch.setText("want to watch");
                }
            }

            @Override
            public void onFailure(Call<BooleanDTO> call, Throwable t) {
            }
        });
    }

    private void reminderClicked(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiReminders apiReminders = retrofit.create(ApiReminders.class);

        String cookie = sessionController.getCookie();
        Call<BooleanDTO> call;
        if (this.mReminder.getText().toString().toLowerCase().equals("Add reminder"))
            call = apiReminders.addReminder(cookie, this.id);
        else
            call = apiReminders.deleteReminder(cookie, this.id);

        call.enqueue(new Callback<BooleanDTO>() {
            @Override
            public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                BooleanDTO result = response.body();
                if (result == null) {
                    mCallback.logoutUser();
                } else {
                    if (mReminder.getText().toString().toLowerCase().equals("want to watch"))
                        mReminder.setText("Delete reminder");
                    else
                        mReminder.setText("Add reminder");
                }
            }

            @Override
            public void onFailure(Call<BooleanDTO> call, Throwable t) {
            }
        });
    }
}
