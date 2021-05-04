package com.najkhan.tsp.TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains Generative programming solution of TSP
 * Main class calls solve method of this class to solve the TSP
 */
public class TSPGenerativeProgramingSolution {

    ArrayList<City> cities = Main.CITIES;

    //Maximum number of cities
    int maxCities = cities.size();
    //minimum distance
    double minSum = Float.MAX_VALUE;
    // permutation of cities as taken from the file
    int[] permutation = new int[maxCities];
    //Best permutation with lowest distance of path
    int[] miniPermutation = new int[maxCities];
    // distance matrix
    double[][] distanceTable = new double[maxCities][maxCities];

    //Method to solve tsp
    public void solve(){
        boolean[] remainingCities = new boolean[maxCities];
        loadPermutation();
        fillMatrix();
        //boolean[] mask = new boolean[maxCities];
        search(maxCities-1,0,remainingCities);
        printMinPath();

    }

    // Recursive method to generate permutations and evaluate them according to path length
    // it save the permutation with minimum path length
    public void search(int subNumOfCities,double sumMain,boolean[] remainingCities){

        //Check if the sum of the partial path plus distance of MST of remaining nodes is greater then minSum return
        if (sumMain + mstDistance(remainingCities)>minSum){
            remainingCities[permutation[subNumOfCities]] = false;
            return;
        }

        if (subNumOfCities == 1){
            // Check the permutation if the its distance is lower then min distance save the permuation
            checkPermutation(sumMain+findDistanceFromMatrix(permutation[0],permutation[1]));
        }else {
            for (int index =0; index< subNumOfCities; index++){
                // swap the first and second to last cities in the permutation array
                swap(index,subNumOfCities-1);
                // turn index of remainingCities array to true so it wont be included in MST distance calculation
                remainingCities[permutation[subNumOfCities-1]] = true;
                //call search recursively
                search(subNumOfCities-1,findDistanceFromMatrix(permutation[subNumOfCities-1],permutation[subNumOfCities])+sumMain,remainingCities);
                //swap cities back to original state
                swap(index,subNumOfCities-1);
            }
        }
    }

    // method to swap the nodes in the permutation array
    public void swap(int numOne, int numLast){
        int tempP = permutation[numOne];
        permutation[numOne] = permutation[numLast];
        permutation[numLast] = tempP;

    }

    //Load permutation array with nodes from node arraylist
    public void loadPermutation(){
        for (int index = 0; index < cities.size(); index++){
            permutation[index] = index;
        }
    }

    // Method to check the permutation and save the total distance of the permutation
    public void checkPermutation(double sum){
        // add distance to source from last city to the sum
        sum += findDistanceFromMatrix(permutation[0], permutation[maxCities-1]);
        saveSum(sum);
    }

    //Method to save the sum of distances
    public void saveSum(double sum){
        // Only save the sum if it is less then minimum sum
        if (sum < minSum){
            minSum = sum;
            for (int index=0; index<maxCities; index++){
                miniPermutation[index] = permutation[index];
            }
        }
    }

    //Method to print the path
    public void printMinPath(){
        System.out.println("Path: ");
        for (int i = 0;i<miniPermutation.length;i++){
            System.out.print((miniPermutation[i]+1)+ ", ");
            if (i==miniPermutation.length-1){
                System.out.print(miniPermutation[0]+1);
            }
        }
        System.out.println(" distance = " + minSum);
    }


    // method to find Euclidean distance
    public double findDistance(City fromCity, City toCity){

        return Math.sqrt(Math.pow((toCity.getContent()[1]-fromCity.getContent()[1]),2) + Math.pow((toCity.getContent()[2]-fromCity.getContent()[2]),2));
    }

    // method to load the distances in distance matrix
    public void fillMatrix(){
        for (int fromCity =0;fromCity<maxCities;fromCity++){
            for (int toCity =0;toCity<maxCities;toCity++){

                distanceTable[permutation[fromCity]][permutation[toCity]] = findDistance(cities.get(permutation[fromCity]), cities.get(permutation[toCity]));
            }
        }
    }

    // method to find distance from distance matrix
    public double findDistanceFromMatrix(int fromCity,int toCity){
        return distanceTable[fromCity][toCity];
    }


    // calculates minimum spanning tree of the remaining nodes
    public double mstDistance(boolean[] mask){
        int smallest =0;
        int newCity;
        int counter=0;
        double minDistance;
        double currentDistance;
        double totalDistance = 0;
        int[] remainingCit = new int[maxCities];
        double[] distanceRemaining = new double[maxCities];
        int[] minPath = new int[maxCities];

        // check which cities are not in path and add them to array of remaining cities
        for (int indexAllCities = 0; indexAllCities < maxCities; indexAllCities++){

            if (!mask[indexAllCities]){

                remainingCit[counter] = indexAllCities;
                counter++;
            }
        }

        // take last city from array of remaining cities
        newCity = remainingCit[counter-1];

        // make all distances of remaining cities array infinite
        for (int index = 0; index < counter-1;index++){
            distanceRemaining[index] = Double.MAX_VALUE;
        }

        // calculate the MST
        for (int index = counter-1; index>0; index--){

            // initialize minDistance to infinite value
            minDistance = Double.MAX_VALUE;

            // loop through all the remaining cities to find city with minimum distance
            for (int subIndex =0; subIndex < index;subIndex++){
                currentDistance = findDistanceFromMatrix(remainingCit[subIndex],newCity);

                //update the distance in remaining cities distance array if better distance is found
                if (currentDistance < distanceRemaining[subIndex]){
                    distanceRemaining[subIndex] = currentDistance;
                    minPath[subIndex] = newCity;
                }

                // update the minDistance if shorter distance is found
                if (distanceRemaining[subIndex] < minDistance){
                    minDistance = distanceRemaining[subIndex];
                    smallest = subIndex;
                }
            }

            // assign new city value of the city with smallest distance
            newCity = remainingCit[smallest];
            totalDistance += minDistance;
            remainingCit[smallest] = remainingCit[index-1];
        }

        return totalDistance;
    }

}
