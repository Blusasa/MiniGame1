package start;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gameObjects.Exit;
import gameObjects.Exit.DIRECTION;
import gameObjects.Room;

public class LoadService {
	private final String roomFilePath = "/Resources/rooms.txt";
	private final String exitFilePath = "/Resources/exits.txt";
	
	
	protected List<Room> loadRooms(){
		InputStream stream = this.getClass().getResourceAsStream(roomFilePath);
		Scanner in = new Scanner(stream);

		List<Room> rooms = new ArrayList<Room>();
		while(in.hasNext()) {
			int roomNum = Integer.parseInt(in.nextLine());
			boolean keyRoom = Boolean.parseBoolean(in.nextLine());
			boolean lockRoom = Boolean.parseBoolean(in.nextLine());
			String title = in.nextLine();
			boolean hasVisited = Boolean.parseBoolean(in.nextLine());
			String description = in.nextLine();
			String specialLine = in.nextLine();
			Room room = null;
			if(!specialLine.equalsIgnoreCase("none")) {
				room = new Room(roomNum, title, hasVisited, description, specialLine);
			} else if(keyRoom){
				room = new Room(roomNum, keyRoom, title, hasVisited, description);
			} else if(lockRoom) {
				room = new Room(roomNum, title, lockRoom, hasVisited, description);
			} else {
				room = new Room(roomNum, title, hasVisited, description);
			}
			rooms.add(room);
		}
		in.close();
		return rooms;

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
				String[] exitLine = in.nextLine().split(" ");
				String direction = exitLine[0];
				int nextRoomNum = Integer.parseInt(exitLine[1]);
				
				//if an exit is not a valid exit, than it is paired with a -1 value in the file. This "if" skips all of those
				if(nextRoomNum >= 0) {
					DIRECTION directionENUM = DIRECTION.valueOf(direction);
					Room nextRoom = rooms.stream()
							.filter(r -> r.getRoomNum() == nextRoomNum)
							.findFirst()
							.get();
					Exit exit = new Exit(directionENUM, nextRoom);
					currentRoom.addExit(exit);
				}
			}
		}
		
		in.close();
		return true;
	}
}



















