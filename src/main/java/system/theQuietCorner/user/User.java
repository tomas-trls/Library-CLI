package system.theQuietCorner.user;

import system.ColoursUtils;
import static system.theQuietCorner.user.UserUtils.*;

public abstract class User {
    private long id;
    private String name;

    private int age;
    private String email;

    private String password;

    public User(String name, int age, String email, String password) {
        this.id = generateUniqueId();
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract UserType getType();

    public String getInformation() {
        return  String.format(" (#%d), %s, is %d years old, email %s, password %s, is a/an %s",
                this.id,ColoursUtils.green(this.name), this.age, this.email, this.password, getType());
    }

}
