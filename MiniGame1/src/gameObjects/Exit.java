/**Class: Exit
 * @author: Michael Conner
 * @version: 1.0
 * Course: ITEC 3860 Spring 2022
 * Written: February 6, 2021
 * 
 * This class is used as a way to store and connect rooms via the direction the next room is in and the next room itself.
 */

package gameObjects;

public class Exit {
	
	/**ENUM: DIRECTION
	 * Used to handle directions to the next room.
	 */
	public enum DIRECTION{
		NORTH("N"), SOUTH("S"), EAST("E"), WEST("W"), UP("U"), DOWN("D");
		
		private String abrv;
		
		/**Constructor
		 * @param abrv: letter used as abbreviation for the direction enum
		 */
		private DIRECTION(String abrv) {
			this.abrv = abrv;
		}
		
		/**Method: getAbbreviation
		 * 
		 * @return a string abbreviation of the enum
		 */
		public String getAbbreviation() {
			return this.abrv;
		}
	}
	
	private DIRECTION direction;
	private Room nextRoom;
	
	/**Constructor
	 * builds an Exit with a direction and the destination room
	 * @param direction: the enum direction that the next room is
	 * @param nextRoom: the next room when exiting
	 */
	public Exit(DIRECTION direction, Room nextRoom) {
		this.direction = direction;
		this.nextRoom = nextRoom;
	}
	
	/**Method: getDirection
	 * 
	 * @return DIRECTION: the direction for the current instance
	 */
	public DIRECTION getDirection() {
		return this.direction;
	}
	
	/**Method: getNextRoom
	 * 
	 * @return Room: the next room for the current instance
	 */
	public Room getNextRoom() {
		return this.nextRoom;
	}
}
