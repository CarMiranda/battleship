package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import rmi.Client.IJeu;
import rmi.Client.IUtilisateur;
/**
 * Cette interface représente un jeu dans le serveur RMI.
 * @author Carlos MIRANDA
 *
 */
public interface IJeuDistant extends Remote {
	/**
	 * Permet d'associer le jeu distants aux jeux locaux des clients.
	 * @param jeu le jeu.
	 * @param utilisateur utilisateur qui veut enregistrer son jeu.
	 * @throws RemoteException
	 */
	void setJeuLocal(IJeu jeu, IUtilisateur utilisateur) throws RemoteException;
	
	/**
	 * Commence un jeu, i.e. instruit au joueur1 de placer sa flotte et informe le joueur2.
	 * @throws RemoteException
	 */
	void jouer() throws RemoteException;
	
	/**
	 * Methode appelée par le jeu local pour informer d'une attaque effectuée, informer l'autre joueur de l'attaque et 
	 * informer cet autre joueur que c'est son tour.
	 * @param nom du joueur qui doit jouer.
	 * @param coordonneesAttaquees coordonnees a attaquer.
	 * @return si l'attaque a réussi (on a touché un batteau ennemi).
	 * @throws RemoteException
	 */
	boolean tour(String nom, Coordonnees coordonneesAttaquees) throws RemoteException;
	
	/**
	 * Méthode appelée par le jeu local pour savoir quel est le bateau suivant à placer
	 * @param nom du joueur qui doit jouer.
	 * @throws RemoteException
	 */
	void placementFlotte(String nom) throws RemoteException;
	
	/**
	 * Place le bateau dans la carte de jeu.
	 * @param nomJoueur nom du joueur courant.
	 * @param nomBateau nom du bateau à placer.
	 * @param bateau liste des coordonnées où l'on veut placer le bateau
	 * @throws RemoteException
	 */
	void placerBateau(String nomJoueur, String nomBateau, List<Coordonnees> bateau) throws RemoteException;
	
	/**
	 * Informe les clients soit d'une victoire par forait, soit d'une défaite par forfait.
	 * @param nom nom du joueur courant.
	 * @throws RemoteException
	 */
	void forfait(String nom) throws RemoteException;
	
	/**
	 * Getter
	 * @param nom nom du joueur.
	 * @return nom de l'adversaire.
	 * @throws RemoteException
	 */
	String getNomAdversaire(String nom) throws RemoteException;
	
	/**
	 * Getter
	 * @param nom nom du joueur.
	 * @param coules true si on veut savoir le nombre de bateauw coulés.
	 * @return nombre de bateaux coulés ou non du joueur.
	 * @throws RemoteException
	 */
	int getNbBateaux(String nom, boolean coules) throws RemoteException;
	
	/**
	 * Getter
	 * @param nom nom du joueur.
	 * @param nomBateau nom du type de bateau.
	 * @param coules true si on veut savoir le nombre de bateauw coulés.
	 * @return nombre de bateaux coulés ou non pour un type donné pour le joueur donné.
	 * @throws RemoteException
	 */
	int getNbBateaux(String nom, String nomBateau, boolean coules) throws RemoteException;
	
	/**
	 * Getter 
	 * @param nom nom du jouer.
	 * @param coules true si on veut savoir le nombre de bateaux coulés.
	 * @return nombre de bateaux ennemis coulés ou non pour le joueur donné.
	 * @throws RemoteException
	 */
	int getNbBateauxAdversaire(String nom, boolean coules) throws RemoteException;
	
	/**
	 * Getter
	 * @param nom nom du joueur.
	 * @param nomBateau nom du type de bateau.
	 * @param coules coules true si on veut savoir le nombre de bateauw coulés
	 * @return nombre de bateaux ennemis coulés ou non pour un type donné pour le joueur donné.
	 * @throws RemoteException
	 */
	int getNbBateauxAdversaire(String nom, String nomBateau, boolean coules) throws RemoteException;
	
	/**
	 * Getter
	 * @return hauteur de la carte.
	 * @throws RemoteException
	 */
	int getHauteurCarte() throws RemoteException;
	
	/**
	 * Getter
	 * @return largeur de la carte.
	 * @throws RemoteException
	 */
	int getLargeurCarte() throws RemoteException;
	
	/**
	 * Informe le serveur qu'un de deux joueurs est prêt et démarre le jeu lorsque le deux joueurs sont prêts.
	 * @throws RemoteException
	 */
	void informerPret() throws RemoteException;

}
