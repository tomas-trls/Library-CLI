package system.theQuietCorner.user;

import java.util.Comparator;

public class SortUserByHasLoanedBook implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        int hasLoanedBooksComparison = Boolean.compare(o2.isHasLoanedBooks(), o1.isHasLoanedBooks());
        if (hasLoanedBooksComparison == 0) {
            return (int) (o1.getId() - o2.getId());
        }
        return hasLoanedBooksComparison;
    }

}
