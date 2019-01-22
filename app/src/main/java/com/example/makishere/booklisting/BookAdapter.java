package com.example.makishere.booklisting;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class BookAdapter extends ArrayAdapter {
    private static final String LOG_TAG = ArrayAdapter.class.getName();

    public BookAdapter(Context context, List<Book> books) {
        super(context,0,books);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //private static final String LOCATION_SEPARATOR = " of ";
        View listitemView = convertView;
        if(listitemView==null){
            listitemView=LayoutInflater.from(getContext()).inflate(R.layout.book_list_item,parent,false);
        }
        Log.i(LOG_TAG, "TEST:Adapter Running");
        try {


            Book currentBook = (Book) getItem(position);
            String author = currentBook.getAuthors();
            String title = currentBook.getTitle();
            String url = currentBook.getUrl();
            String date=currentBook.getPublishedDate();
           // Date dateobject = new Date(currentBook.getPublishedDate());
            TextView authorView = (TextView) listitemView.findViewById(R.id.author);
            authorView.setText(author);
            TextView titleView = (TextView) listitemView.findViewById(R.id.title);
            titleView.setText(title);
            //TextView urlView = (TextView) listitemView.findViewById(R.id.)
//

            TextView dateView = (TextView) listitemView.findViewById(R.id.date);
            dateView.setText(date);
        }
        catch (Exception e){
            Log.e("BookAdapter", "Test:Problem displaying the book JSON results", e);
        }
       // Log.i(LOG_TAG, "TEST:Adapter Running");
        return listitemView;
    }
}
