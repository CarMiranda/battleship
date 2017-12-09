package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bateau extends UnicastRemoteObject implements IBateau {

	private static final long serialVersionUID = 8080164946147281260L;
	private final int taille;
	private final String nom;
	private boolean estCoule;
	private List<IPartieBateau> bateau;

	public Bateau(TypesBateau type) 
			throws RemoteException {
		taille = type.getTaille();
		nom = type.getNom();
		estCoule = false;
		bateau = new ArrayList<IPartieBateau>(this.taille);
	};
	
	@Override
	public void placer(List<ICarreauCarte> lcc) throws CarreauUtiliseException, RemoteException {
		if (!CarreauCarte.aligne(lcc)) throw new IllegalArgumentException();
		for (ICarreauCarte cc: lcc) {
			if (cc.contientBateau()) throw new CarreauUtiliseException();
		}
		for (ICarreauCarte cc: lcc) {
			this.bateau.add(new PartieBateau(cc));
		}
	}
	
	@Override
	public boolean estCoule() 
			throws RemoteException {
		if (this.estCoule) return true;
		for (IPartieBateau pb : bateau) {
			if (!pb.estTouchee()) return false;
		}
		this.estCoule = true;
		return true;
	}
	
	@Override
	public int getTaille() throws RemoteException { return this.taille; }
	
	@Override
	public List<IPartieBateau> getBateau() throws RemoteException { return this.bateau; }
	
	@Override
	public String getNom() throws RemoteException { return this.nom; }
	
	@Override
	public Iterator<IPartieBateau> iterator() { return this.bateau.iterator(); }
}