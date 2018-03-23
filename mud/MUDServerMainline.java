/***********************************************************************
 * mud.MUDServerMainline
 ***********************************************************************/

package mud;

import java.rmi.Naming;
import java.lang.SecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.net.InetAddress;

/*
  MUD game made by Dovydas Pekus, University of Aberdeen

  The code for ServerMainline taken from the RMIShout practical of the course
*/


public class MUDServerMainline {

  public static void main(String args[]) {

    if (args.length < 2) {
      System.err.println("Usage:\njava MUDServerMainline <registryport> <serverport>");
      return;
    }

    try {
      String hostname = (InetAddress.getLocalHost()).getCanonicalHostName();
      int registryport = Integer.parseInt(args[0]);
      int serverport = Integer.parseInt(args[1]);

      System.setProperty("java.security.policy", "mud.policy");
      System.setSecurityManager(new SecurityManager());

      MUDServerImpl mudserver = new MUDServerImpl();
		  MUDServerInterface mudstub = (MUDServerInterface)UnicastRemoteObject.exportObject(mudserver, serverport);

      String regURL = "rmi://" + hostname + ":" + registryport + "/MUDServer";
      System.out.println("Registering " + regURL);
      Naming.rebind(regURL, mudstub);

    } catch (java.net.UnknownHostException e) {
      System.err.println("Cannot get local host name.");
      System.err.println(e.getMessage());
    } catch (java.io.IOException e) {
      System.err.println("Failed to register.");
      System.err.println(e.getMessage());
    }
  }
}
