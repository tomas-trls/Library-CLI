package system.theQuietCorner.library;

import system.ColoursUtils;
import system.theQuietCorner.book.*;
import system.theQuietCorner.user.Admin;
import system.theQuietCorner.user.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
import system.theQuietCorner.user.UserType;

public class Library {
    public static final ArrayList<User> usersList = new ArrayList<User>();
    public static final ArrayList<Book> booksList = new ArrayList<Book>();

    public static final HashMap<Long, Long> libraryLoanedBooks = new HashMap<>();

    public Library() {
        System.out.println("Opening Library");
    }

    public void addUser(User user) {
        usersList.add(user);
    }

    public int getUserCount() {
        return usersList.size();
    }

    public void listUsers() {
        System.out.println("Users List: ");
        for (User user : usersList) {
            System.out.println(user.getInformation("return"));
        }
        ;
    }

    public int getAdminCount() {
        System.out.println("Admins in System: ");
        int adminCount = (int) usersList.stream().filter(user -> user.getType() == UserType.admin).count();
        System.out.println(adminCount);
        return adminCount;
    }

    public int getCustomerCount() {
        System.out.println("Customers in System: ");
        int adminCount = (int) usersList.stream().filter(user -> user.getType() == UserType.customer).count();
        System.out.println(adminCount);
        return adminCount;
    }

    public void listAdmins() {
        System.out.println("Admins List: ");
        usersList.stream().filter(user -> user.getType() == UserType.admin)
                .forEach(user -> user.getInformation(""));
    }

    public void listCustomers() {
        System.out.println("Customers List");
        usersList.stream().filter(user -> user.getType() == UserType.customer).forEach(user -> user.getInformation(""));
    }

    public void addBook(Book book) {
        booksList.add(book);
    }

