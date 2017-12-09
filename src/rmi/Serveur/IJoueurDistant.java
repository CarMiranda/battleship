package rmi.Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import rmi.Client.IJoueur;

public interface IJoueurDistant extends Remote {

	boolean attaquer(ICarreauCarte cc) throws RemoteException;
	void placerBateau(IBateau b, List<ICarreauCarte> lcc) throws CarreauUtiliseException, RemoteException, rmi.Client.CarreauUtiliseException;
	IFlotte getFlotte() throws RemoteException;
	ICarte getCarte() throws RemoteException;
	String getNom() throws RemoteException;
	void placerFlotte() throws RemoteException;
	ICarreauCarte getAttaque() throws RemoteException;
	boolean forfait() throws RemoteException;
	void aGagne(boolean parForfait) throws RemoteException;
	void aPerdu(boolean parForfait) throws RemoteException;
	void setJoueurLocal(IJoueur joueurLocal) throws RemoteException;
	IJoueur getJoueurLocal() throws RemoteException;
	
}
