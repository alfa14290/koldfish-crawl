package de.unikoblenz.west.koldfish.frontier;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class BasicFrontier extends Frontier {
	Set<Long> _data;

	public BasicFrontier() {
		super();
		_data = Collections.synchronizedSet(new HashSet<Long>());
	}

	public Iterator<Long> iterator() {
		return _data.iterator();
	}
   
	/* (non-Javadoc)
	 * overloaded: for Merging the frontier objects
	 */
	public void addAll(Frontier f) {
		this._data.addAll(f.getData());
	}
    
	//public void addAll(Long[] c) {
		//Collections.addAll(this._data, c);
	//}
	/* (non-Javadoc)
	 * overloaded: Directly adding the long[] to frontier objects instead of using list
	 */
	public void addAll(long[] data) {
		for (long value : data) {
			this.add(new Long(value));
		}
	}

	public Set<Long> getData() {
		return this._data;
	}

	public void reset() {
		_data.clear();
	}

	public String toString() {
		return _data.toString();
	}

	public void add(Long l) {
		if (l != null) {
			_data.add(l);
		}

	}

	public void removeAll(Collection<Long> c) {
		_data.removeAll(c);

	}

}
