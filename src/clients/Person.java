package clients;

import java.io.Serializable;

public class Person implements Serializable {
    String name;
    long socialSecurity;

    public Person(String name, long socialSecurity) {
        this.name = name;
        this.socialSecurity = socialSecurity;
    }

    public String getName() {
        return name;
    }

    public long getSocialSecurity() {
        return socialSecurity;
    }
}
