package rmi.Serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class CarreauCarte extends UnicastRemoteObject implements ICarreauCarte {
	
	private static final long serialVersionUID = -7419420286469375210L;
	private final ICoordonnees c;
	private IPartieBateau pb;
	
	public CarreauCarte(int x, int y)
			throws RemoteException {
		pb = null;
		c = new Coordonnees(x, y);
		c.setCarreauCarte(this);
	}
	
	public CarreauCarte(ICoordonnees c)
			throws RemoteException {
		this.c = c;
		this.c.setCarreauCarte(this);
	}
	
	/**
	 * Attaque ce CarreauCarte, i.e. attaque la partie de bateau associée ou
	 * marque la case comme touchée.
	 * @return si on a touché un bateau
	 */
	@Override
	public boolean attaquer() throws RemoteException {
		if (pb.estTouchee()) throw new IllegalArgumentException();
		if (pb == null) {
			return false;
		} else {
			pb.attaquer();
			return true;
		}
	}
	
	/**
	 * Vérifie que la liste de CarreauCarte corresponde à une séquence de CarreauCartes
	 * alignés (cf. Coordonnees.aligne)
	 * @param lsq	Liste de CarreauCarte
	 * @return si les CarreauCarte sont alignés ou pas
	 */
	public final boolean aligne(List<ICarreauCarte> lcc) {
		List<ICoordonnees> lc = new ArrayList<ICoordonnees>(lcc.size());
		for (ICarreauCarte cc : lcc) {
			try {
				lc.add(cc.getCoordonnees());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return Coordonnees.aligne(lc);
	}

	@Override
	public void lierPartieBateau(PartieBateau partieBateau) throws RemoteException, CarreauUtiliseException {
		if (this.pb == null) {
			this.pb = partieBateau;
		} else {
			throw new CarreauUtiliseException();
		}
	}
	
	@Override
	public ICoordonnees getCoordonnees() throws RemoteException { return c; }
	
	@Override
	public boolean contientBateau() { return this.pb != null; }

	@Override
	public boolean equals(Object o) {
		try {
			if (o instanceof ICarreauCarte) return c.equals(((ICarreauCarte) o).getCoordonnees());
			if (o instanceof ICoordonnees) return c.equals((ICoordonnees) o);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
}
