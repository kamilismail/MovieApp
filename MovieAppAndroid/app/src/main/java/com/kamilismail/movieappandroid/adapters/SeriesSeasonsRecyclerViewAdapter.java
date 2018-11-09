package com.kamilismail.movieappandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamilismail.movieappandroid.DTO.search_series.Season;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.helpers.UnitConversionHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeriesSeasonsRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Season> seasonArrayList;
    private RecyclerView mRecyclerView;
    private View view;

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mSeasonNumber;
        public TextView mRelease;

        public MyViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.poster);
            mTitle = v.findViewById(R.id.title);
            mSeasonNumber = v.findViewById(R.id.rating);
            mRelease = v.findViewById(R.id.release);
        }
    }

    // konstruktor adaptera
    public SeriesSeasonsRecyclerViewAdapter(List<Season> nowPlayingDTOList, RecyclerView _recyclerView, View view) {
        this.seasonArrayList = nowPlayingDTOList;
        this.mRecyclerView = _recyclerView;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.seasons_list, viewGroup, false);

        //przejscie do widoku z detalami
        //TODO
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // odnajdujemy indeks klikniętego elementu
                int position = mRecyclerView.getChildAdapterPosition(v);
                Season data = seasonArrayList.get(position);
            }
        });
        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    private void setVisibility(RecyclerView.ViewHolder viewHolder, int visibility) {
        ((MyViewHolder) viewHolder).mTitle.setVisibility(visibility);
        ((MyViewHolder) viewHolder).mTitle.setVisibility(visibility);
        ((MyViewHolder) viewHolder).mSeasonNumber.setVisibility(visibility);
        ((MyViewHolder) viewHolder).mRelease.setVisibility(visibility);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        setVisibility(viewHolder, View.GONE);
        UnitConversionHelper unitConversionHelper = new UnitConversionHelper();
        Season data = seasonArrayList.get(i);
        ((MyViewHolder) viewHolder).mTitle.setText(data.getName());
        ((MyViewHolder) viewHolder).mTitle.setSelected(true);
        ((MyViewHolder) viewHolder).mSeasonNumber.setText("Season " + data.getSeasonNumber());
        ((MyViewHolder) viewHolder).mRelease.setText("Release date: " + data.getAirDate());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + data.getPosterPath())
                .resize((int) unitConversionHelper.convertDpToPixel(130, view.getContext()),
                        (int) unitConversionHelper.convertDpToPixel(200, view.getContext()))
                .into(((MyViewHolder) viewHolder).mImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        setVisibility(viewHolder, View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }

    @Override
    public int getItemCount() {
        return seasonArrayList.size();
    }
}
