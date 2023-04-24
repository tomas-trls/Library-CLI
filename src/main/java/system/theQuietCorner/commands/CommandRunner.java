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
        theQuietCorner.addUser(new Customer("Tomas", 22, "tom@customer.com", "customer"));
        theQuietCorner.addUser(new Customer("Andrea", 30, "andrea@customer.com", "customer"));
        theQuietCorner.addUser(new Customer("antonia", 66, "antonia@customer.com", "customer"));
        theQuietCorner.addUser(new Customer("fernando", 81, "fernando@customer.com", "customer"));
        theQuietCorner.addUser(new Customer("Lalo", 81, "lalo@customer.com", "customer"));
        theQuietCorner.addUser(new Customer("Lucas", 24, "lucas@customer.com", "customer"));
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
                "\n1: Books Control Panel 📚\t2: Users Control Panel 🐳\t3: Settings ⚙️\t 4: Log Out 👋");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                adminBooksCommands(user);
                break;
            case "2":
                adminCustomerMenu(user);
                break;
            case "3":
                settings(user);
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
                settings(user);
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
        if (limit > theQuietCorner.getBooksCount()) {
            limit = theQuietCorner.getBooksCount();
        }
        theQuietCorner.displayLimitedBooks(limit);
        System.out.println("Displaying " + limit + " books.");
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
        System.out.println("1: Id\t2: Title\t3: Author\t4: Genre\t5: Publisher\t6: Amount of times loaned\t7: Go Back ↩️");
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
                theQuietCorner.sortBookBy(BookFilterType.counter, limit);
                sortBookBy(user);
            case "7":
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
        } else {
            loanBook(user);
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
                user.displayLoanedBooks();
                System.out.println("Please insert the id of the book you want to return:");
                int id = Integer.parseInt(scanner.nextLine());
                user.returnBook(id);
                returnBook(user);
            }
        } else if(option.equals("2")) {
            booksListCommands(user);
        } else {
            returnBook(user);
        }
    }


    void adminLoanedBooks(User user) throws Exception {
        System.out.println("\n\t\t\t\t 🌟Admin Loaned Books Menu 🌟\n" +
                "Session of " + user.getName()
        );
        theQuietCorner.displayLibraryLoanedBook();
        System.out.println("1: Search Book By...\t2: Sort Books by...\t3: Print list of Loaned Books in CSV 🖨\t4: See my Loaned Books\t5: Go Back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                System.out.println("Printing Books currently available in the library in CSV....");
                Library.exportLoanToCsv(Library.libraryLoanedBooksArr, "loanBooks.csv");
                adminLoanedBooks(user);
                break;
            case "4":
                user.displayLoanedBooks();
                adminBooksCommands(user);
                break;
            case "5":
                adminBooksCommands(user);
                break;
            default:
                adminLoanedBooks(user);
        }
    }


    void adminCustomerMenu(User user) throws Exception {
        if (usersLimit > theQuietCorner.getUserCount() ){
            usersLimit = theQuietCorner.getUserCount();
        }
        theQuietCorner.listUsersWithLimit(usersLimit);
        System.out.println("Displaying " + usersLimit + " users.");
        System.out.println("1: Change Limit of Display\t2: Search User By...\t3: Sort Users by...\t4: Add a User\t5: Delete A User\t6: Print all users in System 🖨\t7: Go Back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                setCustomLimitForUsers(user);
                break;
            case "2":
                searchUsersBy(user);
                break;
            case "3":
                sortUsersBy(user);
                break;
            case "4":
                System.out.println("Do you want to add a new user?" +
                        "\n1: Yes ✅\t2: Go Back ↩️");
                String prompt = scanner.nextLine();
                if (prompt.equals("1")){
                System.out.println("What type of user do you want to create?" +
                        "\n1: Admin 📊\t2: Customer ");
                String choice = scanner.nextLine();
                if (choice.equals("1")){
                    HashMap<String, String> admin = handleSignUpUserDetails();
                    User newAdmin = new Admin(admin.get("name"), Integer.parseInt(admin.get("age")), admin.get("email"), admin.get("password"));
                    theQuietCorner.addUser(newAdmin);
                    System.out.println("Added new Admin! 🌟");
                    adminCustomerMenu(user);
                } else if (choice.equals("2")) {
                    HashMap<String, String> customer = handleSignUpUserDetails();
                    User newCustomer = new Customer(customer.get("name"), Integer.parseInt(customer.get("age")), customer.get("email"), customer.get("password"));
                    theQuietCorner.addUser(newCustomer);
                    System.out.println("Added new Customer! 🌟");
                    adminCustomerMenu(user);
                }
                } else{
                    adminCustomerMenu(user);
                }
                break;
            case "5":
                System.out.println("Are you sure you want to delete a user? (This will be definitive)" +
                        "\n1: Yes ✅\t2: Go Back ↩️");
                String choice = scanner.nextLine();
                if(choice.equals("1")){
                    System.out.println("Please insert the id of the user you want to delete.");
                    int userDeleteId = Integer.parseInt(scanner.nextLine());
                    theQuietCorner.deleteUser(userDeleteId);
                    System.out.println("User deleted 💥");
                    adminCustomerMenu(user);
                } else {
                    adminCustomerMenu(user);
                }
                break;
            case "6":
                System.out.println("Printing Users in the library in CSV....");
                theQuietCorner.exportUsersToCSV(Library.usersList, "usersInSystem.csv");
                adminCustomerMenu(user);
                break;
            case "7":
                logInAsAdmin(user);
            default:
                adminCustomerMenu(user);
                break;
        }
    }

    int usersLimit = 10;

    void setCustomLimitForUsers(User user) throws Exception {
        System.out.println("1: Set custom limit #️⃣\t 2: Display All Users 📚\t3: Go back ↩️");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            System.out.println("Set new limit: ");
            usersLimit = Integer.parseInt(scanner.nextLine());
            adminCustomerMenu(user);
        } else if (choice.equals("2")) {
            usersLimit = theQuietCorner.getUserCount();
            adminCustomerMenu(user);
        } else {
            adminCustomerMenu(user);
        }
    }

    void searchUsersBy(User user) throws Exception {
        System.out.println("1: Id\t2: Name\t3: Age\t4: Email\t5: Go Back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                theQuietCorner.searchUser(UserFilterType.id);
                searchUsersBy(user);
                break;
            case "2":
                theQuietCorner.searchUser(UserFilterType.name);
                searchUsersBy(user);
                break;
            case "3":
                theQuietCorner.searchUser(UserFilterType.age);
                searchUsersBy(user);
                break;
            case "4":
                theQuietCorner.searchUser(UserFilterType.email);
                searchUsersBy(user);
                break;
            case "5":
                adminCustomerMenu(user);
                break;
            default:
                System.out.println("Please select a valid option ❌");
                searchUsersBy(user);
        }
    }

    void sortUsersBy(User user) throws Exception {
        System.out.println("1: Id\t2: Name\t3: Age\t4: Email\t5: Role\t6: Has Loaned Book?\t7: Go Back ↩️");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                theQuietCorner.sortUsersBy(UserFilterType.id, usersLimit);
                sortUsersBy(user);
                break;
            case "2":
                theQuietCorner.sortUsersBy(UserFilterType.name, usersLimit);
                sortUsersBy(user);
                break;
            case "3":
                theQuietCorner.sortUsersBy(UserFilterType.age, usersLimit);
                sortUsersBy(user);
                break;
            case "4":
                theQuietCorner.sortUsersBy(UserFilterType.email, usersLimit);
                sortUsersBy(user);
                break;
            case "5":
                theQuietCorner.sortUsersBy(UserFilterType.type, usersLimit);
                sortUsersBy(user);
                break;
            case "6":
                theQuietCorner.sortUsersBy(UserFilterType.loaned, usersLimit);
                sortUsersBy(user);
                break;
            case "7":
                adminCustomerMenu(user);
                break;
            default:
                System.out.println("Please select a valid option ❌");
                sortUsersBy(user);
        }
    }

    void settings(User user) throws Exception {
        System.out.println("\n\t\t\t\t 🌟Settings 🌟\n" +
                "Session of " + user.getName() +
                "\n 1: See your details 😊️\t2: Go Back ↩️"
        );
        String option = scanner.nextLine();
        if (option.equals("1")) {
            userDetails(user);
        } else if (option.equals("2")) {
            if (user.getType() == UserType.admin) {
                logInAsAdmin(user);
            } else if (user.getType() == UserType.customer) {
                logInAsCustomer(user);
            }
        }
    }

    void userDetails(User user) throws Exception {
        System.out.println("\n\t\t\t\t 🌟User Details 🌟\n" +
                "\t\t|| Name: " + user.getName() + "\n" +
                "\t\t|| Age: " + user.getAge() + " \n" +
                "\t\t|| Email: " + user.getEmail() + " \n" +
                "\t\t|| Password: " + user.getPassword());
        System.out.println("1: Update your...\t2: Go back ↩️");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            System.out.println("1: Name\t2: Age\t3: Email\t4: Password\t5: Go Back ↩️");
            String value = scanner.nextLine();
            switch (value){
                case "1":
                    System.out.println("Your name is "+user.getName());
                    System.out.println("Update new name:");
                    String name = scanner.nextLine();
                    user.setName(name);
                    userDetails(user);
                    break;
                case "2":
                    System.out.println("You are "+user.getAge()+" years old.");
                    System.out.println("Update your age:");
                    int age = Integer.parseInt(scanner.nextLine());
                    user.setAge(age);
                    userDetails(user);
                    break;
                case "3":
                    System.out.println("Your email address is "+user.getEmail());
                    System.out.println("Update your email:");
                    String email = scanner.nextLine();
                    user.setEmail(email);
                    userDetails(user);
                    break;
                case "4":
                    System.out.println("Your current password is "+user.getPassword());
                    System.out.println("Update your password");
                    String password = scanner.nextLine();
                    user.setPassword(password);
                    userDetails(user);
                    break;
                case "5":
                    userDetails(user);
                default:
                    System.out.println("Please enter a valid option ❌");
                    userDetails(user);
            }
        } else if (choice.equals("2")) {
            settings(user);
        }
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
