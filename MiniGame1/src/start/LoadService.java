/**Class: LoadService
 * @author: Michael Conner
 * @version: 1.0
 * Course: ITEC 3860 Spring 2022
 * Written: February 6, 2021
 * 
 * This class handles the loading of the 2 text files, exits and rooms.
 */


package start;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gameObjects.Exit;
import gameObjects.Exit.DIRECTION;
import gameObjects.Room;

public class LoadService {
	private final String roomFilePath = "/Resources/rooms.txt";
	private final String exitFilePath = "/Resources/exits.txt";
	
	/**Method: loadRooms
	 * Gathers data from the rooms.txt file and populates room objects that are added to a list
	 * @return a list of all rooms read in from the file
	 */
	protected List<Room> loadRooms(){
		//InputStream used to get files relative to the project root
		InputStream stream = this.getClass().getResourceAsStream(roomFilePath);
		Scanner in = new Scanner(stream);

		List<Room> rooms = new ArrayList<Room>();
		
		/* FILE STRUCTURE FOR REFERENCE WHILE READING LOOP:
		 * room number
		 * boolean t/f if the room is a key to another room
		 * boolean t/f if the room requires another room to be explored first
		 * room title
		 * boolean t/f if the room has been visited
		 * SINGLE line description of the room
		 * any special line used by a room 
		 */
		while(in.hasNext()) {
			int roomNum = Integer.parseInt(in.nextLine());
			boolean keyRoom = Boolean.parseBoolean(in.nextLine());
			boolean lockRoom = Boolean.parseBoolean(in.nextLine());
			String title = in.nextLine();
			boolean hasVisited = Boolean.parseBoolean(in.nextLine());
			String description = in.nextLine();
			String specialLine = in.nextLine();
			Room room = null;
			//if a room has no special line, then "none" put in the file. The first if checks if the special line isn't "none"
			if(!specialLine.equalsIgnoreCase("none")) {
				room = new Room(roomNum, title, hasVisited, description, specialLine);
			} else if(keyRoom){ //make a room instance that is a key to another room
				room = new Room(roomNum, keyRoom, title, hasVisited, description);
			} else if(lockRoom) {//make a room instance that needs another room to be explored first
				room = new Room(roomNum, title, lockRoom, hasVisited, description);
			} else { //a generic room with no key/lock/special line
				room = new Room(roomNum, title, hasVisited, description);
			}
			rooms.add(room);
		}
		in.close();
		return rooms;

	}
	
	/**Method: populateRoomExits
	 * takes all the rooms and adds their exits that are read off file
	 * @param rooms: the list of all the room instances
	 */
	protected void populateRoomExits(List<Room> rooms) {
		//InputStream to be passed to the scanner. Used to get files relative to the project root
		InputStream stream = this.getClass().getResourceAsStream(exitFilePath);
		Scanner in = new Scanner(stream);
		
		/* FILE STRUCTURE FOR REFERENCE WHILE READING LOOP:
		 * Room Number / Name the the following exits are for
		 * NORTH #
		 * SOUTH #
		 * EAST #
		 * WEST #
		 * UP #
		 * DOWN #
		 */
		while(in.hasNext()) {
			//take in the whole line to ensure we move to each line correctly
			String roomNumLine = in.nextLine();
			
			//the name is included on the first line for readability, but we only the need the number to find the correct room
			int roomNum = Integer.parseInt(roomNumLine.substring(0, 1));
			
			//the room that all the upcoming exits are read in for is filtered out of the list of all rooms so we can add each exit to the room
			Room currentRoom = rooms.stream()
									.filter(r -> r.getRoomNum() == roomNum)
									.findFirst()
									.get();
			
			//i is 6 since there are only 6 directions between rooms
			for(int i = 1; i <= 6; i++) {
				//reads in a whole line to make sure we move to the next line correctly
				String[] exitLine = in.nextLine().split(" ");
				String direction = exitLine[0];
				
				//grab the destination room number for searching later
				int nextRoomNum = Integer.parseInt(exitLine[1]);
				
				//if an exit is not a valid exit, than it is paired with a -1 value in the file(IE: NORTH -1 means that there is no exit to the NORTH). This "if" skips all of those
				if(nextRoomNum >= 0) {
					//enums are used in the EXIT class for cardinality, this finds the enum out of the string that is passed in
					DIRECTION directionENUM = DIRECTION.valueOf(direction);
					
					//grab the destination room to add to a new exit object
					Room nextRoom = rooms.stream()
							.filter(r -> r.getRoomNum() == nextRoomNum)
							.findFirst()
							.get();
					
					//the enum DIRECTION and destination room are added to an object for use by the ROOM class
					Exit exit = new Exit(directionENUM, nextRoom);
					currentRoom.addExit(exit);
				}
			}
		}
		
		in.close();
	}
}



















