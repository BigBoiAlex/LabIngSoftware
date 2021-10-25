/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package miniproiect;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author alexj
 */
public class MiniProiect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File(args[0]);
        String inputString = "";
        TreeSet<String> outputSet;

        Scanner sc = new Scanner(inputFile);

        while (sc.hasNextLine()) {
            inputString += sc.nextLine();
        }
        System.out.println("String citit" + "\n" + inputString + "\n");

        inputString = inputString.toLowerCase();

        outputSet = new TreeSet<>(Arrays.asList(inputString.split("[ .,?!]")));

        System.out.println("String dupa eliminarea duplicatelor si sortare ");

        for (String s : outputSet) {
            System.out.print(s + " ");
        }
    }
}
