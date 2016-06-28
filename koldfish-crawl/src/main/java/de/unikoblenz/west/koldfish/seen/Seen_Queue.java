package de.unikoblenz.west.koldfish.seen;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;



public class Seen_Queue implements Seen {
	Set<Long> _set;
	public Seen_Queue() {
		_set = Collections.synchronizedSet(new HashSet<Long>());
	}

	public boolean remove(Long val){
		return this._set.remove(val);
	}
	
	public void clear() {
		_set.clear();

	}

	public boolean hasBeenSeen(Long l) {
		return _set.contains(l);
	}

	public boolean add(Collection<Long> longs) {
		return _set.addAll(longs);
	}

	public boolean add(Long l) {
		return _set.add(l);
	}

}
