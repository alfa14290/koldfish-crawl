package de.unikoblenz.west.koldfish.koldfish_test;

import de.unikoblenz.west.koldfish.queue.SpiderQueue;
import de.unikoblenz.west.koldfish.seen.Seen_Queue;
import junit.framework.TestCase;

public class SeenQueueTest extends TestCase {

	/**
	 * checks seen queue
	 * 
	 * @throws Exception
	 */
	public void testSeen() throws Exception {

		Seen_Queue s = new Seen_Queue();
		SpiderQueue q = new SpiderQueue(s);

		Long l1 = 6768678967898976L;
		Long l2 = 6768678967898978L;
		Long l3 = 6768678967898977L;

		s.add(l3);

		assertEquals(q.getSeen(), s);
		assertFalse(q.getSeen().hasBeenSeen(l1));
		assertFalse(q.getSeen().hasBeenSeen(l2));
		assertTrue(q.getSeen().hasBeenSeen(l3));

	}

}
