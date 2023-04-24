package system.theQuietCorner.user;

import java.util.Comparator;

public class SortUserByType implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if( o1.getType().equals(o2.getType()) ) {
            return (int) (o1.getId() - ( o2.getId()));
        }
        return o1.getType().compareTo( o2.getType() );
    }
}
