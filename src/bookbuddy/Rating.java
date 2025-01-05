/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookbuddy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * ratings class 
 * @author anita
 */
public class Rating {
    
    private static final String FILENAME = "savedRatings.txt";
    File f = new File("savedRatings.txt");
    File temp = new File("tempFile.txt");
    
    
    
    //feilds
    private int studentNum;
    private int studentGrade;
    private int starRating;
    private String comments;
    private String bookTitle;
    private Book book;
    
    //constructors
    public Rating(int studentNum, int studentGrade, int starRating, String comments, String bookTitle) {
        this.studentNum = studentNum;
        this.studentGrade = studentGrade;
        this.starRating = starRating;
        this.comments = comments;
        this.bookTitle = bookTitle;
        
        
        try {
            //PrintWriter pw = new PrintWriter(f);
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            pw.println(bookTitle);
            pw.println(studentNum + " " + studentGrade + " " + starRating + " " + comments);
            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException ex) {
            System.out.println("An error occured when writing to the file savedRatings.txt");
        }
        
    }
    
    public Rating(int studentNum, int studentGrade, int starRating, String comments) {
        this.studentNum = studentNum;
        this.studentGrade = studentGrade;
        this.starRating = starRating;
        this.comments = comments;
    }
    
    public static void main (String[] args){
        Rating r = new Rating(3, 11, 3, "hello", "the naturals");
    }
    
    //methods 

    /**
     * Method used for the delete button on ratings
     * @param rating to be retrieved from GUI 
     */
    public void deleteRating(Rating rating) throws FileNotFoundException, IOException {
        
        int num = rating.getStudentNum();
        int grade = rating.getStudentGrade();
        Book bookRated = rating.getBook();
        int stars = rating.getStarRating();
        String comment = rating.getComments();
        
        BufferedReader reader = new BufferedReader(new FileReader(f));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
        
        String lineToDelete = bookRated + " " + num + " " + grade + " " + stars + " " + comment;
        String currentLine;
        
        while((currentLine = reader.readLine()) != null) {
            //trim the new line when comparing with the line to remove 
            String trimmed = currentLine.trim();
            if(trimmed.equals(lineToDelete)) {
                continue;
            } writer.write(currentLine + System.getProperty("line.seperator"));
        }
        writer.close();
        reader.close();
        boolean success = temp.renameTo(f);
    }

    //getters and setters 
    public int getStudentNum() {
        return studentNum;
    }

    public int getStudentGrade() {
        return studentGrade;
    }

    public int getStarRating() {
        return starRating;
    }

    public String getComments() {
        return comments;
    }

    public Book getBook() {
        return book;
    }

    public String getBookTitle() {
        return bookTitle;
    }
    

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public void setStudentGrade(int studentGrade) {
        this.studentGrade = studentGrade;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
}
