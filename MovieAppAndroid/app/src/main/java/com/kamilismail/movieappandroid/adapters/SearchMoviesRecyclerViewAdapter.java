package com.kamilismail.movieappandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamilismail.movieappandroid.DTO.search_movies.Result;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.fragments.SearchFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchMoviesRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Result> nowPlayingDTOS;
    private SearchFragment.SendArgumentsAndLaunchFragment mCallback;
    private RecyclerView mRecyclerView;

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mRating;
        public TextView mRelease;

        public MyViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.poster);
            mTitle = v.findViewById(R.id.title);
            mRating = v.findViewById(R.id.rating);
            mRelease = v.findViewById(R.id.release);
        }
    }

    // konstruktor adaptera
    public SearchMoviesRecyclerViewAdapter(List <Result> nowPlayingDTOList, RecyclerView _recyclerView, SearchFragment.SendArgumentsAndLaunchFragment callback) {
        this.nowPlayingDTOS = nowPlayingDTOList;
        this.mRecyclerView = _recyclerView;
        this.mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.production_list, viewGroup, false);

        //przejscie do widoku z detalami
        //TODO
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // odnajdujemy indeks klikniętego elementu
                int position = mRecyclerView.getChildAdapterPosition(v);
                Result data = nowPlayingDTOS.get(position);
                mCallback.passMovieData(data.getId().toString(), data.getTitle());
            }
        });
        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        Result data = nowPlayingDTOS.get(i);
        if (data.getPosterPath() != null)
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + data.getPosterPath().toString()).resize(320,534).into(((MyViewHolder) viewHolder).mImageView);
        ((MyViewHolder) viewHolder).mTitle.setText(data.getTitle());
        ((MyViewHolder) viewHolder).mTitle.setSelected(true);
        ((MyViewHolder) viewHolder).mRating.setText("Rating: " + data.getVoteAverage());
        ((MyViewHolder) viewHolder).mRelease.setText("Release date: " + data.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return nowPlayingDTOS.size();
    }
}
