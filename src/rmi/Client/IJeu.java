package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IJeu extends Remote {
	
	// Méthodes à distance
	IJoueur getJoueur() throws RemoteException;
	IJoueur getAdversaire() throws RemoteException;
	
}
