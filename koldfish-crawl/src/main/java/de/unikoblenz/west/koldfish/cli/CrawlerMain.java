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

	public static void main(String[] args) throws Exception {
		Options opts = new Options();
		// Take command line orguments for seed file and output file
		opts.addOption("s", "seed", true, "seed file");
		opts.addOption("o", "output", true, "output file");
		// ToDo add other options through options group
		CommandLineParser parser = new GnuParser();

		CommandLine cmd = null;
		cmd = parser.parse(opts, args);
		if (cmd.hasOption('h')) {
			help(opts);
			return;
		}
	}

	// .get converts a uri to path or iri is possible also?

	private static void run(CommandLine cmd) throws FileNotFoundException, IOException, JMSException {
		// check seed file

		Dictionary dictionary = new Dictionary();

		List<Long> actual = null;
		// gives the file path
		File seedList = new File(cmd.getOptionValue("s"));
		System.out.println("reading seeds from " + seedList.getAbsolutePath());
		if (!seedList.exists()) {
			throw new FileNotFoundException("No file found at " + seedList.getAbsolutePath());
		}
		Scanner s = new Scanner(new File("seedList"));

		while (s.hasNextLine()) {
			actual = dictionary.convertIris(Arrays.asList(s.nextLine()));
		}
		s.close();
		Iterable<Long> seeds = actual;

		// seeds = prepareSeedsIterable(seedList);
		// final File f = Paths.get(cmd.getOptionValue("s")).toFile();

		if (!cmd.hasOption('s')) {
			System.out.println("missing seed file");
			return;
		}
		BasicFrontier frontier = new BasicFrontier();
		// To do choose different frontier if there based by options
		for (Long l : seeds)
			frontier.add(l);
		// TO do : Move schedule to a different thread and seen will be common
		Seen _seen = new Seen_Queue();
		SpiderQueue q = new SpiderQueue(_seen);
		q.schedule(frontier);
		Crawler c = new Crawler(q, frontier, _seen);
		c.evaluateList();

	}

	private static Iterable<Long> prepareSeedsIterable(File seedList) {
		// Work with file extensions and stuff if required
		return null;
	}

	// give the possible options
	private static void help(Options opts) {
		HelpFormatter f = new HelpFormatter();
		f.printHelp("java -jar <jar> de.unikoblenz.west.koldfish.crawler.CrawlerMain" + " [" + "-h" + " |"
				+ " -i <seedfile>" + " -o <outputfile>" + " ]", opts);
		return;
	}

}
