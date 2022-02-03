package gameObjects;

public class Room {
	public enum EXITS{
		NORTH, SOUTH, EAST, WEST, UP, DOWN
	}

	private int roomNum;
	private String name;
	private boolean hasVisited;
	private String description;
	private String exits;
	
	public Room(int roomNum, String name, boolean hasVisited, String description, String exits) {
		this.roomNum = roomNum;
		this.name = name;
		this.hasVisited = hasVisited;
		this.description = description;
		this.exits = exits;
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

	public String getExits() {
		return exits;
	}
	
	@Override
	public String toString() {
		String visitStr = this.hasVisited ? "Has been visited": "Hasn't been visited";
		return this.roomNum + "\n" + this.name + " " + visitStr + "\n" + this.description;
	}
}
