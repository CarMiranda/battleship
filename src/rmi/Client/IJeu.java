package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import utilities.Coordonnees;
/**
 * Cette interface représente le Jeu.
 * @author Carlos MIRANDA, Cyril ANTOUN
 *
 */
public interface IJeu extends Remote {
	
	/**
	 * Permet de commencer un jeu.
	 * @throws RemoteException
	 */
	void jouer() throws RemoteException;
	
	/**
	 * Affichage de la fenêtre de jeu.
	 * @throws RemoteException
	 */
	void afficher() throws RemoteException;
	
	/**
	 * Permet de commencer à placer une flotte de bataux.
	 * @throws RemoteException
	 */
	void commencerPlacementFlotte() throws RemoteException;
	
	/**
	 * Permet de finir le placement d'une flotte.
	 * @throws RemoteException
	 */
	void finirPlacementFlotte() throws RemoteException;
	
	/**
	 * Cette méthode permet de placer un bateau aux coordonnées indiquées.
	 * 
	 * @param taille int taille de bateau à placer.
	 * @param nomBateau String nom du bateau à placer.
	 * @throws RemoteException
	 */
	void placerBateau(int taille, String nomBateau) throws RemoteException;
	
	/**
	 * Informe l'utilisateur que l'adversaire place sa flotte.
	 * @throws RemoteException
	 */
	void informerPlacementFlotte() throws RemoteException;
	
	/**
	 * Informe que le jeu commence et qui doit jouer en premier.
	 * @param nomJoueur Joueur qui joue en premier
	 * @throws RemoteException
	 */
	void informerDebutJeu(String nomJoueur) throws RemoteException;
	
	/**
	 * Informe que c'est au joueur de jouer.
	 * @throws RemoteException
	 */
	void informerTour() throws RemoteException;
	
	/**
	 * Informe de la victoire du joueur.
	 * @param parForfait
	 * @throws RemoteException
	 */
	void informerVictoire(boolean parForfait) throws RemoteException;
	
	/**
	 * Informe de la defaite du joueur.
	 * @param parForfait 
	 * @throws RemoteException
	 */
	void informerDefaite(boolean parForfait) throws RemoteException;
	
	/**
	 * Permet de notifier au joueur que le carreau de la carte aux coordonnées passées en paramètre a été attaqué.
	 * @param coordonneesAttaquees
	 * @throws RemoteException
	 */
	void informerAttaque(Coordonnees coordonneesAttaquees) throws RemoteException;
	
	/*/**
	 * Permet d'informer le nombre de bateaux qui restent dans une flotte. 
	 * @throws RemoteException
	 */
	//void informerNbrBateaux() throws RemoteException;
}
