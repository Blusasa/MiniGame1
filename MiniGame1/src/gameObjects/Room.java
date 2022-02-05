package gameObjects;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private int roomNum;
	private String name;
	private boolean hasVisited;
	private String description;
	private List<Exit> exits;
	
	public Room(int roomNum, String name, boolean hasVisited, String description) {
		this.roomNum = roomNum;
		this.name = name;
		this.hasVisited = hasVisited;
		this.description = description;
		this.exits = new ArrayList<>();
	}

	public boolean hasVisited() {
		return hasVisited;
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

	public String getDescription() {
		return description;
	}
	
	public void addExit(Exit exit) {
		exits.add(exit);
	}
	
	@Override
	public String toString() {
		String visitStr = this.hasVisited ? "Has been visited": "Hasn't been visited";
		return this.roomNum + "\n" + this.name + " " + visitStr + "\n" + this.description;
	}
}
