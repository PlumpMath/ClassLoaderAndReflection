package ru.svetozarov.reflection;

import ru.svetozarov.reflection.CustomLoader.JarClassLoared;
import ru.svetozarov.reflection.objects.PeopleOLD;

import java.io.File;

/**
 * Created by Evgenij Svetozarov on 10.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        //PeopleOLD peopleOLD =new PeopleOLD("Ivan",15,25000);
        Reflection reflection =new Reflection();
        //reflection.serialize(peopleOLD);

        //reflection.deserealize();

        JarClassLoared jarClassLoared = new JarClassLoared();

        try {
            Class clazz=jarClassLoared.loadClass("Auto");
            try {
                System.out.println(clazz.newInstance().toString());
                reflection.serialize(clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // Animal execute = (Module) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
