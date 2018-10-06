package com.kamilismail.movieappandroid.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kamilismail.movieappandroid.DTO.search_movies.SearchMovieDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.activities.LoginActivity;
import com.kamilismail.movieappandroid.adapters.SearchMoviesRecyclerViewAdapter;
import com.kamilismail.movieappandroid.connection.ApiSearch;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener,
        MaterialSearchBar.OnSearchActionListener, PopupMenu.OnMenuItemClickListener {

    public static String TAG = "SearchFragment";
    private SessionController sessionController;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    private TextView mChoice;
    private View view;
    private ProgressBar mProgressBar;
    private TextView mInfo;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        this.sessionController = new SessionController(getContext());
        mChoice = view.findViewById(R.id.choice);
        mProgressBar = view.findViewById(R.id.mProgressBarProfile);
        mInfo = view.findViewById(R.id.info);
        mProgressBar.setVisibility(View.GONE);
        mInfo.setVisibility(View.GONE);
        mChoice.setText("for movies");
        searchBar = (MaterialSearchBar) view.findViewById(R.id.searchBar);
        searchBar.setHint("Search for movies");
        searchBar.setSpeechMode(true);
        //enable searchbar callbacks
        searchBar.setOnSearchActionListener(this);
        //restore last queries from disk
        //lastSearches = loadSearchSuggestionFromDisk();
        //searchBar.setLastSuggestions(lastSearches);
        //Inflate menu and setup OnMenuItemClickListener
        searchBar.inflateMenu(R.menu.search_menu);
        searchBar.getMenu().setOnMenuItemClickListener(this);
        return view;
    }

    public static SearchFragment newInstance() {
        SearchFragment searchFragment = new SearchFragment();
        return searchFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Toast.makeText(getContext(), "Zaladowano Search", Toast.LENGTH_SHORT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        mProgressBar.setVisibility(View.VISIBLE);
        mInfo.setVisibility(View.GONE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSearch.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiSearch apiSearch = retrofit.create(ApiSearch.class);

        String cookie = sessionController.getCookie();
        Call<SearchMovieDTO> call;
        switch (mChoice.getText().toString()) {
            case "movies" :
                call = apiSearch.getMovies(cookie, text.toString().replaceAll(" ", "_"));
                break;
            case "series" :
                call = apiSearch.getSeries(cookie, text.toString().replaceAll(" ", "_"));
                break;
            default:
                call = apiSearch.getProductions(cookie, text.toString().replaceAll(" ", "_"));
                break;
        }

        //progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<SearchMovieDTO>() {
            @Override
            public void onResponse(Call<SearchMovieDTO> call, Response<SearchMovieDTO> response) {
                SearchMovieDTO movieDetailDTO = response.body();
                if (movieDetailDTO == null) {
                    sessionController.logoutUser();
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                if (movieDetailDTO.getResults().size() < 1)
                    mInfo.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                onSuccess(movieDetailDTO, view);
            }

            @Override
            public void onFailure(Call<SearchMovieDTO> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                onFailed(view);
            }
        });
    }

    private void onSuccess(SearchMovieDTO movieDetailDTO, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new SearchMoviesRecyclerViewAdapter(movieDetailDTO.getResults(), recyclerView));
        //progressBar.setVisibility(View.GONE);
    }

    private void onFailed(View view) {
        Toast.makeText(view.getContext(), "Server error", Toast.LENGTH_SHORT);
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        mChoice.setText(item.getTitle());
        searchBar.setHint("Search for " + item.getTitle());
        return true;
    }
}
