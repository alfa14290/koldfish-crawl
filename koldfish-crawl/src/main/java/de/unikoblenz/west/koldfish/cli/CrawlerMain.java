package de.unikoblenz.west.koldfish.cli;
import de.unikoblenz.west.koldfish.frontier.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;



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
	private static void run(CommandLine cmd) throws FileNotFoundException, IOException {
		// check seed file
		Iterable<Long> seeds = null;
		// if (cmd.hasOption("s")) {
		File seedList = new File(cmd.getOptionValue("s"));
		System.out.println("reading seeds from " + seedList.getAbsolutePath());
		if (!seedList.exists()) {
			throw new FileNotFoundException("No file found at " + seedList.getAbsolutePath());
		}
		seeds = prepareSeedsIterable(seedList);
		// final File f = Paths.get(cmd.getOptionValue("s")).toFile();

		if (!cmd.hasOption('s')) {
			System.out.println("missing seed file");
			return;
		}
		Frontier frontier = new BasicFrontier();
		//To do choose different frontier if there based by options
		for (Long l: seeds)
			frontier.add(l);
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
