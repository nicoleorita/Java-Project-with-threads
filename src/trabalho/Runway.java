package trabalho;

import java.util.concurrent.atomic.AtomicBoolean;

public class Runway extends Thread{

	private boolean b_open, b_landing;
	private int i_nr;
	private Plane plane;
	
	public Runway(boolean open, int nr){
		this.b_open = open;
		this.i_nr = nr;
	
		this.plane = null;
		this.b_landing = false;
	}
	
	public synchronized void planeLanding(Plane plane) throws InterruptedException {
		while(b_landing || !b_open) {
			System.out.println(plane.i_plane_number + " is waiting in " + this.i_nr);
			wait();
		}
		b_landing = true;
		this.plane = plane;
		notifyAll();
	}
	
	public synchronized void free() {
		b_landing = false;
		//this.plane = null;
		notifyAll();
	}
	
	public boolean isOpen(){
		return b_open;
	}
	
	public synchronized void setOpen(boolean b_open){
		this.b_open = b_open;
		//if(this.b_open)
		notifyAll();
	}
	
	public int get_nr() {
		return i_nr;
	}
	
	@Override
	public String toString() {
		return "Runway" + i_nr;
	}
	
}
