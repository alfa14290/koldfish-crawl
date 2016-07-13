package de.unikoblenz.west.koldfish.cli;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

	public Crawler(SpiderQueue q, Frontier frontier, Seen _seen) {
		this.q = q;
		this.f1 = frontier;
		this._seen = _seen;

	}

	/**
	 * Poll the queue and implements the listner for the Dref Response. Add the
	 * response to frontier Set
	 */
	public void evaluateList() {

		try {
			Long l = q.spiderPoll();
			DataAccessModule dam = new JmsDataAccessModule();

			Frontier f2 = new BasicFrontier();
			DataAccessModuleListener listener = new DataAccessModuleListener() {

				public void onErrorResponse(ErrorResponse response) {
					_seen.remove(new Long(response.getEncodedDerefIri()));

				}

				public void onDerefResponse(DerefResponse response) {
					Iterator<long[]> it = response.iterator();

					while (it.hasNext()) {
						for (long value : it.next()) {
							f2.add(new Long(value));
						}
						

					}

				}
			};
			dam.addListener(listener);
			dam.deref(l.longValue());
			this.f1.addAll(f2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
