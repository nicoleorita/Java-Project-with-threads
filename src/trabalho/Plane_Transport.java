package trabalho;

public class Plane_Transport extends Plane{

	private int i_plane_capacity; //-->só no de CARGA e PASSAGEIROS

	public Plane_Transport(int i_plane_number, String name, int fuel_capacity, int fuel_moment,
			int fuel_consumption, int plane_capacity, Airport airport) {
		super(i_plane_number, name, fuel_capacity,  fuel_moment, fuel_consumption, airport);
		this.i_plane_capacity = plane_capacity;

	}

	public int getCapacity(){
		return i_plane_capacity;
	}

	public void setPlaneCapacity(int plane_capacity){
		this.i_plane_capacity = plane_capacity;
	}
}
