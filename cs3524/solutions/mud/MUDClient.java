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
  // the name of the MUD that the player is currently on
  private static String mudName = "";

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

      serv.initialize();

      // set up the game
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
      System.out.println();

      displayAvailableMUDs();

      System.out.println("Which MUD would you like to join?");
      System.out.println();
      System.out.print(">> ");
      mudName = in.readLine();

      while(!serv.checkIfMUDExists(mudName)) {
        System.out.println();
        System.out.println("Sorry, no such MUD found. Why not try again? (The names are case sensitive)");
        System.out.println();
        System.out.print(">> ");
        mudName = in.readLine();
      }

      joinMUD(mudName);

      System.out.println();
      System.out.println("Let's begin");
      running = true;
      currentLocation = serv.getStartLocation();

      displayOptions();

      System.out.println(serv.getCurrentLocationInfo(currentLocation));

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

    // display all available MUDs
    if (playerInput.contains("muds")) {
      displayAvailableMUDs();
    }

    // move to another MUD
    if (playerInput.contains("changemud")) {
      displayAvailableMUDs();
      System.out.println();
      System.out.println("Which MUD would you like to move to?");
      try {
        System.out.print(">> ");
        mudName = in.readLine();
      } catch (IOException e) {
        System.err.println("I/O error.");
        System.err.println(e.getMessage());
      }

      joinMUD(mudName);
      currentLocation = serv.getStartLocation();
      displayOptions();
    }

    // create a new MUD
    if (playerInput.contains("createmud")) {
      System.out.println("Enter the name of your new MUD:");
      try {
        System.out.print(">> ");
        mudName = in.readLine();
        System.out.println();
      } catch (IOException e) {
        System.err.println("I/O error.");
        System.err.println(e.getMessage());
      }
      createNewMUD(mudName);
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
    System.out.println("* Muds  - display all currently available MUDs");
    System.out.println("* ChangeMUD  - move to another MUD");
    System.out.println("* CreateMUD  - create a new MUD");
    System.out.println("* Exit  - exit the game");
  }

  private static void displayAvailableMUDs() throws RemoteException {
    System.out.println("Currently, these MUDs are available: ");
    System.out.println();
    String[] availableMUDs = serv.getAvailableMUDs();
    for (String mud : availableMUDs) {
      System.out.println("* " + mud);
    }
    System.out.println();
  }

  private static void joinMUD(String mudName) throws RemoteException {
    // drop all items that the player is carrying to avoid item duplication
    for (String item : inventory) {
      serv.dropItem(currentLocation, item);
    }
    inventory.clear();

    System.out.println(serv.createUser(playerName, mudName));
    System.out.println();
  }

  private static void createNewMUD(String mudName) throws RemoteException {
    if(serv.createNewMUD(mudName)) {
      System.out.println("Your MUD " + mudName + " has been created.");
      System.out.println("Would you like to join it? (Y/N)");
      try {
        System.out.print(">> ");
        String answer = in.readLine();
        if (answer=="Y") {
          joinMUD(mudName);
        } else {
          displayOptions();
        }
      } catch (IOException e) {
        System.err.println("I/O error.");
        System.err.println(e.getMessage());
      }
    } else {
      System.out.println("Sorry, but the maximum number of MUDs has been reached.");
    }
  }
}
