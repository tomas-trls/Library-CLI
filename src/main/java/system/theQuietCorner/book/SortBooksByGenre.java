package system.theQuietCorner.book;

import java.util.Comparator;

public class SortBooksByGenre implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        if( o1.getGenre().equals(o2.getGenre()) || o1.getSubgenre().equals(o2.getSubgenre()) ) {
            return (o1.getTitle().compareTo(o2.getTitle()));
        }
        return o1.getGenre().compareTo( o2.getGenre());
    }
}
