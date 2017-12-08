package modele;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * @author Carlos MIRANDA
 * @version 1.0.0
 * Dec 5, 2017
 */

/**
 * @author Carlos MIRANDA
 * @since 1.0.0
 * Dec 5, 2017
 */
public interface IEntree extends Remote {

	// Méthodes remotes
	String getNom() throws RemoteException;
	int getPerdus() throws RemoteException;
	int getGagnes() throws RemoteException;
	
	// Autres méthodes
	void incrementerPerdus();
	void incrementerGagnes();
	
}
