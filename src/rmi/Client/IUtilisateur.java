package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import modele.Difficulte;

import rmi.Serveur.IEntree;
import rmi.Serveur.IJeuDistant;
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
	 * @param utilisateur 
	 * @param jeu
	 * @throws RemoteException
	 */
	void rejoindreJeu(IUtilisateurDistant utilisateur, IJeuDistant jeu) throws RemoteException;
	
	// Méthodes locales
	
	/**
	 * Permet de s'identiefier auprès du logiciel.
	 * @param motDePasse
	 * @return l'utilisateur distant associé à l'utilisateur.
	 * @throws RemoteException
	 */
	IUtilisateurDistant authentification(String motDePasse) throws RemoteException;
	
	/**
	 * Permet de créer un compte.
	 * @param motDePasse
	 * @return l'utilisateur distant associé à l'utilisateur.
	 * @throws RemoteException
	 */
	IUtilisateurDistant inscription(String motDePasse) throws RemoteException;
	
	/**
	 * Permet de coommencer un jeu.
	 * @param utilisateur
	 * @param difficulte
	 * @throws RemoteException
	 */
	void commencerJeu(IUtilisateurDistant utilisateur, Difficulte difficulte) throws RemoteException;
	
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
	 * @param utilisateur
	 * @param estNouveau
	 * @throws RemoteException
	 */
	void informerConnection(IUtilisateurDistant utilisateur, boolean estNouveau) throws RemoteException;
}
