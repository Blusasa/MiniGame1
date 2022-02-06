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
		System.out.println("-------------");
	}
	
	public void startGame() {
		System.out.println("Welcome to my adventure game");
		System.out.println("Press any key to start");
		Scanner in = new Scanner(System.in);
		in.nextLine();
		playRoom(rooms.get(0), in);
	}
	
	private void playRoom(Room room, Scanner in) {
		if(room.getRoomNum() == 9) {
			
		}
		
		System.out.println("-----------------");
		room.printForGame();
		room.setHasVisited(true);
		System.out.println("Where would you like to go?\n----------------");
		
		String input = in.nextLine();
		input = input.toUpperCase();
		
		while(true) {
			if(room.isValidExit(input)) {
				break;
			}
			System.out.println("That was an invalid input or direction. Try again!");
			input = in.nextLine();
			input = input.toUpperCase();
		}
		
		playRoom(room.getExit(input), in);
	}
}
