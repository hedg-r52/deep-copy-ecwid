package models;

/**
 * Created by Andrei Soloviev (hedg.r52@gmail.com).
 * date: 13.10.2022
 */
public class Address {
    private final String street;

    public Address(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }
}