    public void generateBooks() {
        File input = new File(System.getProperty("user.dir") + "/src/main/java/system/theQuietCorner/library/books_data.csv");
        try {
            CsvSchema csv = CsvSchema.emptySchema().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv).readValues(input);
            List<Map<?, ?>> list = mappingIterator.readAll();
            for (Map<?, ?> book : list) {
                booksList.add(new Book(book.get("Title").toString(), book.get("Author").toString(), book.get("Genre").toString(), book.get("SubGenre").toString(), book.get("Publisher").toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Book> fullBookLibraryArr = new ArrayList<>();

    public void copyLibraryArr() {
        fullBookLibraryArr.addAll(booksList);
    }


    public int getBooksCount() {
        return booksList.size();
    }

    public void displayAllBooks() {
        for (Book book : booksList) {
            System.out.println(book.getBookInformation("return"));
        }
    }

    public void displayLimitedBooks(int limit) {
        System.out.println("\t\t\t\t 🌟Books in Library 🌟\n" +
                "Amount of books in Library: " + this.getBooksCount());
        for (int i = 0; i < limit; i++) {
            System.out.println(booksList.get(i).getBookInformation("return"));
        }
    }

    public void displayLimitedBooksExtendedDetails(int limit) {
        System.out.println("\t\t\t\t 🌟Books in Library 🌟\n" +
                "Amount of books in Library: " + this.getBooksCount());
        for (int i = 0; i < limit; i++) {
            booksList.get(i).getBookExtendedDetails();
        }
    }

    public void exportLibraryToCSV(ArrayList<Book> booksArr, String fileName) throws Exception {
        CsvMapper mapper = new CsvMapper();
        File output = new File(System.getProperty("user.dir") + "/src/main/java/system/theQuietCorner/library/" + fileName);
        CsvSchema schema = mapper.schemaFor(Book.class).sortedBy("id", "title", "author", "genre", "subgenre", "publisher").withHeader();
        mapper.writer(schema).writeValue(output, booksArr);
    }

    public static void exportLoanToCsv(ArrayList<Object[]> booksArr, String fileName) throws Exception {
        CsvMapper mapper = new CsvMapper();
        CsvSchema.Builder schemaBuilder = CsvSchema.builder();
        // Add book columns to the schema
        schemaBuilder.addColumn("id")
                .addColumn("title")
                .addColumn("author")
                .addColumn("genre")
                .addColumn("subgenre")
                .addColumn("publisher");
        // Add user columns to the schema
        schemaBuilder.addColumn("userId")
                .addColumn("userName")
                .addColumn("userAge")
                .addColumn("userEmail");
        // Set the schema header
        schemaBuilder.setUseHeader(true);
        CsvSchema schema = schemaBuilder.build().sortedBy("id");
        ArrayList<Object[]> combinedArr = new ArrayList<>();
        for (Object[] objArr : booksArr) {
            Book book = (Book) objArr[0];
            User user = (User) objArr[1];
            Object[] combinedObjArr = new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getSubgenre(),
                    book.getPublisher(),
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getEmail()
            };
            combinedArr.add(combinedObjArr);
        }
        File output = new File(System.getProperty("user.dir") + "/src/main/java/system/theQuietCorner/library/" + fileName);
        FileWriter writer = new FileWriter(output);
        writer.write("");
        writer.close();
        mapper.writer(schema).writeValue(output, combinedArr);
    }


    public void searchBook(BookFilterType filter) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your search query:");
        String query = scanner.nextLine();
        for (Book book : booksList) {
            if (filter == BookFilterType.id) {
                if (book.getId() == Integer.parseInt(query)) {
                    book.getBookExtendedDetails();
                }
            } else if (filter == BookFilterType.title) {
                if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    book.getBookExtendedDetails();
                }
            } else if (filter == BookFilterType.author) {
                if (book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                    book.getBookExtendedDetails();
                }
            } else if (filter == BookFilterType.genre) {
                if (book.getGenre().toLowerCase().contains(query.toLowerCase()) || book.getSubgenre().toLowerCase().contains(query.toLowerCase())) {
                    book.getBookExtendedDetails();
                }
            } else if (filter == BookFilterType.publisher) {
                if (book.getPublisher().toLowerCase().contains(query.toLowerCase())) {
                    book.getBookExtendedDetails();
                }
            } else {
                System.out.println("No results containing all your search terms were found.");
            }
        }
    }

    public void sortBookBy(BookFilterType sortFilter, int limit) {
        switch (sortFilter) {
            case title:
                booksList.sort(new SortBooksByTitle());
                break;
            case author:
                booksList.sort(new SortBooksByAuthor());
                break;
            case genre:
                booksList.sort(new SortBooksByGenre());
                break;
            case publisher:
                booksList.sort(new SortBooksByPublisher());
                break;
            default:
                Collections.sort(booksList);
        }
        displayLimitedBooksExtendedDetails(limit);
    }


    public static ArrayList<Object[]> libraryLoanedBooksArr = new ArrayList<>();

    public void displayLibraryLoanedBook() {
        libraryLoanedBooksArr.clear();
        libraryLoanedBooks.forEach((key, value) -> {
            Book bookInfo = null;
            User userInfo = null;
            for (Book book : fullBookLibraryArr) {
                if (book.getId() == key) {
                    bookInfo = book;
                }
            }
            for (User user : usersList) {
                if (user.getId() == value) {
                    userInfo = user;
                }
            }
            libraryLoanedBooksArr.add(new Object[]{bookInfo, userInfo});
        });
        StringBuilder loanedBooksInfo = new StringBuilder();

        for (Object[] objects : libraryLoanedBooksArr) {
                User user = (User) objects[1];
                Book book = (Book) objects[0];
                loanedBooksInfo.append(book.getBookInformation("return"))
                        .append(", Loaned by: ")
                        .append(user.getInformation("return"))
                        .append(", ")
                        .append(System.lineSeparator());
        }
        System.out.println(loanedBooksInfo);
    }
}
