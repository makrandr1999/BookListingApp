package com.example.makishere.booklisting;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG =QueryUtils.class.getName() ;

    private  QueryUtils(){

    }

    private static List<Book> extractFeatureFromJson(String bookJson) {

        if(TextUtils.isEmpty(bookJson))
            return null;

        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJSONresponse = new JSONObject(bookJson);
            JSONArray bookArray = baseJSONresponse.getJSONArray("items");
            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject properties = currentBook.getJSONObject("volumeInfo");
                //double magnitude = properties.getDouble("mag");
                String title = properties.getString("title");
                String publishedDate = properties.getString("publishedDate");
                String url=properties.getString("infoLink");
                JSONArray authArray = properties.getJSONArray("authors");
                //String[] authors = new String[authArray.length()];
                String authors="";
                for(int j=0;j<authArray.length();j++){
                    authors = authors+" "+authArray.getString(j);
                    //authors= new StringBuilder().append(authors).append(" ").append(authArray[j]).toString();
                }
                // String date = properties.getString(("time"));
               // long time = properties.getLong("time");
                //System.out.println("Test"+title+authors+publishedDate);
              // Toast.makeText(context, authors+""+title, Toast.LENGTH_SHORT).show();


                Book book = new Book(title,authors,url,publishedDate);
                books.add(book);
                Log.i(LOG_TAG, "TEST:"+authors+url+publishedDate+title);


            }
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Test:Problem parsing the book JSON results", e);
        }

        // Return the list of books
        return books;
    }
    // **
    /* Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                Log.i(LOG_TAG, "TEST:200 OK");

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static List<Book> fetchBookData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse=makeHttpRequest(url);
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Book> books = extractFeatureFromJson(jsonResponse);
        Log.i(LOG_TAG, "TEST:FETCH DATA");
        // Return the list of {@link Earthquake}s
        return books;
    }
}


