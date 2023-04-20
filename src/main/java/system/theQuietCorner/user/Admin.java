package system.theQuietCorner.user;

import static system.theQuietCorner.user.UserType.admin;

public class Admin extends User{
    public Admin(String name, int age, String email, String password) {
        super(name, age, email, password);
    }

    @Override
    public UserType getType() {
        return admin;
    }


}
