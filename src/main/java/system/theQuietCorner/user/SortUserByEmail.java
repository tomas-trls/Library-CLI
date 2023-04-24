package system.theQuietCorner.user;

import java.util.Comparator;

public class SortUserByEmail implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if( o1.getEmail().equalsIgnoreCase(o2.getEmail()) ) {
            return (int) (o1.getId() - ( o2.getId()));
        }
        return o1.getEmail().toLowerCase().compareTo( o2.getEmail().toLowerCase() );
    }
}
