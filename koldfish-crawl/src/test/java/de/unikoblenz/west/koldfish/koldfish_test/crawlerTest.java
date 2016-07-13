package de.unikoblenz.west.koldfish.koldfish_test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unikoblenz.west.koldfish.frontier.BasicFrontier;
import de.unikoblenz.west.koldfish.frontier.Frontier;
import junit.framework.TestCase;


public class crawlerTest extends TestCase{
	public void testcrawler() throws Exception {
	Frontier frontier = new BasicFrontier();
	//List<Long[]> data = new LinkedList<Long[]>();
	long[] derefResponse = {1L,2L, 3L};
	long[] derefResponse1 = {3L,5L, 1L};
	//data.add(set1);
	//data.add(set2);
//	frontier.addAll(derefResponse);
//	frontier.addAll(derefResponse1);
	Long url1 = 1L;
	Long url2 = 2L;
	Long url3 = 3L;
	Long url4 = 5L;
	Set<Long> urlSet = new HashSet<Long>();
	urlSet.add(url2);
	urlSet.add(url1);
	urlSet.add(url3);
	urlSet.add(url4);
	assertEquals(urlSet, frontier.getData());
	
	
	
	}
	

}
