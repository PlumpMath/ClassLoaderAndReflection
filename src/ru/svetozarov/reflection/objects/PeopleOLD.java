package ru.svetozarov.reflection.objects;

import java.lang.reflect.Modifier;

/**
 * Created by Evgenij Svetozarov on 10.02.2017.
 */
public class PeopleOLD {
    private String name;
    private int age=0;
    private double salary;

    public PeopleOLD(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}
