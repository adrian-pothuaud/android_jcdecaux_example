package com.example.adrianpothuaud.YouTubeSearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adrianpothuaud.YouTubeSearch.R;
import com.example.adrianpothuaud.YouTubeSearch.interfaces.OnVideoSelectedListener;
import com.example.adrianpothuaud.YouTubeSearch.models.Results;
import com.example.adrianpothuaud.YouTubeSearch.viewholders.ResultsViewHolder;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsViewHolder> {

    private final Results ytresults;
    private OnVideoSelectedListener onVideoSelectedListener;
    private Context activityContext;

    public ResultsAdapter(Results ytresults, Context ctx) {
        this.ytresults = ytresults;
        this.activityContext = ctx;
    }

    @Override
    public ResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_ytresult, parent, false);
        return new ResultsViewHolder(view, activityContext);
    }

    @Override
    public void onBindViewHolder(ResultsViewHolder holder, int position) {
        holder.setOnVideoSelectedListener(onVideoSelectedListener);
        holder.bind(ytresults.getItems().get(position));
    }

    @Override
    public int getItemCount() {
        return ytresults != null ? ytresults.getItems().size() : 0;
    }

    public void setOnVideoSelectedListener(OnVideoSelectedListener onVideoSelectedListener) {
        this.onVideoSelectedListener = onVideoSelectedListener;
    }
}

