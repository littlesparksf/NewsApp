package com.example.android.newsapp2;

public class NewsItem {

    // Section name
    private String mSectionName;

    // Title of story
    private String mTitle;

    // Author of story
    private String mAuthor;

    // Date and time of publication in Guardian format
    private String mDateAndTime;

    // url of story on Guardian.uk
    private String mUrl;

    /*
     * Create a new AndroidFlavor object.
     *
     * @param title is the title of the news story
     * @param author is the author of the news story
     * @param excerpt is an excerpt (first 1-2 sentences) of the news story.
     * @param url is the url of the news story on the Guardian website.
     * */

    public NewsItem(String section, String title, String author, String dateAndTime, String url) {
        mSectionName = section;
        mTitle = title;
        mAuthor = author;
        mDateAndTime = dateAndTime;
        mUrl = url;
    }

    /**
     * Get the time of the earthquake
     */
    public String getmSectionName() {
        return mSectionName;
    }

    /**
     * Get the image resource ID
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * Get the author name
     */
    public String getmAuthor() { return mAuthor; }

    /**
     * Get the time of publication
     */
    public String getmDateAndTime() {
        return mDateAndTime;
    }

    /**
     * Get the earthquake website url
     */
    public String getmUrl() {
        return mUrl;
    }

    public boolean hasAuthor() {
        return mAuthor != "";
    }
}
