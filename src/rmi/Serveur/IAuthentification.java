package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Cetter interface représente le service d'authentification.
 * @author Carlos MIRANDA
 * @since 1.0.0
 * Dec 5, 2017
 */
public interface IAuthentification extends Remote {
	
	/**
	 * Permet à un utilisateur de s'identifier.
	 * @param nom username
	 * @param motDePasse password
	 * @return true si l'authentification est réussi.
	 * @throws RemoteException
	 */
	public boolean authentification(String nom, String motDePasse) throws RemoteException;
	
	/**
	 * Permet à de créer un compte.
	 * @param nom username
	 * @param motDePasse password
	 * @return true si la création du compte a réussi.
	 * @throws RemoteException
	 */
	public boolean inscription(String nom, String motDePasse) throws RemoteException;
	
}
