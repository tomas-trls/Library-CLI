package system.theQuietCorner.user;

import static system.theQuietCorner.user.UserType.customer;

public class Customer extends User{

    public Customer(String name, int age, String email, String password) {
        super(name, age, email, password);
    }

    @Override
    public UserType getType() {
        return customer;
    }
}
