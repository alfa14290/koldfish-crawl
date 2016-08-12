package de.unikoblenz.west.koldfish.frontier;

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

	/**
	 * @return The iterator over frontier set
	 */
	public Iterator<Long> iterator() {
		return _data.iterator();
	}

	/**
	 * @param f
	 * Add the frontier
	 */
	public void addAll(Frontier f) {
		this._data.addAll(f.getData());
	}

	/**
	 * @param l
	 * Add the long value to frontier set
	 */
	public void add(Long l) {
		if (l != null) {
			_data.add(l);
		}
	}

	/**
	 * @param val
	 * Remove the long value to frontier set
	 */
	public boolean remove(Long val) {
		return this._data.remove(val);
	}

	/**
	 * @return the data in the frontier(change to private later)
	 */
	public Set<Long> getData() {
		return this._data;
	}
	public int size(){
		return _data.size();
	}

	public String toString() {
		return _data.toString();
	}

	// public void addAll(long[] data) {
	// for (long value : data) {
	// this.add(new Long(value));
	// }
	// }

	// public void removeAll(Collection<Long> c) {
	// _data.removeAll(c);}

}
