package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modele.Difficulte;

public class Carte extends UnicastRemoteObject implements ICarte {
	
	private static final long serialVersionUID = 2753013296381801190L;
	private final List<ICarreauCarte> carte;
	private final Difficulte difficulte;
	
	public Carte(Difficulte difficulte) throws RemoteException {
		this.difficulte = difficulte;
		carte = new ArrayList<>(difficulte.HAUTEUR * difficulte.LARGEUR);
		int i, j;
		for (i = 0; i < difficulte.HAUTEUR; i++) {
			for (j = 0; j < difficulte.LARGEUR; j++) {
				carte.add(new CarreauCarte(i, j));
			}
		}
	}
	
	@Override
	public ICarreauCarte getCarreauCarte(int x, int y) throws RemoteException {
		return carte.get(carte.indexOf(new Coordonnees(x, y)));
	}
	
	@Override
	public Difficulte getDifficulte() throws RemoteException { return difficulte; }

	@Override
	public Iterator<ICarreauCarte> iterator() throws RemoteException {
		return carte.iterator();
	}
}
