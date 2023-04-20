package system.theQuietCorner.library;

import system.theQuietCorner.book.Book;
import system.theQuietCorner.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;

public class Library {
    private ArrayList<User> usersList = new ArrayList<>();
    private ArrayList<Book> booksList = new ArrayList<Book>();



    public Library() {
        System.out.println("Opening Library");
    }

    public void addUser(User user){
        usersList.add(user);
    }

    public int getUserCount(){
        return usersList.size();
    }

    public void listUsers(){
        for (User user : usersList) {
            System.out.println(user.getInformation());
        };
    }

    public void addBook(Book book){
        booksList.add(book);
    }

    public void generateBooks() {
        File input = new File(System.getProperty("user.dir")+"/src/main/java/system/theQuietCorner/library/books_data.csv");
        try {
            CsvSchema csv = CsvSchema.emptySchema().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
            List<Map<?, ?>> list = mappingIterator.readAll();
            for (Map<?,?> book : list) {
                booksList.add(new Book(book.get("Title").toString(), book.get("Author").toString(), book.get("Genre").toString(), book.get("SubGenre").toString(), book.get("Publisher").toString()));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getBooksCount(){
        return booksList.size();
    }

    public void displayBooks(){
        for (Book book : booksList){
            System.out.println(book.getBookInformation());
        }
    }

}
