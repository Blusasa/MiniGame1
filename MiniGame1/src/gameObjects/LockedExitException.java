/**Class: LockedExitException
 * @author: Michael Conner
 * @version: 1.0
 * Course: ITEC 3860 Spring 2022
 * Written: February 6, 2021
 * 
 * Custom Exception to be used when a player tries to go into a room that is still in a locked state
 */

package gameObjects;

public class LockedExitException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**Constructor
	 * @param message: message to use whenever the exception is caught
	 */
	public LockedExitException(String message) {
		super(message);
	}
	
}
