package cVRP_simulated_annealing;

import java.util.ArrayList;

public class Vehicle extends Tour {
	int capacity;
	int index;
	ArrayList<City> routine = new ArrayList<>();
	
	public Vehicle(int index, int capacity) {
		super();
		this.capacity = capacity;
		this.index = index;
	}
	
	public Vehicle(int index, int capacity,City city) {
		super();
		routine.add(city);
		this.capacity = capacity;
		this.index = index;
	}
	
	public boolean checkOversize(int demand) {
		return demand<capacity;
	}
	
	@Override
	public double getDistance() {
		double res=0;
		if(routine.size()==2) {
			return 0;
		}else {
			for (int i =0; i<routine.size()-1;i++) {
				City before = routine.get(i);
				City after = routine.get(i+1);
				double diff_x = before.x - after.x;
				double diff_y = before.y -after.y;
				double tmp_res = Math.sqrt(diff_x*diff_x + diff_y*diff_y);
				res = tmp_res+ res;
			}
		}
		return res;
	}
	
	public void swapCity(int index, City city) {
		routine.set(index, city);
	}
	public ArrayList<City> copyRoutine(ArrayList<City> routine){
		ArrayList<City> newRoutine = new ArrayList<City>();
		for (int i =0; i< routine.size();i++) {
			City tmp_city = routine.get(i);
			int tmp_x = tmp_city.x;
			int tmp_y = tmp_city.y;
			int tmp_demand = tmp_city.demand;
			newRoutine.add(new City(tmp_x,tmp_y,tmp_demand));
		}
		return newRoutine;
	}
	
}
