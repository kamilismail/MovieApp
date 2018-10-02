package com.kamilismail.movieappandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamilismail.movieappandroid.DTO.tvGuide.TVGuideDTO;
import com.kamilismail.movieappandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailsRecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<TVGuideDTO> tvGuideDTOS;

    private RecyclerView mRecyclerView;

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mRating;
        public TextView mRelease;
        public TextView mHour;
        public TextView mChannel;

        public MyViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.poster);
            mTitle = v.findViewById(R.id.title);
            mRating = v.findViewById(R.id.rating);
            mRelease = v.findViewById(R.id.release);
            mHour = v.findViewById(R.id.hour);
            mChannel = v.findViewById(R.id.channel);
        }
    }

    // konstruktor adaptera
    public MovieDetailsRecyclerViewAdapter(ArrayList <TVGuideDTO> tvGuideDTOS, RecyclerView _recyclerView) {
        this.tvGuideDTOS = tvGuideDTOS;
        this.mRecyclerView = _recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tvguide_list, viewGroup, false);

        //przejscie do widoku z detalami
        //TODO
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // odnajdujemy indeks klikniętego elementu
                int position = mRecyclerView.getChildAdapterPosition(v);
                TVGuideDTO data = tvGuideDTOS.get(position);
            }
        });
        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        TVGuideDTO data = tvGuideDTOS.get(i);
        Picasso.get().load("https://image.tmdb.org/t/p/w500//" + data.getResult().getPosterPath()).resize(320,534).into(((MyViewHolder) viewHolder).mImageView);
        ((MyViewHolder) viewHolder).mTitle.setText(data.getResult().getTitle());
        ((MyViewHolder) viewHolder).mTitle.setSelected(true);
        ((MyViewHolder) viewHolder).mRating.setText("Rating: " + data.getUserRating());
        ((MyViewHolder) viewHolder).mRelease.setText("Release date: " + data.getResult().getReleaseDate());
        ((MyViewHolder) viewHolder).mHour.setText("On tv at: " + data.getHour());
        ((MyViewHolder) viewHolder).mHour.setText("On channel: " + data.getChanel());
    }

    @Override
    public int getItemCount() {
        return tvGuideDTOS.size();
    }
}
