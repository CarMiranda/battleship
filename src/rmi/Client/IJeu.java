package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IJeu extends Remote {
	
	IJoueur getJoueur() throws RemoteException;
	IJoueur getAdversaire() throws RemoteException;
	void jouer() throws RemoteException;
	void afficher() throws RemoteException;
	void placerFlotte() throws RemoteException;
	
}
