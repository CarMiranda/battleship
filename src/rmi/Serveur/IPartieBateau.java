package rmi.Serveur;

import java.rmi.RemoteException;

public interface IPartieBateau {

	boolean estTouchee() throws RemoteException;
	ICarreauCarte getCarreauCarte() throws RemoteException;
	void attaquer() throws RemoteException;
	
}
