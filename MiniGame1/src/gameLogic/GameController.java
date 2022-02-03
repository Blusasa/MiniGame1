package gameLogic;

import java.util.List;
import java.util.Scanner;

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
	}
}
