package rmi.Serveur;

/**
 * @author Carlos MIRANDA
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface IBateau extends Iterable<IPartieBateau>, Remote {

	void placer(List<ICarreauCarte> lcc) throws CarreauUtiliseException, RemoteException;
	boolean estCoule() throws RemoteException;
	int getTaille() throws RemoteException;
	List<IPartieBateau> getBateau() throws RemoteException;
	String getNom() throws RemoteException;
	
}
