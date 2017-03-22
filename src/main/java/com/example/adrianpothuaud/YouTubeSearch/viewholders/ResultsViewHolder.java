package com.example.adrianpothuaud.YouTubeSearch.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrianpothuaud.YouTubeSearch.R;
import com.example.adrianpothuaud.YouTubeSearch.controler.LoadImageTask;
import com.example.adrianpothuaud.YouTubeSearch.interfaces.OnVideoSelectedListener;
import com.example.adrianpothuaud.YouTubeSearch.models.Results;

public class ResultsViewHolder extends RecyclerView.ViewHolder implements  LoadImageTask.Listener {

    private TextView title;
    private TextView description;
    private ImageView imageView;
    private Context context;
    private OnVideoSelectedListener onVideoSelectedListener;

    public ResultsViewHolder(View itemView, Context ctx) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.videoTitle);
        description = (TextView) itemView.findViewById(R.id.videoDescription);
        imageView = (ImageView) itemView.findViewById(R.id.videoThumbnail);
        context = ctx;
    }

    public void bind(final Results.Item result) {
        title.setText(result.getSnippet().getTitle());
        description.setText(result.getSnippet().getDescription());

        new LoadImageTask(this).execute(result.getSnippet().getMediumThumbUrl());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onVideoSelectedListener == null) {
                    return;
                }
                onVideoSelectedListener.onVideoSelected(result);
            }
        });
    }

    public void setOnVideoSelectedListener(OnVideoSelectedListener onVideoSelectedListener) {
        this.onVideoSelectedListener = onVideoSelectedListener;
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        Toast.makeText(context, "Error Loading Image !", Toast.LENGTH_SHORT).show();
    }
}
