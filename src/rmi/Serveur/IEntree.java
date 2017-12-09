package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IEntree extends Remote {

	String getNom() throws RemoteException;
	int getDefaites() throws RemoteException;
	int getVictoires() throws RemoteException;
	void ajouterDefaite() throws RemoteException;
	void ajouterVictoire() throws RemoteException;
	
}
