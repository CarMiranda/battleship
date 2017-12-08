package modele;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * @author Carlos MIRANDA
 */

import java.util.List;

public interface IJoueur extends Remote {
	
	boolean attaquer(CarreauCarte cc) throws RemoteException;
	void placerBateau(Bateau b, List<CarreauCarte> lcc) throws CarreauUtiliseException, RemoteException;
	Flotte getFlotte() throws RemoteException;
	Carte getCarte() throws RemoteException;
	String getNom();

}
