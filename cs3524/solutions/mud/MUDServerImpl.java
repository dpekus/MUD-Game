package cs3524.solutions.mud;

import java.rmi.RemoteException;

public class MUDServerImpl implements MUDServerInterface {

  MUD world = new MUD("mymud.edg", "mymud.msg", "mymud.thg");

  public MUDServerImpl() throws RemoteException {
  }

  public String createUser(String playerName) {
    System.out.println("The player " + playerName + " has joined the server.");
    String startLocation = getStartLocation();
    world.addThing(startLocation, playerName);
    return world.locationInfo("A");
  }

  public String getStartLocation() {
    return world.startLocation();
  }

  public String moveUser(String currentLocation, String direction, String playerName) {
    return currentLocation = world.moveThing(currentLocation, direction, playerName);
  }

  public String getCurrentLocationInfo(String currentLocation) {
    return world.locationInfo(currentLocation);
  }

  public void pickUpItem(String currentLocation, String item) {
    world.delThing(currentLocation, item);
  }

  public void dropItem(String currentLocation, String item) {
    world.addThing(currentLocation, item);
  }

  public void exit(String playerName) {
    System.out.println("The player " + playerName + " has left the server.");
  }
}
