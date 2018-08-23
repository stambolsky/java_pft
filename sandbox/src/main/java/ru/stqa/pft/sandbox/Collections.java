package ru.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

    public static void main(String[] args) {
        String[] langs = {"Java", "C#", "Pythan", "PHP"};

        /*for (int i = 0; i < langs.length; i++) {
            System.out.println("Я хочу получить " + langs[i]);
        }*/

        for (String l : langs) {
            System.out.println("Я хочу получить " + l);
        }

        List<String> languages = Arrays.asList("Java", "C#", "Python");

        for (String l : languages) {
            System.out.println("Я хочу получить " + l);
        }

       /* for (int i = 0; i < languages.size(); i++) {
            System.out.println("Я хочу получить " + languages.get(i));
        }*/


    }
}
