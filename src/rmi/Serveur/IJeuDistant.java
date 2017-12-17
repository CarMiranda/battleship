package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import rmi.Client.IJeu;
import rmi.Client.IUtilisateur;

public interface IJeuDistant extends Remote {
	
	void setJeuLocal(IJeu jeu, IUtilisateur utilisateur) throws RemoteException;
	void jouer() throws RemoteException;
	boolean tour(String nom, Coordonnees coordonneesAttaquees) throws RemoteException;
	void placementFlotte(String nom) throws RemoteException;
	void placerBateau(String nomJoueur, String nomBateau, List<Coordonnees> bateau) throws RemoteException;
	void forfait(String nom) throws RemoteException;
	String getNomAdversaire(String nom) throws RemoteException;
	int getNbBateaux(String nom, boolean coules) throws RemoteException;
	int getNbBateaux(String nom, String nomBateau, boolean coules) throws RemoteException;
	int getNbBateauxAdversaire(String nom, boolean coules) throws RemoteException;
	int getNbBateauxAdversaire(String nom, String nomBateau, boolean coules) throws RemoteException;
	int getHauteurCarte() throws RemoteException;
	int getLargeurCarte() throws RemoteException;
	void informerPret() throws RemoteException;

}
