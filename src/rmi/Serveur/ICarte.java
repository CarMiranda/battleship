package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

import modele.Difficulte;

public interface ICarte extends Iterable<ICarreauCarte>, Remote {

	public ICarreauCarte getCarreauCarte(int x, int y) throws RemoteException;
	Difficulte getDifficulte() throws RemoteException;
	
}
