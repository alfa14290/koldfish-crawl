package de.unikoblenz.west.koldfish.queue;

import java.net.URI;
import java.util.Iterator;



import de.unikoblenz.west.koldfish.frontier.Frontier;
import de.unikoblenz.west.koldfish.seen.*;;

public class SpiderQueue {

	public Seen _seen;

	public void schedule(Frontier f) {
		Iterator<Long> it = f.iterator();
		while (it.hasNext()) {
			Long l = it.next();
			if (!checkSeen(l)) {
				add(l, true);
			}

		}
	}

	private void add(Long l, boolean Processed) {
		// TODO Auto-generated method stub

	}

	private boolean checkSeen(Long l) {
		if (l == null) {
			throw new NullPointerException("l cannot be null");
		}

		return _seen.hasBeenSeen(l);
	}

	public void addSeen(Long l) {
		if (l != null)
			_seen.add(l);

	}
	void setSeen(long l){
		addSeen(l);
	}

	/**
	 * Setter for the Seen instance to use. 
	 * @param seen
	 */
	public void setSeen(Seen seen) {
		_seen = seen;
	}

	/**
	 * Getter for the Seen instance of this queue.
	 * @return
	 */
	public Seen getSeen() {
		return _seen;
	}

}