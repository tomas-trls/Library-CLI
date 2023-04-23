package system;

import system.theQuietCorner.book.Book;
import system.theQuietCorner.commands.CommandRunner;


public class LibraryApplication {
    public static void main(String[] args) throws Exception {
        new CommandRunner().buildLibrary();
    }
}