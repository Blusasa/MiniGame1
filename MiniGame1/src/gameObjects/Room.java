package gameObjects;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import gameObjects.Exit.DIRECTION;

public class Room {
	private int roomNum;
	private String name;
	private boolean hasVisited;
	private String[] description;
	private List<Exit> exits;
	
	public Room(int roomNum, String name, boolean hasVisited, String description) {
		this.roomNum = roomNum;
		this.name = name;
		this.hasVisited = hasVisited;
		this.description = divideDescription(description);
		this.exits = new ArrayList<>();
	}
	

	private String[] divideDescription(String description) {
		//Some descriptions could be longer than others so they may take up varying amounts of lines. In the file, in each description String, "NL" will signify a new line that is used
		//here to split the single line into an array of strings that can all fit on screen
		String[] allTokens = description.split("xNLx");
		return allTokens;
	}

	public String hasVisited() {
		return this.hasVisited ? "Has been visited": "Hasn't been visited";
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
	
	public void addExit(Exit exit) {
		exits.add(exit);
	}
	
	public Room getExit(String input) {
		//the user is allowed to input a single character so this grabs the room based off a single character
		if(input.length() == 1) {
			return exits.stream()
					.filter(e -> e.getDirection().getAbbreviation().equalsIgnoreCase(input))
					.findFirst()
					.get()
					.getNextRoom();
		}
		
		//asking for the valueOf for the enum should be fine here because the input is validated before calling this method so the input should be a valid DIRECTION
		DIRECTION direction = DIRECTION.valueOf(input);
		//the game is going to the next room for whatever exit matches the requested exit for this current instance.
		//This sets up a stream and filters for that exit and grabs the associated next room
		return exits.stream()
				.filter(e -> e.getDirection() == direction)
				.findFirst()
				.get()
				.getNextRoom();
	}
	
	public boolean isValidExit(String input) {
		boolean singleCharValid = exits.stream()
				.anyMatch(e -> e.getDirection().getAbbreviation().equalsIgnoreCase(input));
		
		if(singleCharValid) {
			return true;
		}
		
		DIRECTION direction;
		//The user could put in anything that might not even equal one of the DIRECTION enums, so this will catch any mismatch and return as invalid input/false
		try {
			direction = DIRECTION.valueOf(input.toUpperCase());
		} catch(IllegalArgumentException e) {
			return false;
		}
		
		//This line means that a valid DIRECTION was found and now a stream is set up that tries to find any Exit in the list of exits for a particular instance;
		return exits.stream().anyMatch(e -> e.getDirection() == direction);
	}
	
	@Override
	public String toString() {
		String originalDescription = "";
		for(int i = 0; i < this.description.length; i++) {
			originalDescription += description[i];
		}
		return this.roomNum + "\n" + this.name + " " + this.hasVisited() + "\n" + originalDescription;
	}
	
	public void printForGame() {
		System.out.println(this.roomNum);
		System.out.println(this.name + " " + this.hasVisited());
		for(int i = 0; i < this.description.length; i++) {
			System.out.println(description[i]);
		}
		System.out.print("You can go");
		for(int i = 0; i < this.exits.size(); i++) {
			String exitStr = this.exits.get(i).getDirection().toString();
			System.out.print(" " + exitStr);
		}
		System.out.println();
	}
}
