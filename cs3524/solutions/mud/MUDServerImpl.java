package cs3524.solutions.mud;

import java.rmi.RemoteException;
import java.util.*;

public class MUDServerImpl implements MUDServerInterface {

  // all available MUDs
  private Map<String, MUD> MUDs = new HashMap<String, MUD>();

  // stores the current MUD that the player is on
  private MUD currentInstance;

  // number of players currently online
  private static int players = 0;

  // maximum number of MUDs running at the same time
  private static int maxNumberOfMUDs = 5;

  // maximum number of player currently online
  private static int maxNumberOfPlayers = 10;

  public MUDServerImpl() throws RemoteException {
  }

  public void initialize() {
    MUDs.put("myMUD", new MUD("mymud.edg","mymud.msg","mymud.thg"));
    MUDs.put("myMUD2", new MUD("mymud.edg","mymud.msg","mymud.thg"));
  }

  public String createUser(String playerName, String mudName) {

    if (checkIfMUDExists(mudName)) {
      System.out.println("The player " + playerName + " has joined the " + mudName + " MUD.");
      currentInstance = MUDs.get(mudName);
      currentInstance.addThing(currentInstance.startLocation(), playerName);
      return currentInstance.locationInfo(currentInstance.startLocation());
    } else {
      return "Sorry, no such MUD " + mudName + " found.";
    }
  }

  public String moveUser(String currentLocation, String direction, String playerName) {
    return currentLocation = currentInstance.moveThing(currentLocation, direction, playerName);
  }

  public String getStartLocation() {
    return currentInstance.startLocation();
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

  public String[] getAvailableMUDs() {
    return MUDs.keySet().toArray(new String[MUDs.keySet().size()]);
  }

  public boolean checkIfMUDExists(String mudName) {
    return Arrays.stream(getAvailableMUDs()).anyMatch(mudName::equals);
  }

  public boolean createNewMUD(String mudName) {
    // check if the current number of MUDs are not exceeding the maximum number
    if (maxNumberOfMUDs > MUDs.size()) {
      MUDs.put(mudName, new MUD("mymud.edg","mymud.msg","mymud.thg"));
      return true;
    } else {
      return false;
    }
  }
}
