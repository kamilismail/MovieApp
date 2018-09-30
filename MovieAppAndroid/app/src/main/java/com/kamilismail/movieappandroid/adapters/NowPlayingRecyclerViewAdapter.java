package com.kamilismail.movieappandroid.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamilismail.movieappandroid.DTO.DiscoverDTO;
import com.kamilismail.movieappandroid.DTO.NowPlayingDTO;
import com.kamilismail.movieappandroid.DTO.PopularMoviesDTO;
import com.kamilismail.movieappandroid.DTO.PopularSeriesDTO;
import com.kamilismail.movieappandroid.DTO.UpcomingMoviesDTO;
import com.kamilismail.movieappandroid.R;

import java.util.ArrayList;

public class NowPlayingRecyclerViewAdapter extends RecyclerView.Adapter {

    private DiscoverDTO discoverDTOS;
    private ArrayList<NowPlayingDTO> nowPlayingDTOS;
    private ArrayList<PopularMoviesDTO> popularMoviesDTOS;
    private ArrayList<PopularSeriesDTO> popularSeriesDTOS;
    private ArrayList<UpcomingMoviesDTO> upcomingMoviesDTOS;

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
    public NowPlayingRecyclerViewAdapter(DiscoverDTO _discoverDTOS, RecyclerView _recyclerView) {
        this.discoverDTOS = _discoverDTOS;
        this.nowPlayingDTOS = _discoverDTOS.getNowplaying();
        this.popularMoviesDTOS = _discoverDTOS.getPopularMovies();
        this.popularSeriesDTOS = _discoverDTOS.getPopularSeries();
        this.upcomingMoviesDTOS = _discoverDTOS.getUpcomingMovies();
        this.mRecyclerView = _recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_discover, viewGroup, false);

        //przejscie do widoku z detalami
        //TODO
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // odnajdujemy indeks klikniętego elementu
                int position = mRecyclerView.getChildAdapterPosition(v);
                PubData data = mFavList.get(position);
                if (!data.getId().equals("")) {
                    Intent myIntent = new Intent(mRecyclerView.getContext(), PubInfo.class);
                    myIntent.putExtra("placeID", data.getId());
                    mRecyclerView.getContext().startActivity(myIntent);
                }
            }
        });
        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        PubData data = mFavList.get(i);
        ((MyViewHolder) viewHolder).mName.setText(data.getName());
        ((MyViewHolder) viewHolder).mAddress.setText(data.getAdress());
    }

    @Override
    public int getItemCount() {
        return mFavList.size();
    }
}
