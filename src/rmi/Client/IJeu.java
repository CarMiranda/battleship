package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.Serveur.Coordonnees;

public interface IJeu extends Remote {
	
	void jouer() throws RemoteException;
	void afficher() throws RemoteException;
	void commencerPlacementFlotte() throws RemoteException;
	void finirPlacementFlotte() throws RemoteException;
	void placerBateau(int taille, String nomBateau) throws RemoteException;
	void informerPlacementFlotte() throws RemoteException;
	void informerDebutJeu(String nomJoueur) throws RemoteException;
	void informerTour() throws RemoteException;
	void informerVictoire(boolean parForfait) throws RemoteException;
	void informerDefaite(boolean parForfait) throws RemoteException;
	void informerAttaque(Coordonnees coordonneesAttaquees) throws RemoteException;
	
}
