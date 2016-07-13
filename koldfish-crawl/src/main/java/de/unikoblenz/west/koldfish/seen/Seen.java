package de.unikoblenz.west.koldfish.seen;

import java.util.Collection;

public interface Seen {
	/**
	 * 
	 * @param l
	 * @return True if Long value is seen 
	 */
	public boolean hasBeenSeen(Long l);

	/**
	 * 
	 * @param longs
	 * @return True after Adding the Long Collection
	 */
	//public boolean add(Collection<Long> longs);

	/**
	 * 
	 * @param l
	 * @return True after Adding  the Long Value
	 */
	public boolean add(Long l);
	/**
	 * 
	 * @param l
	 * @return True after Removing the Long Value
	 */
	public boolean remove(Long l);

}



