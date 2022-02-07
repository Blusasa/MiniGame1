/**Class: Main
 * @author: Michael Conner
 * @version: 1.0
 * Course: ITEC 3860 Spring 2022
 * Written: February 6, 2021
 * 
 * This class is the launch point of the game. It calls on the loading class to load the rooms and exits
 * and then starts the game via the GameController class
 */

package start;

import java.util.List;

import gameLogic.GameController;
import gameObjects.Room;

public class Main {
	/**Method: Main
	 * Launch point of the game, loads the game data and then begins the game loop.
	 * @param args: any arguments passed into the program at launch
	 */
	public static void main(String[] args) {
		LoadService loader = new LoadService();

		List<Room> rooms = loader.loadRooms();
		loader.populateRoomExits(rooms);
		
		GameController gc = new GameController(rooms);
		gc.printRooms();
		gc.startGame();
	}
}
