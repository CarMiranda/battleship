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
public interface IAuthentification extends Remote {

	public boolean authentification(String nom, String motDePasse) throws RemoteException;
	public boolean inscription(String nom, String motDePasse) throws RemoteException;
	
}
