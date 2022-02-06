package gameObjects;

public class Exit {
	public enum DIRECTION{
		NORTH("N"), SOUTH("S"), EAST("E"), WEST("W"), UP("U"), DOWN("D");
		
		private String abrv;

		private DIRECTION(String abrv) {
			this.abrv = abrv;
		}
		
		public String getAbbreviation() {
			return this.abrv;
		}
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
