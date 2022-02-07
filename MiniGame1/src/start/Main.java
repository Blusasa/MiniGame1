package start;

import java.util.List;

import gameLogic.GameController;
import gameObjects.Room;

public class Main {
	public static void main(String[] args) {
		LoadService loader = new LoadService();

		List<Room> rooms = loader.loadRooms();
		loader.populateRoomExits(rooms);
		
		GameController gc = new GameController(rooms);
		gc.printRooms();
		gc.startGame();
	}
}
