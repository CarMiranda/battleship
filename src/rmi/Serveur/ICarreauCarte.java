package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICarreauCarte extends Remote {

	boolean attaquer() throws RemoteException;
	boolean contientBateau() throws CarreauUtiliseException, RemoteException;
	ICoordonnees getCoordonnees() throws RemoteException;
	void lierPartieBateau(PartieBateau partieBateau) throws RemoteException, CarreauUtiliseException;
	boolean aligne(List<ICarreauCarte> lcc) throws RemoteException;
}
