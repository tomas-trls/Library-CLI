package system.theQuietCorner.user;

import system.ColoursUtils;
import system.theQuietCorner.book.Book;
import system.theQuietCorner.library.Library;

import java.util.ArrayList;
import java.util.List;

import static system.theQuietCorner.user.UserUtils.*;

public abstract class User {
    private long id;
    private String name;

    private int age;
    private String email;

    private String password;

    private ArrayList<Book> loanedBooks = new ArrayList<>();

    public User(String name, int age, String email, String password) {
        this.id = generateUniqueId();
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract UserType getType();

    public ArrayList<Book> getLoanedBooks() {
        return loanedBooks;
    }


    public String getInformation(String option) {
        if (option.equals("return")) {
            return String.format("(#%d), %s, is %d years old, email: %s, is a/an %s",
                    this.id, ColoursUtils.green(this.name), this.age, this.email, getType());
        } else {
            System.out.printf("(#%d), %s, is %d years old, email: %s, is a/an %s",
                    this.id, ColoursUtils.green(this.name), this.age, this.email, getType());
            return "";
        }
    }

    public void loanBook(int id) throws Exception {
        for (Book book : Library.booksList) {
            if (book.getId() == id) {
                this.loanedBooks.add(book);
                Library.booksList.remove(book);
                Library.libraryLoanedBooks.put(book.getId(), this.id);
                System.out.println("Book is now yours");
                return;
            }
        }
        System.out.println("Book out of stock.");

    }

    public void returnBook(int id) throws Exception {
        for (Book book : loanedBooks) {
            if (book.getId() == id) {
                this.loanedBooks.remove(book);
                Library.booksList.add(book);
                Library.libraryLoanedBooks.remove(book.getId());
                System.out.println("Returned the Book");
                return;
            }
        }
        System.out.println("Wrong Book Id");

    }

    public void displayLoanedBooks() {
        System.out.println(this.name + " Personal Loaned Books: ");
        System.out.println("Books loaned: "+this.loanedBooks.size());
        System.out.println("=============================");
        for (Book book : this.loanedBooks) {
            book.getBookExtendedDetails();
        }
        System.out.println("=============================");
    }

}
