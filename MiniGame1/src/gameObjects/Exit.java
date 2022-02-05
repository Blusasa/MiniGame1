package gameObjects;

public class Exit {
	public enum DIRECTION{
		NORTH, SOUTH, EAST, WEST, UP, DOWN
	}
	
	private DIRECTION direction;
	private Room nextRoom;
	
	public Exit(DIRECTION direction, Room nextRoom) {
		this.direction = direction;
		this.nextRoom = nextRoom;
	}
	
	public DIRECTION getDirection() {
		return this.direction;
	}
	
	public Room getNextRoom() {
		return this.nextRoom;
	}
}
