package de.unikoblenz.west.koldfish.seen;

import java.net.URI;
import java.util.Collection;

public interface Seen {
	public boolean hasBeenSeen(Long l);

	
	public boolean add(Collection<Long> longs);

	
	public boolean add(Long l);
	public boolean remove(Long l);

}



