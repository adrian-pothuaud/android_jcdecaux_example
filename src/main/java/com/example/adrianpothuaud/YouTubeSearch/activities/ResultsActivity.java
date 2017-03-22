package com.example.adrianpothuaud.YouTubeSearch.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adrianpothuaud.YouTubeSearch.Constants;
import com.example.adrianpothuaud.YouTubeSearch.R;
import com.example.adrianpothuaud.YouTubeSearch.adapters.ResultsAdapter;
import com.example.adrianpothuaud.YouTubeSearch.interfaces.OnVideoSelectedListener;
import com.example.adrianpothuaud.YouTubeSearch.models.Results;
import com.google.gson.Gson;

import static com.example.adrianpothuaud.YouTubeSearch.Constants.EXTRA_CHANNEL;
import static com.example.adrianpothuaud.YouTubeSearch.Constants.EXTRA_DESC;
import static com.example.adrianpothuaud.YouTubeSearch.Constants.EXTRA_TITLE;
import static com.example.adrianpothuaud.YouTubeSearch.Constants.EXTRA_VIDEOID;
import static com.example.adrianpothuaud.YouTubeSearch.Constants.YOUTUBE_URL;

public class ResultsActivity extends AppCompatActivity implements OnVideoSelectedListener{

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String searchRqst = intent.getStringExtra(Constants.EXTRA_MESSAGE);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getYTResults(searchRqst);
    }

    private void getYTResults(String searchRqst) {

        // if multi word query, replace ' ' by '+'
        searchRqst = searchRqst.replace(' ', '+');

        StringRequest contractsRequest = new StringRequest(YOUTUBE_URL + "&q=" + searchRqst, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.isEmpty()) {
                    System.out.println("Empty response from string request ... ");
                    Log.e("Response", "Empty response");
                }
                else {
                    // parse data from webservice to get Results as Java object
                    Results ytresults = new Gson().fromJson(response, Results.class);

                    if (ytresults.getItems().isEmpty()) {
                        Log.e("Response", "Empty items");
                        // popup message then go back to Search Activity
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.recyclerView), "Empty search result ...", Snackbar.LENGTH_INDEFINITE)
                                .setAction("BACK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(view.getContext(), SearchActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        snackbar.show();
                    }
                    else {
                        // build adapter view from the parsed object containing the results
                        setAdapter(ytresults);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Contracts", "Error");
            }
        });

        Volley.newRequestQueue(this).add(contractsRequest);
    }

    private void setAdapter(Results ytresults) {
        ResultsAdapter adapter = new ResultsAdapter(ytresults, getBaseContext());
        adapter.setOnVideoSelectedListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onVideoSelected(Results.Item item) {
        // Starts Player Activity with result data
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(EXTRA_VIDEOID, item.getVideoId());
        intent.putExtra(EXTRA_TITLE, item.getSnippet().getTitle());
        intent.putExtra(EXTRA_DESC, item.getSnippet().getDescription());
        intent.putExtra(EXTRA_CHANNEL, item.getSnippet().getChannelTitle());
        startActivity(intent);
    }
}
