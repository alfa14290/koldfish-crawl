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

public class Crawler implements Runnable {

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
        System.out.println("it has been removed because of the error response");

        if (_seen.remove(new Long(response.getEncodedDerefIri()))) {
          CrawlerMain.pendingMessage.decrementAndGet();
        }

        synchronized (CrawlerMain.pendingMessage) {
          CrawlerMain.pendingMessage.notifyAll();
        }
      }

      @Override
      public void onDerefResponse(DerefResponse response) {

        Iterator<long[]> it = response.iterator();

        Frontier f2 = new BasicFrontier();
        CrawlerMain m = new CrawlerMain();
        // if(it!=null)
        // CrawlerMain.getatomicInt();

        while (it.hasNext()) {
          for (long value : it.next()) {
            // System.out.println("the response is " + value);
            f2.add(new Long(value));
            // System.out.println("the response is " + f2.getData());
          }

          q.schedule(f2);
        }

        if (_seen.hasBeenSeen(response.getEncodedDerefIri())) {
          CrawlerMain.pendingMessage.decrementAndGet();
        }
        synchronized (CrawlerMain.pendingMessage) {
          CrawlerMain.pendingMessage.notifyAll();
        }


        System.out.println("++++++++++++++++++++++++++++i am notifying ++++++++++++");



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
  // public void evaluateList() {
  // try {
  // Long l = q.spiderPoll();
  //
  // int pendingMessages = CrawlerMain.atomicInt.incrementAndGet();
  //
  // System.out.println("pending messages: " + pendingMessages);
  //
  // while (pendingMessages > 100) {
  // }
  //
  // dam.deref(l.longValue());
  //
  //
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  //
  // }

  @Override
  public void run() {
    while ((CrawlerMain.pendingMessage.get()) > 0 || !q.isEmpty()) {

      if (CrawlerMain.pendingMessage.get() > 100) {
        try {
          synchronized (CrawlerMain.pendingMessage) {
            CrawlerMain.pendingMessage.wait();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      // System.out.println("the queue size is " + q.queueSize());
      try {
        Long l = q.spiderPoll();
        System.out.println("the long value is " + l);

        int pendingMessages = CrawlerMain.pendingMessage.incrementAndGet();

        System.out.println("pending messages: " + pendingMessages);

        // while (pendingMessages > 100) {
        //
        // }

        dam.deref(l.longValue());

        if (q.isEmpty() && pendingMessages > 0) {
          synchronized (CrawlerMain.pendingMessage) {
            try {
              System.out.println("hello");
              CrawlerMain.pendingMessage.wait();
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }

        }

      } catch (Exception e) {
        e.printStackTrace();
      }
      // c.evaluateList();
      System.out.println("end loop");
    }
    System.out.println(
        "===================================== stopped ======================================");

  }
}
