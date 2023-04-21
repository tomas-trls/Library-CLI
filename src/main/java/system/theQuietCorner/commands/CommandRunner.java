package system.theQuietCorner.commands;

import system.theQuietCorner.library.Library;
import system.theQuietCorner.user.Admin;
import system.theQuietCorner.user.Customer;
import system.theQuietCorner.user.User;
import system.theQuietCorner.user.UserType;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class CommandRunner {
    private final Scanner scanner = new Scanner(System.in);
    Library theQuietCorner = new Library();

    public void start() {
        theQuietCorner.addUser(new Admin("admin", 99, "admin@admin.com","admin"));
        theQuietCorner.addUser(new Customer("customer", 50, "customer@customer.com", "customer"));
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
        }
    }

    void signUp() {
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
                break;
            case "3":
                start();
                break;
            default:
                wrongInput("signUp");
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

    void logIn() {
        System.out.println("\t\t 🌟Login Menu 🌟\n" +
                "\t\t Email: ? ");
        String email = scanner.nextLine();
        System.out.println("\t\t\t\t 🌟Login Menu 🌟\n" +
                "\t\t Email: "+email+" \n" +
                "\t\t Password: ?");
        String password = scanner.nextLine();
        System.out.println("\t\t\t\t 🌟Login Menu 🌟\n" +
                "\t\t Email: "+email+" \n" +
                "\t\t Password: ********");

        for (User user : Library.usersList) {
            if(Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password)) {
                if (user.getType() == UserType.admin){
                    logInAsAdmin(user);
                    return;
                }
            }
        }
        wrongInput("login");
    }


    void logInAsAdmin(User user){
        System.out.println("\n\t\t\t\t 🌟Admins Menu 🌟\n"+
                "1: Books Control Panel 📚\t2: Users Control Panel 🐳\t3: Settings ⚙️\t 4: Exit 👋");
        String option = scanner.nextLine();
        switch (option){
            case "1":
                System.out.println("Books");
                break;
            case "2":
                System.out.println("Users");
                break;
            case "3":
                System.out.println("Settings");
                break;
            case "4":
                exit();
                break;
            default:
               logInAsAdmin(user);
        }
    }

    void logInAsCustomer(User user){
        System.out.println("\n\t\t\t\t 🌟Admins Menu 🌟\n"+
                "1: Books Control Panel 📚\t2: Loans 🐳\t 3: Settings ⚙️\t4: Exit 👋");
        String option = scanner.nextLine();
        switch (option){
            case "1":
                System.out.println("Books");
                break;
            case "2":
                System.out.println("Loans");
                break;
            case "3":
                System.out.println("Settings");
                break;
            case "4":
                exit();
                break;
            default:
                logInAsAdmin(user);
        }
    }


    void exit() {
        System.out.println("Exiting the app... 👋");
    }


    void wrongInput(String option){
        if(option.equals("login")) {
            System.out.println("User Not Found, do you want to retry your credentials?\n" +
                    "1: Yes ✅\t 2: Go Back ↩️");
            String selection = scanner.nextLine();
            if(selection.equals("1")){
                logIn();
            } else if(selection.equals("2")){
                start();
            } else {
                wrongInput("login");
            }
        } else if(option.equals("start")){
            start();
        } else if(option.equals("signUp")){
            signUp();
        }
    }
}
