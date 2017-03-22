package com.example.adrianpothuaud.YouTubeSearch.controler;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.adrianpothuaud.YouTubeSearch.Constants;
import com.example.adrianpothuaud.YouTubeSearch.models.PrefData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class searchQueries {
    /*
    */
    public static List<PrefData> getPreviousQueries(Context context) {
        Map<String, ?> values = context.getSharedPreferences(Constants.SHARED_PREFS_FILENAME, MODE_PRIVATE).getAll();
        List<PrefData> prefDataList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : values.entrySet()) {
            PrefData prefData = new PrefData();
            prefData.query = entry.getKey();
            prefData.dateAdded = entry.getValue().toString();
            prefData.lastUsed = prefData.dateAdded;
            prefDataList.add(prefData);
        }
        return utils.sortByLastUsedDate(prefDataList);
    }

    /*
     *
     */
    public static List<PrefData> filterPreviousQueries(List<PrefData> previousQueries, String query) {
        ArrayList<PrefData> copyOfPreviousQueries = new ArrayList<>();
        int i = 0;
        for (PrefData data : previousQueries) {
            if (data.getQuery().toLowerCase().contains(query.toLowerCase())) {
                copyOfPreviousQueries.add(data);
            }
            i = i + 1;
        }
        return copyOfPreviousQueries;
    }

    /*
    * Save a search query into SharedPreferences
    */
    public static void saveSearchQuery(Context context, String query) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS_FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (PrefData data : getPreviousQueries(context)) {
            if(data.getQuery().equals(query)) {
                // update previous date and time
                editor.remove(data.getQuery());
                editor.putString(query, utils.getCurrentTimeStamp());
                editor.apply();
                return;
            }
        }

        editor.putString(query, utils.getCurrentTimeStamp());
        editor.apply();
    }

    public static void clearPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS_FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
