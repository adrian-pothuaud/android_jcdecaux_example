package com.example.adrianpothuaud.YouTubeSearch.controler;

import android.util.Log;

import com.example.adrianpothuaud.YouTubeSearch.Constants;
import com.example.adrianpothuaud.YouTubeSearch.models.PrefData;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class utils {
    /*
     */
    static String getCurrentTimeStamp() {
        String curDateAndTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.FRANCE).format(new Date());
        Log.d(Constants.DEBUG_TAG, "getting current date and time : " + curDateAndTime);
        return curDateAndTime;
    }

    public static List<PrefData> sortByLastUsedDate(List<PrefData> queries) {
        Collections.sort(queries, new Comparator<PrefData>() {
            @Override
            public int compare(PrefData data1, PrefData data2)
            {
                return  data2.dateAdded.compareTo(data1.dateAdded);
            }
        });
        return queries;
    }
}
