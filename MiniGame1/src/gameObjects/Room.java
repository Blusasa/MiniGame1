/**Class: Room
 * @author: Michael Conner
 * @version: 1.0
 * Course: ITEC 3860 Spring 2022
 * Written: February 6, 2021
 * 
 * This class handles all data for a room object. It also handles validating input for any of its exits and ensures that no locked exit can be entered upon request
 */

package gameObjects;

import java.util.ArrayList;
import java.util.List;

import gameObjects.Exit.DIRECTION;

public class Room {
	private int roomNum;
	private String name;
	private String[] description;
	private String specialLine;
	private boolean hasVisited;
	private boolean keyRoom;
	private boolean lockRoom;
	private List<Exit> exits;
	
	/**Constructor
	 * builds a generic room object
	 * @param roomNum: the number of the room
	 * @param name: The name of the room 
	 * @param hasVisited: boolean that is for whether the player has been to this room before 
	 * @param description: the description of the room
	 */
	public Room(int roomNum, String name, boolean hasVisited, String description) {
		this.roomNum = roomNum;
		this.keyRoom = false;
		this.lockRoom = false;
		this.name = name;
		this.hasVisited = hasVisited;
		this.description = divideDescription(description);
		this.exits = new ArrayList<>();
	}
	
	/**Constructor
	 * builds a room object that has a special line
	 * @param roomNum: the number of the room
	 * @param name: The name of the room 
	 * @param hasVisited: boolean that is for whether the player has been to this room before 
	 * @param description: the description of the room
	 * @param specialLine
	 */
	public Room(int roomNum, String name, boolean hasVisited, String description, String specialLine) {
		this.roomNum = roomNum;
		this.keyRoom = false;
		this.name = name;
		this.hasVisited = hasVisited;
		this.description = divideDescription(description);
		this.specialLine = specialLine;
		this.exits = new ArrayList<>();
	}
	
	/**Constructor
	 * builds a room that unlocks another room
	 * @param roomNum: the number of the room
	 * @param keyRoom: boolean flag to determine if it is a room that unlocks other rooms
	 * @param name: The name of the room 
	 * @param hasVisited: boolean that is for whether the player has been to this room before 
	 * @param description: the description of the room
	 */
	public Room(int roomNum, boolean keyRoom, String name, boolean hasVisited, String description) {
		this.roomNum = roomNum;
		this.keyRoom = keyRoom;
		this.name = name;
		this.hasVisited = hasVisited;
		this.description = divideDescription(description);
		this.exits = new ArrayList<>();
	}
	
	/**Constructor
	 * builds a room that requires another room to be explored first
	 * @param roomNum: the number of the room
	 * @param name: The name of the room 
	 * @param lockRoom" boolean flag that to determine if another room must be explored first
	 * @param hasVisited: boolean that is for whether the player has been to this room before 
	 * @param description: the description of the room
	 */
	public Room(int roomNum, String name, boolean lockRoom, boolean hasVisited, String description) {
		this.roomNum = roomNum;
		this.lockRoom = lockRoom;
		this.keyRoom = false;
		this.name = name;
		this.hasVisited = hasVisited;
		this.description = divideDescription(description);
		this.exits = new ArrayList<>();
	}
	
	/**Method: divideDescription
	 * turns the long single line description into an array of lines to make for better readability to the user
	 * @param description: the single line description passed in from the txt file
	 * @return
	 */
	private String[] divideDescription(String description) {
		//the token xNLx was added to each description to mark off when a new line should start, so this splits the line into multiple lines based off that token
		String[] allTokens = description.split("xNLx");
		return allTokens;
	}
	
	/**Method: hasVisitedString
	 * for private use to get a better String representation of the hasVisited boolean
	 * @return
	 */
	private String hasVisitedString() {
		return this.hasVisited ? "Has been visited": "Hasn't been visited";
	}
	
	public boolean hasVisited() {
		return this.hasVisited;
	}

