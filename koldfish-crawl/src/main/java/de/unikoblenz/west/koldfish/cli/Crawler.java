package de.unikoblenz.west.koldfish.cli;

import java.util.Iterator;

import de.unikoblenz.west.koldfish.dam.DataAccessModule;
import de.unikoblenz.west.koldfish.dam.DataAccessModuleListener;
import de.unikoblenz.west.koldfish.dam.ErrorResponse;
import de.unikoblenz.west.koldfish.dam.impl.JmsDataAccessModule;
import de.unikoblenz.west.koldfish.frontier.BasicFrontier;
import de.unikoblenz.west.koldfish.frontier.Frontier;
import de.unikoblenz.west.koldfish.messages.DerefResponse;
import de.unikoblenz.west.koldfish.queue.SpiderQueue;
import de.unikoblenz.west.koldfish.seen.Seen;

public class Crawler {

  SpiderQueue q;
  Frontier f1;
  Seen _seen;
  DataAccessModule dam;

  public Crawler(SpiderQueue q, Frontier frontier, Seen _seen) throws Exception {
    this.q = q;
    this.f1 = frontier;
    this._seen = _seen;
    dam = new JmsDataAccessModule();
    DataAccessModuleListener listener = new DataAccessModuleListener() {

      @Override
      public void onErrorResponse(ErrorResponse response) {
        if (_seen.remove(new Long(response.getEncodedDerefIri()))) {
          CrawlerMain.atomicInt.decrementAndGet();
        }

        System.out.println("it has been removed because of the error response");
      }

      @Override
      public void onDerefResponse(DerefResponse response) {
        System.out.println(response.getEncodedDerefIri());
        Iterator<long[]> it = response.iterator();

        Frontier f2 = new BasicFrontier();
        CrawlerMain m = new CrawlerMain();
        // if(it!=null)
        // CrawlerMain.getatomicInt();

        while (it.hasNext()) {
          for (long value : it.next()) {
            f2.add(new Long(value));

          }

          q.schedule(f2);
        }

        if (_seen.hasBeenSeen(response.getEncodedDerefIri())) {
          CrawlerMain.atomicInt.decrementAndGet();
        }


        System.out
            .println("frontier size is " + f2.size() + "should not be sameas " + q.queueSize());

      }
    };
    dam.addListener(listener);
    dam.start();

  }

  /**
   * Poll the queue and implements the listner for the Dref Response. Add the response to frontier
   * Set
   */
  public void evaluateList() {
    try {
      Long l = q.spiderPoll();

      int pendingMessages = CrawlerMain.atomicInt.incrementAndGet();

      System.out.println("pending messages: " + pendingMessages);

      while (pendingMessages > 100) {
      }

      dam.deref(l.longValue());


    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
