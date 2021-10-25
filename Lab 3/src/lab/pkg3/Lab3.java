/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab.pkg3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexj
 */
public class Lab3 {

    public static void main(String[] args) {
        Collection<Object> col = new ArrayList<>();
        col.add(7);
        col.add("TEST");
        col.add(false);
        col.add(25.2);

        for (Object obj : col) {
            System.out.println(obj);
        }
        System.out.println("");

        Map<Student, Double> noteStudenti = new HashMap<>();

        Student student1 = new Student("Andrei", "Petrescu");
        Student student2 = new Student("Maria", "Ionescu");
        Student student3 = new Student("Vasile", "Marionescu");

        noteStudenti.put(student1, 10.1);
        noteStudenti.put(student2, 4.2);
        noteStudenti.put(student3, 8.8);

        System.out.println(noteStudenti.get(student2));

    }

}
