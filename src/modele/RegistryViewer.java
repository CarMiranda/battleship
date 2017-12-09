package modele;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryViewer {
  public static void main(String args[]){
    String host = "localhost";
    int port = 5001;
    Registry registry;
	try {
		registry = LocateRegistry.getRegistry(host, port);
		for (String name : registry.list()) {
	        System.out.println(name);
	    }
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }    
}