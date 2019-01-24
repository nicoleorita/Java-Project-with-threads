package trabalho;

import java.util.Comparator;

public class ComparatePlanes implements Comparator<Plane> {

	public int compare(Plane p1, Plane p2) {
		
		if(p1.getFuel_moment() < p1.getFuel_capacity()*0.1)
			return -1;
		if(p2.getFuel_moment() < p2.getFuel_capacity()*0.1)
			return 1;
		if(p1 instanceof Plane_Medic)
			return -1;
		if(p2 instanceof Plane_Medic)
			return 1;
		if(p1.getNumero() < p2.getNumero())
			return -1;
		return 1;
}
}