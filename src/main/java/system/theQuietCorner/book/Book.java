package system.theQuietCorner.book;

import system.ColoursUtils;
import static system.theQuietCorner.book.BookUtils.*;

public class Book {
    private long id;
    private String title;
    private String author;
    private String genre;
    private String subgenre;

    private String publisher;

    public Book(String title, String author, String genre, String subgenre, String publisher) {
        this.id = generateUniqueBookId();
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.subgenre = subgenre;
        this.publisher = publisher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(String subgenre) {
        this.subgenre = subgenre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void getBookInformation(){
        System.out.printf(" (#%d), %s written by %s is a %s and %s book. Published by: %s %n",
                this.id, ColoursUtils.brightYellow(this.title), this.author, this.genre, this.subgenre, this.publisher);
    }

}
