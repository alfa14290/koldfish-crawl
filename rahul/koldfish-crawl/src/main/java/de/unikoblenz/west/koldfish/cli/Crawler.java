package de.unikoblenz.west.koldfish.cli;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.Service.Listener;

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
		dam =  new JmsDataAccessModule();
		DataAccessModuleListener listener = new DataAccessModuleListener() {
			
			public void onErrorResponse(ErrorResponse response) {
				_seen.remove(new Long(response.getEncodedDerefIri()));
				System.out.println("it has been removed");
			}
			
			public void onDerefResponse(DerefResponse response) {
				System.out.println(response.getEncodedDerefIri());
				Iterator<long[]> it = response.iterator();
				Frontier f2 = new BasicFrontier();
				//if(it!=null)
					//CrawlerMain.getatomicInt();
				while (it.hasNext()) {
					for (long value : it.next()) {
						f2.add(new Long(value));
					}
					
					q.schedule(f2);
				}

			}
		};
		dam.addListener(listener);
		dam.start();
       System.out.println("listnerstarted: " + dam.isStarted());
	}

	/**
	 * Poll the queue and implements the listner for the Dref Response. Add the
	 * response to frontier Set
	 */
	public void evaluateList() {

		try {
			Long l = q.spiderPoll();
			System.out.println("i take from queue");

			//Frontier f2 = new BasicFrontier();
			
			
		//	System.out.println("it started ->" +dam.isStarted());
			//while(CrawlerMain.atomicInt.compareAndSet(0, 0)){
			
			dam.deref(l.longValue());
			///System.out.println("it has been removed1");
			//}
			//this.f1.addAll(f2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
