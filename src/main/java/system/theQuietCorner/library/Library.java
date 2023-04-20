package system.theQuietCorner.library;

import system.theQuietCorner.book.Book;
import system.theQuietCorner.user.User;
import java.util.ArrayList;


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

    public int getBooksCount(){
        return booksList.size();
    }

    public void displayBooks(){
        for (Book book : booksList){
            System.out.println(book.getBookInformation());
        }
    }

}
