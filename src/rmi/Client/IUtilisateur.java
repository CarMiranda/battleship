package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;


import rmi.Serveur.IEntree;
import rmi.Serveur.IUtilisateurDistant;

/**
 * Cette interface représente l'interface de la classe Utilisateur.
 * @author Carlos MIRANDA
 *
 */
public interface IUtilisateur extends Remote {

	// Méthodes à distance
	
	/**
	 * Permet de rejoindre un jeu lorsqu'un utilisateur nous défie.
	 * @param nom nom de l'adversaire.
	 * @throws RemoteException
	 */
	void rejoindreJeu(String nom) throws RemoteException;

// Méthodes locales

	/**
	 * Permet de s'identiefier auprès du logiciel.
	 * @param motDePasse le mot de passe
	 * @return l'utilisateur distant associé à l'utilisateur.
	 * @throws RemoteException
	 */
	IUtilisateurDistant authentification(String motDePasse) throws RemoteException;

	/**
	 * Permet de créer un compte.
	 * @param motDePasse le mot de passe.
	 * @return l'utilisateur distant associé à l'utilisateur.
	 * @throws RemoteException
	 */
	IUtilisateurDistant inscription(String motDePasse) throws RemoteException;

	/**
	 * Permet de commencer un jeu.
	 * @param utilisateur l'adversaire.
	 * @param difficulte la difficulte.
	 * @throws RemoteException
	 */
	void commencerJeu(IUtilisateurDistant utilisateur, String difficulte) throws RemoteException;

	/**
	 * Getter
	 * @return nom de l'utilisateur.
	 * @throws RemoteException
	 */
	String getNom() throws RemoteException;
	/**
	 * Permet de se déconnecter.
	 * @throws RemoteException
	 */
	void finirUtilisation() throws RemoteException;

	/**
	 * Getter
	 * @return l"ensemble des utilisateurs du logiciel.
	 * @throws RemoteException
	 */
	Map<String, IUtilisateurDistant> getUtilisateurs() throws RemoteException;

	/**
	 * Getter
	 * @return Ensemble de statistiques de jeu de l'utilisateur concerné.
	 * @throws RemoteException
	 */
	Map<String, IEntree> getStatistiques() throws RemoteException;

	/**
	 * Permet d'actualiser les utilisateurs connectés
	 * @param utilisateur l'utilisateur modifié
	 * @param estNouveau true s'il est nouveau
	 * @throws RemoteException
	 */
	void informerConnection(IUtilisateurDistant utilisateur, boolean estNouveau) throws RemoteException;

	void informerNouvelleEntree(String nomAdversaire) throws RemoteException;
}
