package system.theQuietCorner.library;

import com.fasterxml.jackson.databind.util.JSONPObject;
import system.theQuietCorner.book.Book;
import system.theQuietCorner.user.Customer;
import system.theQuietCorner.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
import system.theQuietCorner.user.UserType;

public class Library {
    public static final ArrayList<User> usersList = new ArrayList<User>();
    public static final ArrayList<Book> booksList = new ArrayList<Book>();



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
        System.out.println("Users List: ");
        for (User user : usersList) {
            System.out.println(user.getInformation());
        };
    }

    public int getAdminCount(){
        System.out.println("Admins in System: ");
        int adminCount = (int) usersList.stream().filter(user -> user.getType() == UserType.admin).count();
        System.out.println(adminCount);
        return adminCount;
    }

    public int getCustomerCount(){
        System.out.println("Customers in System: ");
        int adminCount = (int) usersList.stream().filter(user -> user.getType() == UserType.customer).count();
        System.out.println(adminCount);
        return adminCount;
    }

    public void listAdmins(){
        System.out.println("Admins List: ");
        usersList.stream().filter(user -> user.getType() == UserType.admin)
                .forEach(user -> System.out.println(user.getInformation()));
    }
    public void listCustomers(){System.out.println("Customers List");
       usersList.stream().filter(user -> user.getType() == UserType.customer).forEach(user -> System.out.println(user.getInformation()));
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
        System.out.println("Books in system: ");
        return booksList.size();
    }

    public void displayBooks(){
        System.out.println("Books Library");
        for (Book book : booksList){
            book.getBookInformation();
        }
    }

    public void exportToJson() throws Exception {
        CsvMapper mapper = new CsvMapper();
        File output = new File(System.getProperty("user.dir")+"/src/main/java/system/theQuietCorner/library/newBooksData.csv");
        CsvSchema schema = mapper.schemaFor(Book.class).sortedBy("id", "title", "author", "genre", "subgenre", "publisher").withHeader();
        mapper.writer(schema).writeValue(output, booksList);
    }

}
