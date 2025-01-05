/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookbuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: Rosie
 * BookManager class that connects to GoogleBooks API
 * and searches for a specified book
 * Date: June 
 */

public class BookManager {

    private String author = null;
    private String description = null;
    private String title = null;
    private String imageURL = null;
    private static String response = null;

    //-----------------------------------------------------------------------------
    //Main method for testing
    public static void main(String[] args){
        //System.out.println(BookManager.findByISBN("9781423168232"));
        System.out.println(BookManager.findByTitle("yours truly"));
    }   
    
    //-----------------------------------------------------------------------------
    //Methods

    /**
     * Replaces the spaces in the title
     * to the Google format of URL's
     * @param title String of title
     * @return newly formatted String
     */
    public static String formatTitle(String title){
        title = title.trim().toLowerCase();
        //Google replaces spaces with "%20"
        title = title.replaceAll(" ", "%20");
        return title;
    }
    
    /**
     * Connect to the Google API using 
     * HttpURL and GoogleBooksAPI link
     * @param apiURLString
     * @return 
     */
    public static String makeConnection(String apiURLString) {
        //store information from API
        String responseString = null;
        //connect to GoogleBooks API
        try {
            HttpURLConnection connection = null;
            // build connection
            try {
                URL url = new URL(apiURLString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000); // 5 seconds
                connection.setConnectTimeout(5000); // 5 seconds
            } catch (MalformedURLException e) {
                // Impossible: The only two URLs used in the app are taken from string resources.
                e.printStackTrace();
            } catch (ProtocolException e) {
                // Impossible: "GET" is a perfectly valid request method.
                e.printStackTrace();
            }
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Unsuccessful GET Request");
            } else{
                // Read data from response.
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                //close reader
                reader.close();
                responseString = builder.toString();
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Connection timed out. Returning null");
        } catch (IOException e) {
            System.out.println("IOException when connecting to Google Books API.");
            e.printStackTrace();
        }
        return responseString;
    }
    
    /**
     * Connect to API using isbn# 
     * @param isbn value of the book
     * @return HashMap storing book's info
     */
    public static HashMap findByISBN(String isbn){
        String apiURLString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        return apiSearch(apiURLString);
    }
    
    /**
     * Connect to API with the book's title
     * @param title String
     * @return HashMap of book values
     */
    public static HashMap findByTitle(String title){
        //change connection string
        String apiURLString = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + formatTitle(title);
        return apiSearch(apiURLString);
    }

    /**
     * Search the API and return a HashMap of the book values
     * @param apiURLString
     * @return HashMap of book info
     */
    public static HashMap apiSearch(String apiURLString) {
        String responseString = makeConnection(apiURLString);
        if (responseString == null) {
            System.out.println("No response");
            return null;
        }
        JSONObject responseJson;
        HashMap h = new HashMap();
        //decode the responseString and retrieve useful information
        try {
            //store string in Json Object for easy traversing
            responseJson = new JSONObject(responseString);
            //take a portion of the Json String
            responseJson = responseJson.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
            //gets isbn array with isbn10 and isbn 13
            JSONArray isbns;
            isbns = responseJson.getJSONArray("industryIdentifiers");
            //get isbn10
            String isbn10 = isbns.getJSONObject(0).getString("identifier");
            System.out.println("isbn10: " + isbn10);
            //get isbn13
            String isbn13 = isbns.getJSONObject(1).getString("identifier");
            System.out.println("isbn13: " + isbn13);
            
            //book cover URL
            JSONObject images = responseJson.getJSONObject("imageLinks");
            
            //store author
            JSONArray authors = responseJson.getJSONArray("authors");
            System.out.println(authors.getString(0));

            //load into HashMap
            h.put("author", authors.getString(0));
            h.put("title", responseJson.getString("title"));
            h.put("imageURL", images.getString("thumbnail"));
            h.put("description", responseJson.getString("description"));
            h.put("isbn10", isbn10);
            h.put("isbn13", isbn13);
        } catch (JSONException ex) {
            System.out.println("Book not found.");
        }
        //return HashMap
        return h;
    }
}