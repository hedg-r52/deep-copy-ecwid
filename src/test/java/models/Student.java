package models;

/**
 * Created by Andrei Soloviev (hedg.r52@gmail.com).
 * date: 13.10.2022
 */
public class Student {
    private final String name;
    private final Address address;

    public Student(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }
}
