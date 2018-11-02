package com.kamilismail.movieappandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamilismail.movieappandroid.DTO.PopularSeriesDTO;
import com.kamilismail.movieappandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularSeriesRecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<PopularSeriesDTO> nowPlayingDTOS;

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
    public PopularSeriesRecyclerViewAdapter(ArrayList<PopularSeriesDTO> nowPlayingDTOList, RecyclerView _recyclerView) {
        this.nowPlayingDTOS = nowPlayingDTOList;
        this.mRecyclerView = _recyclerView;
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
                PopularSeriesDTO data = nowPlayingDTOS.get(position);
            }
        });
        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        PopularSeriesDTO data = nowPlayingDTOS.get(i);
        Picasso.get().load(data.getPosterPath()).resize(1000, 561).into(((MyViewHolder) viewHolder).mImageView);
        ((MyViewHolder) viewHolder).mTitle.setText(data.getTitle());
        ((MyViewHolder) viewHolder).mTitle.setSelected(true);
        ((MyViewHolder) viewHolder).mRating.setText("Rating: " + data.getRating());
        ((MyViewHolder) viewHolder).mRelease.setText("Release date: " + data.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return nowPlayingDTOS.size();
    }
}
