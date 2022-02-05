package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gameObjects.Exit;
import gameObjects.Room;
import gameObjects.Exit.DIRECTION;

class RoomTest {

	@Test
	void testIsValidExit() {
		Room masterBd = new Room(0, "Master Bedroom", false, "General Description");
		Room masterBath = new Room(7, "Master Bathroom", false, "General Description");
		Room livingRoom = new Room(2, "Living Room", false, "General Desctiption");
		Room upstairsBed = new Room(1, "Upstairs Bed", false, "General Description");
		Exit exit1 = new Exit(DIRECTION.SOUTH, masterBath);
		Exit exit2 = new Exit(DIRECTION.WEST, upstairsBed);
		Exit exit3 = new Exit(DIRECTION.DOWN, livingRoom);
		
		masterBd.addExit(exit1);
		masterBd.addExit(exit2);
		masterBd.addExit(exit3);
		
		boolean resultSouth = masterBd.isValidExit(DIRECTION.SOUTH);
		boolean resultNorth = masterBd.isValidExit(DIRECTION.NORTH);
		
		assertEquals(true, resultSouth);
		assertEquals(false, resultNorth);
	}

}
