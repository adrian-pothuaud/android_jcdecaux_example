package com.example.adrianpothuaud.YouTubeSearch.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.adrianpothuaud.YouTubeSearch.Constants;
import com.example.adrianpothuaud.YouTubeSearch.R;
import com.example.adrianpothuaud.YouTubeSearch.adapters.SearchQueriesAdapter;
import com.example.adrianpothuaud.YouTubeSearch.controler.searchQueries;
import com.example.adrianpothuaud.YouTubeSearch.interfaces.OnPreviousQuerySelectedListener;
import com.example.adrianpothuaud.YouTubeSearch.models.PrefData;

import java.util.List;

import static com.example.adrianpothuaud.YouTubeSearch.R.id.searchRecyclerView;

public class SearchActivity extends AppCompatActivity implements OnPreviousQuerySelectedListener{

    EditText searchTextField;
    Button searchButton, clearButton;
    String searchRqst;
    List<PrefData> previousQueries;
    List<PrefData> filteredPreviousQueries;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Layout elements instances
        searchTextField = (EditText) findViewById(R.id.searchTextField); // get text edit field
        searchTextField.setText(String.valueOf("")); // reset text input text

        searchButton = (Button) findViewById(R.id.searchButton); // get button
        clearButton = (Button) findViewById(R.id.clearButton);

        recyclerView = (RecyclerView) findViewById(searchRecyclerView);
        // Create new Linear Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        previousQueries = searchQueries.getPreviousQueries(getBaseContext());
        setAdapter(previousQueries);

        // Change listener on text field for search bar similar behavior
        searchTextField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // reload previous queries
                previousQueries = searchQueries.getPreviousQueries(getBaseContext());

                // filter previous queries
                filteredPreviousQueries = searchQueries.filterPreviousQueries(previousQueries, s.toString());

                // Set ListView
                setAdapter(filteredPreviousQueries);
            }
        });

        // Click listener on the button
        searchButton.setOnClickListener(new View.OnClickListener() { // on click on button do ...
            @Override
            public void onClick(View view) {

                searchRqst = searchTextField.getText().toString(); // get text input
                searchTextField.setText(String.valueOf("")); // reset text input text

                if (searchRqst.length() > 0) { // if input

                    searchQueries.saveSearchQuery(getBaseContext(), searchRqst); // save query into SharedPreferences

                    // intent to change current Activity -> go to ResultsActivity
                    Intent intent = new Intent(view.getContext(), ResultsActivity.class);
                    intent.putExtra(Constants.EXTRA_MESSAGE, searchRqst); // search request as an EXTRA for ResultsActivity
                    startActivity(intent); // start ResultsActivity

                } else { // if no input
                    Log.w("warning", "Search text field is empty while search button is clicked ! Nothing will happen ...");
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clean sharedPReferences
                searchQueries.clearPrefs(getBaseContext());
                previousQueries = searchQueries.getPreviousQueries(getBaseContext());
                setAdapter(previousQueries);
            }
        });
    }

    private void setAdapter(List<PrefData> filteredPreviousQueries) {
        SearchQueriesAdapter adapter = new SearchQueriesAdapter(filteredPreviousQueries);
        adapter.setOnPreviousQuerySelectedListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        recyclerView = (RecyclerView) findViewById(searchRecyclerView);
        // Create new Linear Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        previousQueries = searchQueries.getPreviousQueries(getBaseContext());
        setAdapter(previousQueries);
    }

    @Override
    public void onQuerySelected(PrefData data) {
        // Start Results Activity with selected previous query
        searchTextField = (EditText) findViewById(R.id.searchTextField); // get text edit field
        searchTextField.setText(String.valueOf("")); // reset text input text

        searchQueries.saveSearchQuery(getBaseContext(), data.getQuery());

        // intent to change current Activity -> go to ResultsActivity
        Intent intent = new Intent(getBaseContext(), ResultsActivity.class);
        intent.putExtra(Constants.EXTRA_MESSAGE, data.getQuery()); // search request as an EXTRA for ResultsActivity
        startActivity(intent); // start ResultsActivity
    }
}
