package cVRP_simulated_annealing;

public class City implements Cloneable{
    int x;
    int y;
    int demand;
    
    // Constructs a randomly placed city
    public City(int demand){
        this.x = (int)(Math.random()*200);
        this.y = (int)(Math.random()*200);
        this.demand = demand;
    }
    
    // Constructs a city at chosen x, y location
    public City(int x, int y,int demand){
        this.x = x;
        this.y = y;
        this.demand = demand;
    }
    public City(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    // Gets city's x coordinate
    public int getX(){
        return this.x;
    }
    
    // Gets city's y coordinate
    public int getY(){
        return this.y;
    }
    
    // Get city's capacity
    public int getDemand(){
        return this.demand;
    }
    
    // Gets the distance to given city
    public double distanceTo(City city){
        int xDistance = Math.abs(this.getX() - city.getX());
        int yDistance = Math.abs(this.getY() - city.getY());
        double distance = Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
        return distance;
    }
    
    @Override
    public String toString(){
        return getX()+", "+getY()+"," + getDemand();
    }
}