package com.example.makishere.booklisting;

public class Book {
    private String mTitle;
    private String mAuthors;
    private String mUrl;
    private String mPublishedDate;

    public Book(String title ,String authors,String url , String publishedDate){
        mTitle=title;
        mAuthors=authors;
        mUrl=url;
        mPublishedDate=publishedDate;
    }


    public String getAuthors() {
        return mAuthors;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl(){return mUrl;}

    public String getPublishedDate() {
        return mPublishedDate;
    }
}
