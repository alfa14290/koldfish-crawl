package de.unikoblenz.west.koldfish.koldfish_test;

import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.unikoblenz.west.koldfish.frontier.BasicFrontier;
import de.unikoblenz.west.koldfish.frontier.Frontier;
import de.unikoblenz.west.koldfish.queue.SpiderQueue;
import de.unikoblenz.west.koldfish.seen.Seen_Queue;
import junit.framework.TestCase;

public class SpiderQueueTest extends TestCase {
	public void testFrontier() throws Exception {

		Frontier frontier = new BasicFrontier();
		Seen_Queue s = new Seen_Queue();
		s.add(new Long(6768678967898976L));
		SpiderQueue q = new SpiderQueue(s);
		frontier.add(new Long(6768678967898976L));
		frontier.add(new Long(6768678967898976L));
		frontier.add(new Long(6768678967898978L));
		Iterator<Long> it = frontier.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		q.schedule(frontier);
		
		System.out.println(q.spiderPoll());
		//assertEquals(true, )
		
		
	}
}