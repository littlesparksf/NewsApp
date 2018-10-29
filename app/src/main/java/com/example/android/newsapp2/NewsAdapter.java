package com.example.android.newsapp2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param newsItems A List of AndroidFlavor objects to display in a list
     */
    public NewsAdapter(Activity context, ArrayList<NewsItem> newsItems) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, newsItems);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Get the {@link NewsItem} object located at this position in the list
        NewsItem currentNewsItem = getItem(position);

        // Find the TextView for the article section
        TextView sectionTextView = listItemView.findViewById(R.id.news_item_section);
        String sectionName = currentNewsItem.getmSectionName();
        sectionTextView.setText(sectionName);

        // Find the TextView for the article title
        TextView titleTextView = listItemView.findViewById(R.id.news_item_title);
        String newsItemTitle = currentNewsItem.getmTitle();
        titleTextView.setText(newsItemTitle);

        // Find the TextView for the article date
        TextView dateTextView = listItemView.findViewById(R.id.news_item_date);
        String newsItemDate = currentNewsItem.getmDateAndTime();
        dateTextView.setText(newsItemDate);

        // Populate author field if news item has an author
        TextView authorTextView = listItemView.findViewById(R.id.news_item_author);

        if (currentNewsItem.hasAuthor()) {
            authorTextView.setText(currentNewsItem.getmAuthor());
            authorTextView.setVisibility(View.VISIBLE);
        } else {
            authorTextView.setVisibility(View.GONE);
        }

//        // Create a new Date object from the time in milliseconds of the earthquake
//        Date dateObject = new Date(currentNewsItem.getmDateAndTime());
//
//        // Find the TextView with the view id news_item _date
//        TextView dateView = listItemView.findViewById(R.id.news_item_date);
//
//        // Format the date string (i.e. "Mar 3, 1984")
//        String formattedDate = formatDate(dateObject);
//
//        // Display the date of the current news item in that TextView
//        dateView.setText(formattedDate);
//
//        // Find the TextView with the view id news_item_time
//        TextView timeView = (TextView) listItemView.findViewById(R.id.news_item_time);
//
//        // Format the time string (i.e. "4:30PM")
//        String formattedTime = formatTime(dateObject);
//
//        // Display the time of the current earthquake in that TextView
//        timeView.setText(formattedTime);


        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

//    /**
//     * Helper method - return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
//     */
//    private String formatDate(Date dateObject) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
//        return dateFormat.format(dateObject);
//    }
//
//    /**
//     * Helper method - return the formatted date string (i.e. "4:30 PM") from a Date object.
//     */
//    private String formatTime(Date dateObject) {
//        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
//        return timeFormat.format(dateObject);
//    }
}
