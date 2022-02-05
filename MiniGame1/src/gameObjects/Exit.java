package gameObjects;

import java.util.Map;

public class Exit {
	public enum DIRECTION{
		NORTH, SOUTH, EAST, WEST, UP, DOWN
	}

	private Map<DIRECTION, Room> exitPoints;
	private Room currentRoom;
	
	public Exit(Map<DIRECTION, Room> exitPoints, Room currentRoom) {
		this.exitPoints = exitPoints;
		this.currentRoom = currentRoom;
	}
	
	protected void getExits() {
		
	}
	
	protected boolean isValidExit(DIRECTION direction, Room room) {
		return exitPoints.get(direction).getRoomNum() == room.getRoomNum();
	}
}
