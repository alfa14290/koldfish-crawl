/**
 * 
 */
package de.unikoblenz.west.koldfish.seen;

/**
 * @author lkastler
 *
 */
public interface Seen {

  /**
   * @param l
   * @return
   */
  public boolean hasBeenSeen(Long l);

  /**
   * @param l
   */
  public boolean add(Long l);

  /**
   * @param long1
   * @return
   */
  public boolean remove(Long long1);


}
