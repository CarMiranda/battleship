package rmi.Serveur;

import java.rmi.RemoteException;

/**
 * @author Carlos MIRANDA
 */
public interface ICoordonnees {
	
	int getX() throws RemoteException;
	int getY() throws RemoteException;
	void setCarreauCarte(ICarreauCarte cc) throws RemoteException;
	
}
