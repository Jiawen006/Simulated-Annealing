package cVRP_simulated_annealing;

import java.util.ArrayList;

public class TourManager extends ArrayList{
	private static ArrayList destinationCities = new ArrayList<City>();
    // Holds our cities
	public TourManager() {
		
	}
    

//    // Adds a destination city
//    public static void add(City city) {
//        destinationCities.add(city);
//    }
    
    // Get a city
    public static City getCity(int index){
        return (City)destinationCities.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfCities(){
        return destinationCities.size();
    }
    
    //return the max demand city,with its index
    public static int getMax_demand(ArrayList<City> input) {
    	City res = new City(0,0,0);
    	int index = 0;
    	for (int i =0; i <input.size();i++) {
    		if (input.get(i).demand<res.demand) {
    			res = input.get(i);
    			index = i;
    		}
    	}
    	return index;
    }


    
}