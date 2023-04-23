package system.theQuietCorner.book;

import java.util.Comparator;

public class SortBooksByAuthor implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        if( o1.getAuthor().equals(o2.getAuthor()) ) {
            return o1.getTitle().compareTo( o2.getTitle() );
        }
        return o1.getAuthor().compareTo( o2.getAuthor() );
    }
}
