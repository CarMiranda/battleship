package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFlotte extends Iterable<IBateau>, Remote {

	boolean estDetruite() throws RemoteException;
	int getNbBateauxNonCoules() throws RemoteException;
	int getNbBateauxNonCoules(TypesBateau ship) throws RemoteException;
	
}
