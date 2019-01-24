package trabalho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Plane extends Thread{

	private static final int ARRIVING_TIME = 100;
	private static final int SLEEP_TIME = 50;

	private Runway r_runway;
	private Phase e_phase;
	private Type e_type;

	private Airport	airport;
	private int numero;
	
	protected String s_name;
	protected int i_fuel_capacity,
	i_fuel_consumption,
	i_fuel_moment,
	i_time_landing,
	i_timer,
	i_plane_number;


	public Plane(int i_plane_number, String name,
			int fuel_capacity,int fuel_moment,
			int fuel_consumption, Airport airport){
		super();
		this.i_plane_number = i_plane_number;
		this.s_name = name;
		this.i_fuel_capacity = fuel_capacity;
		this.i_fuel_consumption = fuel_consumption;
		this.i_fuel_moment = fuel_moment;
		this.e_phase = Phase.WAITING;
		this.airport = airport;
	}
	
	@Override
	public void run() {

		i_time_landing = 0;
		i_timer=0;

		while(!isInterrupted()){
			
			try {
				if(e_phase == Phase.LANDING) {
					i_time_landing++;
				}
			
				if(i_time_landing >= ARRIVING_TIME) {
					r_runway.free();
					this.airport.planeArrived(this.i_plane_number);
					System.out.println(this.i_plane_number + " Plane" + 
							this.s_name + " ARRIVED at " + this.r_runway);
					interrupt();
				}
				
				if(e_phase == Phase.QUEUE) {
					System.out.println(this.i_plane_number + " QUEUED at runway " 
							+ r_runway.get_nr());
					r_runway.planeLanding(this);
					this.airport.getPlaneLanding(this.i_plane_number);
					System.out.println(this.i_plane_number + " Plane" + 
							this.s_name + " is " + this.e_phase +" at " + this.r_runway);
				}

				sleep(SLEEP_TIME);
				i_timer++;
				if(i_timer % 100 == 0)
					i_fuel_moment -= i_fuel_consumption;
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}	
	}
	

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public void looseFuel() {
		i_fuel_moment -= i_fuel_consumption;
	}

	public Phase getPhase(){
		return e_phase;
	}

	public void setPhase(Phase e_phase){
		this.e_phase = e_phase;
	}

	public Type getType(){
		return e_type;
	}

	public void setType(Type e_type){
		this.e_type = e_type;
	}

	public int getFuel_capacity(){
		return i_fuel_capacity;
	}

	public void setFuel_capacity(int i_fuel_capacity){
		this.i_fuel_capacity = i_fuel_capacity;
	}

	public int getFuel_moment(){
		return i_fuel_moment;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public String getPlaneName () {
		return s_name;
	}

	public void setRunway(Runway r1){
		System.out.println("Changed from " + this.r_runway + " to " + r1);
		this.r_runway=r1;
	}
	
	public Runway getRunway(){
		return r_runway;
	}
	
	public String toString(){
		return s_name.charAt(0) + "[" + i_plane_number + " - "+ " " +s_name +" , " +(int)((i_fuel_moment*100)/i_fuel_capacity) + "%]";		
	}
	
	public int getPlaneNumber() {
		return i_plane_number;
	}
}
