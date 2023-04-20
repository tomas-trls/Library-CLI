package system;

import system.theQuietCorner.book.Book;
import system.theQuietCorner.library.Library;
import system.theQuietCorner.user.Admin;
import system.theQuietCorner.user.Customer;
import system.theQuietCorner.user.User;

public class LibraryApplication {
    public static void main(String[] args) throws Exception {

        Library theQuietCorner = new Library();

        theQuietCorner.addUser(new Admin("Gerome",26,"gerome@gmail.com","password"));
        theQuietCorner.addUser(new Customer("John",43,"gerome@gmail.com","password"));
        theQuietCorner.addUser(new Admin("Tomas",22,"gerome@gmail.com","password"));
        theQuietCorner.addUser(new Customer("Annick",18,"gerome@gmail.com","password"));

        //theQuietCorner.addBook(new Book("Fundamentals of Wavelets","Goswami, Jaideva","tech","signal_processing","Wiley"));

        theQuietCorner.generateBooks();
        theQuietCorner.displayBooks();
    }
}