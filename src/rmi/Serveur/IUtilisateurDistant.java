package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import rmi.Client.IUtilisateur;
/**
 * Cette interface représente un utilisateur dans le serveur RMI.
 * @author Carlos MIRANDA.
 *
 */
public interface IUtilisateurDistant extends Remote {

	/**
	 * Getter
	 * @return ensemble d'utilisateurs distants.
	 * @throws RemoteException
	 */
	Map<String, IUtilisateurDistant> getUtilisateurs() throws RemoteException;
	
	/**
	 * Getter
	 * @return statistiques associées à l'utilisateur.
	 * @throws RemoteException
	 */
	Map<String, IEntree> getStatistiques() throws RemoteException;
	
	/**
	 * Commence un jeu contre l'utilisateur passé en paramètre
	 * @param utilisateur L'adversaire défié
	 * @param difficulte la difficulté du jeu.
	 * @throws RemoteException
	 */
	void commencerJeu(IUtilisateurDistant utilisateur, String difficulte) throws RemoteException;
	
	/**
	 * Permet à un utilisateur de se déconnecter
	 * @throws RemoteException
	 */
	void deconnecter() throws RemoteException;
	
	/**
	 * Permet qu'un utilisateur du client rejoigne le jeu.
	 * @param utilisateur l'utilisateur qu'on défie
	 * @param jeu le jeu.
	 * @return true si l'utilisateur a été notifié correctement
	 * @throws RemoteException
	 */
	boolean notifierJeu(String nom) throws RemoteException;
	
	/**
	 * Getter
	 * @return nom de l'utilisateur distant
	 * @throws RemoteException
	 */
	String getNom() throws RemoteException;
	
	/**
	 * Setter. Permet d'associer un utilsateur local à un utilisateur distant.
	 * @param utilisateur l'utilisateur local (celui qui est dans le client).
	 * @throws RemoteException
	 */
	void setUtilisateurLocal(String nom, IUtilisateur utilisateurLocal) throws RemoteException;
	
	/**
	 * Permet qu'un utilisateur se connecte.
	 * @param estNouveau true si l'utilisateur est un nouvel utilisateur.
	 * @throws RemoteException
	 */
	void connecter(boolean estNouveau) throws RemoteException;
	
	/**
	 * Permet de savoir si un utilisateur est connceté.
	 * @return true si l'utilisateur est connecté.
	 * @throws RemoteException
	 */
	boolean estConnecte() throws RemoteException;
	
	/**
	 * Permet de finir un jeu.
	 * @param adversaire nom de l'adversaire.
	 * @throws RemoteException
	 */
	void finirJeu(String adversaire) throws RemoteException;
	
	/**
	 * Ajoute une victoire à l'utilisateur dans les statistiques de jeu contre l'adversaire passé en paramètre.
	 * @param adversaire nom de l'adversaire
	 * @throws RemoteException
	 */
	void ajouterVictoire(String adversaire) throws RemoteException;
	
	/**
	 * Ajoute une défaite à l'utilisateur dans les statistiques de jeu contre l'adversaire passé en paramètre. 
	 * @param adversaire nom de l'adversaire
	 * @throws RemoteException
	 */
	void ajouterDefaite(String adversaire) throws RemoteException;
	
	/**
	 * Getter
	 * @param adversaire nom de l'adversaire
	 * @return nombre de victoires contre l'adverdaire passé en paramètre
	 * @throws RemoteException
	 */
	int getVictoires(String adversaire) throws RemoteException;
	
	/**
	 * Getter
	 * @param adversaire nom de l'adversaire.
	 * @return nombre de défaites contre l'adverdaire passé en paramètre
	 * @throws RemoteException
	 */
	int getDefaites(String adversaire) throws RemoteException;
	
	/**
	 * Ajoute une entrée(une nouvelle ligne de données) aux statistiques 
	 * @throws RemoteException
	 * @param entree Entrée à ajouter.
	 */
	void ajouterEntree(IEntree entree) throws RemoteException;
	
	/**
	 * Getter
	 * @return Id de la base de données
	 * @throws RemoteException
	 */
	int getBddId() throws RemoteException;
	
	/**
	 * Getter
	 * @return utilisateur local associé
	 * @throws RemoteException
	 */
	IUtilisateur getUtilisateurLocal() throws RemoteException;
	
	/**
	 * Permet d'actualiser les utilisateurs connectés
	 * @param utilisateur utilisateur qui vient de se connecter.
	 * @param estNouveau True si l'utilisateur est un nouveau utilisateur.
	 * @throws RemoteException
	 */
	void informerConnection(IUtilisateurDistant utilisateur, boolean estNouveau) throws RemoteException;
	String getRemoteHost() throws RemoteException;
	int getRemotePort() throws RemoteException;
}
