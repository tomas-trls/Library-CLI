package system.theQuietCorner.user;

import java.util.Comparator;

public class SortUserByName implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if( o1.getName().equalsIgnoreCase(o2.getName()) ) {
            return (int) (o1.getId() - ( o2.getId()));
        }
        return o1.getName().toLowerCase().compareTo( o2.getName().toLowerCase() );
    }
}
