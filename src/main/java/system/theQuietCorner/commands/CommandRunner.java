package system.theQuietCorner.commands;

import system.theQuietCorner.book.Book;
import system.theQuietCorner.book.BookFilterType;
import system.theQuietCorner.book.BookUtils;
import system.theQuietCorner.library.Library;
import system.theQuietCorner.user.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class CommandRunner {
    private final Scanner scanner = new Scanner(System.in);
    public Library theQuietCorner = new Library();

    public void buildLibrary() throws Exception {
        theQuietCorner.generateBooks();
        theQuietCorner.addUser(new Admin("admin", 99, "admin@admin.com", "admin"));
        theQuietCorner.addUser(new Customer("customer", 50, "customer@customer.com", "customer"));
        theQuietCorner.copyLibraryArr();
        start();
    }

    public void start() throws Exception {
        System.out.println("Please choose a valid option :");
        System.out.println("1: Log In 🌟\t2: Sign Up 💫\t3: Exit 👋");

        String option = scanner.nextLine();
        switch (option) {
            case "1":
                logIn();
                break;
            case "2":
                signUp();
                break;
            case "3":
                exit();
                break;
            default:
                wrongInput("start");
                break;
        }
    }

    void signUp() throws Exception {
        System.out.println("Please choose a valid option:\n" +
                "Is this an Admin or Customer account?\n" +
                "1: Admin 📊\t2: Customer 📖 3: Go back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                HashMap<String, String> admin = handleSignUpUserDetails();
                User newAdmin = new Admin(admin.get("name"), Integer.parseInt(admin.get("age")), admin.get("email"), admin.get("password"));
                theQuietCorner.addUser(newAdmin);
                logInAsAdmin(newAdmin);
                break;
            case "2":
                HashMap<String, String> customer = handleSignUpUserDetails();
                User newCustomer = new Customer(customer.get("name"), Integer.parseInt(customer.get("age")), customer.get("email"), customer.get("password"));
                theQuietCorner.addUser(newCustomer);
                logInAsCustomer(newCustomer);
                break;
            case "3":
                start();
                break;
            default:
                wrongInput("signUp");
                break;
        }
    }


    HashMap<String, String> handleSignUpUserDetails() {
        HashMap<String, String> userDetails = new HashMap<>();
        System.out.println("FullName: ?");
        String name = scanner.nextLine();
        userDetails.put("name", name);
        System.out.println("Age: ?");
        String age = scanner.nextLine();
        userDetails.put("age", age);
        System.out.println("Email : ?");
        String email = scanner.nextLine();
        userDetails.put("email", email);
        System.out.println("Password: ?");
        String password = scanner.nextLine();
        userDetails.put("password", password);
        return userDetails;
    }

    void logIn() throws Exception {
        System.out.println("\t\t 🌟Login Menu 🌟\n" +
                "\t\t Email: ? ");
        String email = scanner.nextLine();
        System.out.println("\t\t\t\t 🌟Login Menu 🌟\n" +
                "\t\t Email: " + email + " \n" +
                "\t\t Password: ?");
        String password = scanner.nextLine();
        System.out.println("\t\t\t\t 🌟Login Menu 🌟\n" +
                "\t\t Email: " + email + " \n" +
                "\t\t Password: ********");

        for (User user : Library.usersList) {
            if (Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password)) {
                if (user.getType() == UserType.admin) {
                    logInAsAdmin(user);
                    return;
                } else if (user.getType() == UserType.customer) {
                    logInAsCustomer(user);
                    return;
                }
            }
        }
        wrongInput("login");
    }

    void logInAsAdmin(User user) throws Exception {
        System.out.println("\n\t\t\t\t 🌟Admin Menu 🌟\n" +
                "Session of " + user.getName() +
                "\n1: Books Control Panel 📚\t2: Users Control Panel 🐳\t3: Settings ⚙️\t 4: Exit 👋");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                adminBooksCommands(user);
                break;
            case "2":
                System.out.println("Users");
                break;
            case "3":
                System.out.println("Settings");
                break;
            case "4":
                start();
                break;
            default:
                logInAsAdmin(user);
                break;
        }
    }

    void logInAsCustomer(User user) throws Exception {
        System.out.println("\n\t\t\t\t 🌟Customer Menu 🌟\n" +
                "Session of " + user.getName()
                +
                "\n1: Books Control Panel 📚\t2: Settings ⚙️\t3: Log Out 👋");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                customerBooksCommands(user);
                break;
            case "2":
                System.out.println("Settings");
                break;
            case "3":
                start();
                break;
            default:
                logInAsCustomer(user);
                break;
        }
    }

    void adminBooksCommands(User user) throws Exception {
        System.out.println("\n\t\t\t\t 🌟Admin Books Menu 🌟\n" +
                "Session of " + user.getName() +
                "\n 1: See all books in Library📚\t2: See all books loaned ✉️\t3: Print books in Library in CSV 🖨\t4: Go Back ↩️"
        );
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                booksListCommands(user);
                break;
            case "2":
                adminLoanedBooks(user);
                break;
            case "3":
                System.out.println("Printing Books currently available in the library in CSV....");
                theQuietCorner.exportLibraryToCSV(Library.booksList, "booksInLibrary.csv");
                adminBooksCommands(user);
                break;
            case "4":
                logInAsAdmin(user);
                break;
            default:
                adminBooksCommands(user);
                break;
        }
    }

    void customerBooksCommands(User user) throws Exception {
        System.out.println("\n\t\t\t\t 🌟Customer Books Menu 🌟\n" +
                "Session of " + user.getName() +
                "\n 1: See all books in Library📚\t2: See my books loaned ✉️\t3: Go Back ↩️"
        );
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                booksListCommands(user);
                break;
            case "2":
                user.displayLoanedBooks();
                customerBooksCommands(user);
                break;
            case "3":
                logInAsCustomer(user);
                break;
            default:
                customerBooksCommands(user);
                break;
        }

    }


    int limit = 10;

    void booksListCommands(User user) throws Exception {
        theQuietCorner.displayLimitedBooks(limit);
        System.out.println("\nLimited to " + limit + " books.");
        System.out.println("1: Change Limit of Display\t2: Search Book By...\t3: Sort Books by...\t4: Loan a Book\t5: Return a book\t6: Go Back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                setCustomLimitForLibrary(user);
                break;
            case "2":
                searchBookBy(user);
                break;
            case "3":
                sortBookBy(user);
                break;
            case "4":
                loanBook(user);
                break;
            case "5":
                returnBook(user);
                break;
            case "6":
                if (user.getType() == UserType.admin) {
                    adminBooksCommands(user);
                } else if (user.getType() == UserType.customer) {
                    customerBooksCommands(user);
                }
                break;
            default:
                booksListCommands(user);
                break;
        }
    }


    void setCustomLimitForLibrary(User user) throws Exception {
        System.out.println("1: Set custom limit #️⃣\t 2: Display All Books 📚\t3: Go back ↩️");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            System.out.println("Set new limit: ");
            limit = Integer.parseInt(scanner.nextLine());
            booksListCommands(user);
        } else if (choice.equals("2")) {
            limit = theQuietCorner.getBooksCount();
            booksListCommands(user);
        } else {
            booksListCommands(user);
        }
    }

    void searchBookBy(User user) throws Exception {
        System.out.println("1: Id\t2: Title\t3: Author\t4: Genre\t5: Publisher\t6: Go Back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                theQuietCorner.searchBook(BookFilterType.id);
                searchBookBy(user);
                break;
            case "2":
                theQuietCorner.searchBook(BookFilterType.title);
                searchBookBy(user);
                break;
            case "3":
                theQuietCorner.searchBook(BookFilterType.author);
                searchBookBy(user);
                break;
            case "4":
                theQuietCorner.searchBook(BookFilterType.genre);
                searchBookBy(user);
                break;
            case "5":
                theQuietCorner.searchBook(BookFilterType.publisher);
                searchBookBy(user);
                break;
            case "6":
                booksListCommands(user);
                break;
            default:
                System.out.println("Please select a valid option ❌");
                searchBookBy(user);
        }
    }

    void sortBookBy(User user) throws Exception {
        System.out.println("1: Id\t2: Title\t3: Author\t4: Genre\t5: Publisher\t6: Go Back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                theQuietCorner.sortBookBy(BookFilterType.id, limit);
                sortBookBy(user);
                break;
            case "2":
                theQuietCorner.sortBookBy(BookFilterType.title, limit);
                sortBookBy(user);
                break;
            case "3":
                theQuietCorner.sortBookBy(BookFilterType.author, limit);
                sortBookBy(user);
                break;
            case "4":
                theQuietCorner.sortBookBy(BookFilterType.genre, limit);
                sortBookBy(user);
                break;
            case "5":
                theQuietCorner.sortBookBy(BookFilterType.publisher, limit);
                sortBookBy(user);
                break;
            case "6":
                booksListCommands(user);
                break;
            default:
                System.out.println("Please select a valid option ❌");
                sortBookBy(user);
        }
    }


    void loanBook(User user) throws Exception {
        System.out.println("Do you want to loan a book?\n" +
                "1: Yes ✅\t 2: No, Go Back ↩️");
        String option = scanner.nextLine();
        if (option.equals("1")) {
            System.out.println("Please insert the id of the book you want to borrow:");
            int id = Integer.parseInt(scanner.nextLine());
            user.loanBook(id);
            loanBook(user);
        } else if (option.equals("2")) {
            booksListCommands(user);
        }
    }

    void returnBook(User user) throws Exception {
        System.out.println("Do you want to return a book?\n" +
                "1: Yes ✅\t 2: No, Go Back ↩️");
        String option = scanner.nextLine();
        if (option.equals("1")) {
            if (user.getLoanedBooks().size() == 0) {
                System.out.println("You don't have loaned books.");
                booksListCommands(user);
            } else {
                System.out.println("Please insert the id of the book you want to borrow:");
                int id = Integer.parseInt(scanner.nextLine());
                user.returnBook(id);
                returnBook(user);
            }
        } else if (option.equals("2")) {
            booksListCommands(user);
        }
    }


    void adminLoanedBooks(User user) throws Exception {
        System.out.println("\n\t\t\t\t 🌟Admin Loaned Books Menu 🌟\n" +
                "Session of " + user.getName()
        );
        theQuietCorner.displayLibraryLoanedBook();
        System.out.println("1: Search Book By...\t2: Sort Books by...\t3: Print list of Loaned Books in CSV 🖨\t4: Go Back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":

                break;
            case "2":
                break;
            case "3":
                System.out.println("Printing Books currently available in the library in CSV....");
                theQuietCorner.exportLoanToCsv(Library.libraryLoanedBooksArr, "loanBooks.csv");
                break;
        }
        adminBooksCommands(user);
    }


    void exit() {
        System.out.println("Exiting the app... 👋");
    }


    void wrongInput(String option) throws Exception {
        switch (option) {
            case "login":
                System.out.println("User Not Found, do you want to retry your credentials?\n" +
                        "1: Yes ✅\t 2: Go Back ↩️");
                String selection = scanner.nextLine();
                if (selection.equals("1")) {
                    logIn();
                } else if (selection.equals("2")) {
                    start();
                } else {
                    wrongInput("login");
                }
                break;
            case "start":
                start();
                break;
            case "signUp":
                signUp();
                break;
        }
    }
}
