package de.unikoblenz.west.koldfish.koldfish_test;

import de.unikoblenz.west.koldfish.frontier.BasicFrontier;
import de.unikoblenz.west.koldfish.frontier.Frontier;
import de.unikoblenz.west.koldfish.queue.SpiderQueue;
import de.unikoblenz.west.koldfish.seen.Seen_Queue;
import junit.framework.TestCase;

public class SeenQueueTest extends TestCase {
	
	/**
	 * checks seen at the pooling and scheduling
	 * @throws Exception
	 */
	public void testSeen() throws Exception {

		Frontier frontier = new BasicFrontier();
		Seen_Queue s = new Seen_Queue();

		Long url1 = 6768678967898976L;
		Long url2 = 6768678967898978L;
		Long url3 = 6768678967898977L;
         
		s.add(url3);

		SpiderQueue q = new SpiderQueue(s);

		frontier.add(url1);
		frontier.add(url3);
		frontier.add(url2);
		frontier.remove(url1);
		
		
		q.add(url1, false);
		q.add(url2, false);
		q.add(url3, false);

		
		
		
		assertFalse(q.getSeen().hasBeenSeen(url1));
		assertFalse(q.getSeen().hasBeenSeen(url2));
		assertTrue(q.getSeen().hasBeenSeen(url3));
		
		

	}

}
