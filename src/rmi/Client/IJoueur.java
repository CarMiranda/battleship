package rmi.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


import rmi.Serveur.IBateau;
import rmi.Serveur.ICarreauCarte;
import rmi.Serveur.ICarte;
import rmi.Serveur.IFlotte;

public interface IJoueur extends Remote {
	
	boolean attaquer(ICarreauCarte cc) throws RemoteException;
	void placerBateau(IBateau b, List<ICarreauCarte> lcc) throws CarreauUtiliseException, RemoteException, rmi.Serveur.CarreauUtiliseException;
	IFlotte getFlotte() throws RemoteException;
	ICarte getCarte() throws RemoteException;
	String getNom() throws RemoteException;
	void placerFlotte() throws RemoteException;
	ICarreauCarte getAttaque() throws RemoteException;
	boolean forfait() throws RemoteException;
	void aGagne(boolean parForfait) throws RemoteException;
	void aPerdu(boolean parForfait) throws RemoteException;

}
