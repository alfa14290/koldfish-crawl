package de.unikoblenz.west.koldfish.queue;

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

	/**
	 * put Longs from frontier to queue ToDo: make method synchronized for
	 * thread safety!!!--needs to check
	 * 
	 * @param f
	 */
	public void schedule(Frontier f) {
		Iterator<Long> it = f.iterator();
		while (it.hasNext()) {
			Long l = it.next();
			if (!checkSeen(l)) {
				q.add(l);
			}
			//it.remove();
		}
	}

	/**
	 * 
	 * @return the Long value for the dereference(de.unikoblenz.west.dam) ToDo:
	 *         make method synchronized for thread safety!!!--needs to check
	 * @throws Exception
	 */
	public Long spiderPoll() throws Exception {
		Long next = null;
		while (!q.isEmpty()) {
			next = q.poll();
			if (!checkSeen(next)) {
				setSeen(next);
				return next;
			}
		}
		return next;
	}

	/**
	 * check from seen if this long value has seen or
	 * not(de.unikoblenz.west.koldfish.seen)
	 * 
	 * @param l
	 * @return true if its seen
	 */
	private boolean checkSeen(Long l) {
		if (l == null) {
			throw new NullPointerException("l cannot be null");
		}

		return _seen.hasBeenSeen(l);
	}

	/**
	 * Set the Long value to seen. Add to Seen
	 * Set(de.unikoblenz.west.koldfish.seen)
	 * 
	 * @param l
	 */
	private void setSeen(Long l) {
		if (l != null)
			_seen.add(l);
	}

	/**
	 * Getter for the Seen instance of this queue. only for Test case
	 * 
	 * @return instance of seen set 
	 */
	public Seen getSeen() {
		return _seen;
	}
	

	/**
	 * 
	 * @return true if queue is empty
	 */
	public boolean isEmpty() {
		return q.isEmpty();
	}
}