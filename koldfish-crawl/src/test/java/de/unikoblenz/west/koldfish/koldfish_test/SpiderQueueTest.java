package de.unikoblenz.west.koldfish.koldfish_test;

import java.util.Iterator;
import de.unikoblenz.west.koldfish.frontier.BasicFrontier;
import de.unikoblenz.west.koldfish.frontier.Frontier;
import de.unikoblenz.west.koldfish.queue.SpiderQueue;
import de.unikoblenz.west.koldfish.seen.Seen_Queue;
import junit.framework.TestCase;

public class SpiderQueueTest extends TestCase {
	/**
	 * checks the polling of SpiderQueue Through spiderPoll
	 * @throws Exception
	 */

	public void testQueue() throws Exception {

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
		
		
		
		//q.add(url1, false);
		//q.add(url2, false);
		//q.add(url3, true);

		
		assertEquals(q.spiderPoll(), url1);
		assertEquals(q.spiderPoll(), url2);
		assertEquals(q.spiderPoll(), url3);
		
		

	}
}