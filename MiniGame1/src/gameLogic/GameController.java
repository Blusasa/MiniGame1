package gameLogic;

import java.util.List;
import java.util.Scanner;

import gameObjects.LockedExitException;
import gameObjects.Room;

public class GameController {
	
	private List<Room> rooms;
	private boolean specialExitsActivated = false;
	private final Scanner in = new Scanner(System.in);
	private Room currentRoom;

	public GameController(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	public void printRooms() {
		for(Room r: rooms) {
			System.out.println(r.toString());
		}
	}
	
	public void startGame() {
		System.out.println("Welcome to my adventure game\n\"The Nightmare\"");
		System.out.println("You can type either the direction or a single letter to navigate. Ex: \"NORTH\" or \"n\"");
		System.out.println("Press x to exit");
		System.out.println("Press any key to start");
		in.nextLine();
		currentRoom = rooms.get(0);
		playRoom();
	}
	
	private void playRoom() {
		System.out.println("-----------------");
		if(specialExitsActivated && currentRoom.hasSpecialLine()) {
			currentRoom.printForGameSpecial();
		} else {
			currentRoom.printForGame();
		}
		
		currentRoom.setHasVisited(true);
		
		if(currentRoom.isLockRoom()) {
			endGame();
		}
		System.out.println("Where would you like to go?\n----------------");
		
		String input = in.nextLine();
		input = input.toUpperCase();
		
		if(input.equalsIgnoreCase("x")) {
			endGame();
		}
		
		if(currentRoom.isKeyRoom()) {
			specialExitsActivated = true;
		}
		
		Room nextRoom = null;
		while(true) {
			try {
				nextRoom = currentRoom.getExit(input, specialExitsActivated);
				break;
			} catch (LockedExitException e) {
				System.out.println(e.getMessage() + " Try again!");
				currentRoom.listExits();
			}
			input = in.nextLine();
			input = input.toUpperCase();
			validateInput(input);		
		}
		
		currentRoom = nextRoom;
		playRoom();
	}
	
	private void validateInput(String input) {
		while(true) {
			if(currentRoom.isValidInput(input)) {
				break;
			}
			System.out.println("That was an invalid input or direction. Try again!");
			input = in.nextLine();
			input = input.toUpperCase();
		}
	}

	
	private void endGame() {
		int count = 0;
		for(Room r: rooms) {
			if(r.hasVisited()) {
				count++;
			}
			r.setHasVisited(false);
		}
		System.out.println("You explored " + count + " out of " + rooms.size() + " rooms");
		System.out.println();
		startGame();
	}
}
