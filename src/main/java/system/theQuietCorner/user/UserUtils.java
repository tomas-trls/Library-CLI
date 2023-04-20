package system.theQuietCorner.user;

public class UserUtils {

    private static int nextId = 0;

    public static int generateUniqueId(){
        return ++nextId;
    }

    public static int getNextId() {
        return nextId;
    }
}
