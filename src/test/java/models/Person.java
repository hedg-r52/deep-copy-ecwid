package models;

import java.io.Serializable;

/**
 * Created by Andrei Soloviev (hedg.r52@gmail.com).
 * date: 13.10.2022
 */
public class Person implements Serializable {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
