package de.unikoblenz.west.koldfish.crawler.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

import org.apache.jena.iri.IRIFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.koldfish.crawler.CrawlQueue;
import de.unikoblenz.west.koldfish.crawler.impl.messages.CrawlIri;
import de.unikoblenz.west.koldfish.dam.Executable;

/**
 * reads a text file with one IRI per line and adds it to a given CrawlQueue to start a crawl.
 * 
 * @author lkastler@uni-koblenz.de
 *
 */
public class SeedFeeder implements Executable<IOException> {

	
	private static final Logger log = LoggerFactory.getLogger(SeedFeeder.class);
	
	private final File seedFile;
	private final CrawlQueueInputProducer consumer;
	private final IRIFactory fac = IRIFactory.iriImplementation();
	
	public SeedFeeder(final File seedFile, CrawlQueue queue) {
		log.debug("feed " + seedFile.toString() + " to " + queue);
		
		this.seedFile = seedFile;
		this.consumer = new CrawlQueueInputProducer(queue);
	}
	
	@Override
	public void start() throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(seedFile));
		read.lines().forEach(consumer);
		read.close();
	}
	

	@Override
	public void terminate() throws IOException {
		// nothing to do
	}

	/**
	 * fills the queue with IRIs.
	 * 
	 * @author lkastler@uni-koblenz.de
	 *
	 */
	private class CrawlQueueInputProducer implements Consumer<String> {
		private final CrawlQueue queue;
		
		private CrawlQueueInputProducer(CrawlQueue queue) {
			this.queue = queue;
		}
		
		@Override
		public void accept(String t) {
			log.debug("feed: " + t);
			queue.add(new CrawlIri(fac.construct(t)));
		}
		
	}

	@Override
	public String toString() {
		return "SeedFeeder [seedFile=" + seedFile + ", consumer=" + consumer+ "]";
	}
}
