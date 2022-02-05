package start;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

import gameObjects.Exit;
import gameObjects.Exit.DIRECTION;
import gameObjects.Room;

public class LoadService {
	private final String roomFilePath = "/rooms.txt";
	private final String exitFilePath = "/exits.txt";
	
	protected Callable<List<Room>> loadRooms(){
		
		Callable<List<Room>> loaderThread = new Callable<List<Room>>() {
			public List<Room> call(){
				InputStream stream = this.getClass().getResourceAsStream(roomFilePath);
				Scanner in = new Scanner(stream);
				
				List<Room> rooms = new ArrayList<Room>();
				while(in.hasNext()) {
					int roomNum = Integer.parseInt(in.nextLine());
					String title = in.nextLine();
					boolean hasVisited = Boolean.parseBoolean(in.nextLine());
					String description = in.nextLine();
			
					Room room = new Room(roomNum, title, hasVisited, description);
					rooms.add(room);
				}
				in.close();
				return rooms;
			}
		};
		
		return loaderThread;
	}
	
	protected boolean populateRoomExits(List<Room> rooms) {
		InputStream stream = this.getClass().getResourceAsStream(exitFilePath);
		Scanner in = new Scanner(stream);
		
		while(in.hasNext()) {
			String roomNumLine = in.nextLine();
			//the file is structered as "# RoomName" for readability, the belows trims off the end of the line as we only need the room Number
			int roomNum = Integer.parseInt(roomNumLine.substring(0, 1));
			//we then filter out and grab the room object from our list to be paired with any potential exit points
			Room currentRoom = rooms.stream()
									.filter(r -> r.getRoomNum() == roomNum)
									.findFirst()
									.get();
			for(int i = 1; i <= 6; i++) {
				String[] kvPair = in.nextLine().split(" ");
				//if an exit is not a valid exit, than it is paired with a -1 value in the file. This "if" skips all of those
				if(!kvPair[1].equalsIgnoreCase("-1")) {
					String direction = kvPair[0];
					DIRECTION exitPoint = DIRECTION.valueOf(direction);
					Room exitRoom = rooms.stream()
							.filter(r -> r.getRoomNum() == Integer.parseInt(kvPair[1]))
							.findFirst()
							.get();
					
					Map<DIRECTION, Room> exitPair = new HashMap<>();
					exitPair.put(exitPoint, exitRoom);
					Exit exit = new Exit(exitPair, currentRoom);
					currentRoom.addExit(exit);
				}
			}
		}
		
		in.close();
		return true;
	}
}


















