package com.example.adrianpothuaud.YouTubeSearch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adrianpothuaud.YouTubeSearch.R;
import com.example.adrianpothuaud.YouTubeSearch.interfaces.OnPreviousQuerySelectedListener;
import com.example.adrianpothuaud.YouTubeSearch.models.PrefData;
import com.example.adrianpothuaud.YouTubeSearch.viewholders.SearchQueryViewHolder;

import java.util.List;

public class SearchQueriesAdapter extends RecyclerView.Adapter<SearchQueryViewHolder> {

    private final List<PrefData> dataList;
    private OnPreviousQuerySelectedListener onPreviousQuerySelectedListener;

    public SearchQueriesAdapter(List<PrefData> dataList) {this.dataList = dataList;}

    @Override
    public SearchQueryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_previous_search, parent, false);
        return new SearchQueryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchQueryViewHolder holder, int position) {
        holder.setOnPreviousQuerySelectedListener(onPreviousQuerySelectedListener);
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnPreviousQuerySelectedListener (OnPreviousQuerySelectedListener onPreviousQuerySelectedListener) {
        this.onPreviousQuerySelectedListener = onPreviousQuerySelectedListener;
    }
}
