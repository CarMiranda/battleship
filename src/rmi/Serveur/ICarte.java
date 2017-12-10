package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Iterator;

import modele.Difficulte;

public interface ICarte extends Remote {

	public ICarreauCarte getCarreauCarte(int x, int y) throws RemoteException;
	Difficulte getDifficulte() throws RemoteException;
	Iterator<ICarreauCarte> iterator() throws RemoteException;
	
}
