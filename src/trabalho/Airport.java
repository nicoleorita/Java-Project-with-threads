package trabalho;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;



import ServerPack.GUIServer;
import ServerPack.Server;

public class Airport {

	private String s_name;
	private GUIServer gui;
	private LinkedList<Plane> l_planes_queue;
	private LinkedList<Runway> l_runways;
	private Server server;

	public Airport(String name, Server server){
		this.s_name = name;
		this.server = server;

		gui = new GUIServer(this); 

		l_planes_queue = new LinkedList<>();
		l_runways = new LinkedList<>();

		for (int i = 0; i < 2 ; i++) 
			l_runways.add(new Runway(true, i));
	}

	public Runway random_runway(){
		int random = (int)(Math.random()*50);
		if (random%2==0)
			return l_runways.getFirst();
		return l_runways.getLast();
	}

	public void addPlanesToAirport(LinkedList<Plane> planes){ 
		for(Plane p : planes){								
			l_planes_queue.add(p);
		}
		addPlanesTextArea();
	}

	public void addPlanesTextArea (){
		LinkedList<Plane> l_planes_waiting = new LinkedList<>();
		Collections.sort(l_planes_queue, new ComparatePlanes());
		for(Plane p : l_planes_queue){
			if(p.getPhase() == Phase.QUEUE || p.getPhase() == Phase.WAITING )
				l_planes_waiting.add(p);
		}
		gui.refreshlist(l_planes_waiting);
	}


	public void manage_planes(){
		for(Plane p : l_planes_queue) {
			//p.setRunway(random_runway());
			p.setPhase(Phase.WAITING);
			p.start();
		}

		organize();
	}


	public synchronized void getPlaneLanding(int i_plane_number) {
		for(Iterator<Plane> itr = l_planes_queue.iterator(); itr.hasNext(); ) {
			Plane p = itr.next();
			if(p.getPlaneNumber() == i_plane_number) {
				p.setPhase(Phase.LANDING);
				gui.refreshRunway(p.getRunway().get_nr(), p);
				break;
			}

		}
		addPlanesTextArea();
	}

	public synchronized void planeArrived(int i_plane_number) {
		for(Plane p : l_planes_queue) {
			if(p.getPlaneNumber() == i_plane_number) {
				p.setPhase(Phase.ARRIVED);
				gui.clearRunway(p.getRunway().get_nr());
				break;
			}
		}
		remove();
		//System.out.println("QUEUE size: " + l_planes_queue.size());
		if(l_planes_queue.size() == 0) {
			this.server.closeClientConnection();
			System.out.println("Shut off Client");
		}
		addPlanesTextArea();
		organize();
	}

	private boolean existsPriorities() {
		for(Plane p : l_planes_queue) {
			if(p.getFuel_moment() < p.getFuel_capacity()*0.1)
				return true;
		}
		return false;
	}

	private boolean existsMedic() {
		for(Plane p : l_planes_queue) {
			if(p instanceof Plane_Medic)
				return true;
		}
		return false;
	}

	private void organize() {
		if(existsPriorities()) {
			for(Plane p : l_planes_queue) {
				if(p.getFuel_moment() < p.getFuel_capacity()*0.1 
						&& p.getPhase() == Phase.WAITING) {
					Runway r = random_runway();
					if(r.isOpen()) {
						p.setPhase(Phase.QUEUE);
						p.setRunway(r);
					}
					else {
						p.setPhase(Phase.QUEUE);
						if(r.get_nr()==0)
							p.setRunway(l_runways.getLast());
						else
							p.setRunway(l_runways.getFirst());
					}
				}
			}
		}
		if(!existsPriorities() && existsMedic()) {
			for(Plane p : l_planes_queue) {
				if(p instanceof Plane_Medic && p.getPhase() == Phase.WAITING) {
					Runway r = random_runway();
					if(r.isOpen()) {
						p.setPhase(Phase.QUEUE);
						p.setRunway(r);
					}
					else {
						p.setPhase(Phase.QUEUE);
						if(r.get_nr()==0)
							p.setRunway(l_runways.getLast());
						else
							p.setRunway(l_runways.getFirst());
					}
				}
			}
		}
		if(!existsPriorities() && !existsMedic()) {
			if(l_planes_queue.getFirst().getPhase() == Phase.WAITING) {
				Runway r = random_runway();
				if(r.isOpen()) {
					l_planes_queue.getFirst().setPhase(Phase.QUEUE);
					l_planes_queue.getFirst().setRunway(r);
				}
				else {
					l_planes_queue.getFirst().setPhase(Phase.QUEUE);
					if(r.get_nr() == 0)
						l_planes_queue.getFirst().setRunway(l_runways.getLast());
					else
						l_planes_queue.getFirst().setRunway(l_runways.getFirst());
				}
			}
		}
	}

	public void remove() {
		for(Iterator<Plane> itr = l_planes_queue.iterator(); itr.hasNext();) {
			Plane p = itr.next();
			if(p.getPhase() == Phase.ARRIVED) {
				itr.remove();
				break;
			}
			notifyAll();
		}
	}

	public void informRunway (boolean open, int r1){
		if(open) {
			if(r1 == 0) {
				System.out.println("Abriu runway 1");
				l_runways.getFirst().setOpen(true);
				organize();
			}
			else {
				System.out.println("Abriu runway 2");
				l_runways.getLast().setOpen(true);
				organize();
			}
		}
		if(!open) {
			if(r1 == 0) {
				System.out.println("Fechou runway " + r1);
				l_runways.getFirst().setOpen(false);
				for(Iterator<Plane> itr = l_planes_queue.iterator(); itr.hasNext(); ) {
					Plane p = itr.next();
					if(p.getPhase() == Phase.QUEUE && p.getRunway().get_nr() == r1) {
						p.setPhase(Phase.WAITING);	
					}
				}
				organize();
			}
			else{ 
				System.out.println("Fechou runway " + r1);
				l_runways.getLast().setOpen(false);	
				
				for(Iterator<Plane> itr = l_planes_queue.iterator(); itr.hasNext(); ) {
					Plane p = itr.next();
					if(p.getPhase() == Phase.QUEUE && p.getRunway().get_nr() == r1) { 
						p.setPhase(Phase.WAITING);
					}
				}
				organize();
			}
		}
	}
}
