package cs3524.solutions.mud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MUDClient {

  // prepare the input reader
  private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  // the player's name that they will be using throughout the game
  private static String playerName = "";
  // specify whether the game is running or not
  private static boolean running = false;

  private static MUDServerInterface serv;

  // current location of the player
  private static String currentLocation = "";
  // the items that the player is currently carrying
  private static List<String> inventory = new ArrayList<>();

  public static void main(String args[]) throws RemoteException {

    if (args.length < 2) {
      System.err.println("Usage:\njava MUDClient <host> <port>");
      return;
    }

    String hostname = args[0];
    int port = Integer.parseInt(args[1]);

    System.setProperty("java.security.policy", "mud.policy");
    System.setSecurityManager(new SecurityManager());

    try {
      String regURL = "rmi://" + hostname + ":" + port + "/MUDServer";
      System.out.println("Looking up " + regURL);

      serv = (MUDServerInterface) Naming.lookup(regURL);

      // set up the game
      // TBA: change all of this later
      System.out.println("Welcome!");
      System.out.println("What is your name?");
      try {
        System.out.print(">> ");
        playerName = in.readLine();
      } catch (IOException e) {
        System.err.println("I/O error.");
        System.err.println(e.getMessage());
      }
      System.out.println("Nice to meet you, " + playerName);
      System.out.println("Let's begin");
      running = true;
      currentLocation = serv.getStartLocation();

      displayOptions();

      System.out.println(serv.createUser(playerName));

      runGame();

    } catch (IOException e) {
      System.err.println("I/O error.");
      System.err.println(e.getMessage());
    } catch (java.rmi.NotBoundException e) {
      System.err.println("Server not bound.");
      System.err.println(e.getMessage());
    }
  }

  private static void runGame() throws RemoteException {

    while (running) try {

      System.out.print(">> ");
      String playerInput = in.readLine().toLowerCase();
      handlePlayerInput(playerInput);

    } catch (IOException e) {
      System.err.println("I/O error.");
      System.err.println(e.getMessage());
    }
  }

  // handle an input from the player
  private static void handlePlayerInput(String playerInput) throws RemoteException {

    // move the user in a given direction
    if (playerInput.contains("move")) {
      // get the direction where the player wants to move
      String[] directionString = playerInput.split(" ");
      // move the player and display information about the new location
      System.out.println("You are going " + directionString[1] + "...");
      currentLocation = serv.moveUser("A", directionString[1], playerName);
      System.out.println(serv.getCurrentLocationInfo(currentLocation));
    }

    // pick up an item
    if (playerInput.contains("pick")) {
      // get the name of the item that the player wants to pick up
      String[] itemString = playerInput.split(" ");
      // pick up the item and notify the user about it
      serv.pickUpItem(currentLocation, itemString[1]);
      inventory.add(itemString[1]);
      System.out.println("You have picked up " + itemString[1]);
    }

    // drop an item
    if (playerInput.contains("drop")) {
      // get the name of the item that the player wants to drop
      String[] itemString = playerInput.split(" ");
      //drop the item and notify the user about it
      serv.dropItem(currentLocation, itemString[1]);
      inventory.remove(itemString[1]);
      System.out.println("You have dropped " + itemString[1]);
    }

    // display the contents of player's inventory
    if (playerInput.contains("inventory")) {
      if (inventory.size() < 1) {
        System.out.println("Your inventory is empty.");
      } else {
        System.out.println("You are carrying:");
        for (String item : inventory) {
          System.out.println("* " + item);
        }
      }
    }

    // print the available commands to the player
    if (playerInput.contains("help")) {
      displayOptions();
    }

    // exit the game
    if (playerInput.contains("exit")) {
      serv.exit(playerName);
      running = false;
    }
  }

  private static void displayOptions() {
    System.out.println();
    System.out.println("You can choose from one of these commands:");
    System.out.println();
    System.out.println("* Move <direction>  - move to a selected direction (north, east, south, west)");
    System.out.println("* Pick <item>  - pick up an item from the ground to your inventory");
    System.out.println("* Drop <item>  - drop an item from your inventory to the ground");
    System.out.println("* Help  - display the available commands");
    System.out.println("* Exit  - exit the game");
  }
}
