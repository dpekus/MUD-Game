package cs3524.solutions.mud;

import java.rmi.RemoteException;
import java.util.*;

public class MUDServerImpl implements MUDServerInterface {

  // all available MUDs
  private Map<String, MUD> MUDs = new HashMap<String, MUD>();

  //MUD world = new MUD("mymud.edg", "mymud.msg", "mymud.thg");
  private MUD currentInstance = MUDs.put("myMUD", new MUD("mymud.edg","mymud.msg","mymud.thg"));

  public MUDServerImpl() throws RemoteException {
  }

  public String createUser(String playerName, String mudName) {

    System.out.println("The player " + playerName + " has joined the " + mudName + " MUD.");
    currentInstance = MUDs.get(mudName);
    currentInstance.addThing(currentInstance.startLocation(), playerName);
    return currentInstance.locationInfo(currentInstance.startLocation());
  }

  public String moveUser(String currentLocation, String direction, String playerName) {
    return currentLocation = currentInstance.moveThing(currentLocation, direction, playerName);
  }

  public String getCurrentLocationInfo(String currentLocation) {
    return currentInstance.locationInfo(currentLocation);
  }

  public void pickUpItem(String currentLocation, String item) {
    currentInstance.delThing(currentLocation, item);
  }

  public void dropItem(String currentLocation, String item) {
    currentInstance.addThing(currentLocation, item);
  }

  public void exit(String playerName) {
    System.out.println("The player " + playerName + " has left the server.");
  }

  public String getAvailableMUDs() {
    return MUDs.keySet().toString();
  }
}
