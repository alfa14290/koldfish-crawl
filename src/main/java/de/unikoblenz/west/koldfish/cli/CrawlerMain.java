package de.unikoblenz.west.koldfish.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import de.unikoblenz.west.koldfish.dictionary.Dictionary;
import de.unikoblenz.west.koldfish.frontier.BasicFrontier;
import de.unikoblenz.west.koldfish.queue.SpiderQueue;
import de.unikoblenz.west.koldfish.seen.Seen;
import de.unikoblenz.west.koldfish.seen.Seen_Queue;

public class CrawlerMain {
  static AtomicInteger pendingMessage = new AtomicInteger(0);

  // public static volatile boolean keepProcessing= true;
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
   * Takes the file path consist of IRI and convert to Long to put into Frontier ToDo: choose
   * different frontier if there based by options ToDo: Move schedule to a different thread and seen
   * will be common
   * 
   * @param cmd
   * @throws Exception
   */

  private static void run(CommandLine cmd) throws Exception {
    Dictionary dictionary = new Dictionary();
    List<Long> actual = null;
    File seedList = new File(cmd.getOptionValue("s"));
    System.out.println("reading seeds from " + seedList.getAbsolutePath());
    if (!seedList.exists()) {
      throw new FileNotFoundException("No file found at " + seedList.getAbsolutePath());
    }
    if (!cmd.hasOption('s')) {
      System.out.println("missing seed file");
      return;
    }
    Scanner s = new Scanner(seedList);

    while (s.hasNextLine()) {
      actual = dictionary.transformRDF(Arrays.asList(s.nextLine()));
    }
    s.close();
    Iterable<Long> seeds = actual;


    BasicFrontier frontier = new BasicFrontier();
    for (Long l : seeds)
      frontier.add(l);

    Seen _seen = new Seen_Queue();
    SpiderQueue q = new SpiderQueue(_seen);

    Crawler c = new Crawler(q, frontier, _seen);
    q.schedule(frontier);

    Thread t = new Thread(c);
    t.start();

  }


  /**
   * provide the help to use options
   * 
   * @param opts
   */
  private static void help(Options opts) {
    HelpFormatter f = new HelpFormatter();
    f.printHelp("java -jar <jar> de.unikoblenz.west.koldfish.crawler.CrawlerMain" + " [" + "-h"
        + " |" + " -i <seedfile>" + " -o <outputfile>" + " ]", opts);
    return;
  }
  // public static int getatomicInt() {
  // return atomicInt.incrementAndGet();
  // }
  // public void cancelExecution()
  // {
  // keepProcessing = false;
  // }

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
