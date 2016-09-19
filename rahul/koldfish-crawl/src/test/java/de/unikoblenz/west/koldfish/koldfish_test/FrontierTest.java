package de.unikoblenz.west.koldfish.koldfish_test;
import java.util.HashSet;
import java.util.Set;

import de.unikoblenz.west.koldfish.frontier.BasicFrontier;
import de.unikoblenz.west.koldfish.frontier.Frontier;
import junit.framework.TestCase;


public class FrontierTest extends TestCase {
	public void testFrontier() throws Exception {
		Long url1 = 6768678967898976L;
		Long url2 = 6768678967898978L;
		Set<Long> urlSet = new HashSet<Long>();
		urlSet.add(url2);
		urlSet.add(url1);
		Frontier frontier = new BasicFrontier();
		frontier.add(new Long(url1));
		frontier.add(new Long(url2));
        assertEquals(urlSet, frontier.getData());
		
		

	}

}
