package modele;

/**
 * Cette classe représente un carreau de la carte du jeu 
 * (coordonnées et potentiellement une partie de bateau).
 * 
 * @author Carlos MIRANDA
 */
// 04-12-17 - Carlos - Refactoring

import java.util.ArrayList;
import java.util.List;

public class CarreauCarte implements ICarreauCarte {
	
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
	public static final boolean aligne(List<CarreauCarte> lcc) {
		List<Coordonnees> lc = new ArrayList<Coordonnees>(lcc.size());
		for (CarreauCarte cc : lcc) {
			lc.add(cc.getCoordonnees());
		}
		return Coordonnees.aligne(lc);
	}
	
	public void lierPartieBateau(PartieBateau pb) throws CarreauUtiliseException {
		if (this.pb == null) {
			this.pb = pb;
		} else {
			throw new CarreauUtiliseException();
		}
	}
	
	public Coordonnees getCoordonnees() { return c; }
	
	public boolean contientBateau() { return this.pb != null; }

	@Override
	public boolean equals(Object o) {
		if (o instanceof CarreauCarte) return c.equals(((CarreauCarte) o).getCoordonnees());
		if (o instanceof Coordonnees) return c.equals((Coordonnees) o);
		return false;
	}
}
