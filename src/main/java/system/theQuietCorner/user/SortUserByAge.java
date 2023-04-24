package system.theQuietCorner.user;

import java.util.Comparator;

public class SortUserByAge implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if( o1.getAge() == o2.getAge()) {
            return (int) (o1.getId() - ( o2.getId()));
        }
        return String.valueOf(o1.getAge()).compareTo( String.valueOf(o2.getAge()) );
    }
}
