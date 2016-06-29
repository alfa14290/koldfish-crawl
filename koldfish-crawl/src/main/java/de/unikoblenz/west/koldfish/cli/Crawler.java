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

	public Crawler(SpiderQueue q2, Frontier frontier, Seen _seen) {
		this.q = q2;
		this.f1 = frontier;
		this._seen = _seen;

	}

	public void evaluateList() {

		try {
			Long l = q.spiderPoll();
			DataAccessModule dam = new JmsDataAccessModule();
			// List<Long[]> data = new LinkedList<Long[]>();
			Frontier f2 = new BasicFrontier();
			DataAccessModuleListener listener = new DataAccessModuleListener() {
				// removing the Long value from seen if there is error
				public void onErrorResponse(ErrorResponse response) {
					_seen.remove(new Long(response.getEncodedDerefIri()));

				}

				public void onDerefResponse(DerefResponse response) {
					Iterator<long[]> it = response.iterator();
					// f2.addAll(it);

					while (it.hasNext()) {
						// data.addAll( it.next());
						
						// store directly in object instead of list of long
						f2.addAll(it.next());
						
					}

				}
			};
			dam.addListener(listener);
			dam.deref(l.longValue());
			this.f1.addAll(f2);

			// Set<Long> set = new HashSet<>();

			// for (Long[] values : data) {

			// Collections.addAll(set, values);
			// f2.addAll(Arrays.asList(values));
			// f2.addAll(f1);

			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
