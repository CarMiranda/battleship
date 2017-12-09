package rmi.Serveur;

import java.rmi.RemoteException;

public interface ICarreauCarte {

	boolean attaquer() throws RemoteException;
	boolean contientBateau() throws CarreauUtiliseException, RemoteException;
	ICoordonnees getCoordonnees() throws RemoteException;
	void lierPartieBateau(PartieBateau partieBateau) throws RemoteException, CarreauUtiliseException;
	
}
