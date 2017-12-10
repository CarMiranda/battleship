package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Iterator;

public interface IFlotte extends Remote {

	boolean estDetruite() throws RemoteException;
	Iterator<IBateau> iterator() throws RemoteException;
	
}
