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
import com.kamilismail.movieappandroid.DTO.RemindersDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.adapters.RemindersRecyclerViewAdapter;
import com.kamilismail.movieappandroid.connection.ApiReminders;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationFragment extends Fragment {

    private SendArgumentsAndLaunchFragment mCallback;

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();

        void passMovieData(String id, String title);
    }

    public static String TAG = "NotificationFragment";
    private SessionController sessionController;
    @BindView(R.id.mProgressBarProfile)
    ProgressBar progressBar;
    @BindView(R.id.info)
    TextView nothingFound;
    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout pullRefreshLayout;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        this.sessionController = new SessionController(getContext());
        progressBar.setVisibility(View.VISIBLE);
        nothingFound.setVisibility(View.GONE);

        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(view);
            }
        });

        getData(view);
        return view;
    }

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiReminders apiReminders = retrofit.create(ApiReminders.class);

        String cookie = sessionController.getCookie();
        Call<ArrayList<RemindersDTO>> call = apiReminders.getReminders(cookie);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ArrayList<RemindersDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<RemindersDTO>> call, Response<ArrayList<RemindersDTO>> response) {
                ArrayList<RemindersDTO> remindersDTOS = response.body();
                if (remindersDTOS == null) {
                    mCallback.logoutUser();
                }
                onSuccess(remindersDTOS, view);
                pullRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<RemindersDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                onFailed(view);
            }
        });
    }

    private void onSuccess(ArrayList<RemindersDTO> movieDetailDTOS, final View view) {
        if (!movieDetailDTOS.isEmpty()) {
            RecyclerView recyclerView = view.findViewById(R.id.reminderList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new RemindersRecyclerViewAdapter(movieDetailDTOS, recyclerView, mCallback));
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
