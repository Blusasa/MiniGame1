package gameLogic;

import java.util.List;
import java.util.Scanner;

import com.sun.javafx.scene.traversal.Direction;

import gameObjects.Exit.DIRECTION;
import gameObjects.Room;

public class GameController {
	
	private List<Room> rooms;

	public GameController(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	public void printRooms() {
		for(Room r: rooms) {
			System.out.println(r.toString());
		}
	}
	
	public void startGame() {
		System.out.println("Welcome to my adventure game");
		System.out.println("Press any key to start");
		Scanner in = new Scanner(System.in);
		in.nextLine();
		playRoom(rooms.get(0), in);
	}
	
	private void playRoom(Room room, Scanner in) {
		System.out.println(room.getRoomNum() + " " + room.getName());
		room.setHasVisited(true);
		System.out.println(room.hasVisited());
		System.out.println(room.getDescription());
		System.out.println("Where would you like to go?");
		DIRECTION direction = DIRECTION.valueOf(in.nextLine());
		if(room.isValidExit(direction)) {
			playRoom(room.getExit(direction).getNextRoom(), in);
		} else {
			System.out.println("Sorry that was a in valid direction. Please try again");
		}
	}
}
