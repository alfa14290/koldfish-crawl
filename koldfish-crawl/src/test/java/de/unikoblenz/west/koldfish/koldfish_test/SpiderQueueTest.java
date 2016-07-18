package de.unikoblenz.west.koldfish.koldfish_test;

import de.unikoblenz.west.koldfish.frontier.BasicFrontier;
import de.unikoblenz.west.koldfish.frontier.Frontier;
import de.unikoblenz.west.koldfish.queue.SpiderQueue;
import de.unikoblenz.west.koldfish.seen.Seen;
import de.unikoblenz.west.koldfish.seen.Seen_Queue;
import junit.framework.TestCase;

public class SpiderQueueTest extends TestCase {
	/**
	 * checks the Scheduling and polling of SpiderQueue Through spiderPoll
	 * 
	 * @throws Exception
	 */

	public void testQueue() throws Exception {

		Frontier frontier = new BasicFrontier();
		Seen s = new Seen_Queue();
		SpiderQueue q = new SpiderQueue(s);

		Long l1 = 6768678967898976L;
		Long l2 = 6768678967898978L;
		Long l3 = 6768678967898977L;
		s.add(l3);
		frontier.add(l1);
		frontier.add(l3);
		frontier.add(l2);
		q.schedule(frontier);
		
	
		assertEquals(q.spiderPoll(), l1);
		assertEquals(q.spiderPoll(), l2);
		assertNull(q.spiderPoll());
		assertNotSame(q.spiderPoll(), l3);

	}
}