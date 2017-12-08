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
public interface IJeu extends Remote {
	
	// Méthodes à distance
	Joueur getJoueur(String nom) throws RemoteException;
	void finirJeu() throws RemoteException;
	
}
