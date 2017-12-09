package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import modele.Difficulte;

import rmi.Client.CarreauUtiliseException;
import rmi.Client.IJoueur;

public class JoueurDistant extends UnicastRemoteObject implements IJoueurDistant {

	private static final long serialVersionUID = 7877826252900082790L;
	private final String nom;
	private final ICarte carte;
	private final IFlotte flotte;
	private IJoueur joueurLocal;

	public JoueurDistant(String nom, Difficulte difficulte) throws RemoteException {
		this.nom = nom;
		carte = new Carte(difficulte);
		flotte = new Flotte();
	}
	
	public void setJoueurLocal(IJoueur joueurLocal) throws RemoteException {
		this.joueurLocal = joueurLocal;
	}
	
	@Override
	public IJoueur getJoueurLocal() { return joueurLocal; }

	@Override
	public boolean attaquer(ICarreauCarte cc) throws RemoteException {
		return cc.attaquer();
	}

	@Override
	public void placerBateau(IBateau bateau, List<ICarreauCarte> lcc) throws CarreauUtiliseException, RemoteException, rmi.Serveur.CarreauUtiliseException {
		if (bateau.getTaille() != lcc.size()) throw new IllegalArgumentException();
		bateau.placer(lcc);
	}

	@Override
	public IFlotte getFlotte() throws RemoteException { return flotte; }

	@Override
	public ICarte getCarte() throws RemoteException { return carte; }

	@Override
	public String getNom() throws RemoteException { return nom; }

	@Override
	public void placerFlotte() throws RemoteException {
		joueurLocal.placerFlotte();
	}

	@Override
	public ICarreauCarte getAttaque() throws RemoteException {
		return joueurLocal.getAttaque();
	}

	@Override
	public boolean forfait() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void aGagne(boolean parForfait) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aPerdu(boolean parForfait) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
