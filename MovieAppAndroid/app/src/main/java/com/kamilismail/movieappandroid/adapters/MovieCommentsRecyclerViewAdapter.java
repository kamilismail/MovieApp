package com.kamilismail.movieappandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kamilismail.movieappandroid.DTO.search_movies.MovieCommentsDTO;
import com.kamilismail.movieappandroid.R;

import java.util.ArrayList;

public class MovieCommentsRecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<MovieCommentsDTO> commentsDTOArrayList;
    private RecyclerView mRecyclerView;
    private View view;

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mUsername;
        public TextView mComment;

        public MyViewHolder(View v) {
            super(v);
            mUsername = v.findViewById(R.id.username);
            mComment = v.findViewById(R.id.comment);
        }
    }

    // konstruktor adaptera
    public MovieCommentsRecyclerViewAdapter(ArrayList<MovieCommentsDTO> commentsDTOArrayList, RecyclerView _recyclerView, View view) {
        this.commentsDTOArrayList = commentsDTOArrayList;
        this.mRecyclerView = _recyclerView;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comments_list, viewGroup, false);

        //przejscie do widoku z detalami
        //TODO
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // odnajdujemy indeks klikniętego elementu
                int position = mRecyclerView.getChildAdapterPosition(v);
                MovieCommentsDTO data = commentsDTOArrayList.get(position);
            }
        });
        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    private void setVisibility(RecyclerView.ViewHolder viewHolder, int visibility) {
        ((MyViewHolder) viewHolder).mUsername.setVisibility(visibility);
        ((MyViewHolder) viewHolder).mComment.setVisibility(visibility);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        setVisibility(viewHolder, View.GONE);
        MovieCommentsDTO data = commentsDTOArrayList.get(i);
        ((MyViewHolder) viewHolder).mUsername.setText(data.getUserName());
        ((MyViewHolder) viewHolder).mUsername.setSelected(true);
        String comment;
        if (data.getComment().length() > 270) {
            comment = data.getComment().substring(0, 270) + "...";
        } else comment = data.getComment();
        ((MyViewHolder) viewHolder).mComment.setText("\"" + comment + "\"");
        setVisibility(viewHolder, View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return commentsDTOArrayList.size();
    }
}
