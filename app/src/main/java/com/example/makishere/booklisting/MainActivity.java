package com.example.makishere.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private static final String GOOGLE_API_URL="  https://www.googleapis.com/books/v1/volumes?q=android&maxResults=15";
    private static final int Book_Loader_ID=1;
    private static final String LOG_TAG =MainActivity.class.getName() ;
    private BookAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView bookListView = (ListView) findViewById(R.id.list);
       // mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        //BookListView.setEmptyView(mEmptyStateTextView);




        // Create a new adapter that takes an empty list of Books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current Book that was clicked on
                Book currentBook = (Book) mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri BookUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the Book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, BookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(Book_Loader_ID, null, this);

        }
        else{
           // View loadingIndicator = findViewById(R.id.loading_spinner);
            //loadingIndicator.setVisibility(View.GONE);
            //mEmptyStateTextView.setText(R.string.no_internet);
        }
    }
    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        Log.i(LOG_TAG, "TEST:Create Loader");
        return new BookLoader(this,GOOGLE_API_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Clear the adapter of previous earthquake data

     //   View progress= findViewById(R.id.loading_spinner);
       // progress.setVisibility(View.GONE);
        //mEmptyStateTextView.setText(R.string.no_earthquakes);
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
        Log.i(LOG_TAG, "TEST:Load finished");


    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
        Log.i(LOG_TAG, "TEST:Reset loader");

    }

}
