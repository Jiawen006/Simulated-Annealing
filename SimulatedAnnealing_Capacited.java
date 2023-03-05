package cVRP_simulated_annealing;

import java.util.ArrayList;
import java.util.Collections;

public class SimulatedAnnealing_Capacited {

    // Calculate the acceptance probability
    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
    public static double getdistance(ArrayList<Vehicle> vehicle) {
    	double res=0;
    	for (Vehicle ele: vehicle) {
        	res = res + ele.getDistance();
        }
    	return res;
    }
    public static void printdistance(ArrayList<Vehicle> vehicle) {
    	for (Vehicle ele: vehicle) {
        	System.out.println("Initial solution distance: " + ele.getDistance());
        }
    }
    public static ArrayList<Vehicle> copyVehicle(ArrayList<Vehicle> vehicle){
    	ArrayList<Vehicle> newVehicle = new ArrayList<Vehicle>();
    	for(int i=0; i <vehicle.size();i++) {
    		Vehicle tmp_vehicle = vehicle.get(i);
    		int index = tmp_vehicle.index;
    		int capacity = tmp_vehicle.capacity;
    		ArrayList <City> tmp_routine = tmp_vehicle.copyRoutine(tmp_vehicle.routine);
    		newVehicle.add(new Vehicle(index, capacity));
    		newVehicle.get(i).routine = tmp_routine;
    	}
    	return newVehicle;
    }
    
    public static TourManager addCity(TourManager city_info) {
    	// Create and add our cities
//    	City city = new City(40,40,10000);
//      city_info.add(city);
        City city1 = new City(20,80,1);
        city_info.add(city1);
        City city2 = new City(80,80,1);
        city_info.add(city2);
        City city3 = new City(0,70,2);
        city_info.add(city3);
        City city4 = new City(10, 70,4);
        city_info.add(city4);
        City city5 = new City(50, 60,2);
        city_info.add(city5);
        City city6 = new City(70, 60,4);
        city_info.add(city6);
        City city7 = new City(30,50,8);
        city_info.add(city7);
        City city8 = new City(60,50,8);
        city_info.add(city8);
        City city9 = new City(50,30,1);
        city_info.add(city9);
        City city10 = new City(80,30,2);
        city_info.add(city10);
        City city11 = new City(10,20,1);
        city_info.add(city11);
        City city12 = new City(20,20,2);
        city_info.add(city12);
        City city13 = new City(30,10,4);
        city_info.add(city13);
        City city14 = new City(60, 10,4);
        city_info.add(city14);
        City city15 = new City(0, 0,8);
        city_info.add(city15);
        City city16 = new City(70, 0,8);
        city_info.add(city16);
        return city_info;
    }
    
    public final static int numberOfVehicle =4;
	private static City set;
    public  static ArrayList<Vehicle> addVehicle(ArrayList<Vehicle> vehicle) {
    	vehicle.add(new Vehicle ( 0,0,new City(0,0,0)));
    	for (int index = 1; index<=numberOfVehicle; index++) {
    		ArrayList<City> newTour = new ArrayList<City>();
    		vehicle.add(new Vehicle(index, 15,new City(40,40,0)));
    	}
    	return vehicle;
    }
    
    public static void main(String[] args) {
        //ArrayList city_info = new ArrayList <City>();
        TourManager city_info = new TourManager();
        
    	city_info = addCity(city_info);
        ArrayList<Vehicle> vehicle = new ArrayList<Vehicle> ();
        vehicle = addVehicle(vehicle);

        // Set initial temp
        double temp = 1000000000;

        // Cooling rate
        double coolingRate = 0.0002;

        // Initialize initial solution(greedy method)
        ArrayList<City> tmp_city = (ArrayList<City>)city_info.clone();
        while(!tmp_city.isEmpty()) {
        	int index = ((TourManager) tmp_city).getMax_demand(tmp_city);
        	City tmp = tmp_city.remove(index);
        	for (int i=1; i <=numberOfVehicle;i++) {
        		if (vehicle.get(i).checkOversize(tmp.demand)) {
        			vehicle.get(i).routine.add(tmp);
        			vehicle.get(i).capacity = vehicle.get(i).capacity - tmp.demand;
        			break;
        		}
        	}
        }
        
        //go back to the original place
        for (int i =1; i <=numberOfVehicle;i++) {
        	vehicle.get(i).routine.add(new City(40,40,0));
        }
        
        double dist = getdistance(vehicle);
        //printdistance(vehicle);
        System.out.println("Initial total solution distance: " + dist);


      //Set as current best
//        ArrayList<Vehicle> best_vehicle = new ArrayList<Vehicle>(vehicle);
        ArrayList<Vehicle> best_vehicle = copyVehicle (vehicle);
        // Loop until system has cooled
        while (temp > 1) {
            ArrayList <Vehicle> tmp_vehicle = copyVehicle (vehicle);

            // Get a pair of random cars in the vehicle arraylist
            int vehicle_num1 = (int) (Math.random()*4+1);
            int vehicle_num2 = (int) (Math.random()*4+1);
            
            //determine which two vehicle need to swap
            Vehicle vehicle1 = tmp_vehicle.get(vehicle_num1);
            Vehicle vehicle2 = tmp_vehicle.get(vehicle_num2);
            
            //determine which two city to swap, return the index
            int citySwap_num1 = (int) (Math.random()*(vehicle1.routine.size()-2)+1);
            int citySwap_num2 = (int) (Math.random()*(vehicle2.routine.size()-2)+1);

            // Get the cities at selected positions in the tour
            City citySwap1 = vehicle1.routine.get(citySwap_num1);
            City citySwap2 = vehicle2.routine.get(citySwap_num2);
            
            if(citySwap1.demand+vehicle1.capacity>=citySwap2.demand 
            		&&citySwap2.demand+ vehicle2.capacity>=citySwap1.demand) {
            	// Swap them
            	vehicle1.swapCity(citySwap_num1,citySwap2);
            	vehicle2.swapCity(citySwap_num2, citySwap1);
            	//tmp_vehicle.get(vehicle_num1).routine.set(citySwap_num1, citySwap2);
            	//tmp_vehicle.get(vehicle_num2).routine.set(citySwap_num2, citySwap1);
            	
            	//update the demand
            	tmp_vehicle.get(vehicle_num1).capacity = tmp_vehicle.get(vehicle_num1).capacity 
            			+(citySwap1.demand-citySwap2.demand);
            	tmp_vehicle.get(vehicle_num2).capacity = tmp_vehicle.get(vehicle_num2).capacity 
            			-(citySwap1.demand-citySwap2.demand);
                double currentEnergy = getdistance(vehicle);
                double neighbourEnergy = getdistance(tmp_vehicle);
             // Decide if we should accept the neighbour
              if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
            	  vehicle = tmp_vehicle;
              }
              //Keep track of the best solution found
              if(getdistance(vehicle)<getdistance(best_vehicle)) {
              	best_vehicle = vehicle;
              }
            }else {
            }
                   
            // Cool system
            temp *= 1-coolingRate;
        }
        
        dist = getdistance(best_vehicle);
        System.out.println("Final total solution distance: " + dist);
    }
}