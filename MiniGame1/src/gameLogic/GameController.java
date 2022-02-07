/**Class: GameController
 * @author: Michael Conner
 * @version: 1.0
 * Course: ITEC 3860 Spring 2022
 * Written: February 6, 2021
 * 
 * This class handles the core game play loop. It is responsible for displaying the rooms to the user, prompting the user for their next move, and moving the game forward until resolution.
 */

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
	
	/**Constructor
	 * builds the Controller object that will run the game play loop
	 * @param rooms: the list of rooms that the game contains
	 */
	public GameController(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	/**Method: printRooms
	 * this prints all the rooms before any of the game begins
	 */
	public void printRooms() {
		for(Room r: rooms) {
			System.out.println(r.toString());
		}
	}
	
	/**Method: startGame
	 * this the start point/"menu" of the game. It gives the user directions on how to navigate through the game and the title of the game
	 */
	public void startGame() {
		System.out.println("Welcome to my adventure game\n\"The Nightmare\"");
		System.out.println("You can type either the direction or a single letter to navigate. Ex: \"NORTH\" or \"n\"");
		System.out.println("Press x to exit");
		System.out.println("Press any key to start");
		in.nextLine();
		currentRoom = rooms.get(0);
		playRoom();
	}
	
	/**Method: playRoom
	 * the core of the game play loop happens here. This prints all of the room details to the console and takes in the user's requested 
	 * exit and moves the game forward.
	 */
	private void playRoom() {
		//This exists just divide up rooms easier
		System.out.println("-----------------");
		
		//if the specialExits are activated and the current room has a line to go with it, this will print out that line instead of the first description
		if(specialExitsActivated && currentRoom.hasSpecialLine()) {
			currentRoom.printForGameSpecial();
		} else {
			currentRoom.printForGame();
		}
		
		//the current room is set to visited for any future re-entry by the user
		currentRoom.setHasVisited(true);
		
		//the locked room is the last room in the game so if the user made it into the locked room than the game goes to ending
		if(currentRoom.isLockRoom()) {
			endGame();
		}
		
		//prompting the user for where they want to go
		System.out.println("Where would you like to go?\n----------------");
		
		String input = in.nextLine();
		//even if the input is valid, it will throw an exception if its not upper case to match the DIRECTION enum values
		input = input.toUpperCase();
		
		//check for if the user wants to end the game
		if(input.equalsIgnoreCase("x")) {
			endGame();
		}
		
		//make sure that the input is matches with an exit in the current room
		while(true) {
			validateInput(input);
			break;
		}
		
		//if the room the user is currently in is the key to unlock the final door, this updates the state in this controller to account for that
		if(currentRoom.isKeyRoom()) {
			specialExitsActivated = true;
		}
		
		Room nextRoom = null;
		
		//loop to get the next room that will continue to go until a valid room is chosen by the user
		while(true) {
			try {
				//try to get a valid room from the selected exit. The input is valid but the exit could still be locked
				nextRoom = currentRoom.getExit(input, specialExitsActivated);
				break;
			} catch (LockedExitException e) {
				//display to the user that the room is locked and to try again
				System.out.println(e.getMessage() + " Try again!");
				//redisplay the possible exits to the user
				currentRoom.listExits();
			}
			
			input = in.nextLine();
			input = input.toUpperCase();
			
			//validate the new input that the user has given
			validateInput(input);		
		}
		
		//change the current room to the now valid next room to continue driving the game loop
		currentRoom = nextRoom;
		
		//start over
		playRoom();
	}
	
	/**Method: validateInput
	 * method to ensure that any input used to retrieve an exit is valid and won't cause any errors
	 * @param input: the user input to validate
	 */
	private void validateInput(String input) {
		//this loop will continuously run until the user provides an input that matches with possible exits for the room
		while(true) {
			//if the input is valid than the loop will break
			if(currentRoom.isValidInput(input)) {
				break;
			}
			
			//Prompting the user to try again
			System.out.println("That was an invalid input or direction. Try again!");
			
			//refreshing the input to be validated
			input = in.nextLine();
			input = input.toUpperCase();
		}
	}

	/**Method: endGame
	 *method used either when the user requests for the game to end or they reach the final room
	 */
	private void endGame() {
		int count = 0;
		
		//loop to tally how many rooms they visited
		for(Room r: rooms) {
			if(r.hasVisited()) {
				count++;
			}
			
			//reset all visitation flags for potential new playthroughs
			r.setHasVisited(false);
		}
		
		//display to the user how many rooms they explored
		System.out.println("You explored " + count + " out of " + rooms.size() + " rooms");
		System.out.println();
		
		//go back to the beginning "menu" of the game
		startGame();
	}
}
