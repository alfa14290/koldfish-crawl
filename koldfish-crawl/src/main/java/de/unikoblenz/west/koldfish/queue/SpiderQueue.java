package de.unikoblenz.west.koldfish.queue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.unikoblenz.west.koldfish.frontier.Frontier;
import de.unikoblenz.west.koldfish.seen.*;;

public class SpiderQueue {

	public Seen _seen;
	Queue<Long> q;

	public SpiderQueue(Seen seen) {
		super();
		_seen = seen;
		q = new ConcurrentLinkedQueue<Long>();
	}

	// put Longs from frontier to queue
	// use synchronized for thread safety!!!--needs to check
	public void schedule(Frontier f) {
		Iterator<Long> it = f.iterator();

		while (it.hasNext()) {
			Long l = it.next();
			if (!checkSeen(l)) {
				add(l, true);

			}

		}
	}

	// use synchronized for thread safety!!!--needs to check
	public Long spiderPoll() throws Exception {
		if (q == null) {
			// return null;
			throw new Exception("Queue not intialized");
		}

		Long next = null;

		int empty = 0;

		while (!q.isEmpty()) {
			next = q.poll();

			if (!checkSeen(next)) {
				setSeen(next);
				return next;
			}

		}

		return next;

	}
    // adding to queue by checking if this is not seen
	private void add(Long l, boolean Processed) {

		if (!Processed) {
			if (q == null) {
				q = new ConcurrentLinkedQueue<Long>();
				q.add(l);
			}
			q.add(l);
		}
	}
    // check from seen if this long value has seen or not 
	//retrun true if its seen
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
   // setting to seen in the Seen set with the help of addSeen
	void setSeen(long l) {
		addSeen(l);
	}

	/**
	 * Setter for the Seen instance to use.
	 * 
	 * @param seen
	 */
	public void setSeen(Seen seen) {
		_seen = seen;
	}

	/**
	 * Getter for the Seen instance of this queue.
	 * 
	 * @return
	 */
	public Seen getSeen() {
		return _seen;
	}
	/**
	 * @param _seen
	 * @param q
	 */

}