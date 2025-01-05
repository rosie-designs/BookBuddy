package bookbuddy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Author: Rosie and Anita.
 * Book class that instantiates the book object.
 * Date: June 6, 2024
 */
public class Book {

    //private variables for book object
    private ArrayList<Rating> ratings = new ArrayList<Rating>();
    private String author;
    private String isbn10;
    private String isbn13;
    private BufferedImage image;
    private String description;
    private String title;

    //-----------------------------------------------------------------------------
    //Constructor
    /**
     * Rosie
     * Takes in a Hashmap of book values and sets the book vars accordingly
     */
    public Book(HashMap h) {
        title = String.valueOf(h.get("title"));
        isbn10 = String.valueOf(h.get("isbn10"));
        isbn13 = String.valueOf(h.get("isbn13"));
        author = String.valueOf(h.get("author"));
        description = String.valueOf(h.get("description"));
        //load image from method
        image = findImage(String.valueOf(h.get("imageURL")));
    }

    //-----------------------------------------------------------------------------
    //Main method
    /**
     * for testing purposes
     */
    public static void main(String args[]) {
        //test book instantiation
        Book book = new Book(BookManager.findByISBN("9780063250833"));
        System.out.println(book);
    }

    //-----------------------------------------------------------------------------
    //Methods
    
    /**
     * Rosie
     * Loads image from image URL
     * @param imageURL link given by GoogleBookAPI
     * @return BufferedImage object
     */
    public BufferedImage findImage(String imageURL) {
        BufferedImage image = null;
        //read image and set it to image var
        try {
            image = ImageIO.read(new URL(imageURL));
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL");;
        } catch (IOException ex) {
            System.out.println("Error reading the image");
        }
        //return bufferedImage
        return image;
    }

    /**
     * load saved ratings from file into arrayList for easy access
     * anita
     */
    public void loadRatings() {
        try {
            Scanner sc = new Scanner(new File("savedRatings.txt"));
            while(sc.hasNextLine()) {
                if(sc.nextLine().equals(this.getTitle())) {
                    int num = sc.nextInt();
                    int grade = sc.nextInt();
                    int star = sc.nextInt();
                    String comments = sc.nextLine();
                    Rating r = new Rating(num, grade, star, comments);
                    ratings.add(r);
                    System.out.println("999");
                }
            }
            System.out.println("88");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }

    /**
     *
     * @return the arrayList of ratings
     */
    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the isbn10
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     * @return the isbn13
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
}
