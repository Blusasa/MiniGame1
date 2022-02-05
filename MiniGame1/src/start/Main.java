package start;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import gameLogic.GameController;
import gameObjects.Room;

public class Main {
	public static void main(String[] args) {
		LoadService loader = new LoadService();
		
		FutureTask<List<Room>> loadingRooms = new FutureTask<>(loader.loadRooms());
		List<Room> rooms = null;
		new Thread(loadingRooms).start();
		
		try {
			rooms = loadingRooms.get(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Error while loading");
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("Loading timed out");
		}
		
		loader.populateRoomExits(rooms);
		GameController gc = new GameController(rooms);
		gc.printRooms();
		gc.startGame();
	}
}
