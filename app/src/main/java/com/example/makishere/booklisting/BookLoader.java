package com.example.makishere.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
//import android.support.v4.content.AsyncTaskLoader;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private static final String LOG_TAG=BookLoader.class.getName();

    private String murl;

    public BookLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        murl=url;


    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST:Start LOADING");
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        // TODO: Implement this method
        Log.i(LOG_TAG, "TEST:LOAD IN BACKGROUND");
        if(murl==null)
            return null;
        List<Book> books= QueryUtils.fetchBookData(murl);
        Log.i(LOG_TAG, "TEST:LOAD IN BACKGROUND");

        return books;
    }
}
