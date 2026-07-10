package ModelBuilderClass.Builder;

import ModelBuilderClass.ModelClass.User;

public class UserBuilder extends User {

    public UserBuilder() {
        super();
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }
}