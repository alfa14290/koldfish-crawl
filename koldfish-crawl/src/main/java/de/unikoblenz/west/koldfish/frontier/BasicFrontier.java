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
