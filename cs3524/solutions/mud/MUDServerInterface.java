package cs3524.solutions.mud;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MUDServerInterface extends Remote {

  public void initialize() throws RemoteException;
  public String createUser(String playerName, String mudName) throws RemoteException;
  public boolean checkIfPlayerLimitNotExceededInMUD(String mudName) throws RemoteException;
  public boolean checkIfPlayerLimitNotExceeded() throws RemoteException;
  public String moveUser(String currentLocation, String direction, String playerName) throws RemoteException;
  public String getStartLocation() throws RemoteException;
  public String getCurrentLocationInfo(String currentLocation) throws RemoteException;
  public void pickUpItem(String currentLocation, String item) throws RemoteException;
  public void dropItem(String currentLocation, String item) throws RemoteException;
  public String[] getCurrentPlayersInMUD() throws RemoteException;
  public void exit(String playerName) throws RemoteException;
  public String[] getAvailableMUDs() throws RemoteException;
  public boolean checkIfMUDExists(String mudName) throws RemoteException;
  public boolean createNewMUD(String mudName) throws RemoteException;
}
