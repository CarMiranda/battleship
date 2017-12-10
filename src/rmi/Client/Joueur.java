package rmi.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import rmi.Serveur.IBateau;
import rmi.Serveur.ICarreauCarte;
import rmi.Serveur.ICarte;
import rmi.Serveur.IFlotte;
import rmi.Serveur.IJoueurDistant;

public class Joueur extends UnicastRemoteObject implements IJoueur {

	private static final long serialVersionUID = -8393983641830143492L;
	private final IJoueurDistant joueurDistant;
	
	public Joueur(IJoueurDistant joueurDistant) throws RemoteException {
		this.joueurDistant = joueurDistant;
		this.joueurDistant.setJoueurLocal(this);
	}
	
	public boolean attaquer(ICarreauCarte cc) throws RemoteException {
		return joueurDistant.attaquer(cc);
	}
	
	public void placerBateau(IBateau bateau, List<ICarreauCarte> lcc) throws CarreauUtiliseException, RemoteException, rmi.Serveur.CarreauUtiliseException {
		joueurDistant.placerBateau(bateau, lcc);
	}
	
	@Override
	public IFlotte getFlotte() throws RemoteException { return joueurDistant.getFlotte(); }

	@Override
	public ICarte getCarte() throws RemoteException { return joueurDistant.getCarte(); }

	@Override
	public String getNom() throws RemoteException { return joueurDistant.getNom(); }

	@Override
	public void placerFlotte() throws RemoteException {}

	@Override
	public ICarreauCarte getAttaque() throws RemoteException {
		return null;
	}

	@Override
	public boolean forfait() throws RemoteException {
		joueurDistant.forfait();
		return false;
	}

	@Override
	public void aGagne(boolean parForfait) throws RemoteException {		
	}

	@Override
	public void aPerdu(boolean parForfait) throws RemoteException {		
	}

}

