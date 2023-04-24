package system.theQuietCorner.user;

import java.util.Comparator;

public class SortUserById implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        if( String.valueOf(o1.getId()).equals(String.valueOf(o2.getId())) ) {
            return (o1.getName().compareTo( o2.getName()));
        }
        return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()) );
    }
}
