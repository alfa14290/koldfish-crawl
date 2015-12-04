package de.unikoblenz.west.koldfish.crawler;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.jena.rdf.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.koldfish.crawler.impl.Crawler;
import de.unikoblenz.west.koldfish.crawler.impl.FileOutputReceiver;
import de.unikoblenz.west.koldfish.dam.Negotiator;
import de.unikoblenz.west.koldfish.dam.Receiver;
import de.unikoblenz.west.koldfish.dam.impl.SimpleNegotiator;
import de.unikoblenz.west.koldfish.dam.impl.messages.ModelReportMessage;
import de.unikoblenz.west.koldfish.dam.messages.ReportMessage;

public class CrawlerMain implements Receiver {

	private static final Logger log = LoggerFactory.getLogger(CrawlerMain.class);
	private Crawler crawl;
	
	
	public static void main(String[] args) throws Exception {
		Options opts = new Options();
		// first endpoint
		opts.addOption("s", "seed", true, "seed file");
		opts.addOption("o", "output", true, "output file");
		CommandLineParser parser = new GnuParser();

		CommandLine cmd = null;
		cmd = parser.parse(opts, args);

		// help
		if (cmd.hasOption('h')) {
			help(opts);
			return;
		}

		if(!cmd.hasOption('s')) {
			log.error("missing seed file");
			return;
		}
		
		final File f = Paths.get(cmd.getOptionValue("s")).toFile();
		
		if(!cmd.hasOption("o")) {
			new CrawlerMain(f);
		}
		else {
			new CrawlerMain(f, new FileOutputReceiver(cmd.getOptionValue("o")));
		}
		
	}
	
	private static void help(Options opts) {
		HelpFormatter f = new HelpFormatter();
		f.printHelp(
				"java -jar <jar> de.unikoblenz.west.koldfish.crawler.CrawlerMain"
				+ " ["
				+ "-h"
				+ " |"
				+ " -i <seedfile>"
				+ " -o <outputfile>"
				+ " ]",
				opts);
		return;
	}
	
	public CrawlerMain(File seedFile, Receiver... recvs) throws Exception  {
		log.debug("start");	
		
		Negotiator<Model> neg = new SimpleNegotiator();
		neg.addReceiver(this);
		for(Receiver r : recvs) {
			neg.addReceiver(r);
		}
		
		neg.start();
		
		crawl = new Crawler(neg);
		
		crawl.start();
		
		crawl.feed(seedFile);
		
		log.debug("end");
	}

	/* (non-Javadoc)
	 * @see de.unikoblenz.west.koldfish.dam.Receiver#report(de.unikoblenz.west.koldfish.dam.messages.ReportMessage)
	 */
	@Override
	public void report(ReportMessage<?> rm) {
		if(rm instanceof ModelReportMessage) {
			log.debug("crawled URI: " + rm.getResourceIRI().toDisplayString());
		}
	}	
}
