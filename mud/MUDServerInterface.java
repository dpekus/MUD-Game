/***********************************************************************
 * mud.MUDServerInterface
 ***********************************************************************/

package mud;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
  MUD game made by Dovydas Pekus, University of Aberdeen

  The client will be accessing these methods, they are implemented in the
  MUDServerImpl file
*/

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
  public Integer getMUDCount() throws RemoteException;
  public boolean checkIfMUDExists(String mudName) throws RemoteException;
  public boolean createNewMUD(String mudName) throws RemoteException;
  public Integer getMaxNumberOfMuds() throws RemoteException;
  public void setNewMaxNumberOfMUDs(Integer newMaxMUDS) throws RemoteException;
}
