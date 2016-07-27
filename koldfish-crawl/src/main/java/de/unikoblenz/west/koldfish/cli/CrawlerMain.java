package de.unikoblenz.west.koldfish.cli;

import de.unikoblenz.west.koldfish.dictionary.Dictionary;
import de.unikoblenz.west.koldfish.frontier.*;

import de.unikoblenz.west.koldfish.queue.SpiderQueue;
import de.unikoblenz.west.koldfish.seen.Seen;
import de.unikoblenz.west.koldfish.seen.Seen_Queue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.jms.JMSException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.jena.iri.IRI;

public class CrawlerMain {
	/**
	 * Parse the command line options To Do: Add another options if required
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Options opts = new Options();
		opts.addOption("s", "seed", true, "seed file");
		opts.addOption("o", "output", true, "output file");
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = null;
		cmd = parser.parse(opts, args);
		if (cmd.hasOption('h')) {
			help(opts);
			return;
		}
		run(cmd);
	}

	/**
	 * Takes the file path consist of IRI and convert to Long to put into
	 * Frontier ToDo: choose different frontier if there based by options ToDo:
	 * Move schedule to a different thread and seen will be common
	 * 
	 * @param cmd
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JMSException
	 */

	private static void run(CommandLine cmd) throws FileNotFoundException, IOException, JMSException {
		Dictionary dictionary = new Dictionary();
		List<Long> actual = null;
		File seedList = new File(cmd.getOptionValue("s"));
		System.out.println("reading seeds from " + seedList.getAbsolutePath());
		if (!seedList.exists()) {
			throw new FileNotFoundException("No file found at " + seedList.getAbsolutePath());
		}
		Scanner s = new Scanner(seedList);

		while (s.hasNextLine()) {
			actual = dictionary.convertIris(Arrays.asList(s.nextLine()));
		}
		s.close();
		Iterable<Long> seeds = actual;

		if (!cmd.hasOption('s')) {
			System.out.println("missing seed file");
			return;
		}
		BasicFrontier frontier = new BasicFrontier();
		for (Long l : seeds)
			frontier.add(l);
		Seen _seen = new Seen_Queue();
		SpiderQueue q = new SpiderQueue(_seen);
		Crawler c = new Crawler(q, frontier, _seen);
		do {
			// TO Do!! check the size of queue then schedule from frontier
			q.schedule(frontier);
			c.evaluateList();
		}while (!(q.isEmpty()));
	}

	/**
	 * provide the help to use options
	 * 
	 * @param opts
	 */
	private static void help(Options opts) {
		HelpFormatter f = new HelpFormatter();
		f.printHelp("java -jar <jar> de.unikoblenz.west.koldfish.crawler.CrawlerMain" + " [" + "-h" + " |"
				+ " -i <seedfile>" + " -o <outputfile>" + " ]", opts);
		return;
	}

	/**
	 * Work with file extensions and stuff if required
	 * 
	 * @param seedList
	 * @return
	 */
	private static Iterable<Long> prepareSeedsIterable(File seedList) {
		return null;
	}

}
