package de.unikoblenz.west.koldfish.frontier;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

//import de.unikoblenz.west.koldfish.dam.*;

public abstract class Frontier implements Iterable<Long> {
	
	public abstract void add(Long l);

	public void addAll(Collection<Long> c) {
		for (Long l : c) {
			add(l);
		}
		c = null;
	}
	public void addAll(Frontier f){
		
	}
	//public abstract void addAll(Long[] c);
	public abstract void addAll(long[] data);
//	public abstract void remove(URI u);
	public abstract void removeAll(Collection<Long> c);
	public abstract void reset();
	public abstract Iterator<Long> iterator();

	public abstract Set<Long> getData();
}
