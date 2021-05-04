package com.najkhan.tsp.TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class it runs the whole program
 * it runs TSPGenerativePrograming solution and TwoOptTwo classes by calling their solve methods depending on
 * size of the number of cities which it reads from file whose path is provided in the FILEPATH constant
 */
public class Main {

    //File path-- please provide the path of test file
    public static final String FILEPATH = "D:\\yearIII\\ArtificialIntelligence\\TSP\\src\\com\\najkhan\\tsp\\dijstra\\Cities.txt";

    // array of cities loaded from the file
    public static final ArrayList<City> CITIES = fileReader(FILEPATH);

    // main method runs the methods needed to solve TSP
    public static void main(String[] args) {

        // check if number of cities is greater then 17 solve TSP using 2-Opt algorithm
        if (CITIES.size()>17){

            System.out.println("------------------------");
            System.out.println("Results Produced By 2-Opt Algorithm");
            // start the stopwatch
            long start = System.nanoTime();
            // Declare and initialize class that solves TSP using 2-Opt method
            TwoOptTwo twoOptTwo = new TwoOptTwo();
            // Call the solve method to solve TSP
            twoOptTwo.solve();
            // Stop the stopwatch
            long end = System.nanoTime();
            // calculate run time of program in nano seconds and print it
            long duration2 = end-start;
            System.out.println("Run time nanos = " + duration2);

        }
        // if number of cities is less then 18 solve the TSP using Dynamic programing solution
        if(CITIES.size()<18) {

            System.out.println("------------------------");
            System.out.println("Deterministic Results Produced by Dynamic Programming Algorithm");
            // start the stopwatch
            long start = System.nanoTime();
            // Declare and initialize the class that uses Dynamic programming algorithm to solve TSP
            TSPGenerativeProgramingSolution tspGenerativeProgramingSolution = new TSPGenerativeProgramingSolution();
            // Method to solve TSP
            tspGenerativeProgramingSolution.solve();
            // Stop the stopwatch
            long end = System.nanoTime();
            // calculate run time of program in seconds and print it
            long duration1 = end - start;
            System.out.println("Run time seconds = " + duration1 / 1000000000);
        }
    }

    //Method to read file
    public static ArrayList<City> fileReader(String path){
        // Arraylist to be filled by input from file
        ArrayList<City> cities = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scan = new Scanner(file);
            // Loop until the end of file and add each city in file to array
            while(scan.hasNextLine()){
                City node = new City();
                // trim and split each line at white space
                String[] line = scan.nextLine().trim().split("\\s+");
                // check if line does not contain only spaces
                if (!line[0].equals("")) {
                    // Save number and coordinates of the city in array
                    int[] data = {Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2])};
                    node.setContent(data);
                    // add the city to the node
                    cities.add(node);
                }
            }
            // array containing cities loaded from input file
            return cities;
        // Handle error and print stack trace of error if it occurs and return null
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }


    }
}
