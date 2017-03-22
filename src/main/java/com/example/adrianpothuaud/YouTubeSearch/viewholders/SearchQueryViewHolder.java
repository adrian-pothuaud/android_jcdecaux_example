package com.example.adrianpothuaud.YouTubeSearch.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.adrianpothuaud.YouTubeSearch.R;
import com.example.adrianpothuaud.YouTubeSearch.interfaces.OnPreviousQuerySelectedListener;
import com.example.adrianpothuaud.YouTubeSearch.models.PrefData;

public class SearchQueryViewHolder extends RecyclerView.ViewHolder {

    private TextView query;
    private TextView date;
    private OnPreviousQuerySelectedListener onPreviousQuerySelectedListener;

    public SearchQueryViewHolder(View view) {
        super(view);
        query = (TextView) view.findViewById(R.id.previous_search_query);
        date = (TextView) view.findViewById(R.id.previous_search_date);
    }

    public void bind(final PrefData data) {
        query.setText(data.getQuery());
        date.setText(data.getLastUsed());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPreviousQuerySelectedListener == null) {
                    return;
                }
                onPreviousQuerySelectedListener.onQuerySelected(data);
            }
        });
    }

    public void setOnPreviousQuerySelectedListener(OnPreviousQuerySelectedListener onPreviousQuerySelectedListener) {
        this.onPreviousQuerySelectedListener = onPreviousQuerySelectedListener;
    }
}
