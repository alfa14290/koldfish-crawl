package de.unikoblenz.west.koldfish.cli;



import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.unikoblenz.west.koldfish.dam.DataAccessModule;
import de.unikoblenz.west.koldfish.dam.DataAccessModuleListener;
import de.unikoblenz.west.koldfish.dam.DerefResponse;
import de.unikoblenz.west.koldfish.dam.ErrorResponse;
import de.unikoblenz.west.koldfish.dam.impl.JmsDataAccessModule;
import de.unikoblenz.west.koldfish.frontier.Frontier;
import de.unikoblenz.west.koldfish.queue.SpiderQueue;

public class Crawler {
	
	SpiderQueue q;
	
	public Crawler(SpiderQueue q){
	this.q = q;
		
	}
	
	public void evaluateList() {
        
			try {
				Long l = q.spiderPoll();
				 DataAccessModule dam = new JmsDataAccessModule();
				 List<long[]> data = new LinkedList<long[]>();
				
				 DataAccessModuleListener listener = new DataAccessModuleListener() {
					
					public void onErrorResponse(ErrorResponse response) {
						// TODO Auto-generated method stub
						
					}
					
					public void onDerefResponse(DerefResponse response) {
						// TODO Auto-generated method stub
						
					}
				} ;
				dam.addListener(listener);
				 dam.deref(l.longValue());
				 ///listener.
				 //=
					//      new DataAccessModuleListener();
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
	
 }
}
