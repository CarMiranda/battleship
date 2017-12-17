package rmi.Serveur;

import java.util.ArrayList;
import java.util.List;

public class CarreauCarte {
	
	private final Coordonnees c;
	private PartieBateau pb;
	
	public CarreauCarte(int x, int y) {
		pb = null;
		c = new Coordonnees(x, y);
		c.setCarreauCarte(this);
	}
	
	public CarreauCarte(Coordonnees c) {
		this.c = c;
		this.c.setCarreauCarte(this);
	}
	
	/**
	 * Attaque ce CarreauCarte, i.e. attaque la partie de bateau associée ou
	 * marque la case comme touchée.
	 * @return si on a touché un bateau
	 */
	public boolean attaquer() {
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
	public final boolean aligne(List<CarreauCarte> lcc) {
		List<Coordonnees> lc = new ArrayList<Coordonnees>(lcc.size());
		for (CarreauCarte cc : lcc) {
			lc.add(cc.getCoordonnees());
		}
		return Coordonnees.aligne(lc);
	}

	public void lierPartieBateau(PartieBateau partieBateau) throws CarreauUtiliseException {
		if (this.pb == null) {
			this.pb = partieBateau;
		} else {
			throw new CarreauUtiliseException();
		}
	}
	
	public Coordonnees getCoordonnees() { return c; }
	
	public boolean contientBateau() { return this.pb != null; }

	public boolean equals(Object o) {
		if (o instanceof CarreauCarte) return c.equals(((CarreauCarte) o).getCoordonnees());
		if (o instanceof Coordonnees) return c.equals((Coordonnees) o);
		return false;
	}
}