	public void setHasVisited(boolean hasVisited) {
		this.hasVisited = hasVisited;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public String getName() {
		return name;
	}

	public String[] getDescription() {
		return description;
	}
	
	public boolean isKeyRoom() {
		return keyRoom;
	}
	
	public boolean hasSpecialLine() {
		return specialLine != null;
	}
	
	public boolean isLockRoom() {
		return lockRoom;
	}
	
	public void addExit(Exit exit) {
		exits.add(exit);
	}
	
	/**Method: getExit
	 * 
	 * @param input: the input the user gave for the direction they want to go
	 * @param unlockFlag: flag for if the locked exit has been unlocked
	 * @return the room that matches the exit chosen by the user
	 * @throws LockedExitException: exception for trying to enter a locked room
	 */
	public Room getExit(String input, boolean unlockFlag) throws LockedExitException{
		Room exitRoom;
		
		//the user is allowed to input a single character so this grabs the room based off a single character
		if(input.length() == 1) {
			exitRoom = exits.stream()
					.filter(e -> e.getDirection().getAbbreviation().equalsIgnoreCase(input))
					.findFirst()
					.get()
					.getNextRoom();
		} else {
			//asking for the valueOf for the enum should be fine here because the input is validated before calling this method so the input should be a valid DIRECTION
			DIRECTION direction = DIRECTION.valueOf(input);
			//the game is going to the next room for whatever exit matches the requested exit for this current instance.
			//This sets up a stream and filters for that exit and grabs the associated next room
			exitRoom = exits.stream()
					.filter(e -> e.getDirection() == direction)
					.findFirst()
					.get()
					.getNextRoom();
		}
		
		//this checks to see if the current room is a) a locked room and b) if the "lock" has been unlocked to prevent entering rooms prematurely
		if(!unlockFlag && exitRoom.isLockRoom()) {
			//exception thrown with message to be used by gameController upon trying to enter a locked room
			throw new LockedExitException("This exit is currently locked, keep exploring rooms.");
		}
		
		return exitRoom;
	}
	
	/**Method: isValidInput
	 * runs through all the exits of the room and sees if the input aligns with any of them
	 * @param input: the direction that the user is requesting to go
	 * @return if the requested input matches to any of the exits for the room instance
	 */
	public boolean isValidInput(String input) {
		/*the user can input single char abbreviations of directions so this checks for validity if the user does it
		 *by searching for any matching abbreviations of directions from all exits in the room
		*/
		if(input.length() == 1) {
			return exits.stream()
					.anyMatch(e -> e.getDirection().getAbbreviation().equalsIgnoreCase(input));
		}
		
		DIRECTION direction;
		//The user could put in anything that might not even equal one of the DIRECTION enums, so this will catch any mismatch and return as invalid input/false
		try {
			direction = DIRECTION.valueOf(input);
		} catch(IllegalArgumentException e) {
			return false;
		}
		
		//This line means that a valid DIRECTION was found and now a stream is set up that tries to find any Exit in the list of exits for a particular instance;
		return exits.stream().anyMatch(e -> e.getDirection() == direction);
	}
	
	/**Method: printForGameSpecial
	 * This will print out the special line of a room instead of the first description after the key room has been entered
	 */
	public void printForGameSpecial() {
		System.out.println("Room: " + this.roomNum);
		System.out.println(this.name + " / " + this.hasVisitedString());
		System.out.println(this.specialLine);
		System.out.print("You can go");
		for(int i = 0; i < this.exits.size(); i++) {
			String exitStr = this.exits.get(i).getDirection().toString();
			System.out.print(" " + exitStr);
		}
		System.out.println();
	}
	
	/**Method: printForGame
	 * This method will print details of the game in a format more suitable for presentation to the user as opposed to the toString method
	 */
	public void printForGame() {
		System.out.println("Room: " + this.roomNum);
		System.out.println(this.name + " / " + this.hasVisitedString());
		for(int i = 0; i < this.description.length; i++) {
			System.out.println(description[i]);
		}
		listExits();
	}
	
	/**Method: listExits
	 * A utility method to be used if the user needs to be reminded of the possible room exits
	 */
	public void listExits() {
		if(exits.size() > 0) {
			System.out.print("You can go");
			for(int i = 0; i < this.exits.size(); i++) {
				String exitStr = this.exits.get(i).getDirection().toString();
				System.out.print(" " + exitStr);
			}
			System.out.println();
		}
	}
	
	/**Method: toString
	 * A string representation of a room instance for usage on first load of game
	 */
	@Override
	public String toString() {
		String originalDescription = "";
		for(int i = 0; i < this.description.length; i++) {
			originalDescription += description[i] + "\n";
		}
		
		return "ID: " + roomNum + "\nName: " + name + "\nVisited Status: " + hasVisitedString() +  "\nDescription: " + originalDescription
				+ "===================================================================";
	}
}
