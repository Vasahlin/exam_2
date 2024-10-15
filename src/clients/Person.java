package clients;

public class Person {
    String name;
    String socialSecurity;

    public Person(String name, String socialSecurity) {
        this.name = name;
        this.socialSecurity = socialSecurity;
    }

    public String getName() {
        return name;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }
}
