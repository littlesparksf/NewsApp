package com.example.android.newsapp2;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    // implements LoaderManager.LoaderCallbacks<List<com.example.android.newsapp2.NewsItem>>

    private static final int NEWS_ITEM_LOADER_ID = 1;

    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * The request url that we are using
     */
    private String GUARDIAN_REQUEST_URL;

    /**
     * Adapter for list of news articles
     */
    private NewsAdapter mAdapter;

    /**
     * Empty TextView in case data does not load.
     */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView newsItemListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsItemListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of news items as input
        mAdapter = new NewsAdapter(this, new ArrayList<com.example.android.newsapp2.NewsItem>());

        // Set the adapter on the {@link ListView}
        // so that the list can be populated in the user interface
        newsItemListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected newws item.
        newsItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current news item that was clicked on
                com.example.android.newsapp2.NewsItem currentNewsItem = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNewsItem.getmUrl());

                // Create a new intent to view the news item URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check the state of network connectivity.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_ITEM_LOADER_ID, null, this);
            Log.e(LOG_TAG, "Loader has been initiated.");
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            //Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int i, Bundle args) {
        // Create a new loader for the request URL
        // Use UriBuilder to create the following url:
        // "https://content.guardianapis.com/search?api-key=6c1435b3-a131-42c4-a6f9-0cd4eb5fe70c&show-tags=contributor"
        Log.e(LOG_TAG, "Loader has been created.");
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("content.guardianapis.com")
                    .appendPath("search")
                    .appendQueryParameter("api-key", "6c1435b3-a131-42c4-a6f9-0cd4eb5fe70c")
                    .appendQueryParameter("show-tags", "contributor");
            GUARDIAN_REQUEST_URL = builder.build().toString();
        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<com.example.android.newsapp2.NewsItem>> loader, List<com.example.android.newsapp2.NewsItem> newsItemList) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText("News items have not been loaded - please sit tight!");

        // Clear the adapter of previous news item data
        mAdapter.clear();
        Log.e(LOG_TAG, "Loading has been finished.");

        // If there is a valid list of {@link News Item}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsItemList != null && !newsItemList.isEmpty()) {
            mAdapter.addAll(newsItemList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<com.example.android.newsapp2.NewsItem>> loader) {
        ///Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.e(LOG_TAG, "Loader has been reset.");
    }
}
