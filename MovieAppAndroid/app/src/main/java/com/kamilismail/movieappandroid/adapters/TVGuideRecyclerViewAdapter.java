package com.kamilismail.movieappandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamilismail.movieappandroid.DTO.TVGuideDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.fragments.TVFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TVGuideRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<TVGuideDTO> movieDetailDTOS;
    private RecyclerView mRecyclerView;
    private TVFragment.SendArgumentsAndLaunchFragment mCallback;

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ImageView mLogo;
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
            mLogo = v.findViewById(R.id.logo);
        }
    }

    // konstruktor adaptera
    public TVGuideRecyclerViewAdapter(ArrayList <TVGuideDTO> movieDetailDTOS, RecyclerView _recyclerView, TVFragment.SendArgumentsAndLaunchFragment callback) {
        this.movieDetailDTOS = movieDetailDTOS;
        this.mRecyclerView = _recyclerView;
        this.mCallback = callback;
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
                TVGuideDTO data = movieDetailDTOS.get(position);
                mCallback.passMovieData(data.getId(), data.getTitle());
            }
        });
        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        TVGuideDTO data = movieDetailDTOS.get(i);
        Picasso.get().load(data.getPosterPath()).resize(1000,561).into(((MyViewHolder) viewHolder).mImageView);
        ((MyViewHolder) viewHolder).mTitle.setText(data.getTitle());
        ((MyViewHolder) viewHolder).mTitle.setSelected(true);
        ((MyViewHolder) viewHolder).mRating.setText("Rating: " + data.getRating());
        ((MyViewHolder) viewHolder).mRelease.setText("Release date: " + data.getReleaseDate());
        ((MyViewHolder) viewHolder).mHour.setText("When: " + data.getHour().substring(11,16));
        ((MyViewHolder) viewHolder).mChannel.setText("Channel: " + data.getChanel());
        if (!data.getLogoPath().isEmpty())
            Picasso.get().load(data.getLogoPath()).resize(150,100).into(((MyViewHolder) viewHolder).mLogo);
    }

    @Override
    public int getItemCount() {
        return movieDetailDTOS.size();
    }
}
