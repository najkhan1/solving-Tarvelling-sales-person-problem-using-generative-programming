package com.najkhan.tsp.TSP;

import java.util.Comparator;

/**
 * This class produces City objects
 * object produced by this class are used by TSPGenerativeProgrammingSolution and TwoOptTwo classes to
 * solve TSP
 */
public class City implements Comparator<City> {
    // Array containing number of city and its coordinates
    private int[] content;
    private double distance;

    // empty constructor
    public City() {
    }
    // parameterised constructor
    public City(int[] content, double distance) {
        this.content = content;
        this.distance = distance;
    }

    public int[] getContent() {
        return content;
    }

    public void setContent(int[] content) {
        this.content = content;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    // method to compare the nodes and arrange them according to distance
    @Override
    public int compare(City node1, City node2) {
        if (node1.getDistance()<node2.getDistance()){
            return -1;
        }else if (node1.getDistance()> node2.getDistance()){
            return 1;
        }
        return 0;
    }
}
